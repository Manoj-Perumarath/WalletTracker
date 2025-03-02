package com.manoj.wallettracker.presentation.addedittx.vm

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.manoj.wallettracker.constants.AppConstants
import com.manoj.wallettracker.constants.CategoryType
import com.manoj.wallettracker.constants.TransactionType
import com.manoj.wallettracker.data.model.transaction.Transaction
import com.manoj.wallettracker.data.ui.addedittx.effect.TransactionEffect
import com.manoj.wallettracker.data.ui.addedittx.repo.TransactionRepo
import com.manoj.wallettracker.data.ui.addedittx.repoimpl.TransactionRepoImpl
import com.manoj.wallettracker.data.ui.addedittx.state.TransactionState
import com.manoj.wallettracker.foundation.core.vm.BaseViewModel
import com.manoj.wallettracker.foundation.utils.formatAsBigDecimal
import com.manoj.wallettracker.presentation.addedittx.action.TransactionAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val transactionRepoImpl: TransactionRepoImpl
) : BaseViewModel<TransactionState, TransactionAction, TransactionEffect, TransactionRepo>(
    TransactionState(), transactionRepoImpl
) {

    private val transactionId =
        if (!savedStateHandle.get<String>(AppConstants.ARG_TRANSACTION_ID).isNullOrEmpty()) {
            savedStateHandle.get<String>(AppConstants.ARG_TRANSACTION_ID)!!.toInt()
        } else {
            null
        }

    init {
        fetchCategories()
        initSaveAction()
        getTransactionDetails()
    }

    private fun getTransactionDetails() {
        viewModelScope.launch {
            if (transactionId.toString().isNotEmpty()) {
                if (transactionId != null) {
                    repository.getTransaction(transactionId)
                        .onStart { setState { copy(isEditMode = true) } }
                        .collect {
                            setState {
                                copy(
                                    transactionType = it.type,
                                    categoryType = it.categoryType,
                                    totalAmount = TextFieldValue(it.amount.toString(), TextRange(8)),
                                    transactionDate = it.date,
                                    transactionCreatedAt = it.createdAt,
                                    transactionUpdatedAt = it.updatedAt
                                )
                            }
                        }
                }
            }
        }
    }

    private fun initSaveAction() {

    }

    private fun fetchCategories() {
        viewModelScope.launch {
            if (transactionId != null) {
                setState {
                    copy(
                        transactionType = TransactionType.EXPENSE,
                        categoryType = CategoryType.FOOD,
                        totalAmount = TextFieldValue("0", TextRange(8)),
                        transactionDate = LocalDateTime.now(),
                        transactionCreatedAt = LocalDateTime.now(),
                        transactionUpdatedAt = null
                    )
                }
            }
        }
    }

    override fun dispatch(action: TransactionAction) {
        when (action) {
            TransactionAction.Save -> {
                viewModelScope.launch {
                    transactionRepoImpl.saveTransaction(
                        Transaction(
                            id = transactionId,
                            amount = state.value.totalAmount.formatAsBigDecimal(),
                            date = state.value.transactionDate,
                            type = state.value.transactionType,
                            categoryType = state.value.categoryType,
                            createdAt = state.value.transactionCreatedAt,
                            updatedAt = state.value.transactionUpdatedAt,
                        )
                    )
                    setEffect(TransactionEffect.ClosePage)
                }
            }

            TransactionAction.Delete -> {
                viewModelScope.launch {
                    transactionId?.let { repository.deleteTransaction(it) }
                    setEffect(TransactionEffect.ClosePage)
                }
            }

            is TransactionAction.SelectTransactionType -> {
                viewModelScope.launch {
                    setState { copy(transactionType = action.type) }
                }
            }

            is TransactionAction.SelectDate -> {
                viewModelScope.launch {
                    if (action.selectedDate != null) {
                        setState {
                            copy(
                                showDatePicker = false,
                                transactionDate = LocalDateTime.of(
                                    action.selectedDate.toLocalDate(),
                                    LocalTime.now()
                                )
                            )
                        }
                    }
                }
            }

            TransactionAction.DismissDatePicker -> {
                viewModelScope.launch {
                    setState { copy(showDatePicker = false) }
                }
            }

            TransactionAction.ShowDatePicker -> {
                viewModelScope.launch {
                    setState { copy(showDatePicker = true) }
                }
            }

            is TransactionAction.TotalAmountAction.Change -> {
                viewModelScope.launch {
                    val amount = action.totalAmount.text.toBigDecimal()
                    if (amount <= 1000000.toBigDecimal()) {
                        setState {
                            copy(
                                totalAmount = action.totalAmount.copy(
                                    text = amount.toString(),
                                    selection = TextRange(amount.toString().length)
                                )
                            )
                        }
                    }
                }
            }

            is TransactionAction.SelectCategory -> {
                viewModelScope.launch {
                    setState { copy(categoryType = action.selectedCategoryType) }
                }
            }
        }
    }

}