package com.manoj.wallettracker.data.state.transaction

import androidx.compose.runtime.Immutable
import com.manoj.wallettracker.constants.CategoryType
import com.manoj.wallettracker.data.model.TransactionItem
import com.manoj.wallettracker.data.model.transaction.ExpenseReview

@Immutable
data class TransactionSummaryState(
    val expenseReview: ExpenseReview,
    val transactionItems: List<TransactionItem>,
    val latestTransactionItems: List<TransactionItem>,
    val budgetExpense: List<Triple<CategoryType, Long, Long>>,
) {
    companion object {
        fun initial() = TransactionSummaryState(
            expenseReview = ExpenseReview(0L, 0L, 0L),
            transactionItems = listOf(),
            latestTransactionItems = listOf(),
            budgetExpense = listOf(
                Triple(CategoryType.FOOD, 0L, 0L),
                Triple(CategoryType.TRANSPORT, 0L, 0L),
                Triple(CategoryType.BILLS, 0L, 0L),
                Triple(CategoryType.ENTERTAINMENT, 0L, 0L),
                Triple(CategoryType.OTHERS, 0L, 0L),
            )
        )
    }
}
