package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio

import android.net.Uri
import androidx.test.platform.app.InstrumentationRegistry
import com.mrgranfiesta.ponteenformaguerrero3.data.filesystem.StorageFileService
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.EjercicioSnapshotRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.UriUtils
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkObject
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DeleteEjercicioUseCaseTest {
    @RelaxedMockK
    private lateinit var ejercicioRepo: EjercicioRepository

    @RelaxedMockK
    private lateinit var ejercicioSnapshotRepo: EjercicioSnapshotRepository

    private lateinit var deleteEjercicioUseCase: DeleteEjercicioUseCase

    /*
    * No ejercutar a la vez, sino da error
    * */
    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        mockkObject(UriUtils)
        mockkObject(StorageFileService)
        deleteEjercicioUseCase = DeleteEjercicioUseCase(
            ejercicioRepo = ejercicioRepo,
            ejercicioSnapshotRepo = ejercicioSnapshotRepo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test deleteEjercicioUseCase sin eliminar imagen`() = runTest {
        val ejercicioInfoDto = EjercicioInfoDto(
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

        coEvery {
            UriUtils.getNameByUri(
                photoUri = any(),
                rol = any()
            )
        } returns ""

        coEvery { ejercicioSnapshotRepo.isNotUseNameImg(any()) } returns false

        deleteEjercicioUseCase(
            ejercicio = ejercicioInfoDto,
            context = InstrumentationRegistry.getInstrumentation().targetContext
        )

        coVerify(exactly = 0) { StorageFileService.deleteImg(any(), any(), any()) }
    }

    @Test
    fun `test deleteEjercicioUseCase con eliminacion de imagen`() = runTest {
        val ejercicioInfoDto = EjercicioInfoDto(
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

        coEvery {
            UriUtils.getNameByUri(
                photoUri = any(),
                rol = any()
            )
        } returns ""

        coEvery { ejercicioSnapshotRepo.isNotUseNameImg(any()) } returns true

        deleteEjercicioUseCase(
            ejercicio = ejercicioInfoDto,
            context = InstrumentationRegistry.getInstrumentation().targetContext
        )

        coVerify { StorageFileService.deleteImg(any(), any(), any()) }
        coVerify { ejercicioRepo.delete(any()) }
    }
}