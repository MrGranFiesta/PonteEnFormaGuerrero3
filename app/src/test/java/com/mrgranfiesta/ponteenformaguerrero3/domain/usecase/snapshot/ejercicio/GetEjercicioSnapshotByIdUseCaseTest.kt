package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.snapshot.ejercicio

import android.net.Uri
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.snapshot.ejercicio.EjercicioSnapshotInfoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.EjercicioSnapshotRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.snapshot.EjercicioSnapshotInfoDto
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
class GetEjercicioSnapshotByIdUseCaseTest {
    @RelaxedMockK
    private lateinit var repo: EjercicioSnapshotRepository

    private lateinit var getEjercicioSnapshotByIdUseCase : GetEjercicioSnapshotByIdUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getEjercicioSnapshotByIdUseCase = GetEjercicioSnapshotByIdUseCase(
            repo = repo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test getEjercicioSnapshotByIdUseCase`() = runTest {
        val ejercicioSnapshotInfoDB = EjercicioSnapshotInfoDB(
            idEjercicio = 0,
            idUser = 0,
            nombre = "Press Banca",
            isSimetria = true,
            musculoSet = setOf(Musculo.TRAPECIO),
            nivel = Nivel.MEDIO,
            descripcion = "",
            nameImg = "",
            rol = Rol.INIT_DATA_USER
        )

        val ejercicioSnapshotInfoDtoSolution = EjercicioSnapshotInfoDto(
            idEjercicio = 0,
            idUser = 0,
            nombre = "Press Banca",
            isSimetria = true,
            musculoSet = setOf(Musculo.TRAPECIO),
            nivel = Nivel.MEDIO,
            descripcion = "",
            photoUri = Uri.EMPTY
        )

        coEvery { repo.getEjercicioSnapshotInfoByPk(any()) } returns flowOf(ejercicioSnapshotInfoDB)

        val ejercicioSnapshotInfoDtoResult = getEjercicioSnapshotByIdUseCase(1).first()

        assertEquals(ejercicioSnapshotInfoDtoSolution, ejercicioSnapshotInfoDtoResult)
    }
}