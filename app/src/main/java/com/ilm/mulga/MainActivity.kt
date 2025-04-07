package com.ilm.mulga

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ilm.mulga.feature.component.main.MainScreen
import com.ilm.mulga.feature.login.LoginScreen
import com.ilm.mulga.feature.transaction_detail.TransactionAddScreen
import com.ilm.mulga.ui.theme.MulGaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 알림 권한 확인 → 없으면 설정으로 이동
        if (!isNotificationServiceEnabled()) {
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }

        enableEdgeToEdge()

        setContent {
            MulGaTheme {
                var isLoggedIn by remember { mutableStateOf(false) }
                val rootNavController = rememberNavController()

                if (isLoggedIn) {
                    NavHost(navController = rootNavController, startDestination = "main") {
                        composable("main") {
                            MainScreen(
                                onNavigateToTransactionAdd = {
                                    rootNavController.navigate("transaction_add")
                                }
                            )
                        }
                        composable("transaction_add") {
                            TransactionAddScreen(navController = rootNavController)
                        }
                    }
                } else {
                    LoginScreen(onLoginSuccess = { isLoggedIn = true })
                }
            }
        }
    }

    private fun isNotificationServiceEnabled(): Boolean {
        val flat = Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        return flat?.contains(packageName) == true
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MulGaTheme {
        LoginScreen(onLoginSuccess = {})
    }
}
