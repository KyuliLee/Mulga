# 250310 월


### 완료한 일
- 팀원들과 논의 후에 역할을 분담
    - 인프라 : 이규리, 윤태한
        - EC2 서버를 받으면 바로 ci/cd 할 수 있도록 미리 준비
    - 백 : 김본, 정연희
    - 프론트 : 윤혜진, 황정현
        - 안드로이드 스튜디오 사용 확정 


### 진행 중인 일
- CI/CD 방법 찾기



## CI / CD


### CI. Continuous Integration

- 성공적인 CI : 개발자가 어플리케이션에 적용한 변경 사항 병합, 자동으로 병합한 버전으로 빌드, 테스트(단위 테스트, 통합 테스트) 자동화로 변경 사항 검증


### CD. Continuous Deployment

- 성공적인 CD : CI를 통해 검증한 코드를 서버에 릴리즈하는 것


### 대표적인 툴

- Jenkins
    - 장점
        - 광범위한 플러그인 생태계
        - 강력한 커뮤니티
        - 복잡한 워크플로우에 적합
    - 단점
        - 초기 설정 복잡
        - UI가 구식
        - 유지 보수, 업데이트 필요
- Gitlab CI/CD
    - 장점
        - gitlab과 연동이 쉬움
        - 자동화된 CI/CD 구성 제공
        - Docker 이미지 관리 쉬움
    - 단점
        - 대규모 프로젝트에서 복잡성 증가
        - 무료 버전 한계 있음
        - github를 더 많이 씀. gitlab에 종속됨


### 도커

- 매번 같은 환경에서 어플리케이션을 빌드하므로 오류날 확률이 낮음


### 안드로이드 앱 ci/cd

안드로이드 스튜디오

코드 푸시 → 젠킨스가 도커파일 실행 → apk 파일 생성 → s3로 전송

앱에 업데이트 버튼 만듦 → 클릭 → 최신 apk 파일이 있는 s3 주소로 이동해서 다운로드(로직 구현 필요) → 업데이트됨

- CI
    - main에 push 하면 도커 이미지 빌드, 테스트 실행,
        - 레파지토리의 루트 디렉토리에 Dockerfile 만들어놓음
        - https://insight.infograb.net/docs/user/quick_start_ci_cd/
        - https://f-lab.kr/insight/docker-ci-cd-pipeline

- CD 웹 일반적으로
    - CI가 성공하면 도커 허브에 이미지 푸시, 배포할 때 이미지로 컨테이너 생성 및 실행
    - 웹 개발의 경우 서버에 자동으로 배포하는 것이지만 우리는 앱이기 때문에 apk를 만드는 것까지일 것 같다.(구글 플레이스토어에 배포도 안 하니까)

- CD 안드로이드 앱
    - CI의 결과로 만들어진 apk 파일을 s3에 전송
        - CI/CD 스크립트에서 AWS CLI 설치, `aws s3 cp` 명령어를 사용하여 파일을 S3 버킷으로 업로드
        - FTP는 안 됨. S3는 FTP 지원 X, 기본적으로 http, https를 통해 API 호출로 파일 업로드
            
            `build/outputs/apk/` 폴더 아래에 저장됩니다. 예를 들어:
            
            - **디버그 빌드**: `app/build/outputs/apk/debug/`
            - **릴리즈 빌드**: `app/build/outputs/apk/release/`


### Spring Boot ci/cd

코드 푸시 → 젠킨스가 도커파일 실행 → 도커 이미지 빌드, 컨테이너 실행

