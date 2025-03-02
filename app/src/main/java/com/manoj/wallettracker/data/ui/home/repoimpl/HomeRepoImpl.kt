package com.manoj.wallettracker.data.ui.home.repoimpl

import com.manoj.wallettracker.constants.CategoryType
import com.manoj.wallettracker.constants.TransactionType
import com.manoj.wallettracker.data.model.TransactionItem
import com.manoj.wallettracker.data.ui.home.repo.HomeRepo
import com.manoj.wallettracker.data.usecase.WalletUseCase
import com.manoj.wallettracker.foundation.core.date.generateThisMonthDateTimeRange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDateTime
import javax.inject.Inject

class HomeRepoImpl @Inject constructor(private val walletUseCase: WalletUseCase) : HomeRepo {
    override fun getLastTransaction(): Flow<List<TransactionItem>> {
        val (startDate, endDate) = LocalDateTime.now().generateThisMonthDateTimeRange()
        return walletUseCase.getLatestTransactions(
            startDate = startDate,
            endDate = endDate,
            limit = 2,
            transactionType = TransactionType.EXPENSE
        )
    }
   override fun getAllTransactions(): Flow<List<TransactionItem>> {
        val (startDate, endDate) = LocalDateTime.now().generateThisMonthDateTimeRange()
        return walletUseCase.getAllTransactions(
            startDate = startDate,
            endDate = endDate,
            transactionType = TransactionType.EXPENSE
        )
    }

    override fun getIncomeStatus(): Flow<Pair<Long, Long>> {
        val (startDate, endDate) = LocalDateTime.now().generateThisMonthDateTimeRange()
        return combine(
            walletUseCase.getTransactions(
                startDate = startDate, endDate = endDate, transactionType = TransactionType.EXPENSE
            ),
            walletUseCase.getTransactions(
                startDate = startDate, endDate = endDate, transactionType = TransactionType.INCOME
            ),
        ) { expenseList, incomeList ->
            Pair(expenseList.sumOf { it.amount.toLong() }, incomeList.sumOf { it.amount.toLong() })
        }
    }

    override fun getBudgetExpenses(): Flow<List<Triple<CategoryType, Long, Long>>> {
        val (startDate, endDate) = LocalDateTime.now().generateThisMonthDateTimeRange()
        return combine(
            getBudgetExpense(CategoryType.FOOD),
            getBudgetExpense(CategoryType.BILLS),
            getBudgetExpense(CategoryType.ENTERTAINMENT),
            getBudgetExpense(CategoryType.TRANSPORT),
            getBudgetExpense(CategoryType.OTHERS)
        ) { foods, bills, entertainment, transport, others ->
            listOf(
                foods,
                bills,
                entertainment,
                transport,
                others
            )
        }
    }

    private fun getBudgetExpense(categoryType: CategoryType): Flow<Triple<CategoryType, Long, Long>> {
        val (startDate, endDate) = LocalDateTime.now().generateThisMonthDateTimeRange()
        return combine(
            walletUseCase.getTransactions(
                startDate = startDate,
                endDate = endDate,
                transactionType = TransactionType.BUDGET,
                categoryType = categoryType
            ),
            walletUseCase.getTransactions(
                startDate = startDate, endDate = endDate, categoryType = categoryType, transactionType = TransactionType.EXPENSE
            ),
            { item, itemExp ->
                Triple(
                    categoryType,
                    item.sumOf { it.amount.toLong() },
                    itemExp.sumOf { it.amount.toLong() })
            })
    }
}