package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.rutina


import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.rutina.RutinaInfoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.RutinaRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.AnswerState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.rutina.RutinaInfoDto
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

class GetRutinaByIdUseCaseTest {
    @RelaxedMockK
    private lateinit var repo : RutinaRepository

    private lateinit var getRutinaByIdUseCase : GetRutinaByIdUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getRutinaByIdUseCase = GetRutinaByIdUseCase(
            repo = repo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test getRutinaByIdUseCase`() = runTest {
        val rutinaInfoDB = RutinaInfoDB(
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
            rol = Rol.INIT_DATA_USER
        )

        val rutinaInfoDtoSolution = RutinaInfoDto(
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
            rol = Rol.INIT_DATA_USER
        )

        coEvery { repo.getRutinaInfoDBByPkFlow(any()) } returns flowOf(rutinaInfoDB)
        val rutinaInfoDtoResult = getRutinaByIdUseCase(1).first()

        assertEquals(rutinaInfoDtoSolution, rutinaInfoDtoResult)
    }
}