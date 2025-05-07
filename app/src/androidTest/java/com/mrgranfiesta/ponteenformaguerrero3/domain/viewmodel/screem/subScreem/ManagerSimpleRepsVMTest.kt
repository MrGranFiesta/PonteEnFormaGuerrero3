package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem

import android.net.Uri
import androidx.test.platform.app.InstrumentationRegistry
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.StateSerie
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.StepEntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.SoundUtils
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.CountdownVM
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkObject
import io.mockk.spyk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.isAccessible

class ManagerSimpleRepsVMTest {
    lateinit var viewModel: ManagerSimpleRepsVM

    @RelaxedMockK
    private lateinit var countdownVM: CountdownVM

    private fun getListStep() = listOf(
        StepEntrenamientoDto(
            idEjercicio = 1,
            idStep = 1,
            nombre = "Press Banca",
            photoUri = Uri.EMPTY,
            isSimetria = true,
            cantidad = 1,
            musculoSet = mutableSetOf(Musculo.TRAPECIO),
            serie = 1,
            ordenExecution = 1,
            tipo = TipoEsfuerzo.CRONO
        )
    )

    private fun setStateSerie1(stateSerie: StateSerie) {
        val method =
            ManagerSimpleRepsVM::class.declaredFunctions.find { it.name == "setStateSerie1" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, stateSerie)
        }
    }

    private fun setSerie1(serie: Int) {
        val method =
            ManagerSimpleRepsVM::class.declaredFunctions.find { it.name == "setSerie1" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, serie)
        }
    }

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        mockkObject(SoundUtils)
        viewModel = spyk(
            ManagerSimpleRepsVM(
                countdownVM1 = countdownVM,
                countdownVM2 = countdownVM,
                stepList = getListStep(),
                descanso = 10,
                cursorStep = 0,
                plusStep = {},
                minusStep = { bool -> }
            ),
            recordPrivateCalls = true
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun testNextSerieCurrentSerieEqualsSerie() {
        this.setSerie1(1)
        viewModel.nextSerie()

        coVerify { viewModel.nextStep() }
    }

    @Test
    fun testNextSerieIsActiveStateSerie1() {
        this.setSerie1(5)
        this.setStateSerie1(StateSerie.ACTIVO_PLAY)
        viewModel.nextSerie()

        coVerify { countdownVM.setActive(any()) }
        coVerify { viewModel["nextSerie1"]() }
    }

    @Test
    fun testNextSerieIsDescanso() = runTest {
        setSerie1(5)
        setStateSerie1(StateSerie.DESCANSO_PLAY)
        viewModel.nextSerie()

        val result = viewModel.stateSerie1.first()
        assertEquals(StateSerie.ACTIVO_PLAY, result)
    }

    @Test
    fun testPreviousSerieIfNotFirstSerieAndSerieEqualsZero() {
        this.setSerie1(0)
        viewModel.cursorStep++
        viewModel.previousSerie()

        verify { viewModel.previousStep(isResetLastSerie = true) }
    }

    @Test
    fun testPreviousSerieIsActiveStateSerie1AndSerie1GreatherThanZero() = runTest {
        setSerie1(1)
        setStateSerie1(StateSerie.ACTIVO_PLAY)
        viewModel.previousSerie()

        val state = viewModel.stateSerie1.first()

        assertEquals(StateSerie.DESCANSO_PLAY, state)

        verify { countdownVM.setEndTime(any()) }
        verify { countdownVM.setActive(any()) }
    }

    @Test
    fun testPreviousSerieIsDescansoStateSerie1AndSerie1GreatherThanZero() = runTest {
        setSerie1(1)
        setStateSerie1(StateSerie.DESCANSO_PLAY)
        viewModel.previousSerie()

        val state = viewModel.stateSerie1.first()

        assertEquals(StateSerie.ACTIVO_PLAY, state)

        verify { viewModel["setStateSerie1"](ofType<StateSerie>()) }
        verify { viewModel["previousSerie1"]() }
    }

    @Test
    fun testIteratorTimerIsDescansoNotNextStep() = runTest {
        coEvery { SoundUtils.playSound(any(), any()) } answers {}

        setStateSerie1(StateSerie.DESCANSO_PLAY)
        setSerie1(0)

        viewModel.iteratorTimer(
            isInverse = true,
            context = InstrumentationRegistry.getInstrumentation().targetContext
        )

        val result = viewModel.stateSerie1.first()

        assertEquals(StateSerie.ACTIVO_PLAY, result)
        coVerify(exactly = 0) { viewModel["nextStep"]() }
    }

    @Test
    fun testIteratorTimerIsDescansoAndNextStep() = runTest {
        coEvery { SoundUtils.playSound(any(), any()) } answers {}

        setStateSerie1(StateSerie.DESCANSO_PLAY)
        setSerie1(1)

        viewModel.iteratorTimer(
            isInverse = true,
            context = InstrumentationRegistry.getInstrumentation().targetContext
        )

        val result = viewModel.stateSerie1.first()

        assertEquals(StateSerie.ACTIVO_PLAY, result)
        coVerify { viewModel["nextStep"]() }
    }

    @Test
    fun testIteratorTimerIsActiveOrInitial() {
        coEvery { SoundUtils.playSound(any(), any()) } answers {}

        setStateSerie1(StateSerie.ACTIVO_PLAY)

        viewModel.iteratorTimer(
            isInverse = true,
            context = InstrumentationRegistry.getInstrumentation().targetContext
        )

        coVerify { viewModel["nextSerie"]() }
    }

    @Test
    fun testIteratorTimerIsFinishToNothingToDo() = runTest {
        coEvery { SoundUtils.playSound(any(), any()) } answers {}

        setStateSerie1(StateSerie.FINISHED)

        viewModel.iteratorTimer(
            isInverse = true,
            context = InstrumentationRegistry.getInstrumentation().targetContext
        )

        val result = viewModel.stateSerie1.first()
        assertEquals(StateSerie.FINISHED, result)
        coVerify(exactly = 0) { viewModel["nextSerie"]() }
    }

    @Test
    fun testChangeStateByCtrlTimerState1AndState2IsDescanso() = runTest {
        setStateSerie1(StateSerie.DESCANSO_PAUSE)
        viewModel.changeStateByCtrlTimer()

        val resultState1 = viewModel.stateSerie1.first()

        assertEquals(StateSerie.DESCANSO_PLAY, resultState1)
    }

    @Test
    fun testChangeStateByCtrlTimerState1IsDescanso() = runTest {
        setStateSerie1(StateSerie.DESCANSO_PLAY)
        viewModel.changeStateByCtrlTimer()

        val resultState1 = viewModel.stateSerie1.first()

        assertEquals(StateSerie.DESCANSO_PAUSE, resultState1)
    }

    @Test
    fun testOnPauseIsDescansoPlay() {
        setStateSerie1(StateSerie.DESCANSO_PLAY)

        viewModel.onPause()

        coVerify(exactly = 1) { countdownVM.stopCountdown() }
        assertFalse(viewModel.isPauseManually)
    }

    @Test
    fun testOnPauseIsDescansoPause() {
        setStateSerie1(StateSerie.DESCANSO_PAUSE)

        viewModel.onPause()

        coVerify(exactly = 0) { countdownVM.stopCountdown() }
        assertTrue(viewModel.isPauseManually)
    }

    @Test
    fun testOnPauseNothingToDo() {
        setStateSerie1(StateSerie.FINISHED)

        viewModel.onPause()

        coVerify(exactly = 0) { countdownVM.stopCountdown() }
        assertFalse(viewModel.isPauseManually)
    }

    @Test
    fun testOnResumeIsPauseManually() {
        viewModel.isPauseManually = true
        setStateSerie1(StateSerie.FINISHED)

        viewModel.onResume()

        coVerify(exactly = 0) { countdownVM.playCountdown() }
        assertFalse(viewModel.isPauseManually)
    }

    @Test
    fun testOnResumeIsDescansoPlay() {
        viewModel.isPauseManually = false
        setStateSerie1(StateSerie.DESCANSO_PLAY)

        viewModel.onResume()

        coVerify(exactly = 1) { countdownVM.playCountdown() }
        assertFalse(viewModel.isPauseManually)
    }
}