package com.mrgranfiesta.ponteenformaguerrero3.presentation.states.material.info

import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.MaterialInfoDto

sealed interface MaterialInfoUiState {
    data object Loading : MaterialInfoUiState
    data class ErrorUI(val throwable: Throwable) : MaterialInfoUiState
    data class Success(val materialDB: MaterialInfoDto) : MaterialInfoUiState
}