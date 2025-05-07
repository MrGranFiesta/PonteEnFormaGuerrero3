package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.snapshot

import androidx.core.net.toUri
import androidx.test.platform.app.InstrumentationRegistry
import com.mrgranfiesta.ponteenformaguerrero3.data.filesystem.StorageFileService
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.EjercicioSnapshotRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.MaterialSnapshotRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.SnapshotCronoRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_EJERCICIO_IMG
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_MATERIAL_IMG
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DRAWABLE_URI
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.UriUtils
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DeleteSnapshotUseCaseTest {
    @RelaxedMockK
    private lateinit var snapshotRepo: SnapshotCronoRepository

    @RelaxedMockK
    private lateinit var ejercicioSnapshotRepo: EjercicioSnapshotRepository

    @RelaxedMockK
    private lateinit var materialSnapshotRepo: MaterialSnapshotRepository

    private lateinit var deleteSnapshotUseCase: DeleteSnapshotUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        mockkObject(UriUtils)
        mockkObject(StorageFileService)
        deleteSnapshotUseCase = DeleteSnapshotUseCase(
            context = InstrumentationRegistry.getInstrumentation().targetContext,
            snapshotRepo = snapshotRepo,
            ejercicioSnapshotRepo = ejercicioSnapshotRepo,
            materialSnapshotRepo = materialSnapshotRepo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test deleteSnapshotUseCase con eliminacion de imagenes`() {
        coEvery { ejercicioSnapshotRepo.getNameImgByRutinaId(any()) } returns listOf("ejer200_000")
        every { UriUtils.getUriResource(eq(DIR_EJERCICIO_IMG), any()) } returns "${DRAWABLE_URI}ejer2_000_000".toUri()
        coEvery { ejercicioSnapshotRepo.isNotUseNameImgGlobal(any()) } returns true

        coEvery { materialSnapshotRepo.getNameImgByRutinaId(any()) } returns listOf("mat200_000")
        every { UriUtils.getUriResource(eq(DIR_MATERIAL_IMG), any()) } returns "${DRAWABLE_URI}mate2_000_000".toUri()
        coEvery { materialSnapshotRepo.isNotUseNameImgGlobal(any()) } returns true

        coEvery { StorageFileService.deleteImg(any(), any(), any()) } returns true
        deleteSnapshotUseCase(1)

        coVerify { ejercicioSnapshotRepo.getNameImgByRutinaId(any()) }
        coVerify { StorageFileService.deleteImg(any(), any(), eq(DIR_EJERCICIO_IMG)) }
        coVerify { StorageFileService.deleteImg(any(), any(), eq(DIR_MATERIAL_IMG)) }
        coVerify { snapshotRepo.deleteMaterialByIdRutina(any()) }
        coVerify { snapshotRepo.deleteEjercicioByIdRutina(any()) }
        coVerify { snapshotRepo.deleteRutinaByIdPk(any()) }
    }

    @Test
    fun `test deleteSnapshotUseCase sin eliminacion de imagenes`() {
        coEvery { ejercicioSnapshotRepo.getNameImgByRutinaId(any()) } returns listOf("ejer200_000")
        coEvery { ejercicioSnapshotRepo.isNotUseNameImgGlobal(any()) } returns false

        coEvery { materialSnapshotRepo.getNameImgByRutinaId(any()) } returns listOf("mat200_000")
        coEvery { materialSnapshotRepo.isNotUseNameImgGlobal(any()) } returns false

        deleteSnapshotUseCase(1)

        coVerify { ejercicioSnapshotRepo.getNameImgByRutinaId(any()) }
        coVerify(exactly = 0) { StorageFileService.deleteImg(any(), any(), any()) }
        coVerify { snapshotRepo.deleteMaterialByIdRutina(any()) }
        coVerify { snapshotRepo.deleteEjercicioByIdRutina(any()) }
        coVerify { snapshotRepo.deleteRutinaByIdPk(any()) }
    }
}