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

class ManagerDualCronoVMTest {
    lateinit var viewModel: ManagerDualCronoVM

    @RelaxedMockK
    private lateinit var countdownVM: CountdownVM

    var resultMinusStep = false

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
            ManagerDualCronoVM::class.declaredFunctions.find { it.name == "setStateSerie1" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, stateSerie)
        }
    }

    private fun setStateSerie2(stateSerie: StateSerie) {
        val method =
            ManagerDualCronoVM::class.declaredFunctions.find { it.name == "setStateSerie2" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, stateSerie)
        }
    }

    private fun setSerie1(serie: Int) {
        val method =
            ManagerDualCronoVM::class.declaredFunctions.find { it.name == "setSerie1" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, serie)
        }
    }

    private fun setSerie2(serie: Int) {
        val method =
            ManagerDualCronoVM::class.declaredFunctions.find { it.name == "setSerie2" }
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
            ManagerDualCronoVM(
                countdownVM1 = countdownVM,
                countdownVM2 = countdownVM,
                stepList = getListStep(),
                descanso = 15,
                cursorStep = 0,
                plusStep = { },
                minusStep = { bool -> resultMinusStep = bool }
            ),
            recordPrivateCalls = true
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
        resultMinusStep = false
    }

    @Test
    fun testGetInitTimeStateEqualsInitial() {
        this.setStateSerie2(StateSerie.INITIAL)
        val result = viewModel.getInitTime(
            isInverse = true
        )

        assertEquals(1000, result)
    }

    @Test
    fun testGetInitTimeInverseAndCurrentSerieLessThanOrEqualsSerie() {
        this.setSerie2(3)
        this.setStateSerie2(StateSerie.ACTIVO_PAUSE)

        val result = viewModel.getInitTime(
            isInverse = true
        )

        assertEquals(1000, result)
    }

    @Test
    fun testGetInitTimeElse() {
        this.setStateSerie2(StateSerie.ACTIVO_PAUSE)
        val result = viewModel.getInitTime(
            isInverse = false
        )

        assertEquals(15, result)
    }

    @Test
    fun testIsAutoPlayStateEqualsInitialAndInverse() {
        this.setStateSerie2(StateSerie.INITIAL)

        val result = viewModel.isAutoPlay(
            isInverse = true
        )

        assertFalse(result)
    }

    @Test
    fun testIsAutoPlayStateEqualsInitialAndNotInverse() {
        this.setStateSerie2(StateSerie.INITIAL)

        val result = viewModel.isAutoPlay(
            isInverse = false
        )

        assertTrue(result)
    }

    @Test
    fun testIsAutoPlaySerieEqualsCurrentSerieAndInverse() {
        this.setStateSerie2(StateSerie.ACTIVO_PLAY)
        this.setSerie2(3)
        val result = viewModel.isAutoPlay(
            isInverse = true
        )

        assertFalse(result)
    }

    @Test
    fun testIsAutoPlayElse() {
        this.setStateSerie2(StateSerie.ACTIVO_PLAY)
        this.setSerie2(5)
        val result = viewModel.isAutoPlay(
            isInverse = true
        )

        assertFalse(result)
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
        coVerify { viewModel["resetLastDescanso"](ofType<Long>()) }

    }

    @Test
    fun testNextSerieSerie1LessThanSerie2() = runTest {
        setSerie2(3)
        setSerie1(5)
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
            ManagerDualCronoVM::class.declaredFunctions.find { it.name == "getStateBother" }
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
            ManagerDualCronoVM::class.declaredFunctions.find { it.name == "getStateBother" }
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
            ManagerDualCronoVM::class.declaredFunctions.find { it.name == "getStateThis" }
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
            ManagerDualCronoVM::class.declaredFunctions.find { it.name == "getStateThis" }
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
            ManagerDualCronoVM::class.declaredFunctions.find { it.name == "activeBother" }
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
            ManagerDualCronoVM::class.declaredFunctions.find { it.name == "activeBother" }
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
            ManagerDualCronoVM::class.declaredFunctions.find { it.name == "setStateThis" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, StateSerie.DESCANSO_PLAY, false)
        } as? StateSerie

        val resultState1 = viewModel.stateSerie1.first()
        val resultState2 = viewModel.stateSerie2.first()

        assertEquals(StateSerie.DESCANSO_PLAY, resultState1)
        assertEquals(StateSerie.ACTIVO_PLAY, resultState2)
    }

    @Test
    fun testSetStateThisInverse() = runTest {
        setStateSerie1(StateSerie.DESCANSO_PLAY)
        setStateSerie2(StateSerie.ACTIVO_PLAY)

        val method =
            ManagerDualCronoVM::class.declaredFunctions.find { it.name == "setStateThis" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, StateSerie.DESCANSO_PLAY, true)
        } as? StateSerie

        val resultState1 = viewModel.stateSerie2.first()
        val resultState2 = viewModel.stateSerie2.first()

        assertEquals(StateSerie.DESCANSO_PLAY, resultState1)
        assertEquals(StateSerie.DESCANSO_PLAY, resultState2)
    }

    @Test
    fun testGetSerieThisInverse() {
        this.setSerie1(20)
        this.setSerie2(30)

        val method =
            ManagerDualCronoVM::class.declaredFunctions.find { it.name == "getSerieThis" }
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
            ManagerDualCronoVM::class.declaredFunctions.find { it.name == "getSerieThis" }
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
            ManagerDualCronoVM::class.declaredFunctions.find { it.name == "nextThisSerie" }
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
            ManagerDualCronoVM::class.declaredFunctions.find { it.name == "nextThisSerie" }
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
        setSerie2(3)
        setStateSerie2(StateSerie.DESCANSO_PLAY)

        val method =
            ManagerDualCronoVM::class.declaredFunctions.find { it.name == "handlerDescanso" }
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
            ManagerDualCronoVM::class.declaredFunctions.find { it.name == "handlerDescanso" }
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
            ManagerDualCronoVM::class.declaredFunctions.find { it.name == "handlerDescanso" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, true)
        }

        val resultState1 = viewModel.stateSerie1.first()
        val resultState2 = viewModel.stateSerie2.first()

        assertEquals(StateSerie.ACTIVO_PLAY, resultState1)
        assertEquals(StateSerie.ACTIVO_WAIT, resultState2)

        coVerify { countdownVM.stopCountdown() }
    }

    @Test
    fun testHandlerDescansoActiveBotherSecurity2() = runTest {
        setSerie1(0)
        setStateSerie1(StateSerie.DESCANSO_PLAY)

        setSerie2(0)
        setStateSerie2(StateSerie.DESCANSO_PLAY)

        val method =
            ManagerDualCronoVM::class.declaredFunctions.find { it.name == "handlerDescanso" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, true)
        }

        val resultState1 = viewModel.stateSerie1.first()
        val resultState2 = viewModel.stateSerie2.first()

        assertEquals(StateSerie.DESCANSO_PLAY, resultState1)
        assertEquals(StateSerie.ACTIVO_WAIT, resultState2)

        coVerify { countdownVM.stopCountdown() }
    }

    @Test
    fun testHandlerDescansoActiveBotherSecurity3() = runTest {
        setSerie1(1)
        setStateSerie1(StateSerie.DESCANSO_PLAY)

        setSerie2(0)
        setStateSerie2(StateSerie.DESCANSO_PLAY)

        val method =
            ManagerDualCronoVM::class.declaredFunctions.find { it.name == "handlerDescanso" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, false)
        }

        val resultState1 = viewModel.stateSerie1.first()
        val resultState2 = viewModel.stateSerie2.first()

        assertEquals(StateSerie.ACTIVO_WAIT, resultState1)
        assertEquals(StateSerie.DESCANSO_PLAY, resultState2)

        coVerify { countdownVM.stopCountdown() }
    }

    @Test
    fun testHandlerDescansoThisActivePlay() = runTest {
        setSerie1(1)
        setStateSerie1(StateSerie.ACTIVO_WAIT)

        setSerie2(0)
        setStateSerie2(StateSerie.DESCANSO_PLAY)

        val method =
            ManagerDualCronoVM::class.declaredFunctions.find { it.name == "handlerDescanso" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, true)
        }

        val resultState1 = viewModel.stateSerie1.first()
        val resultState2 = viewModel.stateSerie2.first()

        assertEquals(StateSerie.ACTIVO_WAIT, resultState1)
        assertEquals(StateSerie.ACTIVO_PLAY, resultState2)

        coVerify { countdownVM.playCountdown() }
    }

    @Test
    fun testHandlerActiveIsInitial() = runTest {
        setSerie1(1)
        setStateSerie1(StateSerie.INITIAL)

        setSerie2(0)
        setStateSerie2(StateSerie.DESCANSO_PLAY)

        val method =
            ManagerDualCronoVM::class.declaredFunctions.find { it.name == "handlerActive" }
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
            ManagerDualCronoVM::class.declaredFunctions.find { it.name == "handlerActive" }
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
            ManagerDualCronoVM::class.declaredFunctions.find { it.name == "handlerActive" }
        method?.let {
            it.isAccessible = true
            it.call(viewModel, true)
        }

        coVerify(exactly = 0) { viewModel["activeBother"](ofType<Boolean>()) }
    }

    @Test
    fun testChangeStateByCtrlTimerState2IsExtremeState() = runTest {
        setStateSerie1(StateSerie.ACTIVO_PAUSE)
        setStateSerie2(StateSerie.INITIAL)
        viewModel.changeStateByCtrlTimer()

        val result = viewModel.stateSerie1.first()

        assertEquals(StateSerie.ACTIVO_PLAY, result)
    }

    @Test
    fun testChangeStateByCtrlTimerState2IsActiveWait() = runTest {
        setStateSerie1(StateSerie.ACTIVO_PAUSE)
        setStateSerie2(StateSerie.ACTIVO_WAIT)
        viewModel.changeStateByCtrlTimer()

        val result = viewModel.stateSerie1.first()

        assertEquals(StateSerie.ACTIVO_PLAY, result)
    }

    @Test
    fun testChangeStateByCtrlTimerState2IsDescansoAndState1IsActive() = runTest {
        setStateSerie1(StateSerie.ACTIVO_PAUSE)
        setStateSerie2(StateSerie.DESCANSO_PLAY)
        viewModel.changeStateByCtrlTimer()

        val result = viewModel.stateSerie1.first()

        assertEquals(StateSerie.ACTIVO_PLAY, result)
    }

    @Test
    fun testChangeStateByCtrlTimerState21IsDescansoAndState1IsActive() = runTest {
        setStateSerie1(StateSerie.ACTIVO_PAUSE)
        setStateSerie2(StateSerie.DESCANSO_PLAY)
        viewModel.changeStateByCtrlTimer()

        val result = viewModel.stateSerie1.first()

        assertEquals(StateSerie.ACTIVO_PLAY, result)
    }

    @Test
    fun testChangeStateByCtrlTimerState1IsFinished() = runTest {
        setStateSerie1(StateSerie.FINISHED)
        setStateSerie2(StateSerie.DESCANSO_PLAY)
        viewModel.changeStateByCtrlTimer()

        val result = viewModel.stateSerie2.first()

        assertEquals(StateSerie.DESCANSO_PAUSE, result)
    }

    @Test
    fun testChangeStateByCtrlTimerState1IsActiveWait() = runTest {
        setStateSerie1(StateSerie.ACTIVO_WAIT)
        setStateSerie2(StateSerie.ACTIVO_PLAY)
        viewModel.changeStateByCtrlTimer()

        val result = viewModel.stateSerie2.first()

        assertEquals(StateSerie.ACTIVO_PAUSE, result)
    }

    @Test
    fun testChangeStateByCtrlTimerState2IsActiveAndState1IsDescanso() = runTest {
        setStateSerie1(StateSerie.DESCANSO_PAUSE)
        setStateSerie2(StateSerie.ACTIVO_PAUSE)
        viewModel.changeStateByCtrlTimer()

        val result = viewModel.stateSerie2.first()

        assertEquals(StateSerie.ACTIVO_PLAY, result)
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
    fun testIsPausedTimerState2IsExtremeState() {
        val result = viewModel.isPausedTimer(
            stateSerie1 = StateSerie.DESCANSO_PAUSE,
            stateSerie2 = StateSerie.INITIAL
        )

        assertTrue(result)
    }

    @Test
    fun testIsPausedTimerState2IsActiveWait() {
        val result = viewModel.isPausedTimer(
            stateSerie1 = StateSerie.DESCANSO_PAUSE,
            stateSerie2 = StateSerie.ACTIVO_WAIT
        )

        assertTrue(result)
    }

    @Test
    fun testIsPausedTimerState2IsDescansoAndState1IsActive() {
        val result = viewModel.isPausedTimer(
            stateSerie1 = StateSerie.DESCANSO_PLAY,
            stateSerie2 = StateSerie.ACTIVO_PAUSE
        )

        assertTrue(result)
    }

    @Test
    fun testIsPausedTimerState1IsExtremeState() {
        val result = viewModel.isPausedTimer(
            stateSerie1 = StateSerie.INITIAL,
            stateSerie2 = StateSerie.DESCANSO_PAUSE
        )

        assertTrue(result)
    }

    @Test
    fun testIsPausedTimerState1IsActiveWait() {
        val result = viewModel.isPausedTimer(
            stateSerie1 = StateSerie.ACTIVO_WAIT,
            stateSerie2 = StateSerie.DESCANSO_PLAY
        )

        assertTrue(result)
    }

    @Test
    fun testIsPausedTimerState1IsDescansoAndState2IsActive() {
        val result = viewModel.isPausedTimer(
            stateSerie1 = StateSerie.ACTIVO_PAUSE,
            stateSerie2 = StateSerie.DESCANSO_PLAY
        )

        assertTrue(result)
    }

    @Test
    fun testIsPausedTimerState1AndState2IsDescanso() {
        val result = viewModel.isPausedTimer(
            stateSerie1 = StateSerie.DESCANSO_PAUSE,
            stateSerie2 = StateSerie.DESCANSO_PAUSE
        )

        assertTrue(result)
    }

    @Test
    fun testOnPauseNotStopManuallyState1() {
        setStateSerie1(StateSerie.DESCANSO_PLAY)
        setStateSerie2(StateSerie.FINISHED)

        viewModel.onPause()

        assertFalse(viewModel.isPauseManually1)
        coVerify { countdownVM.stopCountdown() }
    }

    @Test
    fun testOnPauseWithStopManuallyState1() {
        setStateSerie1(StateSerie.DESCANSO_PAUSE)
        setStateSerie2(StateSerie.FINISHED)

        viewModel.onPause()

        assertTrue(viewModel.isPauseManually1)
        coVerify(exactly = 0) { countdownVM.stopCountdown() }
    }

    @Test
    fun testOnPauseNotStopManuallyState2() {
        setStateSerie1(StateSerie.FINISHED)
        setStateSerie2(StateSerie.DESCANSO_PLAY)

        viewModel.onPause()

        assertFalse(viewModel.isPauseManually2)
        coVerify { countdownVM.stopCountdown() }
    }

    @Test
    fun testOnPauseWithStopManuallyState2() {
        setStateSerie1(StateSerie.FINISHED)
        setStateSerie2(StateSerie.DESCANSO_PAUSE)

        viewModel.onPause()

        assertTrue(viewModel.isPauseManually2)
        coVerify(exactly = 0) { countdownVM.stopCountdown() }
    }

    @Test
    fun testOnPauseNotStopManuallyState1AndState2() {
        setStateSerie1(StateSerie.DESCANSO_PLAY)
        setStateSerie2(StateSerie.DESCANSO_PLAY)

        viewModel.onPause()

        assertFalse(viewModel.isPauseManually1)
        assertFalse(viewModel.isPauseManually2)
        coVerify(exactly = 2) { countdownVM.stopCountdown() }
    }

    @Test
    fun testOnPauseWithStopManuallyState1AndState2() {
        setStateSerie1(StateSerie.DESCANSO_PAUSE)
        setStateSerie2(StateSerie.DESCANSO_PAUSE)

        viewModel.onPause()

        assertTrue(viewModel.isPauseManually1)
        assertTrue(viewModel.isPauseManually2)
        coVerify(exactly = 0) { countdownVM.stopCountdown() }
    }

    @Test
    fun testOnResumeWithPlayCountdown1() {
        viewModel.isPauseManually1 = false
        viewModel.isPauseManually2 = false
        setStateSerie1(StateSerie.DESCANSO_PLAY)
        setStateSerie2(StateSerie.FINISHED)

        viewModel.onResume()

        coVerify(exactly = 1) { countdownVM.playCountdown() }
    }

    @Test
    fun testOnResumeWithPlayCountdown2() {
        viewModel.isPauseManually1 = false
        viewModel.isPauseManually2 = false
        setStateSerie1(StateSerie.FINISHED)
        setStateSerie2(StateSerie.DESCANSO_PLAY)

        viewModel.onResume()

        coVerify(exactly = 1) { countdownVM.playCountdown() }
    }

    @Test
    fun testOnResumeWithPlayCountdown1AndCoundown2() {
        viewModel.isPauseManually1 = false
        viewModel.isPauseManually2 = false
        setStateSerie1(StateSerie.DESCANSO_PLAY)
        setStateSerie2(StateSerie.DESCANSO_PLAY)

        viewModel.onResume()

        coVerify(exactly = 2) { countdownVM.playCountdown() }
    }

    @Test
    fun testOnResumeNothingToDo() {
        viewModel.isPauseManually1 = false
        viewModel.isPauseManually2 = false
        setStateSerie1(StateSerie.FINISHED)
        setStateSerie2(StateSerie.FINISHED)

        viewModel.onResume()

        coVerify(exactly = 0) { countdownVM.playCountdown() }
    }

    @Test
    fun testOnResumeIsPauseManually1() {
        viewModel.isPauseManually1 = true
        viewModel.isPauseManually2 = false
        setStateSerie1(StateSerie.DESCANSO_PLAY)
        setStateSerie2(StateSerie.FINISHED)

        viewModel.onResume()

        assertFalse(viewModel.isPauseManually1)
        coVerify(exactly = 0) { countdownVM.playCountdown() }
    }

    @Test
    fun testOnResumeIsPauseManually2() {
        viewModel.isPauseManually1 = false
        viewModel.isPauseManually2 = true
        setStateSerie1(StateSerie.FINISHED)
        setStateSerie2(StateSerie.DESCANSO_PLAY)

        viewModel.onResume()

        assertFalse(viewModel.isPauseManually2)
        coVerify(exactly = 0) { countdownVM.playCountdown() }
    }

    @Test
    fun testOnResumeIsPauseManually1AndPauseManually2() {
        viewModel.isPauseManually1 = true
        viewModel.isPauseManually2 = true
        setStateSerie1(StateSerie.DESCANSO_PLAY)
        setStateSerie2(StateSerie.DESCANSO_PLAY)

        viewModel.onResume()

        assertFalse(viewModel.isPauseManually2)
        coVerify(exactly = 0) { countdownVM.playCountdown() }
    }
}