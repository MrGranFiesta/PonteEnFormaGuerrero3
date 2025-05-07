package com.mrgranfiesta.ponteenformaguerrero3.presentation.screem

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ActionModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.IconTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ADD_RUTINA_TOOLSTIP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ERROR_CHARACTER
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_NOT_FIND_RUTINAS
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_RUTINA_LIST_TITLE
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.dialog.RutinaDialogDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.FloatingButtonVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.TopBarVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.RegexExpresion
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.FloatingButtonVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TopBarVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.RutinaListVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.CenteredCircularProgressIndicator
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.ErrorRowItem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.NoneRowItem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.RowRutina
import com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog.FilterRutinaDialog
import com.mrgranfiesta.ponteenformaguerrero3.presentation.navigations.AppScreem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.rutina.list.RutinaListUiState

@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun ConfRutinaList(
    navController: NavController,
    snackbarHost: SnackbarHostState,
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    rutinaListVM: RutinaListVM = hiltViewModel()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<RutinaListUiState>(
        initialValue = RutinaListUiState.Loading,
        key1 = lifecycle,
        key2 = rutinaListVM
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            rutinaListVM.uiState.collect { value = it }
        }
    }

    when (uiState) {
        is RutinaListUiState.ErrorUI -> {
            navController.popBackStack()
        }

        is RutinaListUiState.Loading -> {
            CenteredCircularProgressIndicator()
        }

        is RutinaListUiState.Success -> {
            val rutinaListDB = (uiState as RutinaListUiState.Success).rutinaListDB

            val searchText by topBarVM.searchText.collectAsState()
            val musculoSet by rutinaListVM.musculoSetSearch.collectAsState()
            val nivelSet by rutinaListVM.nivelSetSearch.collectAsState()

            rutinaListVM.initData(
                searchText = searchText,
                musculoSet = musculoSet,
                nivelSet = nivelSet,
                rutinaListDB = rutinaListDB.toMutableSet()
            )
            RutinaListPrepareMenu(navController)
            RutinaListPrepareDialog()
            RutinaListLazyColumn(
                navController = navController,
                snackbarHost = snackbarHost
            )
        }
    }
}

@Composable
fun RutinaListPrepareMenu(
    navController: NavController,
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    floatingButtonVM: FloatingButtonVM = FloatingButtonVMSingle.getFloatingButtonVM()
) {
    LaunchedEffect(true) {
        topBarVM.config(
            title = LABEL_RUTINA_LIST_TITLE,
            icon = IconTopBar.BURGER,
            mode = ModeTopBar.SEARCH_FILTER,
            isGeasture = true,
            action = ActionModeTopBar.OPEN_DRAWER
        )
        if (Rol.isEditable(CurrentUser.user?.rol)) {
            floatingButtonVM.makeFloatingButton(
                icon = Icons.Filled.Add,
                autoClose = true,
                contentDescripcion = "Añadir rutina",
                tooltipText = LABEL_ADD_RUTINA_TOOLSTIP,
                onClick = {
                    navController.navigate(AppScreem.RutinaFormScreem.createRoute(0))
                },
            )
        }
    }
    topBarVM.clearActionList()
}

@Composable
fun RutinaListPrepareDialog(
    rutinaListVM: RutinaListVM = hiltViewModel(),
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM()
) {
    val musculoSetSearch by rutinaListVM.musculoSetSearch.collectAsState()
    val nivelSetSearch by rutinaListVM.nivelSetSearch.collectAsState()
    val filtersDialog by topBarVM.filtersDialog.collectAsState()

    if (filtersDialog) {
        FilterRutinaDialog(
            dismissDialog = { topBarVM.setFiltersDialog(false) },
            params = RutinaDialogDto(
                musculoSet = musculoSetSearch,
                onChangeMusculoSet = { rutinaListVM.setMusculoSetSearch(it) },
                nivelSet = nivelSetSearch.toMutableSet(),
                onChangeNivelSet = { rutinaListVM.setNivelSetSearch(it) }
            )
        )
    }
}

@Composable
fun RutinaListLazyColumn(
    navController: NavController,
    snackbarHost: SnackbarHostState,
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    rutinaListVM: RutinaListVM = hiltViewModel()
) {
    val searchText by topBarVM.searchText.collectAsState()
    val rutinaList by rutinaListVM.rutinaList.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
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
                            top = 8.dp,
                        ),
                    errorTxt = LABEL_ERROR_CHARACTER
                )
            } else if (rutinaList.isEmpty()) {
                NoneRowItem(
                    text = LABEL_NOT_FIND_RUTINAS,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 8.dp,
                        ),
                    icon = Icons.Filled.SentimentVeryDissatisfied
                )
            }
        }
        items(rutinaList) {
            RowRutina(
                nombre = it.nombre,
                nivel = it.nivel,
                musculoSet = it.musculoSet,
                isPremium = it.isPremium,
                navigation = {
                    rutinaListVM.navigate(
                        idRutina = it.idRutina,
                        isPremium = it.isPremium,
                        navController = navController,
                        snackbarHost = snackbarHost
                    )
                }
            )
        }
    }
}