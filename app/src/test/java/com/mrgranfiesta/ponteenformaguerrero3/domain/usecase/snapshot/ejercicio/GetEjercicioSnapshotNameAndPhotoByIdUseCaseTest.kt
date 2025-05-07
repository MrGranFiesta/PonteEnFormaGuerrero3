package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.snapshot.ejercicio


import android.net.Uri
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.snapshot.ejercicio.EjercicioSnapshotInfoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.EjercicioSnapshotRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
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
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GetEjercicioSnapshotNameAndPhotoByIdUseCaseTest {
    @RelaxedMockK
    private lateinit var repo: EjercicioSnapshotRepository

    private lateinit var getEjercicioSnapshotNameAndPhotoByIdUseCase: GetEjercicioSnapshotNameAndPhotoByIdUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getEjercicioSnapshotNameAndPhotoByIdUseCase = GetEjercicioSnapshotNameAndPhotoByIdUseCase(
            repo = repo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test getEjercicioSnapshotNameAndPhotoByIdUseCase`() = runTest {
        val ejercicioSnapshotInfoDB = EjercicioSnapshotInfoDB(
            idEjercicio = 1,
            idUser = 1,
            nombre = "Press Banca",
            isSimetria = true,
            musculoSet = setOf(Musculo.TRAPECIO),
            nivel = Nivel.MEDIO,
            descripcion = "",
            nameImg = "",
            rol = Rol.INIT_DATA_USER
        )
        val ejercicioNameAndPhotoDtoSolution = EjercicioNameAndPhotoDto(
            idEjercicio = 1,
            nombre = "Press Banca",
            photoUri = Uri.EMPTY
        )
        coEvery { repo.getEjercicioSnapshotInfoByPk(any()) } returns flowOf(ejercicioSnapshotInfoDB)
        val ejercicioNameAndPhotoDtoResult = getEjercicioSnapshotNameAndPhotoByIdUseCase(1).first()

        assertEquals(ejercicioNameAndPhotoDtoSolution, ejercicioNameAndPhotoDtoResult)
    }
}