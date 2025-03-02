package com.manoj.wallettracker.presentation.graph

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.manoj.wallettracker.constants.CategoryType
import com.manoj.wallettracker.presentation.home.vm.HomeViewModel


@Composable
fun ExpenseGraphScreen(viewModel: HomeViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ExpenseGraph(state.budgetExpense, state.expenseReview.totalIncome)
}@Composable
private fun ExpenseGraph(expenses: List<Triple<CategoryType, Long, Long>>, income: Long) {
    if (income <= 0) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Please add income and budgets to view",
                style = TextStyle(fontStyle = FontStyle.Normal, fontWeight = FontWeight.Bold)
            )
        }
    } else {
        val maxExpense = maxOf(expenses.maxOfOrNull { it.third } ?: 0L, income)
        val budgetDivisions = 5
        val paddingHorizontal = 120f
        val paddingVertical = 20f
        val barWidth = (LocalDensity.current.run { LocalConfiguration.current.screenWidthDp.dp.toPx() } - paddingHorizontal * 2 - 80f) / expenses.size
        val spaceBetweenBars = 20f

        Canvas(modifier = Modifier.fillMaxSize()) {
            val topPadding = 40f
            val graphHeight = size.height - topPadding - 100f - paddingVertical
            val graphWidth = size.width - paddingHorizontal * 2
            val barStartX = paddingHorizontal

            drawContext.canvas.nativeCanvas.save()
            drawContext.canvas.nativeCanvas.rotate(270f, paddingHorizontal - 60f, graphHeight / 2)
            drawContext.canvas.nativeCanvas.drawText("Amount", paddingHorizontal - 60f, graphHeight / 2, Paint().apply {
                color = android.graphics.Color.BLACK
                textAlign = Paint.Align.CENTER
                textSize = 40f
            })
            drawContext.canvas.nativeCanvas.restore()

            drawContext.canvas.nativeCanvas.drawText("Category", graphWidth / 2 + paddingHorizontal, graphHeight + 80f, Paint().apply {
                color = android.graphics.Color.BLACK
                textAlign = Paint.Align.CENTER
                textSize = 40f
            })

            val divisionHeight = graphHeight / budgetDivisions
            for (i in 0..budgetDivisions) {
                val yPosition = graphHeight - (i * divisionHeight)
                drawLine(color = Color.Gray, start = Offset(paddingHorizontal, yPosition), end = Offset(graphWidth + paddingHorizontal, yPosition), strokeWidth = 1f)
                drawContext.canvas.nativeCanvas.drawText("$${(maxExpense / budgetDivisions * i).toInt()}", paddingHorizontal - 50f, yPosition + 10f, Paint().apply {
                    color = android.graphics.Color.BLACK
                    textAlign = Paint.Align.RIGHT
                    textSize = 20f
                })
            }

            expenses.forEachIndexed { index, expense ->
                val barHeight = (expense.third.toFloat() / maxExpense.toFloat()) * graphHeight
                val xPosition = barStartX + (index * (barWidth + spaceBetweenBars))
                val yPosition = graphHeight - barHeight

                drawRect(color = Color.Cyan, topLeft = Offset(xPosition, yPosition), size = Size(barWidth, barHeight))
                drawContext.canvas.nativeCanvas.drawText(expense.first.name, xPosition + barWidth / 2, graphHeight + 20f, Paint().apply {
                    color = android.graphics.Color.BLACK
                    textAlign = Paint.Align.CENTER
                    textSize = 20f
                })
                drawContext.canvas.nativeCanvas.drawText("Rs. ${expense.third}", xPosition + barWidth / 2, yPosition - 10f, Paint().apply {
                    color = android.graphics.Color.BLACK
                    textAlign = Paint.Align.CENTER
                    textSize = 20f
                })
            }

            drawLine(color = Color.Black, start = Offset(paddingHorizontal, graphHeight), end = Offset(graphWidth + paddingHorizontal, graphHeight), strokeWidth = 2f)
            drawLine(color = Color.Black, start = Offset(paddingHorizontal, 0f), end = Offset(paddingHorizontal, graphHeight), strokeWidth = 2f)

            expenses.forEachIndexed { index, expense ->
                val xPosition = paddingHorizontal + (index * (barWidth + spaceBetweenBars)) + barWidth / 2
                drawContext.canvas.nativeCanvas.drawText("Rs. ${expense.third}", xPosition, graphHeight + 50f, Paint().apply {
                    color = android.graphics.Color.BLACK
                    textAlign = Paint.Align.CENTER
                    textSize = 20f
                })
            }

            val finalIncomeX = paddingHorizontal + (expenses.size * (barWidth + spaceBetweenBars)) + barWidth / 2
            drawContext.canvas.nativeCanvas.drawText("$income", finalIncomeX, graphHeight + 50f, Paint().apply {
                color = android.graphics.Color.BLACK
                textAlign = Paint.Align.CENTER
                textSize = 20f
            })
        }
    }
}