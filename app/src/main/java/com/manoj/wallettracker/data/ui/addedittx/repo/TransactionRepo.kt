package com.manoj.wallettracker.data.ui.addedittx.repo

import com.manoj.wallettracker.data.model.TransactionItem
import com.manoj.wallettracker.data.model.transaction.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepo {

    suspend fun saveTransaction(transaction: Transaction)
    suspend fun deleteTransaction(id: Int)
    fun getTransaction(transactionId: Int): Flow<Transaction>
}