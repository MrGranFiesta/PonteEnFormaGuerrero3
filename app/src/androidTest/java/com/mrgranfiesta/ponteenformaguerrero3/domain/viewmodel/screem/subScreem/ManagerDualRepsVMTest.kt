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
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.isAccessible

class ManagerDualRepsVMTest {
    lateinit var viewModel: ManagerDualRepsVM

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
            serie = 3,
            ordenExecution = 1,
            tipo = TipoEsfuerzo.CRONO
        )
    )

    private fun setStateSerie1(stateSerie: StateSerie) {
        val method =
            ManagerDualRepsVM::class.declaredFunctions.find { it.name == "setStateSerie1" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, stateSerie)
        }
    }

    private fun setStateSerie2(stateSerie: StateSerie) {
        val method =
            ManagerDualRepsVM::class.declaredFunctions.find { it.name == "setStateSerie2" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, stateSerie)
        }
    }

    private fun setSerie2(serie: Int) {
        val method =
            ManagerDualRepsVM::class.declaredFunctions.find { it.name == "setSerie2" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, serie)
        }
    }

    private fun setSerie1(serie: Int) {
        val method =
            ManagerDualRepsVM::class.declaredFunctions.find { it.name == "setSerie1" }
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
            ManagerDualRepsVM(
                countdownVM1 = countdownVM,
                countdownVM2 = countdownVM,
                stepList = getListStep(),
                descanso = 15,
                cursorStep = 0,
                plusStep = {},
                minusStep = { bool -> }
            ),
            recordPrivateCalls = true)
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun testNextSerieCurrentSerieEqualsSerie() {
        this.setSerie1(3)
        this.setSerie2(3)
        viewModel.nextSerie()

        coVerify { viewModel.nextStep() }
    }

    @Test
    fun testNextSerieCurrentUserEqualsSerie1() = runTest {
        setSerie1(3)
        setSerie2(5)
        viewModel.nextSerie()

        coVerify { viewModel["nextSerie2"]() }
        coVerify { viewModel["resetLastDescanso"]() }
    }

    @Test
    fun testNextSerieSerie1LessThanSerie2() = runTest {
        setSerie2(5)
        setSerie1(7)
        viewModel.nextSerie()

        coVerify { viewModel["nextSerie2"]() }
        coVerify { viewModel["resetSerie1"]() }
    }

    @Test
    fun testNextSerieElse() = runTest {
        setSerie2(5)
        setSerie1(5)
        viewModel.nextSerie()

        coVerify { viewModel["nextSerie1"]() }
        coVerify { viewModel["resetSerie2"]() }
    }

    @Test
    fun testPreviousSerieIfNotFirstSerieAndSerieEqualsZero() {
        this.setSerie1(0)
        viewModel.cursorStep++
        viewModel.previousSerie()

        verify { viewModel.previousStep(isResetLastSerie = true) }
    }

    @Test
    fun testPreviousSerieSerie1Equals1AndSerie2Equals0() {
        this.setSerie1(1)
        this.setSerie2(0)
        viewModel.previousSerie()

        verify { viewModel["previousSerie1"]() }
        verify { viewModel["resetIntialSerie"]() }
    }

    @Test
    fun testPreviousSerieSerie2GreatherEqualsThanZeroSerie1() {
        this.setSerie1(1)
        this.setSerie2(1)
        viewModel.previousSerie()

        verify { viewModel["previousSerie2"]() }
        verify { viewModel["resetSerie2"]() }
    }

    @Test
    fun testPreviousSerieSerie1GreatherZero() {
        this.setSerie1(2)
        this.setSerie2(1)
        viewModel.previousSerie()

        verify { viewModel["previousSerie1"]() }
        verify { viewModel["resetSerie1"]() }
    }

    @Test
    fun testGetStateBotherNoInverse() {
        setStateSerie1(StateSerie.DESCANSO_PLAY)
        setStateSerie2(StateSerie.ACTIVO_PLAY)
        val method =
            ManagerDualRepsVM::class.declaredFunctions.find { it.name == "getStateBother" }
        val result = method?.let {
            it.isAccessible = true
            it.call(viewModel, false)
        } as? StateSerie

        assertEquals(StateSerie.ACTIVO_PLAY, result)
    }

    @Test
    fun testGetStateBotherInverse() {
        setStateSerie1(StateSerie.DESCANSO_PLAY)
        setStateSerie2(StateSerie.ACTIVO_PLAY)
        val method =
            ManagerDualRepsVM::class.declaredFunctions.find { it.name == "getStateBother" }
        val result = method?.let {
            it.isAccessible = true
            it.call(viewModel, true)
        } as? StateSerie

        assertEquals(StateSerie.DESCANSO_PLAY, result)
    }

    @Test
    fun testGetStateThisNoInverse() {
        setStateSerie1(StateSerie.DESCANSO_PLAY)
        setStateSerie2(StateSerie.ACTIVO_PLAY)
        val method =
            ManagerDualRepsVM::class.declaredFunctions.find { it.name == "getStateThis" }
        val result = method?.let {
            it.isAccessible = true
            it.call(viewModel, false)
        } as? StateSerie

        assertEquals(StateSerie.DESCANSO_PLAY, result)
    }

    @Test
    fun testGetStateThisInverse() {
        setStateSerie1(StateSerie.DESCANSO_PLAY)
        setStateSerie2(StateSerie.ACTIVO_PLAY)
        val method =
            ManagerDualRepsVM::class.declaredFunctions.find { it.name == "getStateThis" }
        val result = method?.let {
            it.isAccessible = true
            it.call(viewModel, true)
        } as? StateSerie

        assertEquals(StateSerie.ACTIVO_PLAY, result)
    }

    @Test
    fun testActiveBotherNoInverse() = runTest {
        setStateSerie1(StateSerie.DESCANSO_PLAY)
        setStateSerie2(StateSerie.ACTIVO_PLAY)
        val method =
            ManagerDualRepsVM::class.declaredFunctions.find { it.name == "activeBother" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, false)
        } as? StateSerie

        val resultState1 = viewModel.stateSerie1.first()
        val resultState2 = viewModel.stateSerie2.first()

        assertEquals(StateSerie.DESCANSO_PLAY, resultState1)
        assertEquals(StateSerie.ACTIVO_PLAY, resultState2)
    }

    @Test
    fun testActiveBotherInverse() = runTest {
        setStateSerie1(StateSerie.DESCANSO_PLAY)
        setStateSerie2(StateSerie.ACTIVO_PLAY)
        val method =
            ManagerDualRepsVM::class.declaredFunctions.find { it.name == "activeBother" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, true)
        } as? StateSerie

        val resultState1 = viewModel.stateSerie1.first()
        val resultState2 = viewModel.stateSerie2.first()

        assertEquals(StateSerie.ACTIVO_PLAY, resultState1)
        assertEquals(StateSerie.ACTIVO_PLAY, resultState2)
    }

    @Test
    fun testSetStateThisNoInverse() = runTest {
        setStateSerie1(StateSerie.DESCANSO_PLAY)
        setStateSerie2(StateSerie.ACTIVO_PLAY)
        val method =
            ManagerDualRepsVM::class.declaredFunctions.find { it.name == "setStateThis" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, StateSerie.INITIAL, false)
        } as? StateSerie

        val resultState1 = viewModel.stateSerie1.first()
        val resultState2 = viewModel.stateSerie2.first()

        assertEquals(StateSerie.INITIAL, resultState1)
        assertEquals(StateSerie.ACTIVO_PLAY, resultState2)
    }

    @Test
    fun testSetStateThisInverse() = runTest {
        setStateSerie1(StateSerie.DESCANSO_PLAY)
        setStateSerie2(StateSerie.ACTIVO_PLAY)
        val method =
            ManagerDualRepsVM::class.declaredFunctions.find { it.name == "setStateThis" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, StateSerie.ACTIVO_WAIT, true)
        } as? StateSerie

        val resultState1 = viewModel.stateSerie1.first()
        val resultState2 = viewModel.stateSerie2.first()

        assertEquals(StateSerie.DESCANSO_PLAY, resultState1)
        assertEquals(StateSerie.ACTIVO_WAIT, resultState2)
    }

    @Test
    fun testGetSerieThisInverse() {
        this.setSerie1(20)
        this.setSerie2(30)

        val method =
            ManagerDualRepsVM::class.declaredFunctions.find { it.name == "getSerieThis" }
        val result = method?.let {
            it.isAccessible = true
            it.call(viewModel, true)
        } as? Int

        assertEquals(30, result)
    }

    @Test
    fun testGetSerieThisNoInverse() {
        this.setSerie1(20)
        this.setSerie2(30)

        val method =
            ManagerDualRepsVM::class.declaredFunctions.find { it.name == "getSerieThis" }
        val result = method?.let {
            it.isAccessible = true
            it.call(viewModel, false)
        } as? Int

        assertEquals(20, result)
    }

    @Test
    fun testNextThisSerieInverse() = runTest {
        setSerie1(20)
        setSerie2(30)

        val method =
            ManagerDualRepsVM::class.declaredFunctions.find { it.name == "nextThisSerie" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, true)
        }

        val resultSerie1 = viewModel.serie1.first()
        val resultSerie2 = viewModel.serie2.first()

        assertEquals(20, resultSerie1)
        assertEquals(31, resultSerie2)
    }

    @Test
    fun testNextThisSerieNoInverse() = runTest {
        setSerie1(20)
        setSerie2(30)

        val method =
            ManagerDualRepsVM::class.declaredFunctions.find { it.name == "nextThisSerie" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, false)
        }

        val resultSerie1 = viewModel.serie1.first()
        val resultSerie2 = viewModel.serie2.first()

        assertEquals(21, resultSerie1)
        assertEquals(30, resultSerie2)
    }

    @Test
    fun testIteratorTimerDescansoToHandleDescanso() {
        coEvery { viewModel["handlerDescanso"](ofType<Boolean>()) } answers {}
        coEvery { SoundUtils.playSound(any(), any()) } answers {}

        setStateSerie2(StateSerie.DESCANSO_PLAY)

        viewModel.iteratorTimer(
            isInverse = true,
            context = InstrumentationRegistry.getInstrumentation().targetContext
        )

        coVerify { viewModel["handlerDescanso"](ofType<Boolean>()) }
    }

    @Test
    fun testIteratorTimerActiveOrInitialToHandleActive() {
        coEvery { SoundUtils.playSound(any(), any()) } answers {}

        setStateSerie2(StateSerie.ACTIVO_PLAY)

        viewModel.iteratorTimer(
            isInverse = true,
            context = InstrumentationRegistry.getInstrumentation().targetContext
        )

        coVerify { viewModel["handlerActive"](ofType<Boolean>()) }
    }

    @Test
    fun testIteratorTimerFinishToNothingToDo() {
        coEvery { SoundUtils.playSound(any(), any()) } answers {}

        setStateSerie2(StateSerie.FINISHED)

        viewModel.iteratorTimer(
            isInverse = true,
            context = InstrumentationRegistry.getInstrumentation().targetContext
        )

        coVerify(exactly = 0) { viewModel["handlerActive"](ofType<Boolean>()) }
        coVerify(exactly = 0) { viewModel["handlerDescanso"](ofType<Boolean>()) }
    }

    @Test
    fun testHandlerDescansoThisStateFinished() = runTest {
        setSerie1(3)
        setStateSerie1(StateSerie.DESCANSO_PLAY)

        setSerie2(3)
        setStateSerie2(StateSerie.DESCANSO_PLAY)

        val method =
            ManagerDualRepsVM::class.declaredFunctions.find { it.name == "handlerDescanso" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, true)
        }

        val resultState1 = viewModel.stateSerie1.first()
        val resultState2 = viewModel.stateSerie2.first()

        assertNotEquals(StateSerie.FINISHED, resultState1)
        assertEquals(StateSerie.FINISHED, resultState2)
    }

    @Test
    fun testHandlerDescansoThisAndBrotherStateFinished() = runTest {
        setSerie1(3)
        setStateSerie1(StateSerie.FINISHED)

        setSerie2(3)
        setStateSerie2(StateSerie.DESCANSO_PLAY)

        val method =
            ManagerDualRepsVM::class.declaredFunctions.find { it.name == "handlerDescanso" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, true)
        }

        val resultState1 = viewModel.stateSerie1.first()
        val resultState2 = viewModel.stateSerie2.first()

        assertEquals(StateSerie.FINISHED, resultState1)
        assertEquals(StateSerie.FINISHED, resultState2)

        coVerify { viewModel["nextStep"]() }
    }

    @Test
    fun testHandlerDescansoActiveBotherSecurity1() = runTest {
        setSerie1(1)
        setStateSerie1(StateSerie.ACTIVO_PLAY)

        setSerie2(0)
        setStateSerie2(StateSerie.DESCANSO_PLAY)

        val method =
            ManagerDualRepsVM::class.declaredFunctions.find { it.name == "handlerDescanso" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, true)
        }

        val resultState1 = viewModel.stateSerie1.first()
        val resultState2 = viewModel.stateSerie2.first()

        assertEquals(StateSerie.ACTIVO_PLAY, resultState1)
        assertEquals(StateSerie.ACTIVO_WAIT, resultState2)
    }

    @Test
    fun testHandlerDescansoActiveBotherSecurity2() = runTest {
        setSerie1(0)
        setStateSerie1(StateSerie.DESCANSO_PLAY)

        setSerie2(0)
        setStateSerie2(StateSerie.DESCANSO_PLAY)

        val method =
            ManagerDualRepsVM::class.declaredFunctions.find { it.name == "handlerDescanso" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, true)
        }

        val resultState1 = viewModel.stateSerie1.first()
        val resultState2 = viewModel.stateSerie2.first()

        assertEquals(StateSerie.DESCANSO_PLAY, resultState1)
        assertEquals(StateSerie.ACTIVO_WAIT, resultState2)
    }

    @Test
    fun testHandlerDescansoActiveBotherSecurity3() = runTest {
        setSerie1(1)
        setStateSerie1(StateSerie.DESCANSO_PLAY)

        setSerie2(0)
        setStateSerie2(StateSerie.DESCANSO_PLAY)

        val method =
            ManagerDualRepsVM::class.declaredFunctions.find { it.name == "handlerDescanso" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, false)
        }

        val resultState1 = viewModel.stateSerie1.first()
        val resultState2 = viewModel.stateSerie2.first()

        assertEquals(StateSerie.ACTIVO_WAIT, resultState1)
        assertEquals(StateSerie.DESCANSO_PLAY, resultState2)
    }

    @Test
    fun testHandlerDescansoThisActivePlay() = runTest {
        setSerie1(1)
        setStateSerie1(StateSerie.ACTIVO_WAIT)

        setSerie2(0)
        setStateSerie2(StateSerie.DESCANSO_PLAY)

        val method =
            ManagerDualRepsVM::class.declaredFunctions.find { it.name == "handlerDescanso" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, true)
        }

        val resultState1 = viewModel.stateSerie1.first()
        val resultState2 = viewModel.stateSerie2.first()

        assertEquals(StateSerie.ACTIVO_WAIT, resultState1)
        assertEquals(StateSerie.ACTIVO_PLAY, resultState2)
    }

    @Test
    fun testHandlerActiveIsInitial() = runTest {
        setSerie1(1)
        setStateSerie1(StateSerie.INITIAL)

        setSerie2(0)
        setStateSerie2(StateSerie.DESCANSO_PLAY)

        val method =
            ManagerDualRepsVM::class.declaredFunctions.find { it.name == "handlerActive" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, true)
        }

        coVerify { viewModel["activeBother"](ofType<Boolean>()) }
    }

    @Test
    fun testHandlerActiveIsActiveWait() = runTest {
        setSerie1(1)
        setStateSerie1(StateSerie.INITIAL)

        setSerie2(0)
        setStateSerie2(StateSerie.DESCANSO_PLAY)

        val method =
            ManagerDualRepsVM::class.declaredFunctions.find { it.name == "handlerActive" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, true)
        }

        coVerify { viewModel["activeBother"](ofType<Boolean>()) }
    }

    @Test
    fun testHandlerActiveIsNormal() = runTest {
        setSerie1(1)
        setStateSerie1(StateSerie.ACTIVO_PLAY)

        setSerie2(0)
        setStateSerie2(StateSerie.DESCANSO_PLAY)

        val method =
            ManagerDualRepsVM::class.declaredFunctions.find { it.name == "handlerActive" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, true)
        }

        coVerify(exactly = 0) { viewModel["activeBother"](ofType<Boolean>()) }
    }

    @Test
    fun testChangeStateByCtrlTimerState1AndState2IsDescanso() = runTest {
        setStateSerie1(StateSerie.DESCANSO_PAUSE)
        setStateSerie2(StateSerie.DESCANSO_PAUSE)
        viewModel.changeStateByCtrlTimer()

        val resultState1 = viewModel.stateSerie1.first()
        val resultState2 = viewModel.stateSerie2.first()

        assertEquals(StateSerie.DESCANSO_PLAY, resultState1)
        assertEquals(StateSerie.DESCANSO_PLAY, resultState2)
    }

    @Test
    fun testChangeStateByCtrlTimerState1IsDescanso() = runTest {
        setStateSerie1(StateSerie.DESCANSO_PAUSE)
        setStateSerie2(StateSerie.ACTIVO_WAIT)
        viewModel.changeStateByCtrlTimer()

        val resultState1 = viewModel.stateSerie1.first()
        val resultState2 = viewModel.stateSerie2.first()

        assertEquals(StateSerie.DESCANSO_PLAY, resultState1)
        assertEquals(StateSerie.ACTIVO_WAIT, resultState2)
    }

    @Test
    fun testChangeStateByCtrlTimerState2IsDescanso() = runTest {
        setStateSerie1(StateSerie.ACTIVO_WAIT)
        setStateSerie2(StateSerie.DESCANSO_PAUSE)
        viewModel.changeStateByCtrlTimer()

        val resultState1 = viewModel.stateSerie1.first()
        val resultState2 = viewModel.stateSerie2.first()

        assertEquals(StateSerie.ACTIVO_WAIT, resultState1)
        assertEquals(StateSerie.DESCANSO_PLAY, resultState2)
    }

    @Test
    fun testIsPausedTimerIsState1AndState2IsDescanso() {
        val result = viewModel.isPausedTimer(
            stateSerie1 = StateSerie.DESCANSO_PAUSE,
            stateSerie2 = StateSerie.DESCANSO_PAUSE
        )

        assertTrue(result)
    }

    @Test
    fun testIsPausedTimerIsState2IsDescanso() {
        val result = viewModel.isPausedTimer(
            stateSerie1 = StateSerie.ACTIVO_WAIT,
            stateSerie2 = StateSerie.DESCANSO_PAUSE
        )

        assertTrue(result)
    }

    @Test
    fun testIsPausedTimerIsState1IsDescanso() {
        val result = viewModel.isPausedTimer(
            stateSerie1 = StateSerie.DESCANSO_PAUSE,
            stateSerie2 = StateSerie.ACTIVO_WAIT
        )

        assertTrue(result)
    }

    @Test
    fun testOnPauseState1IsDescansoPlay() {
        setStateSerie1(StateSerie.DESCANSO_PLAY)
        setStateSerie2(StateSerie.FINISHED)

        viewModel.onPause()

        coVerify(exactly = 1) { countdownVM.stopCountdown() }
        assertFalse(viewModel.isPauseManually1)
        assertFalse(viewModel.isPauseManually2)
    }

    @Test
    fun testOnPauseState1IsDescansoPause() {
        setStateSerie1(StateSerie.DESCANSO_PAUSE)
        setStateSerie2(StateSerie.FINISHED)

        viewModel.onPause()

        coVerify(exactly = 0) { countdownVM.stopCountdown() }
        assertTrue(viewModel.isPauseManually1)
        assertFalse(viewModel.isPauseManually2)
    }

    @Test
    fun testOnPauseState2IsDescansoPlay() {
        setStateSerie1(StateSerie.FINISHED)
        setStateSerie2(StateSerie.DESCANSO_PLAY)

        viewModel.onPause()

        coVerify(exactly = 1) { countdownVM.stopCountdown() }
        assertFalse(viewModel.isPauseManually1)
        assertFalse(viewModel.isPauseManually2)
    }

    @Test
    fun testOnPauseState2IsDescansoPause() {
        setStateSerie1(StateSerie.DESCANSO_PAUSE)
        setStateSerie2(StateSerie.FINISHED)

        viewModel.onPause()

        coVerify(exactly = 0) { countdownVM.stopCountdown() }
        assertTrue(viewModel.isPauseManually1)
        assertFalse(viewModel.isPauseManually2)
    }

    @Test
    fun testOnPauseState1AndState2IsDescansoPlay() {
        setStateSerie1(StateSerie.DESCANSO_PLAY)
        setStateSerie2(StateSerie.DESCANSO_PLAY)

        viewModel.onPause()

        coVerify(exactly = 2) { countdownVM.stopCountdown() }
        assertFalse(viewModel.isPauseManually1)
        assertFalse(viewModel.isPauseManually2)
    }

    @Test
    fun testOnPauseState1AndState2IsDescansoPause() {
        setStateSerie1(StateSerie.DESCANSO_PAUSE)
        setStateSerie2(StateSerie.DESCANSO_PAUSE)

        viewModel.onPause()

        coVerify(exactly = 0) { countdownVM.stopCountdown() }
        assertTrue(viewModel.isPauseManually1)
        assertTrue(viewModel.isPauseManually2)
    }

    @Test
    fun testOnResumeWithPlayCountdown1() {
        viewModel.isPauseManually1 = false
        viewModel.isPauseManually2 = false
        setStateSerie1(StateSerie.DESCANSO_PLAY)
        setStateSerie2(StateSerie.FINISHED)

        viewModel.onResume()

        coVerify(exactly = 1) { countdownVM.playCountdown() }
        assertFalse(viewModel.isPauseManually1)
        assertFalse(viewModel.isPauseManually2)
    }

    @Test
    fun testOnResumeWithPlayCountdown2() {
        viewModel.isPauseManually1 = false
        viewModel.isPauseManually2 = false
        setStateSerie1(StateSerie.FINISHED)
        setStateSerie2(StateSerie.DESCANSO_PLAY)

        viewModel.onResume()

        coVerify(exactly = 1) { countdownVM.playCountdown() }
        assertFalse(viewModel.isPauseManually1)
        assertFalse(viewModel.isPauseManually2)
    }

    @Test
    fun testOnResumeWithPlayCountdown1AndCountdown2() {
        viewModel.isPauseManually1 = false
        viewModel.isPauseManually2 = false
        setStateSerie1(StateSerie.DESCANSO_PLAY)
        setStateSerie2(StateSerie.DESCANSO_PLAY)

        viewModel.onResume()

        coVerify(exactly = 2) { countdownVM.playCountdown() }
        assertFalse(viewModel.isPauseManually1)
        assertFalse(viewModel.isPauseManually2)
    }

    @Test
    fun testOnResumeWithNothingToDo() {
        viewModel.isPauseManually1 = false
        viewModel.isPauseManually2 = false
        setStateSerie1(StateSerie.FINISHED)
        setStateSerie2(StateSerie.FINISHED)

        viewModel.onResume()

        coVerify(exactly = 0) { countdownVM.playCountdown() }
        assertFalse(viewModel.isPauseManually1)
        assertFalse(viewModel.isPauseManually2)
    }

    @Test
    fun testOnResumeWithStopManually1() {
        viewModel.isPauseManually1 = true
        viewModel.isPauseManually2 = false
        setStateSerie1(StateSerie.FINISHED)
        setStateSerie2(StateSerie.FINISHED)

        viewModel.onResume()

        coVerify(exactly = 0) { countdownVM.playCountdown() }
        assertFalse(viewModel.isPauseManually1)
        assertFalse(viewModel.isPauseManually2)
    }

    @Test
    fun testOnResumeWithStopManually2() {
        viewModel.isPauseManually1 = false
        viewModel.isPauseManually2 = true
        setStateSerie1(StateSerie.FINISHED)
        setStateSerie2(StateSerie.FINISHED)

        viewModel.onResume()

        coVerify(exactly = 0) { countdownVM.playCountdown() }
        assertFalse(viewModel.isPauseManually1)
        assertFalse(viewModel.isPauseManually2)
    }

    @Test
    fun testOnResumeWithStopManually1AndManually2() {
        viewModel.isPauseManually1 = true
        viewModel.isPauseManually2 = true
        setStateSerie1(StateSerie.FINISHED)
        setStateSerie2(StateSerie.FINISHED)

        viewModel.onResume()

        coVerify(exactly = 0) { countdownVM.playCountdown() }
        assertFalse(viewModel.isPauseManually1)
        assertFalse(viewModel.isPauseManually2)
    }
}