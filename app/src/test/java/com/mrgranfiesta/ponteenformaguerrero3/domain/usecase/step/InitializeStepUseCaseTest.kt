package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.step

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.StepRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.StepBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

class InitializeStepUseCaseTest {
    @RelaxedMockK
    private lateinit var repo: StepRepository

    private lateinit var initializeStepUseCase: InitializeStepUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        initializeStepUseCase = InitializeStepUseCase(
            repo = repo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test initializeStepUseCase`() = runTest {
        val stepBean = StepBean(
            idStep = 1,
            idEjercicio = 1,
            idRutina = 1,
            cantidad = 1,
            serie = 1,
            ordenExecution = 1,
            tipo = TipoEsfuerzo.REPETICION
        )

        val result = initializeStepUseCase(stepBean).await()

        assertEquals(true, result)
    }
}