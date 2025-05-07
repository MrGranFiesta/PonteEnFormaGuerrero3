package com.mrgranfiesta.ponteenformaguerrero3.domain.template

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.StateSerie
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.StepEntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.CountdownVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem.ManagerDualCronoVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem.ManagerDualRepsVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem.ManagerSimpleCronoVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem.ManagerSimpleRepsVM
import kotlinx.coroutines.flow.StateFlow

interface IManagerSerie {
    val countdownVM1: CountdownVM
    val countdownVM2: CountdownVM
    val cursorStep: Int
    val plusStep: () -> Unit
    val minusStep: (Boolean) -> Unit
    val descanso: Long
    val stepList: List<StepEntrenamientoDto>
    var nextStateBatch: () -> Unit

    val serie1: StateFlow<Int>
    val serie2: StateFlow<Int>
    val stateSerie1: StateFlow<StateSerie>
    val stateSerie2: StateFlow<StateSerie>
    val isSimetria: StateFlow<Boolean>
    val nombre: StateFlow<String>
    val photoUri: StateFlow<Uri>

    fun setIsSimetria(isSimetria: Boolean)
    fun setNombre(nombre: String)
    fun setPhotoUri(photoUri: Uri)

    fun nextSerie()
    fun previousSerie()
    fun nextStep() {
        if (cursorStep < this.getNumbersOfStep() - 1) {
            this.plusStep()
        } else {
            this.nextStateBatch()
        }
    }

    fun previousStep(isResetLastSerie: Boolean) {
        if (cursorStep > 0) minusStep(isResetLastSerie)
    }

    fun iteratorTimer(
        isInverse: Boolean,
        context: Context
    )

    fun changeStateByCtrlTimer()
    fun isPausedTimer(
        stateSerie1: StateSerie,
        stateSerie2: StateSerie
    ): Boolean

    fun getNumbersOfStep() = stepList.size
    fun getNumberOfSerie() = stepList[cursorStep].serie
    fun isSimetria() = stepList[cursorStep].isSimetria
    fun getCantidad(): Long
    fun getNombre() = stepList[cursorStep].nombre
    fun getPhotoUri() = stepList[cursorStep].photoUri

    fun getInitTime(isInverse: Boolean): Long
    fun isAutoPlay(isInverse: Boolean): Boolean
    fun initData(
        isPrevious: Boolean = false,
        executionExtra: () -> Unit = {},
        nextStateBatch: () -> Unit
    ): Boolean {
        this.setIsSimetria(this.isSimetria())
        this.setNombre(this.getNombre())
        this.setPhotoUri(this.getPhotoUri())
        this.nextStateBatch = nextStateBatch

        if (isPrevious) {
            this.resetLastSerie()
            executionExtra()
        }
        return true
    }

    fun resetLastSerie()
    fun isActive(state: StateSerie): Boolean
    fun isShow(state: StateSerie): Boolean

    fun onPause()
    fun onResume()

    companion object {
        fun getManager(
            countdownVM1: CountdownVM,
            countdownVM2: CountdownVM,
            stepList: List<StepEntrenamientoDto>,
            descanso: Int,
            cursorStep: Int,
            plusStep: () -> Unit = {},
            minusStep: (Boolean) -> Unit = {}
        ): ViewModelProvider.Factory {
            return when {
                stepList[cursorStep].tipo == TipoEsfuerzo.CRONO && stepList[cursorStep].isSimetria -> {
                    ManagerDualCronoVM.createManager(
                        countdownVM1 = countdownVM1,
                        countdownVM2 = countdownVM2,
                        stepList = stepList,
                        descanso = descanso,
                        cursorStep = cursorStep,
                        plusStep = plusStep,
                        minusStep = minusStep
                    )
                }

                stepList[cursorStep].tipo == TipoEsfuerzo.CRONO -> {
                    ManagerSimpleCronoVM.createManager(
                        countdownVM1 = countdownVM1,
                        countdownVM2 = countdownVM2,
                        stepList = stepList,
                        descanso = descanso,
                        cursorStep = cursorStep,
                        plusStep = plusStep,
                        minusStep = minusStep
                    )
                }

                stepList[cursorStep].isSimetria -> {
                    ManagerDualRepsVM.createManager(
                        countdownVM1 = countdownVM1,
                        countdownVM2 = countdownVM2,
                        stepList = stepList,
                        descanso = descanso,
                        cursorStep = cursorStep,
                        plusStep = plusStep,
                        minusStep = minusStep
                    )
                }

                else -> {
                    ManagerSimpleRepsVM.createManager(
                        countdownVM1 = countdownVM1,
                        countdownVM2 = countdownVM2,
                        stepList = stepList,
                        descanso = descanso,
                        cursorStep = cursorStep,
                        plusStep = plusStep,
                        minusStep = minusStep
                    )
                }
            }
        }
    }
}
