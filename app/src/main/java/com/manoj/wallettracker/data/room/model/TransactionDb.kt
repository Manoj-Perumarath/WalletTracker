package com.manoj.wallettracker.data.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.manoj.wallettracker.constants.CategoryType
import com.manoj.wallettracker.constants.TransactionType
import java.time.LocalDateTime

@Entity
data class TransactionDb(
    @PrimaryKey
    @ColumnInfo(name = "transaction_id")
    val id: Int? = null,
    @ColumnInfo(name = "transaction_categoryType")
    val categoryType: CategoryType,
    @ColumnInfo(name = "transaction_amount")
    val amount: Long,
    @ColumnInfo(name = "transaction_type")
    val type: TransactionType,
    @ColumnInfo(name = "transaction_date")
    val date: LocalDateTime,
    @ColumnInfo(name = "transaction_createdAt")
    val createdAt: LocalDateTime,
    @ColumnInfo(name = "transaction_updatedAt")
    val updatedAt: LocalDateTime? = null
)
