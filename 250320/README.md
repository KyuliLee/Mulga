# 250320 목


### config 서버를 ci/cd 하기
:white_check_mark:   Step 1. EC2에 컨테이너로 올리려는 config 서버가 로컬에서 실행되는지 확인했다.
:white_large_square: Step 2. DOCKERFILE을 만든다.
:white_large_square: Step 3. jenkins를 gitlab과 연결해서 ci/cd를 완료


### ppt 자료를 만들기
:white_check_mark: 팀원들과 야근을 하며 ppt 자료를 만들었다. 아직 개발을 시작하지 않은 상태여서 나는 발표 때 보여줄 화면을 피그마로 만들었다.


### 알게된 것
**ci/cd** 
- application.properties와 application.yaml이 굉장히 다른 것인 줄 알았는데 설정을 나타내는 텍스트 형식만 다른 것이라는 것을 알게 되었다. yaml은 더 계층적이고 콜론(:) 으로 나타내고, properties는 등호(=)로 나타낸다. 그리고 둘이 섞어서 쓸 수 없는 줄 알았는데 설정 파일이 여러 개가 필요할 때 yaml 파일에서 properties 파일을 참조하는 것도 가능했다.
- EC2 인스턴스에 서버를 올릴 때 최초 1회에는 무조건 docker hub에 이미지를 푸시해서 EC2에서 이미지를 pull 하고 컨테이너를 실행시키는 과정이 필요한 줄 알았다. 하지만 처음부터 jenkins를 이용해서 배포가 가능하다는 것을 알았다.

**발표 자료 준비**
- 피그마는 꽤 어렵다.. 프레임과 그룹 개념을 잘 이용하면 화면을 더 빠르고 예쁘게 만들 수 있을 것 같다. 하지만 오늘 그 개념까지 익혀서 만들기에는 시간이 없었다.