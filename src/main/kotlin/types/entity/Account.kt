package com.lecture.types.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "account")
data class Account(
    @Id
    @Column(name = "ulid", length = 12, nullable = false)
    val ulid: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_ulid", nullable = false)
    val user: User,

    @Column(name = "balance", nullable = false, precision = 15, scale = 2)
    val balance: BigDecimal = BigDecimal.ZERO,

    @Column(name = "account_number", length = 20, nullable = false)
    val accountNumber: String,

    @Column(name = "is_deleted", nullable = false)
    val isDeleted: Boolean = false,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),


    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),
)
