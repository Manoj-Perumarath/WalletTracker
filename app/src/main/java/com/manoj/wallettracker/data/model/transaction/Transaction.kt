package com.manoj.wallettracker.data.model.transaction

import com.manoj.wallettracker.constants.CategoryType
import com.manoj.wallettracker.constants.TransactionType
import java.math.BigDecimal
import java.time.LocalDateTime

data class Transaction(
    val id: Int?=null,
    val categoryType: CategoryType,
    val amount: BigDecimal,
    val type: TransactionType,
    val date: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
)
