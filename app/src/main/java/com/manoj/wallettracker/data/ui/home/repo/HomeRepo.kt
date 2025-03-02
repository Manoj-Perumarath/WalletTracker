package com.manoj.wallettracker.data.ui.home.repo

import com.manoj.wallettracker.constants.CategoryType
import com.manoj.wallettracker.data.model.TransactionItem
import kotlinx.coroutines.flow.Flow

interface HomeRepo {
    fun getLastTransaction(): Flow<List<TransactionItem>>
    fun getAllTransactions(): Flow<List<TransactionItem>>
    fun getIncomeStatus(): Flow<Pair<Long, Long>>
    fun getBudgetExpenses(): Flow<List<Triple<CategoryType, Long,Long>>>

}