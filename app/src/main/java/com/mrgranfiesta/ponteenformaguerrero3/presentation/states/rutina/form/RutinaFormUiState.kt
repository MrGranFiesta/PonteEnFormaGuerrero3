package com.mrgranfiesta.ponteenformaguerrero3.presentation.states.rutina.form

import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.rutina.RutinaInfoDto

sealed interface RutinaFormUiState {
    data object Loading : RutinaFormUiState
    data class ErrorUI(val throwable: Throwable) : RutinaFormUiState
    data class Success(val rutinaDB: RutinaInfoDto) : RutinaFormUiState
}