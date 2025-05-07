package com.mrgranfiesta.ponteenformaguerrero3.presentation.states.rutina.list

import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.rutina.RowRutinaDto

sealed interface RutinaListUiState {
    data object Loading : RutinaListUiState
    data class ErrorUI(val throwable: Throwable) : RutinaListUiState
    data class Success(val rutinaListDB: List<RowRutinaDto>) : RutinaListUiState
}