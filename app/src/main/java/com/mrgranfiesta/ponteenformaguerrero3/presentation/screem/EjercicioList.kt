package com.mrgranfiesta.ponteenformaguerrero3.presentation.screem

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
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
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ADD_EJERCICIO_TOOLSTIP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EJERCICIO_LIST_TITLE
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ERROR_CHARACTER
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_NOT_FIND_EJERCICIOS
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.dialog.EjercicioDialogDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.FloatingButtonVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.TopBarVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.RegexExpresion
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.FloatingButtonVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TopBarVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.EjercicioListVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.CenteredCircularProgressIndicator
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.ErrorRowItem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.NoneRowItem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.RowEjercicio
import com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog.FilterEjercicioDialog
import com.mrgranfiesta.ponteenformaguerrero3.presentation.navigations.AppScreem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.ejercicio.list.EjercicioListUiState

@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun ConfEjercicioList(
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
            val musculSet by ejercicioListVM.musculoSetSearch.collectAsState()
            val nivelSet by ejercicioListVM.nivelSetSearch.collectAsState()

            ejercicioListVM.initData(
                searchText = searchText,
                musculoSet = musculSet,
                nivelSet = nivelSet,
                ejercicioListDB = ejercicioListDB.toMutableSet()
            )
            EjercicioListPrepareMenu(navController)
            EjercicioListPrepareDialog()
            EjercicioList(navController)
        }
    }
}

@Composable
fun EjercicioListPrepareMenu(

    navController: NavController,
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    floatingButtonVM: FloatingButtonVM = FloatingButtonVMSingle.getFloatingButtonVM()
) {
    LaunchedEffect(true) {
        topBarVM.config(
            title = LABEL_EJERCICIO_LIST_TITLE,
            icon = IconTopBar.BURGER,
            mode = ModeTopBar.SEARCH_FILTER,
            isGeasture = true,
            action = ActionModeTopBar.OPEN_DRAWER,
        )
        if (Rol.isEditable(CurrentUser.user?.rol)) {
            floatingButtonVM.makeFloatingButton(
                icon = Icons.Filled.Add,
                autoClose = true,
                contentDescripcion = LABEL_ADD_EJERCICIO_TOOLSTIP,
                tooltipText = LABEL_ADD_EJERCICIO_TOOLSTIP,
                onClick = {
                    navController.navigate(AppScreem.EjercicioFormScreem.createRoute(0))
                }
            )
        }
    }
    topBarVM.clearActionList()
}

@Composable
fun EjercicioListPrepareDialog(
    ejercicioListVM: EjercicioListVM = hiltViewModel(),
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM()
) {
    val musculoSetSearch by ejercicioListVM.musculoSetSearch.collectAsState()
    val nivelSetSearch by ejercicioListVM.nivelSetSearch.collectAsState()
    val filtersDialog by topBarVM.filtersDialog.collectAsState()

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
}

@Composable
fun EjercicioList(
    navController: NavController,
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    ejercicioListVM: EjercicioListVM = hiltViewModel()
) {
    val searchText by topBarVM.searchText.collectAsState()
    val ejercicioList by ejercicioListVM.ejercicioList.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                bottom = 8.dp,
                start = 12.dp,
                end = 12.dp
            )
    ) {
        item {
            if (searchText.isNotEmpty() && !searchText.contains(RegexExpresion.SECURED_RE)) {
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
                    navController.navigate(AppScreem.EjercicioInfoScreem.createRoute(it.idEjercicio))
                }
            )
        }
    }
}