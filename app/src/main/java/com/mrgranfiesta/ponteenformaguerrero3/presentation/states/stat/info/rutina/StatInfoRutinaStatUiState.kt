package com.mrgranfiesta.ponteenformaguerrero3.presentation.states.stat.info.rutina

import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.stat.StatRutinaInfoDto

sealed interface StatInfoRutinaStatUiState {
    data object Loading : StatInfoRutinaStatUiState
    data class ErrorUI(val throwable: Throwable) : StatInfoRutinaStatUiState
    data class Success(val statRutinaListDB: StatRutinaInfoDto) : StatInfoRutinaStatUiState
}