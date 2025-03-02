package com.manoj.wallettracker.constants

enum class CategoryType {
    FOOD, TRANSPORT,
    ENTERTAINMENT, BILLS, OTHERS
}

data class CategoryExpense(
    val category: CategoryType,
    val expense: Float,
    val totalBudget: Float
)