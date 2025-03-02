package com.manoj.wallettracker.presentation.home

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AutoGraph
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.manoj.wallettracker.R
import com.manoj.wallettracker.constants.CategoryType
import com.manoj.wallettracker.constants.TransactionType
import com.manoj.wallettracker.data.model.TransactionItem
import com.manoj.wallettracker.data.model.getDateTimeDisplay
import com.manoj.wallettracker.data.model.transaction.ExpenseReview
import com.manoj.wallettracker.data.state.transaction.TransactionSummaryState
import com.manoj.wallettracker.data.ui.addedittx.state.getCategoryName
import com.manoj.wallettracker.foundation.layout.AmountLabel
import com.manoj.wallettracker.foundation.layout.ContentTitle
import com.manoj.wallettracker.foundation.layout.ContentTitle2
import com.manoj.wallettracker.foundation.layout.DateLabel
import com.manoj.wallettracker.foundation.layout.Headline1
import com.manoj.wallettracker.foundation.layout.Headline2
import com.manoj.wallettracker.foundation.layout.Icon
import com.manoj.wallettracker.foundation.layout.IconButton
import com.manoj.wallettracker.foundation.layout.PageLayout
import com.manoj.wallettracker.foundation.layout.TextButton
import com.manoj.wallettracker.foundation.layout.cellShape
import com.manoj.wallettracker.foundation.layout.paddingCell
import com.manoj.wallettracker.foundation.layout.shouldShowDivider
import com.manoj.wallettracker.presentation.home.vm.HomeViewModel
import com.manoj.wallettracker.ui.theme.Expense
import com.manoj.wallettracker.ui.theme.Income

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    onClickAddTransaction: () -> Unit,
    onGraphClick: () -> Unit,
    onSeeMoreLastTransactionClick: () -> Unit,
) {
    val state by homeViewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        Modifier.fillMaxSize(), containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        TransactionSummaryScreen(
            innerPadding,
            state,
            null,
            null,
            onGraphClick,
            onClickAddTransaction,
            {},
            onSeeMoreLastTransactionClick,
        )
    }
}

@Composable
fun TransactionSummaryScreen(
    paddingValues: PaddingValues,
    state: TransactionSummaryState,
    route: String?,
    arguments: Bundle?,
    onGraphClick: () -> Unit,
    onClickAddTransaction: () -> Unit,
    onLastTransactionItemClick: (Int?) -> Unit,
    onSeeMoreLastTransactionClick: () -> Unit,
) {

    TransactionSummaryScreen(
        state = state,
        onGraphClick = onGraphClick,
        onClickAddTransaction = onClickAddTransaction,
        onLastTransactionItemClick = { onLastTransactionItemClick(it.transactionId) },
        onSeeMoreLastTransactionClick = onSeeMoreLastTransactionClick,
    )
}

@Composable
private fun TransactionSummaryScreen(
    state: TransactionSummaryState,
    onGraphClick: () -> Unit,
    onClickAddTransaction: () -> Unit,
    onSeeMoreLastTransactionClick: () -> Unit,
    onLastTransactionItemClick: (TransactionItem) -> Unit,
) {
    PageLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        Header(
            onGraphClick = onGraphClick, onClickAddTransaction = onClickAddTransaction
        )
        Content(
            state = state,
            modifier = Modifier.wrapContentHeight(),
            onSeeMoreLastTransactionClick = onSeeMoreLastTransactionClick,
            onLastTransactionItemClick = onLastTransactionItemClick,
        )
        ExpensesList(
            state.budgetExpense
        )

    }
}

@Composable
private fun Header(
    onGraphClick: () -> Unit, onClickAddTransaction: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.Right,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onGraphClick, color = Color.Transparent) {
            Icon(imageVector = Icons.Rounded.AutoGraph)
        }

        IconButton(onClick = onClickAddTransaction, color = Color.Transparent) {
            Icon(imageVector = Icons.Rounded.Add)
        }
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier.fillMaxSize(),
    state: TransactionSummaryState,
    onSeeMoreLastTransactionClick: () -> Unit,
    onLastTransactionItemClick: (TransactionItem) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        item {
            MainTitleSection(
            )
        }

        item {
            SpacerSection()
        }

        item {
            ExpenseOverview(
                expenseReview = state.expenseReview
            )
        }

        item {
            SpacerSection()
        }

        LastTransactionCell(
            data = state.latestTransactionItems,
            onSeeMoreClick = onSeeMoreLastTransactionClick,
            onItemClick = onLastTransactionItemClick,
        )

        item {
            SpacerSection()
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun MainTitleSection(
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Headline1(
            text = stringResource(R.string.summary), modifier = Modifier
        )
    }
}

@Composable
private fun ExpenseOverview(
    expenseReview: ExpenseReview
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    shape = MaterialTheme.shapes.medium
                )
                .fillMaxWidth()
                .paddingCell()
        ) {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                ExpenseContent(
                    title = stringResource(R.string.income),
                    amount = expenseReview.totalIncome.toString(),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    amountColor = Color.Green
                )

                Box(
                    Modifier
                        .height(42.dp)
                        .width(1.dp)
                        .background(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                )

                ExpenseContent(
                    title = stringResource(R.string.balance),
                    amount = expenseReview.balanceAmount.toString(),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    amountColor = Color.Red
                )
            }
        }
    }
}

@Composable
private fun ExpenseContent(
    modifier: Modifier, title: String, amount: String, amountColor: Color
) {
    Column(modifier = modifier) {
        ContentTitle2(
            text = title, modifier = Modifier.padding(bottom = 2.dp)
        )
        AmountLabel(
            amount = amount, color = amountColor, symbol = "Rs."
        )
    }
}

@Composable
private fun TransactionItemCell(
    title: String,
    dateTime: String,
    amount: String,
    textColor: Color,
    amountColor: Color,
    shape: Shape,
    shouldShowDivider: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(shape)
            .clickable(onClick = onClick),
        shape = shape,
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, bottom = 2.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
            ) {
                ContentTitle(
                    text = title, modifier = Modifier.padding(end = 4.dp), color = textColor
                )

                DateLabel(
                    text = dateTime, color = textColor
                )
            }

            AmountLabel(
                amount = amount,
                symbol = "Rs",
                color = amountColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, bottom = 2.dp, end = 16.dp),
            )

            if (shouldShowDivider) {
                Divider(
                    needSpacer = !isSelected, color = if (isSelected) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    }
                )
            }

        }
    }
}


@Composable
private fun Empty(
    title: String, message: String
) {
    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
        Column(
            Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondary, shape = MaterialTheme.shapes.medium
                )
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            ContentTitle(
                text = title, modifier = Modifier.padding(bottom = 2.dp)
            )
            ContentTitle(
                text = message, color = MaterialTheme.colorScheme.onBackground.copy(0.1f)
            )
        }
    }
}

@Composable
private fun SpacerSection() {
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun SpacerHeadline2() {
    Spacer(Modifier.height(10.dp))
}

@Composable
private fun Divider(
    needSpacer: Boolean,
    color: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
) {
    Row {
        if (needSpacer) {
            Spacer(
                Modifier
                    .width(16.dp)
                    .height(1.dp)
                    .background(color = MaterialTheme.colorScheme.secondary)
            )
        }
        HorizontalDivider(color = color)
    }
}


private fun LazyListScope.LastTransactionCell(
    data: List<TransactionItem>,
    onSeeMoreClick: () -> Unit,
    onItemClick: (TransactionItem) -> Unit,
) {
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Headline2(text = stringResource(R.string.transaction_last))
            TextButton(
                text = stringResource(R.string.show_more),
                modifier = Modifier.align(Alignment.Bottom),
                onClick = onSeeMoreClick
            )
        }

        SpacerHeadline2()
    }

    if (data.isEmpty()) {
        item {
            Empty(
                title = stringResource(R.string.transaction_last_no_data_title),
                message = stringResource(R.string.transaction_last_no_data_message)
            )
        }
    } else {
        val size = data.size
        itemsIndexed(items = data, key = { _, item -> item.transactionId!! }) { index, item ->
            TransactionItemCell(
                title = item.categoryType.getCategoryName(),
                dateTime = item.getDateTimeDisplay(),
                amount = item.amount.toPlainString(),
                amountColor = item.getAmountColor(
                ),
                shape = cellShape(index, size),
                shouldShowDivider = shouldShowDivider(index, size),
                isSelected = item.isSelected,
                onClick = { onItemClick(item) },
                textColor = item.getTextColor()
            )
        }
    }
}

@Composable
fun ExpensesList(categoryExpenses: List<Triple<CategoryType, Long, Long>>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Headline1(
                text = stringResource(R.string.summary), modifier = Modifier
            )
        }
        items(categoryExpenses) { categoryExpense ->
            if (categoryExpense.second > 0)
                CategoryItem(categoryExpense)
        }
    }
}

@Composable
fun CategoryItem(categoryExpense: Triple<CategoryType, Long, Long>) {
    val progress: Float = categoryExpense.third.toFloat() / categoryExpense.second.toFloat()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = categoryExpense.first.name,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        // Progress Bar
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth(),
            color = when (categoryExpense.first) {
                CategoryType.FOOD -> Color.Green
                CategoryType.TRANSPORT -> Color.Blue
                CategoryType.ENTERTAINMENT -> Color.Yellow
                CategoryType.BILLS -> Color.Red
                CategoryType.OTHERS -> Color.Gray
            },
        )

        Text(
            text = stringResource(R.string.spent) + "${categoryExpense.third}" + stringResource(R.string.budget_slash) + "${categoryExpense.second}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}


fun TransactionItem.getAmountColor(): Color {
    return if (type == TransactionType.INCOME) {
        Income
    } else {
        Expense
    }
}

@Composable
fun TransactionItem.getTextColor(): Color {
    return if (isSelected) {
        MaterialTheme.colorScheme.tertiary
    } else {
        MaterialTheme.colorScheme.tertiary
    }
}