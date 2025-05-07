package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.crono

import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.crono.EntrenamientoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EntrenamientoRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.AnswerState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.EntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.objects.AnswerStateLoading
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After

class GetEntrenamientoUseCaseTest {

    private lateinit var getEntrenamientoUseCase : GetEntrenamientoUseCase

    @RelaxedMockK
    private lateinit var repo : EntrenamientoRepository

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getEntrenamientoUseCase = GetEntrenamientoUseCase(repo)
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test getEntrenamientoUseCase`() = runTest {
        val entrenamientoDB = EntrenamientoDB(
            idRutina = 1,
            calentamiento = AnswerState.ASK_LATER,
            estiramiento = AnswerState.ASK_LATER,
            movArticular = AnswerState.ASK_LATER,
            descanso = 1,
            musculoSet = mutableSetOf(Musculo.TRAPECIO)
        )
        val entrenamientoDtoSolution = EntrenamientoDto(
            idRutina = 1,
            calentamiento = AnswerStateLoading.ASK_LATER,
            estiramiento = AnswerState.ASK_LATER,
            movArticular = AnswerState.ASK_LATER,
            descanso = 1,
            musculoSet = mutableSetOf(Musculo.TRAPECIO)
        )
        coEvery { repo.getEntrenamiento(any()) } returns flowOf(entrenamientoDB)

        val entrenamientoDtoResult = getEntrenamientoUseCase(1).first()

        assertEquals(entrenamientoDtoSolution, entrenamientoDtoResult)
    }
}