package com.lecture.domains.bank.service

import com.lecture.common.logging.Logging
import com.lecture.common.transaction.Transactionl
import com.lecture.types.dto.Response
import org.slf4j.Logger
import org.springframework.stereotype.Service
import java.math.BigDecimal
import kotlin.math.log

@Service
class BankService(
    private val transition: Transactionl,
    private val logger : Logger = Logging.getLogger(BankService::class.java)
) {
    fun createAccount(ulid: String): Response<String> = Logging.logFor(logger) { log ->
        log["userUlid"] = ulid
        transition.run {  }
    }

    fun balance(userUlid: String, accountUlid: String): Response<BigDecimal> = Logging.logFor(logger) { log ->
        log["userUlid"] = userUlid
        log["accountUlid"] = accountUlid
        transition.run {  }
    }

    fun removeAccount(userUlid: String, accountUlid: String): Response<String> = Logging.logFor(logger) { log ->
        log["userUlid"] = userUlid
        log["accountUlid"] = accountUlid
        transition.run {  }
    }
}