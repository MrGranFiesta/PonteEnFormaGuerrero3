package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.SoundFile
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.StateSerie
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.StepEntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.template.IManagerSerie
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.DateUtils
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.SoundUtils
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.CountdownVM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class ManagerDualCronoVM(
    override var countdownVM1: CountdownVM,
    override var countdownVM2: CountdownVM,
    override var stepList: List<StepEntrenamientoDto>,
    override var descanso: Long,
    override var cursorStep: Int,
    override val plusStep: () -> Unit = {},
    override val minusStep: (Boolean) -> Unit = {}
) : ViewModel(), IManagerSerie {
    private val _serie1 = MutableStateFlow(0)
    override val serie1: StateFlow<Int> = _serie1
    private fun setSerie1(serie1: Int) {
        _serie1.value = serie1
    }

    private fun nextSerie1() = _serie1.value++
    private fun previousSerie1() = _serie1.value--

    private val _serie2 = MutableStateFlow(0)
    override val serie2: StateFlow<Int> = _serie2
    private fun setSerie2(serie2: Int) {
        _serie2.value = serie2
    }

    private fun nextSerie2() = _serie2.value++
    private fun previousSerie2() = _serie2.value--

    private var _stateSerie1 = MutableStateFlow(StateSerie.ACTIVO_PLAY)
    override val stateSerie1: StateFlow<StateSerie> = _stateSerie1
    private fun setStateSerie1(stateSerie1: StateSerie) {
        _stateSerie1.value = stateSerie1
    }

    private var _stateSerie2 = MutableStateFlow(StateSerie.INITIAL)
    override val stateSerie2: StateFlow<StateSerie> = _stateSerie2
    private fun setStateSerie2(stateSerie2: StateSerie) {
        _stateSerie2.value = stateSerie2
    }

    private var _isSimetria = MutableStateFlow(false)
    override val isSimetria: StateFlow<Boolean> = _isSimetria
    override fun setIsSimetria(isSimetria: Boolean) {
        _isSimetria.value = isSimetria
    }

    private var _photoUri = MutableStateFlow(Uri.EMPTY)
    override val photoUri: StateFlow<Uri> = _photoUri
    override fun setPhotoUri(photoUri: Uri) {
        _photoUri.value = photoUri
    }

    private var _nombre = MutableStateFlow("")
    override val nombre: StateFlow<String> = _nombre
    override fun setNombre(nombre: String) {
        _nombre.value = nombre
    }

    override var nextStateBatch: () -> Unit = {}

    override fun getCantidad() = DateUtils.getSecondsToMiliseconds(stepList[cursorStep].cantidad)

    override fun getInitTime(isInverse: Boolean): Long {
        return when {
            stateSerie2.value == StateSerie.INITIAL -> this.getCantidad()
            isInverse && this.getNumberOfSerie() - 1 <= this.serie2.value -> this.getCantidad()
            else -> this.descanso
        }
    }

    override fun isAutoPlay(isInverse: Boolean): Boolean {
        return when {
            isInverse && stateSerie2.value == StateSerie.INITIAL -> false
            !isInverse && stateSerie2.value == StateSerie.INITIAL -> true
            isInverse && this.serie2.value == this.getNumberOfSerie() - 1 -> true
            else -> false
        }
    }

    override fun nextSerie() {
        when {
            this.getNumberOfSerie() == _serie2.value && this.getNumberOfSerie() == _serie1.value -> this.nextStep()
            this.getNumberOfSerie() == _serie1.value -> {
                this.nextSerie2()
                this.resetLastDescanso(descanso)
            }

            _serie2.value < _serie1.value -> {
                this.nextSerie2()
                this.resetSerie1()
            }

            else -> {
                this.nextSerie1()
                this.resetSerie2()
            }
        }
    }

    override fun previousSerie() {
        when {
            _serie1.value == 0 && cursorStep > 0 -> this.previousStep(isResetLastSerie = true)
            _serie1.value == 1 && _serie2.value == 0 -> {
                this.previousSerie1()
                this.resetIntialSerie()
            }

            _serie1.value > 0 && _serie2.value >= _serie1.value -> {
                this.previousSerie2()
                this.resetSerie2()
            }

            _serie1.value > 0 -> {
                this.previousSerie1()
                this.resetSerie1()
            }
        }
    }

    private fun getStateBother(isInverse: Boolean) =
        if (isInverse) _stateSerie1.value else _stateSerie2.value

    private fun getStateThis(isInverse: Boolean) =
        if (isInverse) _stateSerie2.value else _stateSerie1.value

    private fun activeBother(isInverse: Boolean) {
        if (isInverse) {
            this.setStateSerie1(StateSerie.ACTIVO_PLAY)
        } else {
            this.setStateSerie2(StateSerie.ACTIVO_PLAY)
        }
    }

    private fun setStateThis(state: StateSerie, isInverse: Boolean) {
        if (isInverse) this.setStateSerie2(state) else this.setStateSerie1(state)
    }

    private fun getCountdownBother(isInverse: Boolean) =
        if (isInverse) countdownVM1 else countdownVM2

    private fun getCountdownThis(isInverse: Boolean): CountdownVM =
        if (isInverse) countdownVM2 else countdownVM1

    private fun getSerieThis(isInverse: Boolean) = if (isInverse) serie2.value else serie1.value

    private fun nextThisSerie(isInverse: Boolean) =
        if (isInverse) this.nextSerie2() else this.nextSerie1()

    @Synchronized
    override fun iteratorTimer(
        isInverse: Boolean,
        context: Context
    ) {
        SoundUtils.playSound(context, SoundFile.SWEET_ALERT)
        val thisState = this.getStateThis(isInverse)
        when (thisState) {
            StateSerie.DESCANSO_PLAY, StateSerie.DESCANSO_PAUSE -> this.handlerDescanso(isInverse)
            StateSerie.ACTIVO_PAUSE, StateSerie.ACTIVO_PLAY, StateSerie.ACTIVO_WAIT, StateSerie.INITIAL -> this.handlerActive(
                isInverse
            )

            StateSerie.FINISHED -> {
                /*NOTHING TO DO*/
            }
        }
    }

    @Synchronized
    private fun handlerDescanso(isInverse: Boolean) {
        val botherState = this.getStateBother(isInverse)
        val thisCountdownVM = this.getCountdownThis(isInverse)
        val thisSerie = this.getSerieThis(isInverse)

        thisCountdownVM.setEndTime(this.getCantidad())
        val isActivoBrother =
            StateSerie.isActive(botherState) && StateSerie.ACTIVO_WAIT != botherState
        val isSerie2NeverUnderSerie1 = isInverse && serie2.value == serie1.value
        val isSerie1Greater =
            !isInverse && StateSerie.isDescanso(botherState) && serie2.value < serie1.value
        if (getNumberOfSerie() == thisSerie) {
            this.setStateThis(StateSerie.FINISHED, isInverse)
            thisCountdownVM.stopCountdown()
            if (botherState == StateSerie.FINISHED) {
                this.nextStep()
            }
        } else if (isActivoBrother || isSerie2NeverUnderSerie1 || isSerie1Greater) {
            this.setStateThis(StateSerie.ACTIVO_WAIT, isInverse)
            thisCountdownVM.stopCountdown()
        } else {
            this.setStateThis(StateSerie.ACTIVO_PLAY, isInverse)
            thisCountdownVM.playCountdown()
        }
    }

    @Synchronized
    private fun handlerActive(isInverse: Boolean) {
        val botherState = this.getStateBother(isInverse)
        val thisCountdownVM = this.getCountdownThis(isInverse)
        val brotherCountdownVM = this.getCountdownBother(isInverse)
        thisCountdownVM.setEndTime(descanso)
        this.nextThisSerie(isInverse)
        this.setStateThis(StateSerie.DESCANSO_PLAY, isInverse)
        thisCountdownVM.playCountdown()
        if (StateSerie.ACTIVO_WAIT == botherState || StateSerie.INITIAL == botherState) {
            this.activeBother(isInverse)
            brotherCountdownVM.setEndTime(getCantidad())
            brotherCountdownVM.playCountdown()
        }
    }

    override fun changeStateByCtrlTimer() {
        val isReverseState2 = StateSerie.changeReverseState(_stateSerie2.value)
        val isReverseState1 = StateSerie.changeReverseState(_stateSerie1.value)
        when {
            StateSerie.isExtremeState(_stateSerie2.value) || StateSerie.ACTIVO_WAIT == _stateSerie2.value
                    || StateSerie.isDescanso(_stateSerie2.value) && StateSerie.isActive(_stateSerie1.value) -> {
                countdownVM1.setActive(StateSerie.isPlay(isReverseState1))
                this.setStateSerie1(isReverseState1)
            }

            StateSerie.FINISHED == _stateSerie1.value || StateSerie.ACTIVO_WAIT == _stateSerie1.value
                    || StateSerie.isActive(_stateSerie2.value) && StateSerie.isDescanso(_stateSerie1.value) -> {
                countdownVM2.setActive(StateSerie.isPlay(isReverseState2))
                this.setStateSerie2(isReverseState2)
            }

            StateSerie.isDescanso(_stateSerie2.value) && StateSerie.isDescanso(_stateSerie1.value) -> {
                countdownVM2.setActive(StateSerie.isPlay(isReverseState2))
                countdownVM1.setActive(StateSerie.isPlay(isReverseState1))
                this.setStateSerie2(isReverseState2)
                this.setStateSerie1(isReverseState1)
            }
        }
    }

    override fun isPausedTimer(
        stateSerie1: StateSerie,
        stateSerie2: StateSerie
    ): Boolean {
        return when {
            StateSerie.isExtremeState(stateSerie2) || StateSerie.ACTIVO_WAIT == stateSerie2
                    || (StateSerie.isDescanso(stateSerie2) && StateSerie.isActive(stateSerie1))
                -> StateSerie.isStop(stateSerie1)

            StateSerie.isExtremeState(stateSerie1) || StateSerie.ACTIVO_WAIT == stateSerie1
                    || (StateSerie.isActive(stateSerie2) && StateSerie.isDescanso(stateSerie1))
                -> StateSerie.isStop(stateSerie2)

            StateSerie.isDescanso(stateSerie2) && StateSerie.isDescanso(stateSerie1) -> {
                StateSerie.isStop(stateSerie2) && StateSerie.isStop(stateSerie1)
            }

            else -> false
        }
    }

    override fun isShow(state: StateSerie): Boolean {
        return StateSerie.isNotExtremeState(state)
    }

    private fun resetSerie2() {
        this.setStateSerie2(StateSerie.ACTIVO_PLAY)
        this.setStateSerie1(StateSerie.DESCANSO_PLAY)
        countdownVM2.setEndTime(getCantidad())
        countdownVM1.setEndTime(descanso)
        countdownVM2.setActive(true)
        countdownVM1.setActive(true)
    }

    private fun resetSerie1() {
        this.setStateSerie2(StateSerie.DESCANSO_PLAY)
        this.setStateSerie1(StateSerie.ACTIVO_PLAY)
        countdownVM2.setEndTime(descanso)
        countdownVM1.setEndTime(getCantidad())
        countdownVM2.setActive(true)
        countdownVM1.setActive(true)
    }

    override fun resetLastSerie() {
        this.setSerie2(getNumberOfSerie() - 1)
        this.setSerie1(getNumberOfSerie())
        this.setStateSerie2(StateSerie.ACTIVO_PLAY)
        this.setStateSerie1(StateSerie.FINISHED)
        countdownVM2.setEndTime(getCantidad())
        countdownVM1.setEndTime(getCantidad())
        countdownVM2.setActive(true)
        countdownVM1.setActive(false)
    }

    override fun isActive(state: StateSerie) = true

    private fun resetLastDescanso(seconds: Long) {
        this.setStateSerie2(StateSerie.DESCANSO_PLAY)
        countdownVM2.setEndTime(seconds)
        countdownVM2.setActive(true)
    }

    private fun resetIntialSerie() {
        this.setStateSerie2(StateSerie.INITIAL)
        this.setStateSerie1(StateSerie.ACTIVO_PLAY)
        countdownVM2.setEndTime(getCantidad())
        countdownVM1.setEndTime(getCantidad())
        countdownVM2.setActive(false)
        countdownVM1.setActive(true)
    }

    var isPauseManually1 = false
    var isPauseManually2 = false

    override fun onPause() {
        if (StateSerie.isPlay(_stateSerie1.value)) {
            countdownVM1.stopCountdown()
        } else if (StateSerie.isPause(_stateSerie1.value)) {
            isPauseManually1 = true
        }
        if (StateSerie.isPlay(_stateSerie2.value)) {
            countdownVM2.stopCountdown()
        } else if (StateSerie.isPause(_stateSerie2.value)) {
            isPauseManually2 = true
        }
    }

    override fun onResume() {
        if (isPauseManually1) {
            isPauseManually1 = false
        } else if (StateSerie.isPlay(_stateSerie1.value)) {
            countdownVM1.playCountdown()
        }
        if (isPauseManually2) {
            isPauseManually2 = false
        } else if (StateSerie.isPlay(_stateSerie2.value)) {
            countdownVM1.playCountdown()
        }
    }

    companion object {
        fun createManager(
            countdownVM1: CountdownVM,
            countdownVM2: CountdownVM,
            stepList: List<StepEntrenamientoDto>,
            descanso: Int,
            cursorStep: Int,
            plusStep: () -> Unit,
            minusStep: (Boolean) -> Unit
        ): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    ManagerDualCronoVM(
                        countdownVM1 = countdownVM1,
                        countdownVM2 = countdownVM2,
                        stepList = stepList,
                        descanso = DateUtils.getSecondsToMiliseconds(descanso),
                        cursorStep = cursorStep,
                        plusStep = plusStep,
                        minusStep = minusStep
                    )
                }
            }
        }
    }
}