# 은행 서버 프로젝트 실습을 통해 배우는 코틀린 마스터 클래스

위 프로젝트를 학습한 리포지토리입니다.

https://www.inflearn.com/course/%EC%9D%80%ED%96%89%EC%84%9C%EB%B2%84-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%EC%8B%A4%EC%8A%B5-%EC%BD%94%ED%8B%80%EB%A6%B0-%EB%A7%88%EC%8A%A4%ED%84%B0%ED%81%B4%EB%9E%98%EC%8A%A4/dashboard


1. 여러 의존성 생성할 경우
```kotlin
@Service
class AuthService(
    private val oAuth2Services: Map<String, OAuthServiceInterface>
) 
```

위와 같이 OAuthServiceInterface 를 상속하고 있는 클래스들을 Map 형태로 주입받아 사용할 수 있습니다.