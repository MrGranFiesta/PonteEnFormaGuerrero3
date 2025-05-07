package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.rutina


import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.rutina.RowRutinaDB
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.RutinaRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.rutina.RowRutinaDto
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

class GetRowRutinaUseCaseTest {
    @RelaxedMockK
    private lateinit var repo: RutinaRepository

    private lateinit var getRowRutinaUseCase: GetRowRutinaUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getRowRutinaUseCase = GetRowRutinaUseCase(
            repo = repo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test getRowRutinaUseCase`() = runTest {
        val listRowRutinaDB = listOf(
            RowRutinaDB(
                idRutina = 1,
                idUser = 1,
                nombre = "Flexiones intermedias",
                nivel = Nivel.MEDIO,
                musculoSet = setOf(Musculo.PECHO),
                isPremium = true
            )
        )

        val listRowRutinaDtoSolution = listOf(
            RowRutinaDto(
                idRutina = 1,
                idUser = 1,
                nombre = "Flexiones intermedias",
                musculoSet = setOf(Musculo.PECHO),
                nivel = Nivel.MEDIO,
                isPremium = true
            )
        )

        coEvery { repo.getRowRutinaAll(any()) } returns flowOf(listRowRutinaDB)

        val listRowRutinaDtoresult = getRowRutinaUseCase().first()

        assertEquals(listRowRutinaDtoSolution, listRowRutinaDtoresult)
    }
}