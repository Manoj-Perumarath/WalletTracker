package com.manoj.wallettracker.foundation.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp


@Composable
fun ModalTitle(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Unspecified
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Center),
            color = textColor,
        )
    }
}

@Composable
fun TitleBar(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier,
        style = MaterialTheme.typography.titleSmall.copy(
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium
        ),
        text = text
    )
}

@Composable
fun TitleBarPrimary(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier,
        style = MaterialTheme.typography.titleSmall.copy(
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium
        ),
        text = text
    )
}

@Composable
fun Headline1(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        style = MaterialTheme.typography.titleLarge,
        text = text,
        modifier = modifier
    )
}

@Composable
fun Headline2(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        style = MaterialTheme.typography.titleMedium,
        text = text,
        modifier = modifier
    )
}

@Composable
fun HeadlineLabel(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)),
        text = text.uppercase(),
        modifier = modifier
    )
}

@Composable
fun ContentTitle(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    fontWeight: FontWeight = FontWeight.Normal,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
) {
    Text(
        style = MaterialTheme.typography.titleSmall.copy(color = color, fontWeight = fontWeight),
        text = text,
        modifier = modifier,
        maxLines = maxLines,
        overflow = overflow,
    )
}

@Composable
fun ContentTitle2(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
) {
    Text(
        style = MaterialTheme.typography.bodyLarge.copy(color = color),
        text = text,
        modifier = modifier
    )
}

@Composable
fun TabLabel(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        style = MaterialTheme.typography.labelMedium,
        text = text,
        modifier = modifier
    )
}

@Composable
fun AmountLabel(
    modifier: Modifier = Modifier,
    amount: String,
    symbol: String,
    color: Color = MaterialTheme.colorScheme.onBackground,
) {
    AmountLabel(
        modifier = modifier,
        amount = amount,
        amountFontSize = 20.sp,
        symbol = symbol,
        symbolFontSize = 14.sp,
        color = color
    )
}

private const val TEXT_SCALE_REDUCTION_INTERVAL = 0.9f

@Composable
fun AmountLabel(
    modifier: Modifier = Modifier,
    amount: String,
    amountFontSize: TextUnit,
    symbol: String,
    symbolFontSize: TextUnit,
    color: Color = MaterialTheme.colorScheme.onBackground,
) {
    var textSize by remember { mutableStateOf(amountFontSize) }
    var symbolTextSize by remember { mutableStateOf(symbolFontSize) }

    Text(
        style = MaterialTheme.typography.headlineMedium.copy(color = color, fontSize = textSize),
        text = AnnotatedString(
            text = amount,
            spanStyles = listOf(
                AnnotatedString.Range(
                    SpanStyle(fontSize = symbolTextSize),
                    amount.indexOf(symbol),
                    amount.indexOf(symbol) + symbol.length
                )
            )
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier,
        onTextLayout = { textLayoutResult ->
            val maxCurrentLineIndex: Int = textLayoutResult.lineCount - 1
            if (textLayoutResult.isLineEllipsized(maxCurrentLineIndex)) {
                textSize = textSize.times(TEXT_SCALE_REDUCTION_INTERVAL)
                symbolTextSize = symbolTextSize.times(TEXT_SCALE_REDUCTION_INTERVAL)
            }
        },
    )
}

@Composable
fun ErrorLabel(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.error),
        text = text,
        modifier = modifier
    )
}

@Composable
fun DateLabel(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colorScheme.onBackground,
) {
    Text(
        style = MaterialTheme.typography.bodySmall.copy(color = color.copy(alpha = 0.5f)),
        text = text,
        modifier = modifier
    )
}

