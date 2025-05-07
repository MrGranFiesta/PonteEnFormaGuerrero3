package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material

import android.net.Uri
import androidx.core.net.toUri
import androidx.test.platform.app.InstrumentationRegistry
import com.mrgranfiesta.ponteenformaguerrero3.data.filesystem.StorageFileService
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.MaterialRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.MaterialSnapshotRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DRAWABLE_URI
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.MaterialInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.UriUtils
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DeleteMaterialUseCaseTest {
    @RelaxedMockK
    private lateinit var materialRepo: MaterialRepository

    @RelaxedMockK
    private lateinit var materialSnapshotRepo: MaterialSnapshotRepository

    private lateinit var deleteMaterialUseCase: DeleteMaterialUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        mockkObject(UriUtils)
        mockkObject(StorageFileService)
        deleteMaterialUseCase = DeleteMaterialUseCase(
            materialRepo = materialRepo,
            materialSnapshotRepo = materialSnapshotRepo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test deleteMaterialUseCase con eliminacion de imagen`() {
        val materialInfoDto = MaterialInfoDto(
            idMaterial = 1,
            idUser = 1,
            nombre = "Banco",
            isMaterialWeight = true,
            descripcion = "",
            photoUri = "${DRAWABLE_URI}mate2_000_000".toUri(),
            rol = Rol.INIT_DATA_USER
        )
        coEvery {
            UriUtils.getNameByUri(
                photoUri = any(),
                rol = any()
            )
        } returns "mate2_000_000"

        coEvery { materialSnapshotRepo.isNotUseNameImg(any()) } returns true
        coEvery { StorageFileService.deleteImg(any(), any(), any()) } returns true

        deleteMaterialUseCase(
            context = InstrumentationRegistry.getInstrumentation().targetContext,
            material = materialInfoDto
        )

        coVerify { materialRepo.delete(any()) }
    }

    @Test
    fun `test deleteMaterialUseCase sin eliminacion de imagen`() {
        val materialInfoDto = MaterialInfoDto(
            idMaterial = 1,
            idUser = 1,
            nombre = "Banco",
            isMaterialWeight = true,
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

        coEvery { StorageFileService.deleteImg(any(), any(), any()) } returns false
        coEvery { materialSnapshotRepo.isNotUseNameImg(any()) } returns false

        deleteMaterialUseCase(
            context = InstrumentationRegistry.getInstrumentation().targetContext,
            material = materialInfoDto
        )

        coVerify { materialRepo.delete(any()) }
    }
}