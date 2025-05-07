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
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UpdateMaterialUseCaseTest {
    @RelaxedMockK
    private lateinit var repoMaterial: MaterialRepository

    @RelaxedMockK
    private lateinit var repoMaterialSnapshot: MaterialSnapshotRepository

    private lateinit var updateMaterialUseCase: UpdateMaterialUseCase

    /*
    * No probar todos a la vez, probar de 1 en uno por problemas de hilos
    * */
    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        mockkObject(StorageFileService)
        mockkObject(UriUtils)
        updateMaterialUseCase = UpdateMaterialUseCase(
            repoMaterial = repoMaterial,
            repoMaterialSnapshot = repoMaterialSnapshot
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test updateMaterialUseCase sin hacer cambios en la imagen`() = runTest {
        val materialInfoDto = MaterialInfoDto(
            idMaterial = 1,
            idUser = 1,
            nombre = "",
            isMaterialWeight = true,
            descripcion = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        coEvery { StorageFileService.insertImg(
            context = any(),
            galeryUri = any(),
            dir = any()
        ) } returns ""
        coEvery {
            StorageFileService.updateImg(
                context = any(),
                galeryUri = any(),
                dir = any(),
                deleteImgUri = any()
            )
        } returns ""

        updateMaterialUseCase(
            context = InstrumentationRegistry.getInstrumentation().targetContext,
            deleteImgUri = Uri.EMPTY,
            material = materialInfoDto
        )

        coVerify(exactly = 0) {
            StorageFileService.insertImg(
                context = any(),
                galeryUri = any(),
                dir = any()
            )
        }
        coVerify(exactly = 0) {
            StorageFileService.updateImg(
                context = any(),
                galeryUri = any(),
                dir = any(),
                deleteImgUri = any(),
            )
        }
        coVerify { repoMaterial.update(any()) }
    }

    @Test
    fun `test updateMaterialUseCase insertando una nueva imagen y conservando la anterior`() =
        runTest {
            val materialInfoDto = MaterialInfoDto(
                idMaterial = 1,
                idUser = 1,
                nombre = "",
                isMaterialWeight = true,
                descripcion = "",
                photoUri = Uri.EMPTY,
                rol = Rol.INIT_DATA_USER
            )

            coEvery { UriUtils.getNameByUri(any()) } returns "material999_999"
            coEvery { repoMaterialSnapshot.isUseNameImg(any()) } returns true
            coEvery { StorageFileService.insertImg(
                context = any(),
                galeryUri = any(),
                dir = any()
            ) } returns ""
            coEvery {
                StorageFileService.updateImg(
                    context = any(),
                    galeryUri = any(),
                    dir = any(),
                    deleteImgUri = any()
                )
            } returns ""
            updateMaterialUseCase(
                context = InstrumentationRegistry.getInstrumentation().targetContext,
                deleteImgUri = "${DRAWABLE_URI}material999_999".toUri(),
                material = materialInfoDto
            )

            coVerify(exactly = 1) {
                StorageFileService.insertImg(
                    context = any(),
                    galeryUri = any(),
                    dir = any()
                )
            }
            coVerify(exactly = 0) {
                StorageFileService.updateImg(
                    context = any(),
                    galeryUri = any(),
                    dir = any(),
                    deleteImgUri = any(),
                )
            }
            coVerify { repoMaterial.update(any()) }
        }

    @Test
    fun `test updateMaterialUseCase actualizar una nueva imagen y conservando la anterior`() =
        runTest {
            val materialInfoDto = MaterialInfoDto(
                idMaterial = 1,
                idUser = 1,
                nombre = "",
                isMaterialWeight = true,
                descripcion = "",
                photoUri = "${DRAWABLE_URI}material999_999".toUri(),
                rol = Rol.INIT_DATA_USER
            )

            coEvery { UriUtils.getNameByUri(any()) } returns "material999_999"
            coEvery { repoMaterialSnapshot.isUseNameImg(any()) } returns false
            coEvery { StorageFileService.insertImg(
                context = any(),
                galeryUri = any(),
                dir = any()
            ) } returns ""
            coEvery {
                StorageFileService.updateImg(
                    context = any(),
                    galeryUri = any(),
                    dir = any(),
                    deleteImgUri = any()
                )
            } returns ""

            updateMaterialUseCase(
                context = InstrumentationRegistry.getInstrumentation().targetContext,
                deleteImgUri = Uri.EMPTY,
                material = materialInfoDto
            )

            coVerify(exactly = 0) {
                StorageFileService.insertImg(
                    context = any(),
                    galeryUri = any(),
                    dir = any()
                )
            }
            coVerify(exactly = 1) {
                StorageFileService.updateImg(
                    context = any(),
                    galeryUri = any(),
                    dir = any(),
                    deleteImgUri = any(),
                )
            }
            coVerify { repoMaterial.update(any()) }
        }
}