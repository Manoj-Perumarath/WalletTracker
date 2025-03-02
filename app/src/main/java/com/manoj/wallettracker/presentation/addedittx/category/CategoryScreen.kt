package com.manoj.wallettracker.presentation.addedittx.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.manoj.wallettracker.constants.CategoryType
import com.manoj.wallettracker.data.ui.addedittx.state.getCategoryTypes
import com.manoj.wallettracker.presentation.addedittx.action.TransactionAction
import com.manoj.wallettracker.presentation.addedittx.vm.TransactionDetailViewModel


@Composable
fun CategorySelectionScreen(
    viewModel: TransactionDetailViewModel,
    onClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CategorySelectionScreen(
        categoryItems = state.categories,
        selectedCategory = state.categoryType,
        onClick = {
            viewModel.dispatch(TransactionAction.SelectCategory(it))
            onClick()
        }
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun CategorySelectionScreen(
    categoryItems: Map<Int, List<CategoryType>>,
    selectedCategory: CategoryType,
    onClick: (CategoryType) -> Unit,
) {
    val sheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val bottomSheetState =
        rememberBottomSheetScaffoldState()
    val showBottomSheet = remember { mutableStateOf(true) }

//    LaunchedEffect(showBottomSheet.value) {
//        if (showBottomSheet.value) sheetState.show() else sheetState.hide()
//    }
    LaunchedEffect(showBottomSheet.value) {
        if (showBottomSheet.value) bottomSheetState.bottomSheetState.expand() else bottomSheetState.bottomSheetState.collapse()
    }
    Column(
        modifier = Modifier.background(
            color = androidx.compose.material3.MaterialTheme.colorScheme.background,
            shape = RectangleShape
        )
    ) {
        BottomSheetScaffold(
            scaffoldState = bottomSheetState,
            sheetContent = {
                Column(modifier = Modifier.wrapContentHeight()) {
                    getCategoryTypes().forEach { category ->
                        CategoryItem(
                            category = category,
                            selectedCategory = selectedCategory == category,
                            onClick = { onClick(CategoryType.FOOD) }
                        )
                    }
                }
            },
            content = {}
        )
    }
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
        Text(text = category.name, color = MaterialTheme.colors.secondaryVariant)
    }
    Divider(modifier = Modifier.padding(horizontal = 16.dp))
}
