package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_NIVEL
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.CheckNivelListItemVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.CheckNivelListVM

@Composable
fun CheckNivelList(
    nivelSetTemp: Set<Nivel>,
    onChangeNivelSet: (Set<Nivel>) -> Unit,
    checkNivelListVM: CheckNivelListVM = hiltViewModel()
) {
    val parentCheckState by checkNivelListVM.parentCheckState.collectAsState()

    checkNivelListVM.initData(nivelSetTemp)

    TriStateCheckList(
        txt = LABEL_NIVEL,
        parentCheckState = parentCheckState,
        changeIsChecked = {
            checkNivelListVM.onCheckTriState(onChangeNivelSet)
        }
    )

    checkNivelListVM.nivelList.forEach { nivel ->
        CheckNivelListItem(
            nivelList = nivelSetTemp,
            nivel = nivel,
            parentCheckState = parentCheckState,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                ),
            changeGlobalCheck = {
                checkNivelListVM.setParentCheckState(it)
            },
            onChangeNivelSet = onChangeNivelSet,
            checkNivelListItemVM = viewModel(key = nivel.toString())
        )
    }
}


@Composable
fun CheckNivelListItem(
    nivelList: Set<Nivel>,
    nivel: Nivel,
    onChangeNivelSet: (Set<Nivel>) -> Unit,
    parentCheckState: ToggleableState,
    changeGlobalCheck: (ToggleableState) -> Unit,
    modifier: Modifier = Modifier,
    checkNivelListItemVM: CheckNivelListItemVM = hiltViewModel()
) {
    val isChecked by checkNivelListItemVM.isChecked.collectAsState()

    LaunchedEffect(parentCheckState) {
        checkNivelListItemVM.setIsChecked(nivelList.contains(nivel))
    }

    CheckNivel(
        nivel = nivel,
        isChecked = isChecked,
        modifier = modifier,
        changeIsChecked = {
            checkNivelListItemVM.onCheckText(
                isCheck = it,
                nivel = nivel,
                nivelList = nivelList,
                onChangeNivelSet = onChangeNivelSet,
                changeGlobalCheck = changeGlobalCheck,
            )
        }
    )
}