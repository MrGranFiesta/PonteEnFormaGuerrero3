package com.mrgranfiesta.ponteenformaguerrero3.presentation.states.material.list

import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialDto

sealed interface MaterialListUiState {
    data object Loading : MaterialListUiState
    data class ErrorUI(val throwable: Throwable) : MaterialListUiState
    data class Success(val materialListDB: List<RowMaterialDto>) : MaterialListUiState
}