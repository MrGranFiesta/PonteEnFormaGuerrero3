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


class ManagerSimpleRepsVM(
    override var countdownVM1: CountdownVM,
    override var countdownVM2: CountdownVM,
    override var stepList: List<StepEntrenamientoDto>,
    override var descanso: Long,
    override var cursorStep: Int,
    override var plusStep: () -> Unit = {},
    override var minusStep: (Boolean) -> Unit = {}
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

    private var _stateSerie1 = MutableStateFlow(StateSerie.ACTIVO_PLAY)
    override val stateSerie1: StateFlow<StateSerie> = _stateSerie1
    private fun setStateSerie1(stateSerie1: StateSerie) {
        _stateSerie1.value = stateSerie1
    }

    private var _stateSerie2 = MutableStateFlow(StateSerie.ACTIVO_PLAY)
    override val stateSerie2: StateFlow<StateSerie> = _stateSerie2

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

    override fun getCantidad() = stepList[cursorStep].cantidad.toLong()

    override fun getInitTime(isInverse: Boolean) = descanso

    override fun isAutoPlay(isInverse: Boolean) = true

    override fun nextSerie() {
        when {
            this.getNumberOfSerie() == _serie1.value -> this.nextStep()
            StateSerie.isActive(_stateSerie1.value) -> {
                this.setStateSerie1(StateSerie.DESCANSO_PLAY)
                countdownVM1.setEndTime(descanso)
                countdownVM1.setActive(true)
                this.nextSerie1()
            }

            StateSerie.isDescanso(_stateSerie1.value) -> this.setStateSerie1(StateSerie.ACTIVO_PLAY)
        }
    }

    override fun previousSerie() {
        when {
            _serie1.value == 0 && cursorStep > 0 -> this.previousStep(isResetLastSerie = true)
            StateSerie.isActive(_stateSerie1.value) && _serie1.value > 0 -> {
                this.setStateSerie1(StateSerie.DESCANSO_PLAY)
                countdownVM1.setEndTime(descanso)
                countdownVM1.setActive(true)
            }

            StateSerie.isDescanso(_stateSerie1.value) && _serie1.value > 0 -> {
                this.setStateSerie1(StateSerie.ACTIVO_PLAY)
                this.previousSerie1()
            }
        }
    }

    @Synchronized
    override fun iteratorTimer(
        isInverse: Boolean,
        context: Context
    ) {
        SoundUtils.playSound(context, SoundFile.SWEET_ALERT)
        when (_stateSerie1.value) {
            StateSerie.DESCANSO_PLAY, StateSerie.DESCANSO_PAUSE -> {
                //Vas a trabajar
                this.setStateSerie1(StateSerie.ACTIVO_PLAY)
                if (_serie1.value == this.getNumberOfSerie()) {
                    this.nextStep()
                }
            }

            StateSerie.ACTIVO_PAUSE, StateSerie.ACTIVO_PLAY, StateSerie.ACTIVO_WAIT, StateSerie.INITIAL -> {
                nextSerie()
            }

            StateSerie.FINISHED -> {
                /*NOTHING TO DO*/
            }
        }
    }

    override fun changeStateByCtrlTimer() {
        if (StateSerie.isPlay(_stateSerie1.value)) {
            this.countdownVM1.stopCountdown()
        } else {
            this.countdownVM1.playCountdown()
        }
        this.setStateSerie1(StateSerie.changeReverseState(_stateSerie1.value))
    }

    override fun isPausedTimer(
        stateSerie1: StateSerie,
        stateSerie2: StateSerie
    ) = StateSerie.isPause(stateSerie1)

    override fun isActive(state: StateSerie) = StateSerie.isActive(state)

    override fun resetLastSerie() {
        this.setSerie1(getNumberOfSerie() - 1)
    }

    override fun isShow(state: StateSerie) = true

    var isPauseManually = false

    override fun onPause() {
        if (stateSerie1.value == StateSerie.DESCANSO_PLAY) {
            countdownVM1.stopCountdown()
        } else if (stateSerie1.value == StateSerie.DESCANSO_PAUSE) {
            isPauseManually = true
        }
    }

    override fun onResume() {
        if (isPauseManually) {
            isPauseManually = false
        } else if (stateSerie1.value == StateSerie.DESCANSO_PLAY) {
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
                    ManagerSimpleRepsVM(
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