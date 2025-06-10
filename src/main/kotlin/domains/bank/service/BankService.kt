package com.lecture.domains.bank.service

import com.github.f4b6a3.ulid.UlidCreator
import com.lecture.common.exception.CustomException
import com.lecture.common.exception.ErrorCode
import com.lecture.common.logging.Logging
import com.lecture.common.transaction.Transactionl
import com.lecture.domains.bank.repository.BankAccountRepository
import com.lecture.domains.bank.repository.BankUserRepository
import com.lecture.types.dto.Response
import com.lecture.types.dto.ResponseProvider
import com.lecture.types.entity.Account
import okhttp3.internal.EMPTY_REQUEST
import org.slf4j.Logger
import org.springframework.stereotype.Service
import java.lang.Math.random
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.math.log
import kotlin.run

@Service
class BankService(
    private val bankUserRepository: BankUserRepository,
    private val bankAccountRepository: BankAccountRepository,
    private val transaction: Transactionl,
    private val logger : Logger = Logging.getLogger(BankService::class.java)
) {
    fun createAccount(userUlid: String) : Response<String> = Logging.logFor(logger)  { log ->
        log["userUlid"] = userUlid

        transaction.run {
            val user  = bankUserRepository.findByUlid(userUlid)

            val ulid = UlidCreator.getUlid().toString()
            val accountNumber = generateAccountUlid()

            val account = Account(
                ulid = ulid,
                user = user,
                accountNumber = accountNumber,
            )

            try {
                bankAccountRepository.save(account)
            } catch (e: Exception) {
                throw CustomException(ErrorCode.FAILED_TO_SAVE_DATA, e.message)
            }
        }

        return@logFor ResponseProvider.success("SUCCESS")
    }

    fun balance(userUlid: String, accountUlid: String): Response<BigDecimal> = Logging.logFor(logger) { log ->
        log["userUlid"] = userUlid
        log["accountUlid"] = accountUlid

        // TODO -> 동시성이슈가 발생할 여지가 크다.
        return@logFor transaction.run {
            val account = bankAccountRepository.findByUlid(accountUlid) ?: throw CustomException(ErrorCode.FAILED_TO_FIND_ACCOUNT, accountUlid)
            if (account.user.ulid != userUlid) throw CustomException(ErrorCode.MISS_MATCH_ACOUNT_ULID_AND_USER_ULID)
            ResponseProvider.success(account.balance)
        }
    }

    fun removeAccount(userUlid: String, accountUlid: String): Response<String> = Logging.logFor(logger) { log ->
        log["userUlid"] = userUlid
        log["accountUlid"] = accountUlid
        return@logFor transaction.run {
            val account = bankAccountRepository.findByUlid(accountUlid) ?: throw CustomException(ErrorCode.FAILED_TO_FIND_ACCOUNT, accountUlid)

            if(account.user.ulid != userUlid) throw CustomException(ErrorCode.FAILED_TO_FIND_ACCOUNT)
            if(account.balance.compareTo(BigDecimal.ZERO) != 0 ) throw CustomException(ErrorCode.ACCOUNT_BALANCE_IS_NOT_ZERO, account.balance.toString())

            val updateAccount = account.copy(
                isDeleted = true,
                deletedAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )

            bankAccountRepository.save(updateAccount)

            ResponseProvider.success("SUCCESS")
        }

    }

    private fun generateAccountUlid(): String {
        val bankCode = "003"
        val section = "12"

        val number = random().toString()

        return "$bankCode-$section-$number"
    }
}