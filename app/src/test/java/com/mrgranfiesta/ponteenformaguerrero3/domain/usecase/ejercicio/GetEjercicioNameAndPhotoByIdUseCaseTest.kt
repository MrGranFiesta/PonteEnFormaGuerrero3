package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio

import android.net.Uri
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.ejercicio.EjercicioNameAndPhotoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioNameAndPhotoDto
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GetEjercicioNameAndPhotoByIdUseCaseTest {
    @RelaxedMockK
    private lateinit var repo : EjercicioRepository

    private lateinit var getEjercicioNameAndPhotoByIdUseCase : GetEjercicioNameAndPhotoByIdUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getEjercicioNameAndPhotoByIdUseCase = GetEjercicioNameAndPhotoByIdUseCase(
            repo = repo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test getEjercicioNameAndPhotoByIdUseCase`() = runTest {
        val ejercicioInfoDB = EjercicioNameAndPhotoDB(
            idEjercicio = 1,
            nombre = "Flexiones",
            nameImg = "",
            rol = Rol.INIT_DATA_USER
        )
        val ejercicioNameAndPhotoDtoSolution = EjercicioNameAndPhotoDto(
            idEjercicio = 1,
            nombre = "Flexiones",
            photoUri = Uri.EMPTY
        )

        coEvery { repo.getEjercicioNameAndPhotoDB(any()) } returns flowOf(ejercicioInfoDB)
        val ejercicioNameAndPhotoDtoResult = getEjercicioNameAndPhotoByIdUseCase(1).first()

        assertEquals(ejercicioNameAndPhotoDtoSolution, ejercicioNameAndPhotoDtoResult)
    }
}