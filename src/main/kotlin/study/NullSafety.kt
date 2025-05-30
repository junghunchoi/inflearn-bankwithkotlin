package com.lecture.study
/**
 * 널 안전성 (Null Safety) 학습
 * ?., ?:, !!, 스마트 캐스팅 등 코틀린의 null 처리 방법
 */

data class Address(val city: String, val street: String?)
data class Person(val name: String, val address: Address?)

class UserService {
    private val users = mutableMapOf<String, Person>()

    fun addUser(id: String, person: Person) {
        users[id] = person
    }

    fun getUser(id: String): Person? = users[id]

    fun getAllUsers(): List<Person> = users.values.toList()
}

fun main() {
    println("=== 널 안전성 학습 ===")

    val userService = UserService()

    // 테스트 데이터 준비
    userService.addUser("1", Person("김철수", Address("서울", "강남대로 123")))
    userService.addUser("2", Person("박영희", Address("부산", null)))
    userService.addUser("3", Person("이민수", null))

    println("--- 안전 호출 연산자 (?.) 학습 ---")
    // TODO: 안전 호출 연산자를 사용해서 null 체크 없이 안전하게 접근

    val user1 = userService.getUser("1")
    // 1. 사용자의 도시 정보 안전하게 가져오기
    val city1 = user1?.address?.city
    println("User 1 city: $city1")

    val user2 = userService.getUser("2")
    // 2. 사용자의 거리 정보 안전하게 가져오기 (null일 수 있음)
    val street2 = user2?.address?.street
    println("User 2 street: $street2")

    val user3 = userService.getUser("3")
    // 3. 주소가 null인 사용자의 도시 정보 가져오기
    val city3 = user3?.address?.city
    println("User 3 city: $city3")

    println("\n--- 엘비스 연산자 (?:) 학습 ---")
    // TODO: 엘비스 연산자를 사용해서 null일 때 기본값 제공

    // 4. 도시 정보가 없으면 "Unknown" 반환
    val cityWithDefault1 = user1?.address?.city ?: "Unknown"
    val cityWithDefault3 = user3?.address?.city ?: "Unknown"

    println("User 1 city with default: $cityWithDefault1")
    println("User 3 city with default: $cityWithDefault3")

    // 5. 거리 정보가 없으면 "주소 미상" 반환
    val streetWithDefault = user2?.address?.street ?: "주소 미상"
    println("User 2 street with default: $streetWithDefault")

    println("\n--- 연산자 체이닝 연습 ---")
    // TODO: 복잡한 null 체크를 우아하게 처리

    fun getUserInfo(userId: String): String {
        val user = userService.getUser(userId)
        // 6. 사용자 정보를 문자열로 포맷팅 (null 안전하게)
        return "Name: ${user?.name ?: "Unknown"}, " +
                "City: ${user?.address?.city ?: "Unknown"}, " +
                "Street: ${user?.address?.street ?: "Not specified"}"
    }

    for (i in 1..4) {
        println("User $i info: ${getUserInfo(i.toString())}")
    }

    println("\n--- 스마트 캐스팅 학습 ---")
    // TODO: null 체크 후 자동으로 non-null 타입으로 캐스팅

    fun processUser(user: Person?) {
        // 7. null 체크 후 스마트 캐스팅 활용
        if (user != null) {
            // 여기서 user는 자동으로 Person (non-null) 타입이 됨
            println("Processing user: ${user.name}")

            // 8. 중첩된 스마트 캐스팅
            if (user.address != null) {
                println("User lives in: ${user.address.city}")

                if (user.address.street != null) {
                    println("Street: ${user.address.street}")
                }
            }
        } else {
            println("User is null")
        }
    }

    processUser(userService.getUser("1"))
    processUser(userService.getUser("999"))

    println("\n--- Not-null assertion (!!) 주의사항 ---")
    // TODO: !!를 사용할 때의 주의사항 학습

    // 9. 확실히 null이 아닐 때만 사용 (주의: KotlinNullPointerException 발생 가능)
    val definitelyNotNull = userService.getUser("1")
    if (definitelyNotNull != null) {
        val userName = definitelyNotNull.name // 이미 null 체크했으므로 안전
        println("Definitely not null user: $userName")
    }

    // !! 잘못된 사용 예시 (주석 처리)
    // val dangerousCall = userService.getUser("999")!!.name // KotlinNullPointerException!

    println("\n--- 실전 활용 예제 ---")
    // TODO: 실제 업무에서 자주 사용하는 패턴들

    // 10. 컬렉션에서 null 안전 처리
    val allUsers = userService.getAllUsers()
    val citiesWithUsers = allUsers
        .mapNotNull { it.address?.city } // null인 것들은 제외
        .distinct()
        .sorted()

    println("Cities with users: $citiesWithUsers")

    // 11. 조건부 실행 (let과 조합)
    userService.getUser("2")?.let { user ->
        println("Found user: ${user.name}")
        user.address?.let { address ->
            println("Address: ${address.city}")
            address.street?.let { street ->
                println("Street: $street")
            } ?: println("Street information not available")
        }
    }

    // 12. 안전한 타입 캐스팅
    fun processAnyUser(any: Any?) {
        val user = any as? Person // 안전한 캐스팅
        user?.let {
            println("Safely cast user: ${it.name}")
        } ?: println("Not a Person or null")
    }

    processAnyUser(userService.getUser("1"))
    processAnyUser("Not a person")
    processAnyUser(null)
}