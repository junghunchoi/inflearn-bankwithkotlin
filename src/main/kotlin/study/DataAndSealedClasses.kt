package com.lecture.study

/**
 * 데이터 클래스와 Sealed 클래스 학습
 * data class의 자동 생성 기능과 sealed class의 타입 안전성
 */

// TODO: 데이터 클래스 학습
// 1. 기본 데이터 클래스 정의
data class Product(
    val id: Long,
    val name: String,
    val price: Double,
    val category: String
) {
    // TODO: 데이터 클래스에 커스텀 메서드 추가
    fun getFormattedPrice(): String {
        // 가격을 원화 형식으로 포맷
        return "₩${String.format("%,.0f", price)}"
    }

    fun isExpensive(): Boolean {
        // TODO: 10만원 이상이면 비싼 상품으로 판단
        return false
    }
}

// 2. 중첩 데이터 클래스
data class OrderItem(
    val product: Product,
    val quantity: Int
) {
    fun getTotalPrice(): Double {
        // TODO: 상품 가격 * 수량 계산
        return 0.0
    }
}

data class Order(
    val id: String,
    val items: List<OrderItem>,
    val customerName: String
) {
    fun getTotalAmount(): Double {
        // TODO: 모든 아이템의 총 가격 계산
        return items.sumOf { it.getTotalPrice() }
    }
}

// TODO: Sealed 클래스 학습 - API 응답 모델링
sealed class ApiResponse<out T> {
    // 3. 성공 응답
    data class Success<T>(val data: T) : ApiResponse<T>()

    // 4. 에러 응답
    data class Error(val message: String, val code: Int) : ApiResponse<Nothing>()

    // 5. 로딩 상태
    object Loading : ApiResponse<Nothing>()

    // 6. 빈 결과
    object Empty : ApiResponse<Nothing>()
}

// TODO: Sealed 클래스 - 결제 상태 모델링
sealed class PaymentStatus {
    object Pending : PaymentStatus()
    object Processing : PaymentStatus()
    data class Completed(val transactionId: String, val amount: Double) : PaymentStatus()
    data class Failed(val reason: String, val retryCount: Int = 0) : PaymentStatus()
    data class Refunded(val refundAmount: Double, val refundDate: String) : PaymentStatus()
}

// TODO: Sealed 클래스 - 네트워크 결과
sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val exception: Throwable) : NetworkResult<Nothing>()
    object NetworkError : NetworkResult<Nothing>()
    object Timeout : NetworkResult<Nothing>()
}

class OrderService {

    // TODO: API 응답 처리 함수
    fun handleApiResponse(response: ApiResponse<List<Product>>): String {
        // 7. when 표현식으로 sealed class 처리 (모든 케이스 처리 필수)
        return when (response) {
            is ApiResponse.Success -> {
                // TODO: 성공 케이스 처리
                "성공: ${response.data.size}개 상품 로드됨"
            }
            is ApiResponse.Error -> {
                // TODO: 에러 케이스 처리
                "에러: ${response.message} (코드: ${response.code})"
            }
            is ApiResponse.Loading -> {
                // TODO: 로딩 케이스 처리
                "로딩 중..."
            }
            is ApiResponse.Empty -> {
                // TODO: 빈 결과 케이스 처리
                "결과 없음"
            }
        }
    }

    // TODO: 결제 상태 처리 함수
    fun processPaymentStatus(status: PaymentStatus): String {
        return when (status) {
            is PaymentStatus.Pending -> {
                // TODO: 대기 상태 처리
                "결제 대기 중..."
            }
            is PaymentStatus.Processing -> {
                // TODO: 처리 중 상태 처리
                ""
            }
            is PaymentStatus.Completed -> {
                // TODO: 완료 상태 처리 (거래ID와 금액 사용)
                ""
            }
            is PaymentStatus.Failed -> {
                // TODO: 실패 상태 처리 (실패 이유와 재시도 횟수 사용)
                ""
            }
            is PaymentStatus.Refunded -> {
                // TODO: 환불 상태 처리
                ""
            }
        }
    }
}

fun main() {
    println("=== 데이터 클래스 학습 ===")

    // TODO: 데이터 클래스 생성 및 기본 기능 테스트
    val product1 = Product(1, "노트북", 1500000.0, "전자제품")
    val product2 = Product(2, "마우스", 50000.0, "전자제품")

    // 8. 자동 생성된 toString() 테스트
    println("Product 1: $product1")

    // 9. 자동 생성된 equals() 테스트
    val product1Copy = Product(1, "노트북", 1500000.0, "전자제품")
    println("product1 == product1Copy: ${product1 == product1Copy}")

    // 10. 자동 생성된 hashCode() 테스트
    println("product1 hashCode: ${product1.hashCode()}")
    println("product1Copy hashCode: ${product1Copy.hashCode()}")

    // 11. copy() 함수 테스트 - 일부 속성만 변경
    val discountedProduct = product1.copy(price = product1.price * 0.9)
    println("Discounted product: $discountedProduct")

    // 12. 구조 분해 선언 (Destructuring Declaration)
    val (id, name, price, category) = product1
    println("Destructured: id=$id, name=$name, price=$price, category=$category")

    // TODO: 커스텀 메서드 테스트
    println("Formatted price: ${product1.getFormattedPrice()}")
    println("Is expensive: ${product1.isExpensive()}")

    println("\n--- 주문 시스템 테스트 ---")
    // TODO: 복잡한 데이터 구조 생성
    val orderItem1 = OrderItem(product1, 1)
    val orderItem2 = OrderItem(product2, 2)
    val order = Order("ORD-001", listOf(orderItem1, orderItem2), "김철수")

    println("Order: $order")
    println("Order total: ₩${String.format("%,.0f", order.getTotalAmount())}")

    println("\n=== Sealed 클래스 학습 ===")

    val orderService = OrderService()

    // TODO: 다양한 API 응답 시나리오 테스트
    val responses = listOf<ApiResponse<List<Product>>>(
        ApiResponse.Success(listOf(product1, product2)),
        ApiResponse.Error("서버 오류", 500),
        ApiResponse.Loading,
        ApiResponse.Empty
    )

    responses.forEach { response ->
        println("API Response: ${orderService.handleApiResponse(response)}")
    }

    println("\n--- 결제 상태 처리 ---")
    // TODO: 다양한 결제 상태 시나리오 테스트
    val paymentStatuses = listOf(
        PaymentStatus.Pending,
        PaymentStatus.Processing,
        PaymentStatus.Completed("TXN-12345", 1500000.0),
        PaymentStatus.Failed("카드 한도 초과", 1),
        PaymentStatus.Refunded(1500000.0, "2024-01-15")
    )

    paymentStatuses.forEach { status ->
        println("Payment Status: ${orderService.processPaymentStatus(status)}")
    }

    println("\n--- Sealed 클래스의 장점 ---")
    // TODO: sealed class vs enum 비교

    // 13. sealed class는 상태와 데이터를 함께 가질 수 있음
    fun handleNetworkResult(result: NetworkResult<String>): String {
        return when (result) {
            is NetworkResult.Success -> "받은 데이터: ${result.data}"
            is NetworkResult.Error -> "예외 발생: ${result.exception.message}"
            is NetworkResult.NetworkError -> "네트워크 연결 오류"
            is NetworkResult.Timeout -> "요청 시간 초과"
            // when이 exhaustive하므로 모든 케이스를 처리해야 함 - 컴파일 타임 안전성!
        }
    }

    val networkResults = listOf<NetworkResult<String>>(
        NetworkResult.Success("Hello World"),
        NetworkResult.Error(RuntimeException("Server Error")),
        NetworkResult.NetworkError,
        NetworkResult.Timeout
    )

    networkResults.forEach { result ->
        println("Network Result: ${handleNetworkResult(result)}")
    }
}