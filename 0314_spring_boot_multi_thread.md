# Spring Boot 백엔드 서버의 멀티스레드 동작 방식

## 1. 개요
Spring Boot는 기본적으로 멀티스레드 환경에서 동작하는 웹 서버를 제공한다. 본 보고서에서는 Spring Boot 백엔드 서버가 요청을 처리하는 방식과 멀티스레드 환경에서의 동작 원리를 분석하고, 이를 제어하는 방법을 설명한다.

## 2. Spring Boot의 기본 멀티스레드 동작
### 2.1 요청 처리 방식
Spring Boot는 기본적으로 **Apache Tomcat**을 내장 웹 서버로 사용하며, 이는 **멀티스레드 기반의 요청 처리 모델**을 사용한다. 즉, HTTP 요청이 들어오면 톰캣의 **스레드 풀**에서 하나의 스레드를 할당하여 요청을 처리하고, 처리가 완료되면 해당 스레드는 다시 스레드 풀로 반환된다.

### 2.2 요청 처리 예제
다음은 Spring Boot의 컨트롤러에서 요청을 처리하는 코드이다.

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    public String test(@RequestParam String name) {
        System.out.println("요청 처리 중: " + name + " - " + Thread.currentThread().getName());
        return "Hello, " + name;
    }
}
```

### 2.3 동시 요청 처리 결과
만약 아래와 같이 여러 개의 요청을 동시에 보낸다면:
```bash
curl "http://localhost:8080/test?name=Alice" &
curl "http://localhost:8080/test?name=Bob" &
curl "http://localhost:8080/test?name=Charlie" &
```
서버 콘솔에는 다음과 같은 출력이 나타난다.
```
요청 처리 중: Alice - http-nio-8080-exec-1
요청 처리 중: Bob - http-nio-8080-exec-2
요청 처리 중: Charlie - http-nio-8080-exec-3
```
각 요청이 **서버 스레드 풀에서 서로 다른 스레드에서 실행됨**을 확인할 수 있다.

## 3. Spring Boot의 멀티스레드 설정
### 3.1 기본 스레드 풀
Spring Boot에서 기본적으로 제공하는 내장 톰캣은 **스레드 풀(Thread Pool) 모델**을 사용하며, 기본 설정은 다음과 같다.

- **최대 스레드 수 (max-threads):** 200
- **최소 유지 스레드 수 (min-spare-threads):** 10

이 설정을 변경하려면 `application.properties` 또는 `application.yml`에서 다음과 같이 조정할 수 있다.

#### application.properties 설정 예제
```properties
server.tomcat.threads.max=200  # 최대 200개의 스레드 사용
server.tomcat.threads.min-spare=10  # 최소 10개의 스레드를 유지
```

## 4. 멀티스레드 환경에서의 고려사항
### 4.1 동시성 문제
멀티스레드 환경에서는 여러 요청이 동시에 같은 자원에 접근할 경우 **동시성 문제(Concurrency Issue)**가 발생할 수 있다. 예를 들어, 공유 자원을 수정하는 로직이 있을 경우 **동기화(synchronization)** 처리가 필요하다.

### 4.2 스레드 풀 과부하 방지
스레드 수가 너무 많으면 **CPU 컨텍스트 스위칭 비용이 증가**하여 성능이 저하될 수 있다. 따라서, 트래픽이 높은 환경에서는 적절한 스레드 수를 설정해야 한다.

### 4.3 비동기 처리 활용
멀티스레드의 성능을 더욱 향상시키기 위해 **비동기 작업(@Async)**을 활용할 수도 있다.
```java
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {
    @Async
    public void asyncMethod() {
        System.out.println("비동기 작업 실행 - " + Thread.currentThread().getName());
    }
}
```
위 코드에서 `@Async`를 사용하면 **별도의 스레드에서 실행되므로 비동기 처리가 가능**하다.

## 5. 결론
Spring Boot 백엔드 서버는 **기본적으로 멀티스레드 환경에서 동작**하며, 요청을 효율적으로 처리하기 위해 **톰캣의 스레드 풀을 활용**한다. 하지만, 동시성 문제를 고려하여 적절한 동기화 및 스레드 관리 전략을 적용하는 것이 중요하다.

