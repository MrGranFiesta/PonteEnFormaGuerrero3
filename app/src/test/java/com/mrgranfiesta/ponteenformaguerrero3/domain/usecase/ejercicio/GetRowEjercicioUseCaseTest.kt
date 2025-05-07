package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio

import android.net.Uri
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.ejercicio.RowEjercicioDB
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.RowEjercicioDto
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
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GetRowEjercicioUseCaseTest {
    @RelaxedMockK
    private lateinit var repo : EjercicioRepository

    private lateinit var getRowEjercicioUseCase : GetRowEjercicioUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getRowEjercicioUseCase = GetRowEjercicioUseCase(
            repo = repo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test getRowEjercicioUseCase`() = runTest {
        val listRowEjercicioDB = listOf(
            RowEjercicioDB(
                idEjercicio = 1,
                nombre = "Flexiones",
                musculoSet = setOf(Musculo.OBLICUOS),
                nivel = Nivel.MEDIO,
                nameImg = "",
                rol = Rol.INIT_DATA_USER
            )
        )

        val listRowEjercicioDtoSolution = listOf(
            RowEjercicioDto(
                idEjercicio = 1,
                nombre = "Flexiones",
                musculoSet = setOf(Musculo.OBLICUOS),
                nivel = Nivel.MEDIO,
                photoUri = Uri.EMPTY
            )
        )
        coEvery { repo.getRowEjercicio(any()) } returns flowOf(listRowEjercicioDB)
        val listRowEjercicioDtoResult = getRowEjercicioUseCase().first()

        assertEquals(listRowEjercicioDtoSolution, listRowEjercicioDtoResult)
    }
}