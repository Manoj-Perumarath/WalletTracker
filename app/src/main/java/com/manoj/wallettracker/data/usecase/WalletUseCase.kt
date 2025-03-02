package com.manoj.wallettracker.data.usecase

import com.manoj.wallettracker.constants.CategoryType
import com.manoj.wallettracker.constants.TransactionType
import com.manoj.wallettracker.data.model.TransactionItem
import com.manoj.wallettracker.data.model.transaction.Transaction
import com.manoj.wallettracker.data.room.converters.toTransaction
import com.manoj.wallettracker.data.room.converters.toTransactionDb
import com.manoj.wallettracker.data.room.converters.toTransactionItem
import com.manoj.wallettracker.data.room.dao.WalletTrackerDao
import com.manoj.wallettracker.foundation.di.DispatcherModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Named

class WalletUseCase @Inject constructor(
    @Named(DispatcherModule.BACKGROUND) private val dispatcher: CoroutineDispatcher,
    private val walletTrackerDao: WalletTrackerDao
) {

    suspend fun insertTransaction(transaction: Transaction) {
        withContext(dispatcher) {
            walletTrackerDao.insertTransaction(transaction.toTransactionDb())
        }
    }

    suspend fun updateTransaction(transaction: Transaction) {
        withContext(dispatcher) {
            walletTrackerDao.updateTransaction(transaction.toTransactionDb())
        }
    }

    suspend fun deleteTransaction(id: Int) {
        return withContext(dispatcher) {
            walletTrackerDao.deleteTransaction(id)

        }
    }

    fun getLatestTransactions(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        transactionType: TransactionType,
        limit: Int
    ): Flow<List<TransactionItem>> {
        return walletTrackerDao.getTransactions(startDate, endDate, transactionType, limit)
            .map { it.toTransactionItem() }
            .flowOn(dispatcher)
    }

    fun getAllTransactions(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        transactionType: TransactionType,
    ): Flow<List<TransactionItem>> {
        return walletTrackerDao.getTransactions(startDate, endDate, transactionType)
            .map { it.toTransactionItem() }
            .flowOn(dispatcher)
    }


    fun getTransactions(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        transactionType: TransactionType,
    ): Flow<List<TransactionItem>> {
        return walletTrackerDao.getTransactions(startDate, endDate, transactionType)
            .map { it.toTransactionItem() }
            .flowOn(dispatcher)
    }

    fun getTransactions(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        transactionType: TransactionType,
        categoryType: CategoryType
    ): Flow<List<TransactionItem>> {
        return walletTrackerDao.getTransactions(startDate, endDate, transactionType, categoryType)
            .map { it.toTransactionItem() }
            .flowOn(dispatcher)
    }

    fun getTransactions(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        categoryType: CategoryType,
    ): Flow<List<TransactionItem>> {
        return walletTrackerDao.getTransactions(startDate, endDate, categoryType)
            .map { it.toTransactionItem() }
            .flowOn(dispatcher)
    }

    fun getTransaction(
        id: Int
    ): Flow<Transaction> {
        return walletTrackerDao.getTransaction(id).map { it.toTransaction() }
            .flowOn(dispatcher)
    }
}