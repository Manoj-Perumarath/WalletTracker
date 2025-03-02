package com.manoj.wallettracker.presentation.alltx

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.manoj.wallettracker.R
import com.manoj.wallettracker.data.model.TransactionItem
import com.manoj.wallettracker.data.model.getDateTimeDisplay
import com.manoj.wallettracker.data.state.transaction.TransactionSummaryState
import com.manoj.wallettracker.data.ui.addedittx.state.getCategoryName
import com.manoj.wallettracker.foundation.layout.AmountLabel
import com.manoj.wallettracker.foundation.layout.ContentTitle
import com.manoj.wallettracker.foundation.layout.DateLabel
import com.manoj.wallettracker.foundation.layout.Empty
import com.manoj.wallettracker.foundation.layout.Headline1
import com.manoj.wallettracker.foundation.layout.PageLayout
import com.manoj.wallettracker.presentation.home.getAmountColor
import com.manoj.wallettracker.presentation.home.vm.HomeViewModel
import com.manoj.wallettracker.ui.theme.DividerAlpha
import com.manoj.wallettracker.ui.theme.WalletTrackerTheme

@Composable
fun AllTransactionScreen(
    viewModel: HomeViewModel,
    onTransactionItemClick: (Int) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold { _padding ->
        WalletTrackerTheme {
            AllTransactionScreen(
                _padding,
                state = state,
                onTransactionItemClick = {
                    onTransactionItemClick(it.transactionId!!)
                },
            )
        }
    }

}

@Composable
private fun AllTransactionScreen(
    paddingValues: PaddingValues,
    state: TransactionSummaryState,
    onTransactionItemClick: (TransactionItem) -> Unit,
) {
    PageLayout(
        modifier = Modifier.fillMaxSize()
    ) {

        Content(
            state = state,
            onTransactionItemClick = onTransactionItemClick,
        )
    }
}

@Composable
private fun Content(
    state: TransactionSummaryState,
    onTransactionItemClick: (TransactionItem) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            Headline1(
                text = stringResource(R.string.all_transactions),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }

        item {
            SpacerSection()
        }

        TransactionCell(
            data = state.transactionItems,
            onItemClick = onTransactionItemClick,
        )

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

private fun LazyListScope.TransactionCell(
    data: List<TransactionItem>,
    onItemClick: (TransactionItem) -> Unit,
) {
    if (data.isEmpty()) {
        item {
            Empty(
                stringResource(R.string.no_transactions),
                modifier = Modifier.height(500.dp)
            )
        }
    } else {
        itemsIndexed(
            items = data,
            key = { _, item -> item.transactionId!! }
        ) { _, item ->
            TransactionItemCell(
                title = item.categoryType.getCategoryName(),
                dateTime = item.getDateTimeDisplay(),
                amount = item.amount.toPlainString(),
                amountColor = item.getAmountColor(
                ),
                onClick = { onItemClick(item) }
            )
        }
    }
}

@Composable
private fun TransactionItemCell(
    title: String,
    dateTime: String,
    amount: String,
    amountColor: Color,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(
                MaterialTheme.colorScheme.secondaryContainer
            ),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.secondaryContainer
                    )
                    .padding(start = 16.dp, top = 8.dp, bottom = 2.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
            ) {
                ContentTitle(
                    text = title,
                    modifier = Modifier.padding(end = 4.dp)
                )

                DateLabel(
                    text = dateTime,
                )
            }

            AmountLabel(
                amount = amount,
                symbol = "Rs. ",
                color = amountColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.secondaryContainer
                    )
                    .padding(start = 16.dp, bottom = 2.dp, end = 16.dp),
            )

            HorizontalDivider(
                modifier = Modifier.padding(start = 16.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = DividerAlpha)
            )
        }
    }
}

@Composable
private fun SpacerSection() {
    Spacer(modifier = Modifier.height(16.dp))
}
