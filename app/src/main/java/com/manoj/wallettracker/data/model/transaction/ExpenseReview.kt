package com.manoj.wallettracker.data.model.transaction

import androidx.compose.runtime.Immutable

@Immutable
data class ExpenseReview(
    val balanceAmount: Long,
    val totalExpense: Long,
    val totalIncome: Long,
)