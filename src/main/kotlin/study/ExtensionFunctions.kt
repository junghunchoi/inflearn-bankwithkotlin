package com.lecture.study

/**
 * 확장 함수 (Extension Functions) 학습
 * 기존 클래스에 새로운 함수를 추가할 수 있는 코틀린의 강력한 기능
 */

// TODO: String 클래스에 확장 함수 추가해보기
// 1. 이메일 유효성 검사 확장 함수 만들기
fun String.isValidEmail(): Boolean {
    // TODO: 간단한 이메일 패턴 검증 로직 작성
    // 힌트: contains("@")와 contains(".")를 활용
    if (this.contains("@") && this.contains(".")) {
        val parts = this.split("@")
        if (parts.size == 2 && parts[1].contains(".")) {
            return true
        }
    }
    return false
}

// 2. 문자열 마스킹 확장 함수 만들기
fun String.maskMiddle(): String {
    // TODO: 문자열 중간 부분을 *로 마스킹하는 로직 작성
    // 예: "홍길동" -> "홍*동", "abcdef" -> "ab**ef"
    if(this.length <= 2) {
        return this // 길이가 2 이하인 경우 마스킹하지 않음
    }

    if (this.length % 2 == 0) {
        val middleIndex = this.length / 2
        return this.substring(0, middleIndex - 1) + "**" + this.substring(middleIndex + 1)
    } else {
        val middleIndex = this.length / 2
        return this.substring(0, middleIndex) + "*" + this.substring(middleIndex + 1)
    }

    return ""
}

// TODO: Int 클래스에 확장 함수 추가해보기
// 3. 숫자를 한국어로 변환하는 확장 함수
fun Int.toKorean(): String {
    // TODO: 1~10까지의 숫자를 한국어로 변환
    // 예: 1 -> "일", 2 -> "이", 3 -> "삼"
    val koreanNumbers = listOf("영", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구")
    if (this in 0..9) {
        return koreanNumbers[this]
    }
    return ""
}

// TODO: List<T>에 확장 함수 추가해보기
// 4. 리스트의 두 번째 요소를 안전하게 가져오는 확장 함수
fun <T> List<T>.secondOrNull(): T? {
    // TODO: 리스트의 두 번째 요소를 안전하게 반환 (없으면 null)

    return this.getOrNull(2)
}

// 5. 리스트를 청크 단위로 나누는 확장 함수 (이미 있지만 직접 구현해보기)
fun <T> List<T>.chunkedBy(size: Int): List<List<T>> {
    // TODO: 리스트를 지정된 크기로 나누는 로직 작성
    // 예: [1,2,3,4,5].chunkedBy(2) -> [[1,2], [3,4], [5]]
    if (size <= 0) return emptyList()
    val result = mutableListOf<List<T>>()
    for (i in this.indices step size) {
        val end = (i + size).coerceAtMost(this.size)
        result.add(this.subList(i, end))
    }
    return result
}

fun main() {
    println("=== 확장 함수 학습 ===")

    // TODO: 작성한 확장 함수들을 테스트해보세요

    // 1. 이메일 검증 테스트
    val email1 = "test@example.com"
    val email2 = "invalid-email"
    println("$email1 is valid: ${email1.isValidEmail()}")
    println("$email2 is valid: ${email2.isValidEmail()}")

    // 2. 문자열 마스킹 테스트
    val name = "홍길동"
    val longText = "abcdefgh"
    println("$name masked: ${name.maskMiddle()}")
    println("$longText masked: ${longText.maskMiddle()}")

    // 3. 숫자 한국어 변환 테스트
    for (i in 1..5) {
        println("$i -> ${i.toKorean()}")
    }

    // 4. 두 번째 요소 가져오기 테스트
    val list1 = listOf("first", "second", "third")
    val list2 = listOf("only one")
    val emptyList = emptyList<String>()

    println("Second of $list1: ${list1.secondOrNull()}")
    println("Second of $list2: ${list2.secondOrNull()}")
    println("Second of empty list: ${emptyList.secondOrNull()}")

    // 5. 청크 나누기 테스트
    val numbers = listOf(1, 2, 3, 4, 5, 6, 7)
    println("$numbers chunked by 3: ${numbers.chunkedBy(3)}")
}