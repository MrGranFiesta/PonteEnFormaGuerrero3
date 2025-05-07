package com.mrgranfiesta.ponteenformaguerrero3.presentation.screem

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
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
import androidx.navigation.NavController
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ActionModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.IconTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ERROR_CHARACTER
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_NOT_FIND_EJERCICIOS
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_SELECT_EJERCICIO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.PARAMS_STEP_SELECTED
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.dialog.EjercicioDialogDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.TopBarVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.RegexExpresion.Companion.SECURED_RE
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TopBarVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.EjercicioListVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.SelectEjercicioListVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.CenteredCircularProgressIndicator
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.ErrorRowItem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.NoneRowItem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.RowEjercicio
import com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog.EditStepDialog
import com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog.FilterEjercicioDialog
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.ejercicio.list.EjercicioListUiState

@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun ConfSelectEjercicioList(
    navController: NavController,
    ejercicioListVM: EjercicioListVM = hiltViewModel(),
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<EjercicioListUiState>(
        initialValue = EjercicioListUiState.Loading,
        key1 = lifecycle,
        key2 = ejercicioListVM
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            ejercicioListVM.uiState.collect { value = it }
        }
    }

    when (uiState) {
        is EjercicioListUiState.ErrorUI -> {
            navController.popBackStack()
        }

        is EjercicioListUiState.Loading -> {
            CenteredCircularProgressIndicator()
        }

        is EjercicioListUiState.Success -> {
            val ejercicioListDB = (uiState as EjercicioListUiState.Success).ejercicioListDB

            val searchText by topBarVM.searchText.collectAsState()
            val musculoSet by ejercicioListVM.musculoSetSearch.collectAsState()
            val nivelSet by ejercicioListVM.nivelSetSearch.collectAsState()

            ejercicioListVM.initData(
                searchText = searchText,
                musculoSet = musculoSet,
                nivelSet = nivelSet,
                ejercicioListDB = ejercicioListDB.toMutableSet()
            )

            SelectEjercicioListPrepareMenu()
            topBarVM.clearActionList()
            SelectEjercicioListPrepareDialog(
                navController = navController,
            )
            SelectEjercicioLazyColumn()
        }
    }
}

@Composable
fun SelectEjercicioListPrepareMenu(
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
) {
    LaunchedEffect(true) {
        topBarVM.config(
            title = LABEL_SELECT_EJERCICIO,
            icon = IconTopBar.ARRROW,
            mode = ModeTopBar.SEARCH_FILTER,
            isGeasture = false,
            action = ActionModeTopBar.POP_BACK_STACK
        )
    }
}

@Composable
fun SelectEjercicioListPrepareDialog(
    navController: NavController,
    ejercicioListVM: EjercicioListVM = hiltViewModel(),
    selectEjercicioListVM: SelectEjercicioListVM = hiltViewModel(),
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
) {
    val musculoSetSearch by ejercicioListVM.musculoSetSearch.collectAsState()
    val nivelSetSearch by ejercicioListVM.nivelSetSearch.collectAsState()
    val isCreateStepDialog by selectEjercicioListVM.isCreateStepDialog.collectAsState()
    val filtersDialog by topBarVM.filtersDialog.collectAsState()
    val idEjercicioSelect by selectEjercicioListVM.idEjercicioSelect.collectAsState()

    if (filtersDialog) {
        FilterEjercicioDialog(
            dismissDialog = { topBarVM.setFiltersDialog(false) },
            params = EjercicioDialogDto(
                musculoSet = musculoSetSearch.toMutableSet(),
                onChangeMusculoSet = { ejercicioListVM.setMusculoSetSearch(it) },
                nivelSet = nivelSetSearch.toMutableSet(),
                onChangeNivelSet = { ejercicioListVM.setNivelSetSearch(it) }
            )
        )
    }
    if (isCreateStepDialog) {
        EditStepDialog(
            dismissDialog = { selectEjercicioListVM.onChangeIsCreateStepDialog(false) },
            step = selectEjercicioListVM.setIdDefaultStepEditDialogDto(idEjercicioSelect),
            onAcceptAction = { stepNew ->
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(
                        key = PARAMS_STEP_SELECTED,
                        value = stepNew
                    )
                navController.popBackStack()
            },
        )
    }
}

@Composable
fun SelectEjercicioLazyColumn(
    ejercicioListVM: EjercicioListVM = hiltViewModel(),
    selectEjercicioListVM: SelectEjercicioListVM = hiltViewModel(),
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM()
) {
    val searchText by topBarVM.searchText.collectAsState()
    val ejercicioList by ejercicioListVM.ejercicioList.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 12.dp,
                end = 12.dp
            )
    ) {
        item {
            Text(
                text = LABEL_SELECT_EJERCICIO,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 8.dp,
                        bottom = 8.dp
                    )
            )
        }
        item {
            if (searchText.isNotEmpty() && !searchText.contains(SECURED_RE)) {
                ErrorRowItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 8.dp
                        ),
                    errorTxt = LABEL_ERROR_CHARACTER
                )
            } else if (ejercicioList.isEmpty()) {
                NoneRowItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 8.dp
                        ),
                    text = LABEL_NOT_FIND_EJERCICIOS,
                    icon = Icons.Filled.SentimentVeryDissatisfied
                )
            }
        }
        items(ejercicioList) {
            RowEjercicio(
                photoUri = it.photoUri,
                nombre = it.nombre,
                nivel = it.nivel,
                onClickItem = {
                    selectEjercicioListVM.onChangeIsCreateStepDialog(true)
                    selectEjercicioListVM.setIdEjercicioSelect(it.idEjercicio)
                }
            )
        }
    }
}