package com.manoj.wallettracker.data.state.transaction

import androidx.compose.runtime.Immutable
import com.manoj.wallettracker.data.model.TransactionItem

@Immutable
data class TransactionState(
    val transactionItems: List<TransactionItem> = listOf(),
)
