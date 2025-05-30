package com.lecture.study

import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 위임 패턴 (Delegation Patterns) 학습
 * 클래스 위임, 프로퍼티 위임, 커스텀 위임자
 */

// TODO: 클래스 위임 (Class Delegation) 학습

// 인터페이스 정의
interface Logger {
    fun log(message: String)
    fun error(message: String)
}

interface DatabaseConnection {
    fun connect(): Boolean
    fun disconnect()
    fun execute(query: String): String
}

// 구현체들
class ConsoleLogger : Logger {
    override fun log(message: String) {
        println("[LOG] $message")
    }

    override fun error(message: String) {
        println("[ERROR] $message")
    }
}

class FileLogger : Logger {
    override fun log(message: String) {
        println("[FILE LOG] $message")
    }

    override fun error(message: String) {
        println("[FILE ERROR] $message")
    }
}

class MySQLConnection : DatabaseConnection {
    override fun connect(): Boolean {
        println("MySQL 연결 성공")
        return true
    }

    override fun disconnect() {
        println("MySQL 연결 해제")
    }

    override fun execute(query: String): String {
        return "MySQL 쿼리 실행: $query"
    }
}

// TODO: 1. 기본 클래스 위임 구현
// UserService는 Logger와 DatabaseConnection의 기능을 위임받음
class UserService(
    logger: Logger,
    database: DatabaseConnection
) : Logger by logger, DatabaseConnection by database {

    // TODO: 추가 비즈니스 로직만 구현
    fun createUser(name: String, email: String): String {
        // 위임받은 메서드들을 직접 사용 가능
        log("사용자 생성 시작: $name")

        if (!connect()) {
            error("데이터베이스 연결 실패")
            return "실패"
        }

        val result = execute("INSERT INTO users (name, email) VALUES ('$name', '$email')")
        log("사용자 생성 완료: $result")

        disconnect()
        return "성공"
    }

    // TODO: 위임된 메서드를 오버라이드도 가능
    override fun log(message: String) {
        // 추가 로직 + 원본 호출
        println("[UserService] $message")
        // 원본 logger의 log 호출하려면 별도 참조 필요
    }
}

// TODO: 프로퍼티 위임 (Property Delegation) 학습

class UserProfile {
    // 2. lazy 위임 - 최초 접근 시에만 초기화
    val expensiveData: String by lazy {
        println("비싼 계산 수행 중...")
        Thread.sleep(1000) // 무거운 작업 시뮬레이션
        "계산된 데이터"
    }

    // 3. observable 위임 - 값 변경 시 콜백 실행
    var userName: String by Delegates.observable("초기값") { property, oldValue, newValue ->
        println("${property.name}이 '$oldValue'에서 '$newValue'로 변경됨")
    }

    // 4. vetoable 위임 - 값 변경 전 검증
    var userAge: Int by Delegates.vetoable(0) { property, oldValue, newValue ->
        // TODO: 나이 검증 로직 작성 (0~150 사이만 허용)
        println("나이 변경 시도: $oldValue -> $newValue")
        newValue in 0..150
    }

    // 5. notNull 위임 - 최초 할당 전까지는 접근 불가
    var userId: String by Delegates.notNull()
}

// TODO: 커스텀 프로퍼티 위임자 작성

// 6. 값 변경 기록을 저장하는 위임자
class HistoryDelegate<T>(private var value: T) : ReadWriteProperty<Any?, T> {
    private val history = mutableListOf<T>()

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        // TODO: 이전 값을 기록에 저장하고 새 값 설정
        history.add(this.value)
        this.value = value
        println("${property.name} 변경: 기록 개수 ${history.size}")
    }

    fun getHistory(): List<T> = history.toList()
}

// 7. 값 범위를 제한하는 위임자
class RangeDelegate(
    private var value: Int,
    private val range: IntRange
) : ReadWriteProperty<Any?, Int> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int = value

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        // TODO: 범위 검증 후 값 설정
        if (value in range) {
            this.value = value
            println("${property.name} 값 설정: $value")
        } else {
            println("${property.name} 값 범위 초과: $value (허용 범위: $range)")
        }
    }
}

// 8. 캐시 기능이 있는 위임자
class CacheDelegate<T>(
    private val loader: () -> T,
    private val ttlMillis: Long = 5000L
) : ReadOnlyProperty<Any?, T> {

    private var cachedValue: T? = null
    private var lastLoadTime: Long = 0

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val currentTime = System.currentTimeMillis()

        // TODO: 캐시가 유효한지 확인하고, 만료되었으면 새로 로드
        if (cachedValue == null || (currentTime - lastLoadTime) > ttlMillis) {
            println("${property.name} 캐시 갱신")
            cachedValue = loader()
            lastLoadTime = currentTime
        } else {
            println("${property.name} 캐시 사용")
        }

        return cachedValue!!
    }
}

// 사용 예제 클래스들
class GamePlayer {
    var name: String by HistoryDelegate("Player")
    var level: Int by RangeDelegate(1, 1..100)
    var hp: Int by RangeDelegate(100, 0..1000)

    // 캐시된 프로퍼티 예제
    val powerScore: Int by CacheDelegate({ calculatePowerScore() }, 3000L)

    private fun calculatePowerScore(): Int {
        println("복잡한 파워 스코어 계산 중...")
        Thread.sleep(500)
        return level * 10 + hp / 10
    }

    fun getNameHistory(): List<String> {
        // TODO: 이름 변경 이력 가져오기 (리플렉션 필요)
        // 실제로는 프로퍼티 위임자에 직접 접근하기 어려움
        return emptyList()
    }
}

// TODO: Map을 사용한 위임
open class ConfigManager(private val config: MutableMap<String, Any> = mutableMapOf()) {
    var serverUrl: String by config
    var timeout: Int by config
    var retryCount: Int by config
    var enableLogging: Boolean by config

    fun loadFromProperties() {
        // TODO: 설정 파일에서 값 로드 시뮬레이션
        config["serverUrl"] = "https://api.example.com"
        config["timeout"] = 5000
        config["retryCount"] = 3
        config["enableLogging"] = true
    }

    fun printAllConfigs() {
        config.forEach { (key, value) ->
            println("$key = $value")
        }
    }
}

fun main() {
    println("=== 위임 패턴 학습 ===")

    println("--- 클래스 위임 ---")
    // TODO: 클래스 위임 테스트
    val userService = UserService(
        logger = ConsoleLogger(),
        database = MySQLConnection()
    )

    // 위임받은 메서드들을 직접 사용 가능
    userService.log("서비스 시작")
    userService.createUser("김철수", "kim@example.com")

    println("\n--- 프로퍼티 위임 기본 ---")
    // TODO: 기본 프로퍼티 위임 테스트
    val profile = UserProfile()

    // lazy 테스트
    println("첫 번째 접근:")
    println(profile.expensiveData)
    println("두 번째 접근:")
    println(profile.expensiveData) // 캐시된 값 사용

    // observable 테스트
    profile.userName = "김철수"
    profile.userName = "박영희"

    // vetoable 테스트
    profile.userAge = 25  // 성공
    profile.userAge = 200 // 실패
    println("최종 나이: ${profile.userAge}")

    // notNull 테스트
    profile.userId = "user123"
    println("사용자 ID: ${profile.userId}")

    println("\n--- 커스텀 위임자 ---")
    // TODO: 커스텀 위임자 테스트
    val player = GamePlayer()

    // 이름 변경 기록
    player.name = "전사"
    player.name = "마법사"
    player.name = "도적"

    // 레벨과 HP 범위 제한
    player.level = 50
    player.level = 150 // 범위 초과
    println("현재 레벨: ${player.level}")

    player.hp = 500
    player.hp = 1500 // 범위 초과
    println("현재 HP: ${player.hp}")

    // 캐시된 프로퍼티
    println("파워 스코어 1: ${player.powerScore}")
    println("파워 스코어 2: ${player.powerScore}") // 캐시 사용

    Thread.sleep(4000) // 캐시 만료 대기
    println("파워 스코어 3: ${player.powerScore}") // 다시 계산

    println("\n--- Map 위임 ---")
    // TODO: Map을 사용한 위임 테스트
    val configManager = ConfigManager()

    // 초기값 설정
    configManager.loadFromProperties()

    println("로드된 설정:")
    configManager.printAllConfigs()

    // 프로퍼티로 직접 접근
    println("\n프로퍼티 접근:")
    println("Server URL: ${configManager.serverUrl}")
    println("Timeout: ${configManager.timeout}")

    // 프로퍼티 변경
    configManager.timeout = 10000
    configManager.enableLogging = false

    println("\n변경 후:")
    configManager.printAllConfigs()

    println("\n--- 실전 활용 예제 ---")
    // TODO: 실무에서 유용한 위임 패턴들

    // 설정 값들을 프로퍼티로 관리
    class AppConfig : ConfigManager() {
        init {
            loadFromProperties()
        }

        fun isProduction(): Boolean = serverUrl.contains("production")
        fun shouldRetry(): Boolean = retryCount > 0
    }

    val appConfig = AppConfig()
    println("Production 환경: ${appConfig.isProduction()}")
    println("재시도 설정: ${appConfig.shouldRetry()}")
}