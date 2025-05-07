package com.mrgranfiesta.ponteenformaguerrero3.presentation.states.stat.list

import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.stat.StatRutinaSearchDto

sealed interface StatListUiState {
    data object Loading : StatListUiState
    data class ErrorUI(val throwable: Throwable) : StatListUiState
    data class Success(val statListDB: List<StatRutinaSearchDto>) : StatListUiState
    data object NoPermision : StatListUiState
}