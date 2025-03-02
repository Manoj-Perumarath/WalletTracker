package com.manoj.wallettracker.presentation.addedittx.action

import androidx.compose.ui.text.input.TextFieldValue
import com.manoj.wallettracker.constants.CategoryType
import com.manoj.wallettracker.constants.TransactionType
import java.time.LocalDateTime

interface TransactionAction {

    object Save : TransactionAction
    object Delete : TransactionAction
    data class SelectDate(val selectedDate: LocalDateTime?) : TransactionAction
    data class SelectTransactionType(val type: TransactionType) : TransactionAction
    object DismissDatePicker : TransactionAction
    object ShowDatePicker : TransactionAction
    sealed interface TotalAmountAction : TransactionAction {
        data class Change(val totalAmount: TextFieldValue) : TotalAmountAction
    }
    data class SelectCategory(val selectedCategoryType: CategoryType) : TransactionAction
}