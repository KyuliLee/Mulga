package com.ilm.mulga.domain.usecase

import android.content.Context
import android.util.Log
import com.ilm.mulga.data.dto.request.NotificationMessageDto
import com.ilm.mulga.data.network.NetworkUtil
import com.ilm.mulga.data.repository.UserRepository
import com.ilm.mulga.data.service.RabbitMqPublisher
import com.ilm.mulga.domain.repository.local.NotificationLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class NotificationPublisherUseCase(
    private val repository: NotificationLocalRepository,
    private val publisher: RabbitMqPublisher,
    private val context: Context,
    private val userRepository: UserRepository
) {
    suspend fun connect(): Boolean = withContext(Dispatchers.IO) {
        val result = publisher.connect()
        Log.d("📡RabbitMQ", "Connection result: $result")
        result
    }

    suspend fun publishAllNotifications(queueName: String = "q.mulga") {
        withContext(Dispatchers.IO) {
            if (!NetworkUtil.isNetworkAvailable(context)) {
                Log.w("📡RabbitMQ", "❌ 네트워크 연결 안 됨 → 전송 보류")
                return@withContext
            }

            while (true) {
                val notifications = repository.getNotificationsBatch(50)
                if (notifications.isEmpty()) break

                for ((index, notification) in notifications.withIndex()) {
                    val dto = NotificationMessageDto(
                        userId = userRepository.getUserData()?.id ?: "unknown",
                        appName = notification.appName.toString(),
                        title = notification.title ?: "",
                        content = notification.content ?: "",
                        timestamp = notification.timestamp
                    )
                    val json = Json.encodeToString(dto)

                    var success = false
                    var attempt = 0

                    while (attempt < 3) {
                        success = publisher.publish(queueName, json)
                        if (success) break
                        attempt++
                        repository.increaseRetryCount(notification.id)
                        delay(300L)
                    }

                    if (success) {
                        repository.deleteNotification(notification.id)
                        Log.d("📡RabbitMQ", "[$index] ✅ 전송 성공 → 삭제 완료")
                    } else {
                        repository.deleteNotification(notification.id)
                        Log.w("📡RabbitMQ", "[$index] ❌ 최대 재시도 실패 → 삭제 처리")
                    }
                }
            }
        }
    }
}