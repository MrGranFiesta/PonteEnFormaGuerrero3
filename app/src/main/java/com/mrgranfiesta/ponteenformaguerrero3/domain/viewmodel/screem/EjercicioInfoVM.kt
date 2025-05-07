package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.FlowManager
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio.DeleteEjercicioUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio.GetEjercicioInfoByIdUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material.GetMaterialByIdEjercicioUseCase
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.ejercicio.info.EjercicioInfoMaterialUiState
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.ejercicio.info.EjercicioInfoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class EjercicioInfoVM @Inject constructor(
    private val getEjercicioInfoByIdUseCase : GetEjercicioInfoByIdUseCase,
    private val deleteEjercicioUseCase : DeleteEjercicioUseCase,
    private val getMaterialByIdEjercicioUseCase : GetMaterialByIdEjercicioUseCase
) : ViewModel() {
    var uiStateEjercicio: StateFlow<EjercicioInfoUiState> = this.getFlowInitialEjercicio()
    var uiStateMaterial: StateFlow<EjercicioInfoMaterialUiState> = this.getFlowInitialMaterial()

    private var status = true

    private val _selectDeleteDialog = MutableStateFlow(false)
    val selectDeleteDialog: StateFlow<Boolean> = _selectDeleteDialog
    fun onChangeSelectDeleteDialog(selectDeleteDialog: Boolean) {
        _selectDeleteDialog.value = selectDeleteDialog
    }

    @Synchronized
    fun initData(id: Long) {
        if (status) {
            uiStateEjercicio = this.getFlowInitDataEjercicio(id)
            uiStateMaterial = this.getFlowInitDataMaterial(id)
            status = false
        }
    }

    fun deleteEjercicio(ejercicio: EjercicioInfoDto, context: Context) {
        deleteEjercicioUseCase(ejercicio, context)
    }

    private fun getFlowInitialEjercicio(): StateFlow<EjercicioInfoUiState> {
        return FlowManager.getEjercicioInfoDefault().map { EjercicioInfoUiState.Success(it) }
            .catch { EjercicioInfoUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = EjercicioInfoUiState.Loading
            )
    }

    private fun getFlowInitDataEjercicio(id: Long): StateFlow<EjercicioInfoUiState> {
        return getEjercicioInfoByIdUseCase(id).map { EjercicioInfoUiState.Success(it) }
            .catch { EjercicioInfoUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = EjercicioInfoUiState.Loading
            )
    }

    private fun getFlowInitialMaterial(): StateFlow<EjercicioInfoMaterialUiState> {
        return FlowManager.getEmptyList<RowMaterialDto>()
            .map { EjercicioInfoMaterialUiState.Success(it) }
            .catch { EjercicioInfoMaterialUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = EjercicioInfoMaterialUiState.Loading
            )
    }

    private fun getFlowInitDataMaterial(id: Long): StateFlow<EjercicioInfoMaterialUiState> {
        return getMaterialByIdEjercicioUseCase(id)
            .map { EjercicioInfoMaterialUiState.Success(it) }
            .catch { EjercicioInfoMaterialUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = EjercicioInfoMaterialUiState.Loading
            )
    }
}