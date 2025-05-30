package com.lecture.study


/**
 * 고차 함수와 함수형 프로그래밍 학습
 * 람다, 고차 함수, 컬렉션 처리, 함수형 패턴
 */

data class Employee(
    val id: Int,
    val name: String,
    val department: String,
    val salary: Int,
    val experience: Int
)

data class Sale(
    val id: String,
    val product: String,
    val amount: Double,
    val region: String,
    val date: String,
    val employeeId: Int
)

fun main() {
    println("=== 고차 함수와 함수형 프로그래밍 학습 ===")

    // 테스트 데이터 준비
    val employees = listOf(
        Employee(1, "김철수", "개발팀", 5000, 3),
        Employee(2, "박영희", "마케팅팀", 4500, 5),
        Employee(3, "이민수", "개발팀", 6000, 7),
        Employee(4, "최지영", "영업팀", 4000, 2),
        Employee(5, "정현우", "개발팀", 5500, 4),
        Employee(6, "한미래", "마케팅팀", 4800, 6)
    )

    val sales = listOf(
        Sale("S001", "노트북", 1500000.0, "서울", "2024-01", 1),
        Sale("S002", "마우스", 50000.0, "부산", "2024-01", 2),
        Sale("S003", "키보드", 120000.0, "서울", "2024-02", 1),
        Sale("S004", "모니터", 300000.0, "대구", "2024-02", 3),
        Sale("S005", "헤드셋", 80000.0, "서울", "2024-03", 4)
    )

    println("--- 기본 람다 표현식 ---")
    // TODO: 람다 표현식 기본 문법 학습

    // 1. 간단한 람다 함수
    val add = { a: Int, b: Int -> a + b }
    println("Add function: ${add(5, 3)}")

    // 2. 타입 추론을 활용한 람다
    val multiply: (Int, Int) -> Int = { a, b -> a * b }
    println("Multiply function: ${multiply(4, 5)}")

    // 3. 단일 매개변수 람다 (it 사용)
    val square: (Int) -> Int = { it * it }
    println("Square of 7: ${square(7)}")

    println("\n--- 컬렉션 고차 함수 기본 ---")
    // TODO: map, filter, reduce 등 기본 고차 함수 학습

    // 4. map - 변환
    val employeeNames = employees.map { it.name }
    println("Employee names: $employeeNames")

    // TODO: 급여를 10% 인상한 새로운 리스트 만들기
    val raisedSalaries = employees.map {
        // TODO: 급여 10% 인상 로직
        it.salary
    }
    println("Raised salaries: $raisedSalaries")

    // 5. filter - 필터링
    val devEmployees = employees.filter { it.department == "개발팀" }
    println("Dev employees: ${devEmployees.map { it.name }}")

    // TODO: 경력 5년 이상이고 급여 5000 이상인 직원 찾기
    val seniorHighPaidEmployees = employees.filter {
        // TODO: 조건 작성
        false
    }
    println("Senior high-paid employees: ${seniorHighPaidEmployees.map { it.name }}")

    // 6. reduce와 fold - 집계
    val totalSalary = employees.map { it.salary }.reduce { acc, salary -> acc + salary }
    println("Total salary (reduce): $totalSalary")

    // TODO: fold를 사용해서 평균 급여 계산
    val averageSalary = employees.fold(0) { acc, employee ->
        // TODO: 급여 합계 계산
        acc
    } / employees.size
    println("Average salary: $averageSalary")

    println("\n--- 고급 컬렉션 연산 ---")
    // TODO: groupBy, partition, sortedBy 등 고급 함수 학습

    // 7. groupBy - 그룹화
    val employeesByDept = employees.groupBy { it.department }
    employeesByDept.forEach { (dept, empList) ->
        println("$dept: ${empList.map { it.name }}")
    }

    // TODO: 급여 범위별로 직원 그룹화 (4000미만, 4000-5500, 5500초과)
    val employeesBySalaryRange = employees.groupBy { employee ->
        when {
            employee.salary < 4000 -> "Low"
            employee.salary <= 5500 -> "Medium"
            else -> "High"
        }
    }
    println("Employees by salary range: $employeesBySalaryRange")

    // 8. partition - 조건에 따른 분할
    val (experienced, junior) = employees.partition { it.experience >= 5 }
    println("Experienced: ${experienced.map { it.name }}")
    println("Junior: ${junior.map { it.name }}")

    // 9. sortedBy, sortedWith - 정렬
    val sortedByExperience = employees.sortedBy { it.experience }
    println("Sorted by experience: ${sortedByExperience.map { "${it.name}(${it.experience}년)" }}")

    // TODO: 급여 내림차순, 같으면 경력 오름차순으로 정렬
    val customSorted = employees.sortedWith(compareByDescending<Employee> { it.salary }.thenBy { it.experience })
    println("Custom sorted: ${customSorted.map { "${it.name}(${it.salary}, ${it.experience}년)" }}")

    println("\n--- 함수형 체이닝 ---")
    // TODO: 여러 고차 함수를 체이닝해서 복잡한 데이터 처리

    // 10. 개발팀 직원 중 경력 3년 이상인 사람들의 평균 급여
    val devTeamAvgSalary = employees
        .filter { it.department == "개발팀" }
        .filter { it.experience >= 3 }
        .map { it.salary }
        .average()

    println("Dev team (3+ years) average salary: $devTeamAvgSalary")

    // TODO: 지역별 매출 통계 계산
    val salesByRegion = sales
        .groupBy { it.region }
        .mapValues { (_, salesList) ->
            mapOf(
                "count" to salesList.size,
                "total" to salesList.sumOf { it.amount },
                "average" to salesList.map { it.amount }.average()
            )
        }

    println("Sales by region:")
    salesByRegion.forEach { (region, stats) ->
        println("  $region: ${stats["count"]}건, 총 ${stats["total"]}, 평균 ${stats["average"]}")
    }

    println("\n--- 커스텀 고차 함수 작성 ---")
    // TODO: 자신만의 고차 함수 만들기

    // 11. 조건을 만족하는 요소의 개수를 세는 함수
    fun <T> List<T>.countWhere(predicate: (T) -> Boolean): Int {
        // TODO: predicate 조건을 만족하는 요소 개수 반환
        return 0
    }

    val highSalaryCount = employees.countWhere { it.salary > 5000 }
    println("High salary employees count: $highSalaryCount")

    // 12. 특정 조건으로 변환 후 합계를 구하는 함수
    fun <T> List<T>.sumByCondition(
        condition: (T) -> Boolean,
        mapper: (T) -> Int
    ): Int {
        // TODO: 조건을 만족하는 요소들을 변환 후 합계 계산
        return 0
    }

    val devTeamTotalSalary = employees.sumByCondition(
        condition = { it.department == "개발팀" },
        mapper = { it.salary }
    )
    println("Dev team total salary: $devTeamTotalSalary")

    println("\n--- 함수를 매개변수로 전달 ---")
    // TODO: 함수를 매개변수로 받는 고차 함수 작성

    // 13. 다양한 집계 함수를 매개변수로 받는 함수
    fun <T, R> analyzeData(
        data: List<T>,
        filter: (T) -> Boolean,
        mapper: (T) -> R,
        aggregator: (List<R>) -> R
    ): R {
        return data
            .filter(filter)
            .map(mapper)
            .let(aggregator)
    }

    // 개발팀 직원들의 최대 급여
    val maxDevSalary = analyzeData(
        data = employees,
        filter = { it.department == "개발팀" },
        mapper = { it.salary },
        aggregator = { it.maxOrNull() ?: 0 }
    )
    println("Max dev team salary: $maxDevSalary")

    // TODO: 마케팅팀 직원들의 평균 경력 계산
    val avgMarketingExperience = analyzeData(
        data = employees,
        filter = { /* TODO: 마케팅팀 필터 조건 */ true },
        mapper = { /* TODO: 경력 추출 */ 0 },
        aggregator = { /* TODO: 평균 계산 */ 0 }
    )
    println("Average marketing team experience: $avgMarketingExperience")

    println("\n--- 함수형 프로그래밍 패턴 ---")
    // TODO: 실용적인 함수형 패턴들

    // 14. 파이프라인 패턴
    fun processEmployeeData(employees: List<Employee>): Map<String, Any> {
        return employees
            .filter { it.salary > 4500 }  // 고급여 직원만
            .groupBy { it.department }     // 부서별 그룹화
            .mapValues { (_, empList) ->   // 각 부서별 통계
                mapOf(
                    "count" to empList.size,
                    "avgSalary" to empList.map { it.salary }.average(),
                    "avgExperience" to empList.map { it.experience }.average(),
                    "names" to empList.map { it.name }
                )
            }
    }

    val processedData = processEmployeeData(employees)
    println("Processed employee data: $processedData")

    // 15. 함수 합성 (Function Composition)
    val addTax: (Double) -> Double = { it * 1.1 }
    val formatCurrency: (Double) -> String = { "₩${String.format("%,.0f", it)}" }
    val processPrice: (Double) -> String = { price -> formatCurrency(addTax(price)) }

    sales.forEach { sale ->
        println("${sale.product}: ${processPrice(sale.amount)}")
    }
}