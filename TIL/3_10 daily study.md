# 3_10 daily study



- react native 사용 시 배포에서 어려움이 많고 핵심 서비스에 필요한 NotificationListenerService를 직접 사용하는 것이 아닌 안드로이드 스튜디오에서 불러오는 것이라 다른 프레임워크를 사용하기로 함

- flutter도 react native와 마찬가지로 NotificationListenerService를 직접 사용하는 것이 아니다. 배포에 어려움은 없지만 ios 서비스를 제공할 것이 아니라서 프레임워크를 사용할 근거가 부족하여 최종적으로 android studio meerkat을 사용하기로 결정했다.

- sdk(api 버전)은 구글 플레이스토어에서 "신규 앱은 Android 14(API 수준 34) 이상을 타겟팅해야 합니다"라고 명시되어 있어 34를 사용하기로 하였다.
