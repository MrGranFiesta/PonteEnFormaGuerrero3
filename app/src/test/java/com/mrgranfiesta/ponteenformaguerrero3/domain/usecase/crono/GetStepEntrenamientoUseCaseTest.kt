package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.crono

import android.net.Uri
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.crono.StepEntrenamientoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EntrenamientoRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.StepEntrenamientoDto
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GetStepEntrenamientoUseCaseTest {
    private lateinit var getStepEntrenamientoUseCase: GetStepEntrenamientoUseCase

    @RelaxedMockK
    private lateinit var repo: EntrenamientoRepository

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getStepEntrenamientoUseCase = GetStepEntrenamientoUseCase(repo)
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test getStepEntrenamientoUseCase`() = runTest {
        val listStepDB = listOf(
            StepEntrenamientoDB(
                idEjercicio = 1,
                idStep = 1,
                nombre = "Press Banca",
                nameImg = "",
                isSimetria = true,
                cantidad = 1,
                musculoSet = mutableSetOf(Musculo.TRAPECIO),
                serie = 1,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.CRONO,
                rol = Rol.INIT_DATA_USER
            )
        )

        val listStepDtoSolution = listOf(
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

        coEvery { repo.getStepEntrenamiento(any()) } returns flowOf(listStepDB)

        val listStepDtoResult = getStepEntrenamientoUseCase(1).first()

        assertEquals(listStepDtoSolution, listStepDtoResult)
    }
}