package com.lecture.domains.bank.repository

import com.lecture.types.entity.Account
import org.springframework.data.jpa.repository.JpaRepository

interface BankAccountRepository: JpaRepository<Account, String> {
    fun findByUlid(userUlid: String): Account?

}