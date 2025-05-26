package com.lecture.common.transaction

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

interface Runner{
    fun <T> run(function : () -> T): T?
    fun <T> readOnly(function : () -> T): T?
}

@Component
class Transactionl(
    private val advice: Runner = Advice()
) : Runner {
    override fun <T> run(function: () -> T): T? = advice.run(function)

    override fun <T> readOnly(function: () -> T): T? = advice.run(function)

    @Component
    private class Advice : Runner {
        @Transactional
        override fun <T> run(function: () -> T): T? = function()

        @Transactional(readOnly = true)
        override fun <T> readOnly(function: () -> T): T? = function()
    }
    }
