package com.manoj.wallettracker.presentation.addedittx

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.manoj.wallettracker.R
import com.manoj.wallettracker.constants.CategoryType
import com.manoj.wallettracker.constants.TransactionType
import com.manoj.wallettracker.data.ui.addedittx.effect.TransactionEffect
import com.manoj.wallettracker.data.ui.addedittx.state.TransactionState
import com.manoj.wallettracker.data.ui.addedittx.state.getAmountColor
import com.manoj.wallettracker.data.ui.addedittx.state.getCategoryName
import com.manoj.wallettracker.data.ui.addedittx.state.getCategoryTypes
import com.manoj.wallettracker.data.ui.addedittx.state.getTitle
import com.manoj.wallettracker.data.ui.addedittx.state.getTransactionTypes
import com.manoj.wallettracker.data.ui.addedittx.state.isValid
import com.manoj.wallettracker.data.ui.addedittx.state.transactionDateDisplayable
import com.manoj.wallettracker.foundation.core.date.toLocalDateTime
import com.manoj.wallettracker.foundation.core.date.toMillis
import com.manoj.wallettracker.foundation.ext.TriggerEvent
import com.manoj.wallettracker.foundation.layout.ActionContentCell
import com.manoj.wallettracker.foundation.layout.BasicTextField
import com.manoj.wallettracker.foundation.layout.ContentTitle
import com.manoj.wallettracker.foundation.layout.HeaderEditMode
import com.manoj.wallettracker.foundation.layout.HeadlineLabel
import com.manoj.wallettracker.foundation.layout.Icon
import com.manoj.wallettracker.foundation.layout.PageLayout
import com.manoj.wallettracker.foundation.layout.SecondaryButton
import com.manoj.wallettracker.foundation.layout.TabLabel
import com.manoj.wallettracker.foundation.layout.paddingCell
import com.manoj.wallettracker.presentation.addedittx.action.TransactionAction
import com.manoj.wallettracker.presentation.addedittx.vm.TransactionDetailViewModel
import com.manoj.wallettracker.ui.theme.AlphaDisabled
import com.manoj.wallettracker.ui.theme.AlphaHigh
import com.manoj.wallettracker.ui.theme.MediumRadius
import com.manoj.wallettracker.ui.theme.WalletTrackerTheme
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddEditTransactionScreen(
    viewModel: TransactionDetailViewModel,
    onClosePage: () -> Unit,
    onCancelClick: () -> Unit,
    onCategorySectionClick: () -> Unit
) {
    val sheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val bottomSheetState =
        rememberBottomSheetScaffoldState()
    val showBottomSheet = remember { mutableStateOf(false) }

    LaunchedEffect(showBottomSheet.value) {
        if (showBottomSheet.value) sheetState.show() else sheetState.hide()
    }
//    LaunchedEffect(showBottomSheet.value) {
//        if (showBottomSheet.value) bottomSheetState.bottomSheetState.expand() else bottomSheetState.bottomSheetState.collapse()
//    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val focusRequester = remember { FocusRequester() }
    val localFocusManager = LocalFocusManager.current

    TriggerEvent(
        viewModel = viewModel,
    ) {
        when (it) {
            TransactionEffect.ClosePage -> {
                onClosePage()
            }

            TransactionEffect.ShowKeyboard -> {
                focusRequester.requestFocus()
            }
        }
    }
    Scaffold {_padding ->
        WalletTrackerTheme {

            ModalBottomSheetLayout(
                sheetState = sheetState,
                sheetContent = {
                    Column(modifier = Modifier.wrapContentHeight()) {
                        getCategoryTypes().forEach { category ->
                            CategoryItem(
                                category = category,
                                selectedCategory = state.categoryType == category,
                                onClick = {
                                    viewModel.dispatch(TransactionAction.SelectCategory(category))
                                    showBottomSheet.value = false
                                }
                            )
                        }
                    }
                },
            ) {
                    AddEditTransactionScreen(
                        _padding,
                        state = state,
                        focusRequester = focusRequester,
                        onSaveClick = {
                            localFocusManager.clearFocus()
                            viewModel.dispatch(TransactionAction.Save)
                        },
                        onCancelClick = {
                            localFocusManager.clearFocus()
                            onCancelClick()
                        },
                        onCategorySectionClick = {
                            localFocusManager.clearFocus()
                            showBottomSheet.value = true
                        },
                        onDateSectionClick = {
                            viewModel.dispatch(TransactionAction.ShowDatePicker)
                        },
                        onTransactionTypeSelected = {
                            localFocusManager.clearFocus()
                            viewModel.dispatch(TransactionAction.SelectTransactionType(it))
                        },
                        onDeleteClick = {
                            localFocusManager.clearFocus()
                            viewModel.dispatch(TransactionAction.Delete)
                        },
                        onClickDateCancel = {
                            viewModel.dispatch(TransactionAction.DismissDatePicker)
                        },

                        onClickDateSelect = {
                            viewModel.dispatch(TransactionAction.SelectDate(it))
                        },
                        onTotalAmountChange = {
                            viewModel.dispatch(
                                TransactionAction.TotalAmountAction.Change(
                                    it
                                )
                            )
                        })
            }
        }
    }
}


@Composable
private fun AddEditTransactionScreen(
    paddingValues: PaddingValues,
    state: TransactionState,
    focusRequester: FocusRequester,
    onSaveClick: () -> Unit,

    onCancelClick: () -> Unit,
    onCategorySectionClick: () -> Unit,
    onDateSectionClick: () -> Unit,

    onTransactionTypeSelected: (TransactionType) -> Unit,
    onDeleteClick: () -> Unit,
    onClickDateCancel: () -> Unit,

    onClickDateSelect: (LocalDateTime?) -> Unit,
    onTotalAmountChange: (TextFieldValue) -> Unit
) {
    val localFocusManager = LocalFocusManager.current
    PageLayout(
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        localFocusManager.clearFocus()
                    }
                )
            }
    ) {
        HeaderEditMode(
            isAllowToSave = state.isValid(),
            title = state.getTitle(),
            onSaveClick = onSaveClick,
            onCancelClick = onCancelClick,
        )

        if (!state.isEditMode) {
            TransactionTypeSection(
                transactionTypes = getTransactionTypes(),
                onSelected = onTransactionTypeSelected,
                selectedType = state.transactionType
            )

            Spacer(Modifier.height(16.dp))
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .navigationBarsPadding()
                .imePadding()
        ) {
            item {
                AmountSection(
                    totalAmount = state.totalAmount,
                    totalAmountDisplay = state.totalAmount.text,
                    amountColor = state.getAmountColor(),
                    focusRequester = focusRequester,
                    onTotalAmountChange = onTotalAmountChange,
                )
            }

            item {
                Spacer(Modifier.height(16.dp))
            }

            item {
                GeneralSection(
                    showDatePicker = state.showDatePicker,
                    transactionType = state.transactionType,
                    transactionDateDisplay = state.transactionDateDisplayable(),
                    transactionDateInitial = state.transactionDate,
                    isEditMode = state.isEditMode,
                    selectedCategoryType = state.categoryType,
                    onCategorySectionClick = onCategorySectionClick,
                    onDateSectionClick = onDateSectionClick,
                    onClickDateCancel = onClickDateCancel,
                    onClickDateSelect = onClickDateSelect,
                )
            }

            item {
                Spacer(Modifier.height(16.dp))
            }

            if (state.isEditMode) {
                item {
                    Spacer(Modifier.height(32.dp))
                    SecondaryButton(
                        modifier = Modifier.fillMaxWidth(),
                        border = BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.error
                        ),
                        onClick = onDeleteClick
                    ) {
                        ContentTitle(
                            text = stringResource(R.string.delete),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TransactionTypeSection(
    selectedType: TransactionType,
    transactionTypes: List<TransactionType>,
    onSelected: (TransactionType) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(transactionTypes.size),
            contentPadding = PaddingValues(horizontal = 8.dp),
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = MaterialTheme.shapes.extraLarge
                )
        ) {
            items(transactionTypes) {
                val selected = it == selectedType
                val backgroundColor = if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.secondary
                }
                val contentColor = if (selected) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSecondary
                }

                Chip(
                    onClick = { onSelected(it) },
                    colors = ChipDefaults.chipColors(
                        backgroundColor = backgroundColor,
                        contentColor = contentColor
                    )
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                        CompositionLocalProvider(LocalContentColor provides contentColor) {
                            TabLabel(stringResource(it.getTitle()))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AmountSection(
    totalAmount: TextFieldValue,
    totalAmountDisplay: String,
    amountColor: Color,
    focusRequester: FocusRequester,
    onTotalAmountChange: (TextFieldValue) -> Unit,
) {
    HeadlineLabel(
        text = stringResource(R.string.amount),
        modifier = Modifier.padding(start = 16.dp, bottom = 6.dp)
    )

    Box(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = MaterialTheme.shapes.medium
            )
            .fillMaxWidth()
            .paddingCell()
    ) {
        ContentTitle(
            text = totalAmountDisplay,
            color = amountColor,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                        focusRequester.requestFocus()
                    }
                )
        )
        val localFocusManager = LocalFocusManager.current
        BasicTextField(
            value = totalAmount,
            onValueChange = onTotalAmountChange,
            modifier = Modifier
                .focusRequester(focusRequester)
                .alpha(0f)
                .size(1.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = {
                    localFocusManager.clearFocus()
                }
            ),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GeneralSection(
    showDatePicker: Boolean,
    transactionType: TransactionType,
    transactionDateDisplay: String,
    transactionDateInitial: LocalDateTime,
    isEditMode: Boolean,
    onCategorySectionClick: () -> Unit,
    selectedCategoryType: CategoryType,
    onDateSectionClick: () -> Unit,
    onClickDateCancel: () -> Unit,
    onClickDateSelect: (LocalDateTime?) -> Unit,
) {
    val alpha = if (isEditMode) AlphaDisabled else AlphaHigh
    HeadlineLabel(
        text = stringResource(R.string.general),
        modifier = Modifier.padding(start = 16.dp, bottom = 6.dp)
    )

    if (transactionType == TransactionType.EXPENSE || transactionType == TransactionType.BUDGET) {
        ActionContentCell(
            title = stringResource(R.string.category),
            showDivider = true,
            shape = MaterialTheme.shapes.extraSmall,
            insetSize = 120.dp,
            onClick = onCategorySectionClick,
            trailing = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ContentTitle(
                        text = selectedCategoryType.getCategoryName()
                    )
                    Spacer(Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Rounded.ChevronRight,
                    )
                }
            }
        )
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = transactionDateInitial.toMillis(
                ZoneId.ofOffset(
                    "UTC",
                    ZoneOffset.UTC
                )
            ),
            initialDisplayedMonthMillis = transactionDateInitial.toMillis(
                ZoneId.ofOffset(
                    "UTC",
                    ZoneOffset.UTC
                )
            ),
            yearRange = DatePickerDefaults.YearRange,
            initialDisplayMode = DisplayMode.Picker,
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    val zone = ZoneId.ofOffset("UTC", ZoneOffset.UTC)
                    val start = LocalDate.of(2000, 1, 1).toMillis(zone)
                    val end = LocalDate.now().toMillis(zone)
                    return utcTimeMillis in start..end
                }
            }
        )
        val confirmEnabled by remember { derivedStateOf { datePickerState.selectedDateMillis != null } }
        DatePickerDialog(
            onDismissRequest = onClickDateCancel,
            confirmButton = {
                TextButton(
                    onClick = {
                        onClickDateSelect(datePickerState.selectedDateMillis?.toLocalDateTime())
                    },
                    enabled = confirmEnabled
                ) { Text("Oke") }
            },
            dismissButton = {
                TextButton(
                    onClick = onClickDateCancel
                ) { Text(stringResource(R.string.cancel)) }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    ActionContentCell(
        title = stringResource(R.string.transaction_date),
        showDivider = false,
        shape = RoundedCornerShape(
            bottomStart = MediumRadius,
            bottomEnd = MediumRadius
        ),
        insetSize = 120.dp,
        onClick = onDateSectionClick,
        trailing = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                ContentTitle(
                    text = transactionDateDisplay,
                )
                Spacer(Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Rounded.ChevronRight,
                )
            }
        }
    )
}


@Composable
private fun CategoryItem(
    onClick: () -> Unit,
    selectedCategory: Boolean,
    category: CategoryType,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = { onClick() }
            )
            .padding(16.dp)
    ) {
        Text(
            text = category.name,
            color = androidx.compose.material.MaterialTheme.colors.secondaryVariant
        )
    }
    Divider(modifier = Modifier.padding(horizontal = 16.dp))
}
