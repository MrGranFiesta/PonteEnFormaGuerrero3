package com.mrgranfiesta.ponteenformaguerrero3.presentation.screem

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ActionModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.IconTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ERROR_CHARACTER
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_NOT_FIND_MATERIAL
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_SELECIONAR
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_SELECT_MATERIAL
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_SELECT_MATERIAL_TITLE
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.TopBarVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.RegexExpresion
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TopBarVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.SelectMaterialVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.CenteredCircularProgressIndicator
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.ErrorRowItem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.NoneRowItem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.RowMaterial
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.ejercicio.select.SelectMaterialUiState

@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun ConfSelectMaterial(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry,
    selectMaterialVM: SelectMaterialVM = hiltViewModel(),
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<SelectMaterialUiState>(
        initialValue = SelectMaterialUiState.Loading,
        key1 = lifecycle,
        key2 = selectMaterialVM
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            selectMaterialVM.uiState.collect { value = it }
        }
    }

    when (uiState) {
        is SelectMaterialUiState.ErrorUI -> {
            navController.popBackStack()
        }

        is SelectMaterialUiState.Loading -> {
            CenteredCircularProgressIndicator()
        }

        is SelectMaterialUiState.Success -> {
            val materialListDB = (uiState as SelectMaterialUiState.Success).materialListDB

            val searchText by topBarVM.searchText.collectAsState()

            selectMaterialVM.initData(
                searchText = searchText,
                materialListDB = materialListDB.toMutableSet(),
                navController = navController,
                navBackStackEntry = navBackStackEntry
            )

            SelectMaterialPrepareMenu()
            SelectMaterialList(navController)
        }
    }
}

@Composable
fun SelectMaterialPrepareMenu(
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
) {
    LaunchedEffect(true) {
        topBarVM.config(
            title = LABEL_SELECT_MATERIAL_TITLE,
            icon = IconTopBar.ARRROW,
            mode = ModeTopBar.SEARCH,
            isGeasture = false,
            action = ActionModeTopBar.POP_BACK_STACK
        )
        topBarVM.clearActionList()
    }
}

@Composable
fun SelectMaterialList(
    navController: NavController,
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    selectMaterialVM: SelectMaterialVM = hiltViewModel(),
) {
    val searchText by topBarVM.searchText.collectAsState()
    val materialList by selectMaterialVM.materialList.collectAsState()
    val materialListSelected by selectMaterialVM.materialListSelected.collectAsState()
    Column {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 12.dp,
                    end = 12.dp,
                )
                .weight(1f)
        ) {
            item {
                Text(
                    text = LABEL_SELECT_MATERIAL,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 8.dp
                        )
                )
            }
            item {
                if (searchText.isNotEmpty() && !searchText.contains(RegexExpresion.SECURED_RE)) {
                    ErrorRowItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 8.dp,
                            ),
                        errorTxt = LABEL_ERROR_CHARACTER
                    )
                } else if (materialList.isEmpty()) {
                    NoneRowItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 8.dp,
                            ),
                        text = LABEL_NOT_FIND_MATERIAL,
                        icon = Icons.Filled.SentimentVeryDissatisfied
                    )
                }
            }
            items(materialList) {
                RowMaterial(
                    nombre = it.nombre,
                    photoUri = it.photoUri,
                    rowSelectMaterialVM = viewModel(key = it.hashCode().toString()),
                    onClickCheck = { check ->
                        selectMaterialVM.onCheckAction(check, it)
                    },
                    isSelectionable = true,
                    initialSelect = materialListSelected.contains(it.idMaterial)
                )
            }
        }
        Button(
            onClick = {
                selectMaterialVM.clickSelecionarBt(navController)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 8.dp,
                    bottom = 8.dp
                ),
            shape = RoundedCornerShape(0.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Done,
                contentDescription = "selecionar"
            )
            Text(text = LABEL_SELECIONAR)
        }
    }
}