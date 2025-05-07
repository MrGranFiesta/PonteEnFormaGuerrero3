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
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_MUSCULOS
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.CheckMusculoListItemVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.CheckMusculoListVM

@Composable
fun CheckMusculoList(
    musculoSetTemp: Set<Musculo>,
    onChangeMusculoSet: (Set<Musculo>) -> Unit,
    checkMusculoListVM: CheckMusculoListVM = hiltViewModel()
) {
    val parentCheckState by checkMusculoListVM.parentCheckState.collectAsState()

    checkMusculoListVM.initData(musculoSetTemp)

    TriStateCheckList(
        txt = LABEL_MUSCULOS,
        parentCheckState = parentCheckState,
        changeIsChecked = {
            checkMusculoListVM.onCheckTriState(
                onChangeMusculoSet = onChangeMusculoSet
            )
        }
    )

    checkMusculoListVM.musculoCheckList.forEach { musculo ->
        CheckMusculoListItem(
            musculosList = musculoSetTemp,
            musculo = musculo,
            parentCheckState = parentCheckState,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                ),
            changeGlobalCheck = {
                checkMusculoListVM.setParentCheckState(it)
            },
            onChangeMusculoSet = onChangeMusculoSet,
            checkMusculoListItemVM = viewModel(key = musculo.toString())
        )
    }
}

@Composable
fun CheckMusculoListItem(
    musculosList: Set<Musculo>,
    musculo: Musculo,
    parentCheckState: ToggleableState,
    changeGlobalCheck: (ToggleableState) -> Unit,
    modifier: Modifier = Modifier,
    onChangeMusculoSet: (Set<Musculo>) -> Unit,
    checkMusculoListItemVM: CheckMusculoListItemVM = hiltViewModel()
) {
    val isChecked by checkMusculoListItemVM.isChecked.collectAsState()

    LaunchedEffect(parentCheckState) {
        checkMusculoListItemVM.setIsChecked(musculosList.contains(musculo))
    }

    CheckText(
        txt = musculo.toString(),
        isChecked = isChecked,
        modifier = modifier,
        changeIsChecked = {
            checkMusculoListItemVM.onCheckText(
                isCheck = it,
                musculo = musculo,
                musculosList = musculosList,
                changeGlobalCheck = changeGlobalCheck,
                onChangeMusculoSet = onChangeMusculoSet,
            )
        },
    )
}