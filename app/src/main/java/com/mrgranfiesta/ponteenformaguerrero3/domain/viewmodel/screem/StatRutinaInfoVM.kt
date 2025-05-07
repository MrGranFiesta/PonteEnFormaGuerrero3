package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioNameAndPhotoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.stat.StatRutinaInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.FlowManager
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.snapshot.DeleteSnapshotUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.snapshot.ejercicio.GetEjercicioSnapshotNameAndPhotoByIdUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.snapshot.step.GetStepSnapshotUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.stat.GetStatRutinaInfoUseCase
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.stat.info.rutina.StatInfoRutinaStatUiState
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.stat.info.rutina.StatInfoStepUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class StatRutinaInfoVM @Inject constructor(
    private val deleteSnapshotUseCase : DeleteSnapshotUseCase,
    private val getStatRutinaInfoUseCase : GetStatRutinaInfoUseCase,
    private val getStepSnapshotUseCase : GetStepSnapshotUseCase,
    private val getEjercicioSnapshotNameAndPhotoByIdUseCase : GetEjercicioSnapshotNameAndPhotoByIdUseCase
) : ViewModel() {
    var uiStateStatRutina: StateFlow<StatInfoRutinaStatUiState> = this.getFlowRutinaStatInitial()
    var uiStateStepSnapshot: StateFlow<StatInfoStepUiState> = this.getFlowStepInitial()

    private var status = true

    private val _selectDeleteDialog = MutableStateFlow(false)
    val selectDeleteDialog: StateFlow<Boolean> = _selectDeleteDialog
    fun onChangeSelectDeleteDialog(selectDeleteDialog: Boolean) {
        _selectDeleteDialog.value = selectDeleteDialog
    }

    private val _showMaterialDialog = MutableStateFlow(false)
    val showMaterialDialog: StateFlow<Boolean> = _showMaterialDialog
    fun onChangeShowMaterialDialog(showMaterialDialog: Boolean) {
        _showMaterialDialog.value = showMaterialDialog
    }

    private val _cursor = MutableStateFlow(0)
    val cursor: StateFlow<Int> = _cursor
    fun setCursor(cursor: Int) {
        _cursor.value = cursor
    }

    @Synchronized
    fun initData(id: Long) {
        if (status) {
            uiStateStatRutina = this.getFlowRutinaStatInitData(id)
            uiStateStepSnapshot = this.getFlowStepInitData(id)
            status = false
        }
    }

    fun deleteStat(rutinaOrigen: StatRutinaInfoDto) {
        deleteSnapshotUseCase(rutinaOrigen.idRutinaSnapshot)
    }

    private fun getFlowRutinaStatInitial(): StateFlow<StatInfoRutinaStatUiState> {
        return FlowManager.getStatRutinaInfoDtoBeanDefault().map { StatInfoRutinaStatUiState.Success(it) }
            .catch { StatInfoRutinaStatUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = StatInfoRutinaStatUiState.Loading
            )
    }

    private fun getFlowRutinaStatInitData(id: Long): StateFlow<StatInfoRutinaStatUiState> {
        return getStatRutinaInfoUseCase(id).map { StatInfoRutinaStatUiState.Success(it) }
            .catch { StatInfoRutinaStatUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = StatInfoRutinaStatUiState.Loading
            )
    }

    private fun getFlowStepInitial(): StateFlow<StatInfoStepUiState> {
        return FlowManager.getListStepSnapshotDefault().map { StatInfoStepUiState.Success(it) }
            .catch { StatInfoStepUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = StatInfoStepUiState.Loading
            )
    }

    private fun getFlowStepInitData(id: Long): StateFlow<StatInfoStepUiState> {
        return getStepSnapshotUseCase(id).map { StatInfoStepUiState.Success(it) }
            .catch { StatInfoStepUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = StatInfoStepUiState.Loading
            )
    }

    fun getEjercicioNameAndPhoto(idEjercicioSnapshot : Long): Flow<EjercicioNameAndPhotoDto> {
        return getEjercicioSnapshotNameAndPhotoByIdUseCase(idEjercicioSnapshot)
    }
}