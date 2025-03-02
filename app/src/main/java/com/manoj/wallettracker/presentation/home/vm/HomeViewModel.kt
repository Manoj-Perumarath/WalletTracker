package com.manoj.wallettracker.presentation.home.vm

import androidx.lifecycle.viewModelScope
import com.manoj.wallettracker.data.model.transaction.ExpenseReview
import com.manoj.wallettracker.data.state.transaction.TransactionSummaryState
import com.manoj.wallettracker.data.ui.home.repo.HomeRepo
import com.manoj.wallettracker.data.ui.home.repoimpl.HomeRepoImpl
import com.manoj.wallettracker.foundation.core.vm.BaseViewModel
import com.manoj.wallettracker.presentation.home.action.HomeAction
import com.manoj.wallettracker.presentation.home.effect.HomeEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(homeRepoImpl: HomeRepoImpl) :
    BaseViewModel<TransactionSummaryState, HomeAction, HomeEffect, HomeRepo>(
        TransactionSummaryState.initial(), homeRepoImpl
    ) {

    init {
        initLoadLastTransaction()
        getIncomeExpenseStatus()
        getBudgetExpenses()
    }

    private fun getBudgetExpenses() {
        viewModelScope.launch {
            repository.getBudgetExpenses().collect { items ->
                setState {
                    copy(
                        budgetExpense = items
                    )
                }
            }
        }
    }

    private fun getIncomeExpenseStatus() {
        viewModelScope.launch {
            repository.getIncomeStatus().collect { (expense, income) ->
                setState {
                    copy(
                        expenseReview = ExpenseReview(
                            totalExpense = expense,
                            totalIncome = income,
                            balanceAmount = income - expense,
                        )
                    )
                }
            }
        }
    }

    override fun dispatch(action: HomeAction) {

    }

    private fun initLoadLastTransaction() {
        viewModelScope.launch {
            repository.getLastTransaction().collect {
                setState { copy(latestTransactionItems = it) }
            }
        }
        viewModelScope.launch {
            repository.getAllTransactions().collect {
                setState { copy(transactionItems = it) }
            }
        }

    }

}