package com.mrgranfiesta.ponteenformaguerrero3.presentation.states.material.form

import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.MaterialInfoDto

sealed interface MaterialFormUiState {
    data object Loading : MaterialFormUiState
    data class ErrorUI(val throwable: Throwable) : MaterialFormUiState
    data class Success(val materialDB: MaterialInfoDto) : MaterialFormUiState
}