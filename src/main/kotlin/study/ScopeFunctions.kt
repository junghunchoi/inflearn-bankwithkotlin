package com.lecture.study

/**
 * 스코프 함수 (Scope Functions) 학습
 * let, run, with, apply, also - 각각의 특징과 사용법
 */

data class User(
    var name: String = "",
    var email: String = "",
    var age: Int = 0,
    var isActive: Boolean = false
) {
    init {
        name = "abc"
    }

    fun activate() {
        isActive = true
        println("User $name has been activated")
    }

    fun sendWelcomeEmail() {
        println("Welcome email sent to $email")
    }
}

fun main() {
    println("=== 스코프 함수 학습 ===")

    // TODO: let 사용해보기 - 널 체크와 변환에 주로 사용
    val nullableUser: User? = User()

//    // 1. let을 사용해서 null이 아닐 때만 작업 수행
//    nullableUser?.let {
//        // TODO: user 객체 설정 후 이름 출력
//        // 힌트: it을 사용해서 접근
//        it.name = "홍길동"
//        println("user => ${it}")
//    }

    // 2. let을 사용해서 변환 작업
//    val userName = nullableUser?.let {
//        // TODO: 사용자 이름을 대문자로 변환해서 반환
//        println("user => ${it.name.uppercase()}")
//    }

    println("\n--- run 사용법 ---")
    // TODO: run 사용해보기 - 객체 설정과 결과 반환
    val user1 = User().run {
        // TODO: 사용자 정보 설정하고 활성화 후 사용자 객체 반환
        name = "김철수"
        email = "kim@example.com"
        age = 25
        activate()
        this // 또는 다른 값 반환 가능
    }
    println("Created user: $user1")

    println("\n--- with 사용법 ---")
    // TODO: with 사용해보기 - 객체의 여러 메서드 호출
    val user2 = User()
    with(user2) {
        // TODO: 사용자 정보 설정
        name = "박영희"
        email = "park@example.com"
        age = 30
        // TODO: 활성화 및 웰컴 이메일 발송
    }
    println("User with 'with': $user2")

    println("\n--- apply 사용법 ---")
    // TODO: apply 사용해보기 - 객체 초기화에 최적
    val user3 = User().apply {
        // TODO: 사용자 정보 설정 (반환값은 자기 자신)
        name = "이민수"
        email = "lee@example.com"
        age = 28
    }
    // TODO: apply 후에 추가 작업
    user3.activate()
    println("User with apply: $user3")

    println("\n--- also 사용법 ---")
    // TODO: also 사용해보기 - 부가 작업 수행
    val user4 = User().apply {
        name = "최지영"
        email = "choi@example.com"
        age = 32
    }.also {
        // TODO: 객체 생성 후 로깅이나 검증 작업
        println("User created: ${it.name}")
        // TODO: 추가 검증 로직 작성
    }

    println("\n--- 실전 예제: 빌더 패턴처럼 사용하기 ---")
    // TODO: 체이닝을 활용한 복잡한 객체 생성
    fun createAndSetupUser(name: String, email: String): User {
        return User().apply {
            // TODO: 기본 정보 설정
        }.also {
            // TODO: 로깅
            println("Setting up user: ${it.name}")
        }.apply {
            // TODO: 추가 설정
            activate()
        }.also {
            // TODO: 최종 검증
            if (it.email.contains("@")) {
                it.sendWelcomeEmail()
            }
        }
    }

    val finalUser = createAndSetupUser("정현우", "jung@example.com")
    println("Final user: $finalUser")

    println("\n--- 스코프 함수 비교 연습 ---")
    val testUser: User? = User()

    // TODO: 각 스코프 함수의 차이점 비교해보기
    // 1. let: 널 체크 + 변환
    val result1 = testUser?.let { "User name is ${it.name}" }

    // 2. run: 객체 컨텍스트에서 작업하고 결과 반환
    val result2 = testUser?.run { "User name is $name" }

    // 3. with: non-null 객체의 멤버에 접근
    val result3 = testUser?.let { with(it) { "User name is $name" } }

    // 4. apply: 객체 초기화 후 객체 자체 반환
    val result4 = testUser?.apply { name = "Test User" }

    // 5. also: 객체에 부가 작업 후 객체 자체 반환
    val result5 = testUser?.also { println("Processing user: ${it.name}") }

    println("Results: $result1, $result2, $result3, $result4, $result5")
}


/*
선택 가이드
함수수신객체 참조반환값주요 용도
letit람다 결과null 체크, 변환
runthis람다 결과객체 초기화 + 계산
withthis람다 결과객체의 여러 함수 호출
applythis객체 자체객체 설정/초기화
alsoit객체 자체부가 작업 (로깅, 검증)
실무 팁:

객체를 반환해야 하면: apply, also
계산 결과를 반환해야 하면: let, run, with
null 안전성이 필요하면: let
객체 초기화: apply
부가 작업: also

1. apply (가장 많이 사용)
kotlin// 객체 초기화 - 매우 자주 사용
val user = User().apply {
    name = "John"
    email = "john@example.com"
}

// View 설정 - Android에서 특히 많이 사용
button.apply {
    text = "클릭하세요"
    setOnClickListener { /* ... */ }
    isEnabled = true
}
2. let (두 번째로 많이 사용)
kotlin// null 체크 - 매우 일반적
user?.let {
    println(it.name)
    processUser(it)
}

// 스마트 캐스트와 함께
(response as? SuccessResponse)?.let { success ->
    handleSuccess(success.data)
}
3. also (세 번째)
kotlin// 로깅, 디버깅에 자주 사용
val result = createData()
    .also { logger.debug("Created: $it") }
    .also { validateData(it) }

// 체이닝 중간에 부가 작업
list.filter { it.isValid }
    .also { println("Filtered ${it.size} items") }
    .map { it.transform() }
4. run (네 번째)
kotlin// 복잡한 초기화와 계산
val config = DatabaseConfig().run {
    host = "localhost"
    port = 5432
    connect()
    isConnected() // 결과 반환
}

// 조건 체크
val isValid = user.run {
    name.isNotEmpty() && email.contains("@")
}
5. with (가장 적게 사용)
kotlin// StringBuilder 등에서 가끔 사용
val html = with(StringBuilder()) {
    append("<html>")
    append("<body>Hello</body>")
    append("</html>")
    toString()
}
왜 이런 순서일까?
apply가 1위인 이유:

객체 초기화는 개발에서 가장 기본적이고 빈번한 작업
빌더 패턴처럼 사용 가능해서 코드가 깔끔
Android 개발에서 View 설정할 때 필수

let이 2위인 이유:

Kotlin의 null safety 때문에 ?.let 패턴을 매우 자주 사용
스마트 캐스트와 함께 사용하는 경우가 많음

with가 5위인 이유:

확장 함수가 아니라서 체이닝하기 어려움
apply나 run으로 대체 가능한 경우가 많음
가독성 면에서 다른 함수들보다 떨어짐

실무에서는 apply > let > also 순서로 90% 정도의 사용률을 차지합니다.
 */