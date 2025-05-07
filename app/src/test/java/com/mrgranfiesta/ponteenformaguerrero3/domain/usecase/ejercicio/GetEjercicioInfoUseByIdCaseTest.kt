package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio

import android.net.Uri
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.ejercicio.EjercicioInfoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioInfoDto
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
class GetEjercicioInfoUseByIdCaseTest {
    @RelaxedMockK
    private lateinit var repo: EjercicioRepository

    private lateinit var getEjercicioInfoByIdUseCase : GetEjercicioInfoByIdUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getEjercicioInfoByIdUseCase = GetEjercicioInfoByIdUseCase(
            repo = repo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test getEjercicioInfoByIdCase`() = runTest {
        val ejercicioInfoDB = EjercicioInfoDB(
            idEjercicio = 1,
            idUser = 1,
            nombre = "Flexiones",
            isSimetria = true,
            musculoSet = setOf(Musculo.OBLICUOS),
            nivel = Nivel.MEDIO,
            descripcion = "",
            nameImg = "",
            rol = Rol.INIT_DATA_USER
        )
        val ejercicioInfoDtoSolution = EjercicioInfoDto(
            idEjercicio = 1,
            idUser = 1,
            nombre = "Flexiones",
            isSimetria = true,
            musculoSet = setOf(Musculo.OBLICUOS),
            nivel = Nivel.MEDIO,
            descripcion = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        coEvery { repo.getEjercicioInfo(any()) } returns flowOf(ejercicioInfoDB)
        val ejercicioInfoDtoResult = getEjercicioInfoByIdUseCase(1).first()

        assertEquals(ejercicioInfoDtoSolution, ejercicioInfoDtoResult)
    }
}