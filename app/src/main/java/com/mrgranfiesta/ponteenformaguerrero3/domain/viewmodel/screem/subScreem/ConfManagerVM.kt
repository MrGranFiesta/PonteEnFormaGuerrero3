package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.StepEntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.getManagerCronoDual
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.getManagerCronoSimple
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.getManagerRepsDual
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.getManagerRepsSimple
import com.mrgranfiesta.ponteenformaguerrero3.domain.template.IManagerSerie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ConfManagerVM : ViewModel() {
    private val _cursorStep = MutableStateFlow(0)
    val cursorStep: StateFlow<Int> = _cursorStep

    var isLastSeriePrevius = false
    fun setIsLastSeriePrevius(isLastSeriePrevius: Boolean) {
        this.isLastSeriePrevius = isLastSeriePrevius
    }

    fun nextCursorStep() = _cursorStep.value++
    fun previousCursorStep(isLastSeriePrevius: Boolean) {
        this.setIsLastSeriePrevius(isLastSeriePrevius)
        _cursorStep.value--
    }

    private var _managerVM: IManagerSerie? = null

    @Composable
    fun getOrCreateImagerSerie(
        stepList: List<StepEntrenamientoDto>,
        descanso: Int,
        cursorStep: Int
    ) : IManagerSerie {
        if (_managerVM == null || _managerVM?.cursorStep != cursorStep) {
            _managerVM = getImagerSerie(stepList, descanso, cursorStep)
        }
        return _managerVM!!
    }

    @Composable
    private fun getImagerSerie(
        stepList: List<StepEntrenamientoDto>,
        descanso: Int,
        cursorStep: Int
    ) : IManagerSerie {
        return when {
            TipoEsfuerzo.CRONO == stepList[cursorStep].tipo && stepList[cursorStep].isSimetria -> {
                getManagerCronoDual(
                    confVM = this,
                    stepList = stepList,
                    descanso = descanso,
                    cursorStep = cursorStep
                )
            }

            stepList[cursorStep].isSimetria -> {
                getManagerRepsDual(
                    confVM = this,
                    stepList = stepList,
                    descanso = descanso,
                    cursorStep = cursorStep
                )
            }

            TipoEsfuerzo.CRONO == stepList[cursorStep].tipo -> {
                getManagerCronoSimple(
                    confVM = this,
                    stepList = stepList,
                    descanso = descanso,
                    cursorStep = cursorStep
                )
            }

            else -> {
                getManagerRepsSimple(
                    confVM = this,
                    stepList = stepList,
                    descanso = descanso,
                    cursorStep = cursorStep
                )
            }
        }
    }
}