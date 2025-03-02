package com.manoj.wallettracker.data.ui.addedittx.repoimpl

import com.manoj.wallettracker.data.model.transaction.Transaction
import com.manoj.wallettracker.data.ui.addedittx.repo.TransactionRepo
import com.manoj.wallettracker.data.usecase.WalletUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import java.time.LocalDateTime
import javax.inject.Inject

class TransactionRepoImpl @Inject constructor(private val walletUseCase: WalletUseCase) :
    TransactionRepo {


    override suspend fun saveTransaction(transaction: Transaction) {
        return if (transaction.id == null) {
            insertNewTransaction(
                newTransaction = transaction
            )
        } else {
            updateTransaction(
                transaction = transaction
            )
        }
    }

    override fun getTransaction(transactionId: Int): Flow<Transaction> {
       return  walletUseCase.getTransaction(transactionId)
    }

    private suspend fun insertNewTransaction(
        newTransaction: Transaction
    ) {
        walletUseCase.insertTransaction(
            transaction = newTransaction.copy(
                createdAt = LocalDateTime.now(),
                amount = newTransaction.amount,
            )
        )
    }

    private suspend fun updateTransaction(
        transaction: Transaction
    ) {
        walletUseCase.updateTransaction(
            transaction = transaction.copy(
                createdAt = LocalDateTime.now(),
                amount = transaction.amount,
                id = transaction.id,
                updatedAt = transaction.updatedAt,
                type = transaction.type,
                categoryType = transaction.categoryType
            )
        )
    }


    override suspend fun deleteTransaction(id: Int) {
        walletUseCase.deleteTransaction(id)
    }
}