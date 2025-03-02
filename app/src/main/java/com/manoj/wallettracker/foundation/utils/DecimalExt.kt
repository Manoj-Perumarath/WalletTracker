package com.manoj.wallettracker.foundation.utils

import androidx.compose.ui.text.input.TextFieldValue
import java.math.BigDecimal

fun TextFieldValue.formatAsBigDecimal(): BigDecimal {
    return text.toBigDecimal()
}