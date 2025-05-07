package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.rutina

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.RutinaRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.RutinaBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.AnswerState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

class InitializeRutinaUseCaseTest {
    @RelaxedMockK
    private lateinit var repo : RutinaRepository

    private lateinit var initializeRutinaUseCase : InitializeRutinaUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        initializeRutinaUseCase = InitializeRutinaUseCase(
            repo = repo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test initializeRutinaUseCase`() = runTest {
        val rutinaBean = RutinaBean(
            idRutina = 1,
            idUser = 1,
            nombre = "Flexiones intermedias",
            descripcion = "",
            musculoSet = setOf(Musculo.PECHO),
            nivel = Nivel.MEDIO,
            calentamiento = AnswerState.ASK_LATER,
            estiramiento = AnswerState.ASK_LATER,
            movArticular = AnswerState.ASK_LATER,
            descanso = 20,
            isPremium = true
        )

        val result = initializeRutinaUseCase(
            rutina = rutinaBean
        ).await()

        assertEquals(true, result)
    }
}