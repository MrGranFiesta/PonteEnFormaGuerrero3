package com.mrgranfiesta.ponteenformaguerrero3.presentation.screem

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ActionModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.IconTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ERROR_CHARACTER
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ERROR_NOT_PERMISSION
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_NOT_FIND_RUTINAS
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_STAT_LIST_TITLE
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.dialog.StatDialogDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.FloatingButtonVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.TopBarVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.RegexExpresion
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.FloatingButtonVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TopBarVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.StatListVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.CenteredCircularProgressIndicator
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.ErrorRowItem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.NoneRowItem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.RowStat
import com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog.FilterStatDialog
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.stat.list.StatListUiState

@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun ConfStatList(
    navController: NavController,
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    statListVM: StatListVM = hiltViewModel()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<StatListUiState>(
        initialValue = StatListUiState.Loading,
        key1 = lifecycle,
        key2 = statListVM
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            statListVM.uiState.collect { value = it }
        }
    }
    when (uiState) {
        is StatListUiState.ErrorUI -> {
            navController.popBackStack()
        }

        is StatListUiState.Loading -> {
            CenteredCircularProgressIndicator()
        }

        is StatListUiState.NoPermision -> {
            StatListNoPermision()
        }

        is StatListUiState.Success -> {
            val statListDB = (uiState as StatListUiState.Success).statListDB

            val searchText by topBarVM.searchText.collectAsState()
            val musculoSet by statListVM.musculoSetSearch.collectAsState()
            val rangeDate by statListVM.rangeDate.collectAsState()
            val nivelSet by statListVM.nivelSetSearch.collectAsState()

            statListVM.initData(
                searchText = searchText,
                musculoSet = musculoSet,
                nivelSet = nivelSet,
                rangeDate = rangeDate,
                statListDB = statListDB.toMutableSet()
            )
            StatListPrepareMenu()
            StatListPrepareDialog()
            StatListLazyColumn(navController)
        }
    }
}

@Composable
fun StatListNoPermision() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NoneRowItem(
            text = LABEL_ERROR_NOT_PERMISSION,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 8.dp,
                ),
            icon = Icons.Filled.Lock
        )
    }
}

@Composable
fun StatListPrepareMenu(
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    floatingButtonVM: FloatingButtonVM = FloatingButtonVMSingle.getFloatingButtonVM()
) {
    LaunchedEffect(true) {
        topBarVM.config(
            title = LABEL_STAT_LIST_TITLE,
            icon = IconTopBar.BURGER,
            mode = ModeTopBar.SEARCH_FILTER,
            isGeasture = true,
            action = ActionModeTopBar.OPEN_DRAWER
        )
        floatingButtonVM.resetFloatingButton()
    }
    topBarVM.clearActionList()
}

@Composable
fun StatListPrepareDialog(
    statListVM: StatListVM = hiltViewModel(),
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM()
) {
    val musculoSet by statListVM.musculoSetSearch.collectAsState()
    val nivelSet by statListVM.nivelSetSearch.collectAsState()
    val rangeDate by statListVM.rangeDate.collectAsState()
    val filtersDialog by topBarVM.filtersDialog.collectAsState()

    if (filtersDialog) {
        FilterStatDialog(
            dismissDialog = { topBarVM.setFiltersDialog(false) },
            params = StatDialogDto(
                musculoSet = musculoSet.toMutableSet(),
                onChangeMusculoSet = { statListVM.setMusculoSetSearch(it) },
                nivelSet = nivelSet.toMutableSet(),
                onChangeNivelSet = { statListVM.setNivelSetSearch(it) },
                rangeDate = rangeDate,
                onChangeRangeDate = { statListVM.setRangeDate(it) }
            )
        )
    }
}

@Composable
fun StatListLazyColumn(
    navController: NavController,
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    statListVM: StatListVM = hiltViewModel()
) {
    val searchText by topBarVM.searchText.collectAsState()
    val statList by statListVM.statList.collectAsState()

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
            } else if (statList.isEmpty()) {
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
        items(statList) {
            RowStat(it, navController)
        }
    }
}

