package com.lecture.domains.bank.repository

import com.lecture.types.entity.Account
import com.lecture.types.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface BankUserRepository : JpaRepository<User, String> {
    fun findByUlid(ulid : String) : User
}