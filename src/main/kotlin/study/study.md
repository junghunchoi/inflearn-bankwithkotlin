# 코틀린 고유 기능 학습 가이드

5년차 Java Spring 개발자를 위한 Kotlin 핵심 기능 학습 자료입니다.

## 📁 파일 구성

### 1. ExtensionFunctions.kt
**확장 함수 (Extension Functions)** 학습
- 기존 클래스에 새로운 함수 추가
- String, Int, List 등에 유용한 확장 함수 작성
- 실무에서 자주 사용하는 확장 함수 패턴

**학습 목표:**
- [ ] 이메일 유효성 검사 확장 함수 구현
- [ ] 문자열 마스킹 함수 작성
- [ ] 컬렉션 처리 확장 함수 구현
- [ ] 타입 안전한 확장 함수 작성

### 2. ScopeFunctions.kt
**스코프 함수 (let, run, with, apply, also)** 학습
- 각 스코프 함수의 특징과 차이점
- 객체 초기화와 변환에서의 활용
- 체이닝을 통한 우아한 코드 작성

**학습 목표:**
- [ ] let: null 체크와 변환 작업
- [ ] run: 객체 컨텍스트에서 작업 수행
- [ ] with: 객체의 여러 멤버에 접근
- [ ] apply: 객체 초기화 (빌더 패턴)
- [ ] also: 부가 작업 수행 (로깅, 검증)

### 3. NullSafety.kt
**널 안전성 (Null Safety)** 완전 정복
- 안전 호출 연산자 (?.)
- 엘비스 연산자 (?:)
- 스마트 캐스팅
- Not-null assertion (!!) 주의사항

**학습 목표:**
- [ ] 복잡한 객체 체인에서 null 안전 처리
- [ ] 엘비스 연산자로 기본값 제공
- [ ] 스마트 캐스팅 활용
- [ ] 안전한 타입 캐스팅 (as?)

### 4. DataAndSealedClasses.kt
**데이터 클래스와 Sealed 클래스** 학습
- data class의 자동 생성 기능 (toString, equals, hashCode, copy)
- sealed class로 타입 안전한 상태 관리
- when 표현식과의 조합으로 exhaustive 처리

**학습 목표:**
- [ ] 데이터 클래스의 구조 분해 선언
- [ ] copy 함수로 불변 객체 패턴
- [ ] sealed class로 API 응답 모델링
- [ ] when 표현식의 컴파일 타임 안전성

### 5. HigherOrderFunctions.kt
**고차 함수와 함수형 프로그래밍** 학습
- 람다 표현식과 고차 함수
- 컬렉션 처리 함수 (map, filter, reduce, groupBy 등)
- 함수 체이닝과 파이프라인 패턴
- 커스텀 고차 함수 작성

**학습 목표:**
- [ ] 복잡한 데이터 변환 체이닝
- [ ] 함수를 매개변수로 받는 유틸리티 작성
- [ ] 함수 합성 (Function Composition)
- [ ] 실무 데이터 처리 파이프라인 구축

### 6. DelegationPatterns.kt
**위임 패턴 (Delegation)** 학습
- 클래스 위임 (by 키워드)
- 프로퍼티 위임 (lazy, observable, vetoable 등)
- 커스텀 위임자 작성
- Map을 활용한 동적 프로퍼티

**학습 목표:**
- [ ] 인터페이스 위임으로 컴포지션 구현
- [ ] 프로퍼티 위임으로 보일러플레이트 제거
- [ ] 커스텀 위임자로 비즈니스 로직 캡슐화
- [ ] 설정 관리에 Map 위임 활용

## 🚀 학습 방법

### 1단계: 코드 읽기 및 이해
- 각 파일의 주석과 TODO 부분을 먼저 읽어보세요
- Java와 비교해서 어떤 점이 다른지 파악하세요

### 2단계: TODO 완성하기
- 주석으로 표시된 TODO 부분을 직접 구현해보세요
- 힌트를 참고하되, 먼저 스스로 생각해보세요
- 각 파일의 main 함수를 실행해서 결과를 확인하세요

### 3단계: 실험 및 확장
- 기본 구현을 완성한 후, 추가적인 기능을 만들어보세요
- 실무에서 마주칠 상황을 가정해서 응용해보세요

### 4단계: Spring Boot와 연동
- 학습한 기능들을 실제 Spring Boot 프로젝트에 적용해보세요
- 특히 확장 함수와 스코프 함수는 Controller, Service에서 유용합니다

## 💡 Java 개발자를 위한 팁

[//]: # (### 확장 함수 vs Java의 Utility 클래스)

[//]: # (```java)

[//]: # (// Java)

[//]: # (StringUtils.isValidEmail&#40;email&#41;)

[//]: # ()
[//]: # (// Kotlin)

[//]: # (email.isValidEmail&#40;&#41;)

[//]: # (```)

[//]: # ()
[//]: # (### 스코프 함수 vs Java의 Optional)

[//]: # (```java)

[//]: # (// Java)

[//]: # (Optional.ofNullable&#40;user&#41;)

[//]: # (    .map&#40;u -> u.getName&#40;&#41;&#41;)

[//]: # (    .orElse&#40;"Unknown"&#41;;)

[//]: # ()
[//]: # (// Kotlin)

[//]: # (user?.name ?: "Unknown")

[//]: # (```)

[//]: # ()
[//]: # (### Sealed 클래스 vs Java의 Enum)

[//]: # (```java)

[//]: # (// Java enum의 한계)

[//]: # (enum Result { SUCCESS, ERROR })

[//]: # ()
[//]: # (// Kotlin sealed class의 장점)

[//]: # (sealed class Result<T> {)

[//]: # (    data class Success<T>&#40;val data: T&#41; : Result<T>&#40;&#41;)

[//]: # (    data class Error&#40;val message: String&#41; : Result<Nothing>&#40;&#41;)

[//]: # (})

[//]: # (```)

[//]: # ()
[//]: # (### 고차 함수 vs Java Stream API)

[//]: # (```java)

[//]: # (// Java)

[//]: # (employees.stream&#40;&#41;)

[//]: # (    .filter&#40;e -> e.getDepartment&#40;&#41;.equals&#40;"개발팀"&#41;&#41;)

[//]: # (    .mapToInt&#40;Employee::getSalary&#41;)

[//]: # (    .average&#40;&#41;;)

[//]: # ()
[//]: # (// Kotlin)

[//]: # (employees)

[//]: # (    .filter { it.department == "개발팀" })

[//]: # (    .map { it.salary })

[//]: # (    .average&#40;&#41;)

[//]: # (```)

## 📚 학습 순서 추천

1. **1일차**: ExtensionFunctions.kt + ScopeFunctions.kt
2. **2일차**: NullSafety.kt (가장 중요!)
3. **3일차**: DataAndSealedClasses.kt
4. **4일차**: HigherOrderFunctions.kt
5. **5일차**: DelegationPatterns.kt

## 🔧 실무 적용 예제

### Spring Boot Controller에서 활용
```kotlin
@RestController
class UserController {
    
    @GetMapping("/users/{id}")
    fun getUser(@PathVariable id: String): ResponseEntity<UserResponse> {
        return userService.getUser(id)?.let { user ->
            ResponseEntity.ok(user.toResponse())
        } ?: ResponseEntity.notFound().build()
    }
    
    @PostMapping("/users")
    fun createUser(@RequestBody request: CreateUserRequest): ResponseEntity<*> {
        return request.takeIf { it.isValid() }
            ?.let { userService.createUser(it) }
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.badRequest().build()
    }
}
```

### Service Layer에서 활용
```kotlin
@Service
class UserService {
    
    fun processUsers(): List<UserSummary> {
        return userRepository.findAll()
            .filter { it.isActive }
            .groupBy { it.department }
            .mapValues { (_, users) ->
                UserSummary(
                    count = users.size,
                    avgSalary = users.map { it.salary }.average()
                )
            }
    }
}
```

### 확장 함수로 유틸리티 제공
```kotlin
// String 확장 함수
fun String.toUUID(): UUID? = try {
    UUID.fromString(this)
} catch (e: IllegalArgumentException) {
    null
}

// LocalDateTime 확장 함수
fun LocalDateTime.toKoreanString(): String = 
    this.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"))

// 사용
val userId = request.userId.toUUID() ?: throw IllegalArgumentException("Invalid UUID")
val createdAt = user.createdAt.toKoreanString()
```

## ⚠️ 주의사항

1. **확장 함수 남용 금지**: 너무 많은 확장 함수는 코드를 복잡하게 만듭니다
2. **스코프 함수 과용 주의**: 가독성이 떨어질 수 있습니다
3. **!! 연산자 최소화**: 가능한 한 안전한 호출을 사용하세요
4. **sealed class vs enum**: 상태와 데이터가 필요한 경우에만 sealed class 사용

## 🎯 다음 단계

이 기본기를 마스터한 후에는:
1. **코루틴 (Coroutines)** - 비동기 프로그래밍
2. **DSL 빌더** - 타입 안전한 DSL 작성
3. **인라인 함수** - 성능 최적화
4. **제네릭스** - 고급 타입 시스템

## 📞 문의 및 피드백

학습 중 궁금한 점이나 개선 제안이 있으시면 언제든 말씀해 주세요!

---

**Happy Kotlin Coding! 🎉**