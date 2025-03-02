package com.manoj.wallettracker.foundation.layout

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.manoj.wallettracker.R
import com.manoj.wallettracker.ui.theme.AlphaDisabled
import com.manoj.wallettracker.ui.theme.AlphaHigh
import com.manoj.wallettracker.ui.theme.DividerAlpha
import com.manoj.wallettracker.ui.theme.MediumRadius

@Composable
fun PageLayout(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        content = content
    )
}

@Composable
fun ModalLayout(
    title: @Composable () -> Unit, modifier: Modifier = Modifier, content: LazyListScope.() -> Unit
) {
    ModalLazyColumn(modifier) {
        item {
            Spacer(Modifier.height(24.dp))
            title()
            Spacer(Modifier.height(24.dp))
        }

        content()

        item {
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
fun ModalLazyColumn(
    modifier: Modifier = Modifier, shape: Shape = RectangleShape, content: LazyListScope.() -> Unit
) {
    Box(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.background, shape = shape
        )
    ) {
        LazyColumn(
            modifier = modifier
                .navigationBarsPadding()
                .imePadding(), content = content
        )
    }
}

@Composable
fun ModalRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    content: @Composable RowScope.() -> Unit
) {
    Box(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.background
        )
    ) {
        Row(
            modifier = modifier
                .navigationBarsPadding()
                .imePadding(),
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment,
            content = content
        )
    }
}

@Composable
fun ModalVerticalListLayout(
    count: Int,
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    content: LazyGridScope.() -> Unit
) {

}

@Composable
fun ModalVerticalGridLayout(
    count: Int,
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    content: LazyGridScope.() -> Unit
) {
    Column(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.background, shape = shape
        )
    ) {
        Spacer(Modifier.height(24.dp))
        title()
        Spacer(Modifier.height(24.dp))

        ModalLazyVerticalGrid(count, modifier) {
            content()

            item {
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun ModalLazyVerticalGrid(
    count: Int,
    modifier: Modifier = Modifier,
    content: LazyGridScope.() -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count),
        contentPadding = PaddingValues(horizontal = 8.dp),
        modifier = modifier
            .navigationBarsPadding()
            .imePadding(),
        content = content
    )
}

@Composable
fun Empty(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(modifier.fillMaxWidth()) {
        Text(
            text,
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
        )
    }
}

@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: Color = MaterialTheme.colorScheme.secondary,
    content: @Composable () -> Unit
) {
    val shape = CircleShape
    IconButton(
        onClick = onClick, modifier = modifier
            .background(
                color = color, shape = shape
            )
            .clip(shape), enabled = enabled
    ) {
        content()
    }
}

@Composable
fun Icon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    tint: Color = LocalContentColor.current,
) {
    Icon(
        imageVector = imageVector, contentDescription = "", tint = tint, modifier = modifier
    )
}

private enum class PressState { Pressed, Released }

@Composable
fun TextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    color: Color = MaterialTheme.colorScheme.primary,
    fontWeight: FontWeight = FontWeight.Normal
) {
    var currentState: PressState by remember { mutableStateOf(PressState.Released) }
    val transition = updateTransition(targetState = currentState, label = "animation")
    val alpha: Float by transition.animateFloat(label = "") { state ->
        if (state == PressState.Pressed || !enabled) {
            AlphaDisabled
        } else {
            AlphaHigh
        }
    }

    ContentTitle(text = text,
        color = color.copy(alpha),
        fontWeight = fontWeight,
        modifier = modifier.pointerInput(enabled) {
            detectTapGestures(onPress = {
                if (enabled) {
                    currentState = PressState.Pressed
                    tryAwaitRelease()
                    currentState = PressState.Released
                }
            }, onTap = {
                if (enabled) {
                    onClick()
                }
            })
        })
}

@Stable
fun Modifier.paddingCell() = this.padding(horizontal = 16.dp, vertical = 8.dp)

private val LinearIndicatorWidth = 240.dp
private val LinearIndicatorHeight = ProgressIndicatorDefaults.StrokeWidth

@Composable
internal fun RoundedLinearProgressIndicator(/*@FloatRange(from = 0.0, to = 1.0)*/
                                            progress: Float,
                                            modifier: Modifier = Modifier,
                                            color: Color = androidx.compose.material.MaterialTheme.colors.primary,
                                            trackColor: Color = color.copy(alpha = ProgressIndicatorDefaults.IndicatorBackgroundOpacity)
) {
    Canvas(
        modifier
            .progressSemantics(progress)
            .size(LinearIndicatorWidth, LinearIndicatorHeight)
    ) {
        val strokeWidth = size.height
        drawLinearIndicatorTrack(
            color = trackColor, strokeWidth = strokeWidth
        )
        drawRoundedLinearProgressIndicator(
            startFraction = 0f, endFraction = progress, color = color, strokeWidth = strokeWidth
        )
    }
}


private fun DrawScope.drawLinearIndicatorTrack(
    color: Color, strokeWidth: Float
) = drawRoundedLinearProgressIndicator(0f, 1f, color, strokeWidth)

private fun DrawScope.drawRoundedLinearProgressIndicator(
    startFraction: Float,
    endFraction: Float,
    color: Color,
    strokeWidth: Float,
) {
    val cap = StrokeCap.Round
    val width = size.width
    val height = size.height
    // Start drawing from the vertical center of the stroke
    val yOffset = height / 2

    val roundedCapOffset = size.height / 2

    val isLtr = layoutDirection == LayoutDirection.Ltr
    val barStart =
        (if (isLtr) startFraction else 1f - endFraction) * width + if (isLtr) roundedCapOffset else -roundedCapOffset
    val barEnd =
        (if (isLtr) endFraction else 1f - startFraction) * width - if (isLtr) roundedCapOffset else -roundedCapOffset

    // Progress line
    drawLine(
        color = color,
        start = Offset(barStart, yOffset),
        end = Offset(barEnd, yOffset),
        strokeWidth = strokeWidth,
        cap = cap,
    )
}


@Composable
fun ActionContentCell(
    title: String,
    titleColor: Color = MaterialTheme.colorScheme.onBackground,
    showDivider: Boolean,
    shape: Shape,
    enabled: Boolean = true,
    insetSize: Dp,
    onClick: (() -> Unit) = {},
    trailing: @Composable () -> Unit
) {
    val onClickState = if (enabled) {
        onClick
    } else {
        {}
    }
    val indication = if (enabled) {
        LocalIndication.current
    } else {
        null
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .clickable(onClick = onClickState,
                indication = indication,
                interactionSource = remember { MutableInteractionSource() }),
        color = MaterialTheme.colorScheme.secondary,
        shape = shape,
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .paddingCell()
            ) {
                ContentTitle(
                    text = title, modifier = Modifier.width(insetSize), color = titleColor
                )
                Spacer(Modifier.size(8.dp))
                trailing()
            }
            if (showDivider) {
                Row {
                    Spacer(
                        Modifier
                            .width(16.dp)
                            .height(1.dp)
                            .background(color = MaterialTheme.colorScheme.secondary)
                    )
                    HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = DividerAlpha))
                }
            }
        }
    }
}


@Composable
fun BasicTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderValue: String = "",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.onSurface),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    cursorBrush: Brush = SolidColor(Color.Black),
    decorationBox: @Composable (innerTextField: @Composable () -> Unit) -> Unit = @Composable { innerTextField -> innerTextField() }
) {
    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = value)) }
    val textFieldValue = textFieldValueState.copy(text = value)

    BasicTextField(
        value = textFieldValue,
        onValueChange = {
            textFieldValueState = it
            if (value != it.text) {
                onValueChange(it.text)
            }
        },
        textStyle = textStyle,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        readOnly = readOnly,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        visualTransformation = visualTransformation,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource,
        cursorBrush = cursorBrush,
        decorationBox = decorationBox,
        placeholderValue = placeholderValue
    )
}

@Composable
fun BasicTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    placeholderValue: String = "",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.onSurface),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    cursorBrush: Brush = SolidColor(Color.Black),
    decorationBox: @Composable (innerTextField: @Composable () -> Unit) -> Unit = @Composable { innerTextField -> innerTextField() }
) {
    Box {
        val alpha = if (enabled) {
            AlphaHigh
        } else {
            AlphaDisabled
        }
        androidx.compose.foundation.text.BasicTextField(
            value = value,
            onValueChange = { if (it.text.length <= 15) onValueChange(it) },
            textStyle = textStyle.copy(color = textStyle.color.copy(alpha = alpha)),
            modifier = modifier.fillMaxWidth(),
            enabled = enabled,
            readOnly = readOnly,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            visualTransformation = visualTransformation,
            onTextLayout = onTextLayout,
            interactionSource = interactionSource,
            cursorBrush = cursorBrush,
            decorationBox = decorationBox
        )

        if (value.text.isEmpty()) {
            Text(
                text = placeholderValue,
                style = textStyle,
                color = MaterialTheme.colorScheme.onSurface.copy(AlphaDisabled)
            )
        }
    }
}


@Composable
fun HeaderEditMode(
    isAllowToSave: Boolean, onCancelClick: () -> Unit, onSaveClick: () -> Unit, title: String
) {
    Box(
        modifier = Modifier
            .height(56.dp)
            .padding(horizontal = 4.dp)
            .fillMaxWidth()
    ) {
        TextButton(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp),
            enabled = true,
            text = stringResource(R.string.cancel),
            color = MaterialTheme.colorScheme.onSurface,
            onClick = onCancelClick
        )

        TitleBarPrimary(
            modifier = Modifier.align(Alignment.Center),
            text = title,
        )

        TextButton(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            enabled = isAllowToSave,
            text = stringResource(R.string.save),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium,
            onClick = onSaveClick
        )
    }
}

@Composable
fun SecondaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    border: BorderStroke? = ButtonDefaults.outlinedButtonBorder,
    content: @Composable RowScope.() -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        border = border,
        shape = MaterialTheme.shapes.medium,
        content = content
    )
}

fun cellShape(index: Int, size: Int) = when {
    size == 1 -> {
        RoundedCornerShape(size = MediumRadius)
    }
    index == 0 -> {
        RoundedCornerShape(
            topStart = MediumRadius,
            topEnd = MediumRadius
        )
    }
    index == size - 1 -> {
        RoundedCornerShape(
            bottomStart = MediumRadius,
            bottomEnd = MediumRadius
        )
    }
    else -> {
        RectangleShape
    }
}


fun shouldShowDivider(index: Int, size: Int): Boolean {
    // Don't show divider if the item only 1 and last item
    return if (size != 1) {
        // Show divider if first or middle item
        index == 0 || index < size - 1
    } else {
        false
    }
}
