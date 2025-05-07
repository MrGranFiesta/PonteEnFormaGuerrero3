package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.MaterialInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.FlowManager
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material.DeleteMaterialUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material.GetMaterialByIdUseCase
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.material.info.MaterialInfoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MaterialInfoVM @Inject constructor(
    private val getMaterialByIdUseCase : GetMaterialByIdUseCase,
    private val deleteMaterialUseCase : DeleteMaterialUseCase
) : ViewModel() {
    var uiState: StateFlow<MaterialInfoUiState> = this.getFlowInitial()

    private var status = true

    private val _selectDeleteDialog = MutableStateFlow(false)
    val selectDeleteDialog: StateFlow<Boolean> = _selectDeleteDialog
    fun onChangeSelectDeleteDialog(selectDeleteDialog: Boolean) {
        _selectDeleteDialog.value = selectDeleteDialog
    }

    @Synchronized
    fun initData(id: Long) {
        if (status) {
            uiState = this.getFlowInitData(id)
            status = false
        }
    }

    fun deleteMaterial(context: Context, material: MaterialInfoDto) {
        deleteMaterialUseCase(context, material)
    }

    private fun getFlowInitial(): StateFlow<MaterialInfoUiState> {
        return FlowManager.getFlowMaterialInfoDefault().map { MaterialInfoUiState.Success(it) }
            .catch { MaterialInfoUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = MaterialInfoUiState.Loading
            )
    }

    private fun getFlowInitData(id: Long): StateFlow<MaterialInfoUiState> {
        return getMaterialByIdUseCase(id).map { MaterialInfoUiState.Success(it) }
            .catch { MaterialInfoUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = MaterialInfoUiState.Loading
            )
    }
}