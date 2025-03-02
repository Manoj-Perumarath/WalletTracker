package com.manoj.wallettracker.data.room.converters

import com.manoj.wallettracker.data.model.TransactionItem
import com.manoj.wallettracker.data.model.transaction.Transaction
import com.manoj.wallettracker.data.room.model.TransactionDb


fun Transaction.toTransactionDb(): TransactionDb {
    return TransactionDb(
        id = id,
        categoryType = categoryType,
        amount = amount.toLong(),
        type = type,
        date = date,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}

fun List<TransactionDb>.toTransactionItem() = map {
    it.toTransactionItem()
}

fun TransactionDb.toTransactionItem() = TransactionItem(
    categoryType = categoryType,
    amount = amount.toBigDecimal(),
    transactionId = id,
    date = date,
    type = type,
    isSelected = false,
)

fun TransactionDb.toTransaction() = Transaction(
    categoryType = categoryType,
    amount = amount.toBigDecimal(),
    id = id,
    date = date,
    type = type,
    updatedAt = updatedAt,
    createdAt = createdAt,
)
