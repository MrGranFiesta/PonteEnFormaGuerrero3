package com.mrgranfiesta.ponteenformaguerrero3.presentation.states.rutina.info

import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.rutina.RutinaInfoDto

sealed interface RutinaInfoUiState {
    data object Loading : RutinaInfoUiState
    data class ErrorUI(val throwable: Throwable) : RutinaInfoUiState
    data class Success(val rutinaDB: RutinaInfoDto) : RutinaInfoUiState
}