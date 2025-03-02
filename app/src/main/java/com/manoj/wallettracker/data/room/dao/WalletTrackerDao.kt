package com.manoj.wallettracker.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.manoj.wallettracker.constants.CategoryType
import com.manoj.wallettracker.constants.TransactionType
import com.manoj.wallettracker.data.room.model.TransactionDb
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime


@Dao
interface WalletTrackerDao {

    @Query(
        "SELECT * FROM TransactionDb   WHERE TransactionDb.transaction_id = :id"
    )
    fun getTransaction(
        id: Int
    ): Flow<TransactionDb>

    @Insert
    abstract suspend fun insertTransaction(data: TransactionDb)

    @Update
    abstract suspend fun updateTransaction(data: TransactionDb)

    @Query("DELETE FROM TransactionDb WHERE transaction_id = :id")
    abstract fun deleteTransaction(id: Int)


    @Query(
        """
            SELECT * FROM TransactionDb
            WHERE TransactionDb.transaction_date BETWEEN :startDate AND :endDate
            AND transaction_type = :type
            ORDER BY TransactionDb.transaction_date DESC
               LIMIT :limit
     """
    )
    fun getTransactions(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        type: TransactionType,
        limit: Int
    ): Flow<List<TransactionDb>>

    @Query(
        """
            SELECT * FROM TransactionDb
            WHERE TransactionDb.transaction_date BETWEEN :startDate AND :endDate
            AND transaction_type = :type
            AND transaction_categoryType = :categoryType
            ORDER BY TransactionDb.transaction_date DESC
     """
    )
    fun getTransactions(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        type: TransactionType,
        categoryType: CategoryType,
    ): Flow<List<TransactionDb>>

    @Query(
        """
            SELECT * FROM TransactionDb
            WHERE TransactionDb.transaction_date BETWEEN :startDate AND :endDate
            AND transaction_type = :type
   ORDER BY TransactionDb.transaction_date DESC
        """
    )
    fun getTransactions(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        type: TransactionType
    ): Flow<List<TransactionDb>>

    @Query(
        """
            SELECT * FROM TransactionDb
            WHERE TransactionDb.transaction_date BETWEEN :startDate AND :endDate
            AND transaction_categoryType = :categoryType
   ORDER BY TransactionDb.transaction_date DESC
        """
    )
    fun getTransactions(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        categoryType: CategoryType
    ): Flow<List<TransactionDb>>

}