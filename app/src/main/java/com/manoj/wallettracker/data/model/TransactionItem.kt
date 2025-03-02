package com.manoj.wallettracker.data.model

import com.manoj.wallettracker.constants.CategoryType
import com.manoj.wallettracker.constants.TransactionType
import com.manoj.wallettracker.foundation.core.date.formatDateTime
import java.math.BigDecimal
import java.time.LocalDateTime

data class TransactionItem(
    val transactionId: Int?=null,
    val amount: BigDecimal,
    val categoryType: CategoryType,
    val date: LocalDateTime,
    val type: TransactionType,
    val isSelected: Boolean = false
)


fun TransactionItem.getDateTimeDisplay(): String {
    return date.formatDateTime()
}