package com.manoj.wallettracker.data.ui.addedittx.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import com.manoj.wallettracker.R
import com.manoj.wallettracker.constants.CategoryType
import com.manoj.wallettracker.constants.TransactionType
import com.manoj.wallettracker.foundation.core.date.formatDateTime
import com.manoj.wallettracker.foundation.utils.formatAsBigDecimal
import com.manoj.wallettracker.ui.theme.Budget
import com.manoj.wallettracker.ui.theme.Expense
import com.manoj.wallettracker.ui.theme.Income
import java.math.BigDecimal
import java.time.LocalDateTime

@Immutable
data class TransactionState(
    val transactionType: TransactionType = TransactionType.EXPENSE,
    val categories: Map<Int, List<CategoryType>> = mapOf(),
    val categoryType: CategoryType = CategoryType.FOOD,
    val totalAmount: TextFieldValue = TextFieldValue(text = "0"),
    val note: TextFieldValue = TextFieldValue(),
    val transactionDate: LocalDateTime = LocalDateTime.now(),
    val transactionCreatedAt: LocalDateTime = LocalDateTime.now(),
    val transactionUpdatedAt: LocalDateTime? = null,
    val isEditMode: Boolean = false,
    val showDatePicker: Boolean = false,
)

@Composable
fun TransactionState.getTitle(): String {
    return if (isEditMode) {
        when (transactionType) {
            TransactionType.INCOME -> stringResource(R.string.edit_income)
            TransactionType.EXPENSE -> stringResource(R.string.edit_expense)
            TransactionType.BUDGET -> stringResource(R.string.edit_budget)
        }
    } else {
        stringResource(R.string.add_transaction)
    }
}

fun TransactionType.getTitle(): Int {
    return when (this) {
        TransactionType.INCOME -> R.string.income
        TransactionType.EXPENSE -> R.string.expense
        TransactionType.BUDGET -> R.string.budget
    }
}

fun getTransactionTypes(): List<TransactionType> {
    return listOf(
        TransactionType.EXPENSE,
        TransactionType.INCOME,
        TransactionType.BUDGET
    )
}

fun TransactionState.isValid(): Boolean {
    return totalAmount.formatAsBigDecimal() > BigDecimal.ZERO
}

fun getCategoryTypes(): List<CategoryType> {
    return listOf(
        CategoryType.FOOD,
        CategoryType.TRANSPORT,
        CategoryType.ENTERTAINMENT,
        CategoryType.BILLS,
        CategoryType.OTHERS,
    )
}

fun CategoryType.getCategoryName(): String {
    return when (this) {
        CategoryType.FOOD -> "FOOD"
        CategoryType.TRANSPORT -> "TRANSPORT"
        CategoryType.ENTERTAINMENT -> "ENTERTAINMENT"
        CategoryType.BILLS -> "BILLS"
        CategoryType.OTHERS -> "OTHERS"
    }
}

fun TransactionState.transactionDateDisplayable() = transactionDate.formatDateTime()

fun TransactionState.getAmountColor(): Color {
    return when (transactionType) {
        TransactionType.INCOME -> Income
        TransactionType.EXPENSE -> Expense
        TransactionType.BUDGET -> Budget
    }
}