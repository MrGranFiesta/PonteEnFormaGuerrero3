package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.dialog


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EDIT_STEP_CRONO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EDIT_STEP_REPETICION
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.dialog.MaterialNameDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.dialog.StepEditDialogDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.ObjectFactory
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material.GetMaterialNameAndConfListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditStepDialogVM @Inject constructor(
    private val getMaterialNameAndConfListUseCase: GetMaterialNameAndConfListUseCase
) : ViewModel() {
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private var status = true

    private val _materialNameDtoList = MutableStateFlow(ObjectFactory.getListMaterialNameDto())
    val materialNameDtoList: StateFlow<List<MaterialNameDto>> = _materialNameDtoList
    fun setMaterialNameDtoList(materialNameDtoList: List<MaterialNameDto>) {
        viewModelScope.launch {
            _materialNameDtoList.value = materialNameDtoList.toList()
        }
    }

    private val _cantidad = MutableStateFlow(1)
    val cantidad: StateFlow<Int> = _cantidad
    fun setCantidad(cantidad: Int) {
        _cantidad.value = cantidad
    }

    private val _serie = MutableStateFlow(1)
    val serie: StateFlow<Int> = _serie
    fun setSerie(serie: Int) {
        _serie.value = serie
    }

    private val _tipoEsfuerzo = MutableStateFlow(TipoEsfuerzo.REPETICION)
    val tipoEsfuerzo: StateFlow<TipoEsfuerzo> = _tipoEsfuerzo
    fun setTipoEsfuerzo(tipoEsfuerzo: TipoEsfuerzo) {
        _tipoEsfuerzo.value = tipoEsfuerzo
    }

    fun generateLabelTxtUnid(tipoEsfuerzo: TipoEsfuerzo): String {
        return when (tipoEsfuerzo) {
            TipoEsfuerzo.REPETICION -> LABEL_EDIT_STEP_REPETICION
            TipoEsfuerzo.CRONO -> LABEL_EDIT_STEP_CRONO
        }
    }

    fun typeAmount(tipoEsfuerzo: TipoEsfuerzo): Int {
        return when (tipoEsfuerzo) {
            TipoEsfuerzo.REPETICION -> 2
            TipoEsfuerzo.CRONO -> 5
        }
    }

    @Synchronized
    fun initData(step: StepEditDialogDto) : Boolean {
        if (status) {
            setCantidad(step.cantidad)
            setSerie(step.serie)
            setTipoEsfuerzo(step.tipo)
            setMaterialNameDtoList(step.confMaterialList)
            if (_materialNameDtoList.value.isEmpty()) {
                ioScope.launch {
                    getMaterialNameAndConfListUseCase(
                        step.idEjercicio,
                        step.idStep
                    ).collect {
                        setMaterialNameDtoList(it)
                    }
                }
            }
            status = false
        }
        return true
    }

    fun resetStatus() {
        status = true
    }

    fun getStepEditDialogDto(step: StepEditDialogDto) = StepEditDialogDto(
        idStep = step.idStep,
        idEjercicio = step.idEjercicio,
        cantidad = _cantidad.value,
        serie = _serie.value,
        tipo = _tipoEsfuerzo.value,
        confMaterialList = _materialNameDtoList.value
    )
}