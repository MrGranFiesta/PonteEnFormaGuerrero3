package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio

import android.net.Uri
import androidx.core.net.toUri
import androidx.test.platform.app.InstrumentationRegistry
import com.mrgranfiesta.ponteenformaguerrero3.data.filesystem.StorageFileService
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioMaterialCrossRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.EjercicioSnapshotRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DRAWABLE_URI
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialDto
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
class UpdateEjercicioUserCaseTest {
    @RelaxedMockK
    private lateinit var ejercicioRepo: EjercicioRepository

    @RelaxedMockK
    private lateinit var ejercicioSnapshotRepo: EjercicioSnapshotRepository

    @RelaxedMockK
    private lateinit var crossRepo: EjercicioMaterialCrossRepository

    private lateinit var updateEjercicioUserCase: UpdateEjercicioUserCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        mockkObject(UriUtils)
        mockkObject(StorageFileService)
        updateEjercicioUserCase = UpdateEjercicioUserCase(
            ejercicioRepo = ejercicioRepo,
            ejercicioSnapshotRepo = ejercicioSnapshotRepo,
            crossRepo = crossRepo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test updateEjercicioUserCase`() {
        val ejercicioInfoDto = EjercicioInfoDto(
            idEjercicio = 0,
            idUser = 1,
            nombre = "",
            isSimetria = true,
            musculoSet = setOf(Musculo.TRAPECIO),
            nivel = Nivel.MEDIO,
            descripcion = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        val materialListOrigin = setOf(
            RowMaterialDto(
                idMaterial = 1,
                idUser = 1,
                nombre = "",
                photoUri = Uri.EMPTY
            )
        )

        val materialListUpdate = setOf(
            RowMaterialDto(
                idMaterial = 1,
                idUser = 1,
                nombre = "",
                photoUri = Uri.EMPTY
            )
        )

        updateEjercicioUserCase(
            ejercicio = ejercicioInfoDto,
            deleteImgUri = Uri.EMPTY,
            materialListOrigin = materialListOrigin,
            materialListUpdate = materialListUpdate,
            context = InstrumentationRegistry.getInstrumentation().targetContext
        )

        coVerify { ejercicioRepo.update(any()) }
        coVerify { crossRepo.insertList(any()) }
        coVerify { crossRepo.deleteList(any()) }
    }

    @Test
    fun `test updateEjercicioUserCase con insercion de imagen`() = runTest {
        val ejercicioInfoDto = EjercicioInfoDto(
            idEjercicio = 0,
            idUser = 1,
            nombre = "",
            isSimetria = true,
            musculoSet = setOf(Musculo.TRAPECIO),
            nivel = Nivel.MEDIO,
            descripcion = "",
            photoUri = "${DRAWABLE_URI}ejer2_000_000".toUri(),
            rol = Rol.INIT_DATA_USER
        )

        val materialListOrigin = setOf(
            RowMaterialDto(
                idMaterial = 1,
                idUser = 1,
                nombre = "",
                photoUri = Uri.EMPTY
            )
        )

        val materialListUpdate = setOf(
            RowMaterialDto(
                idMaterial = 1,
                idUser = 1,
                nombre = "",
                photoUri = Uri.EMPTY
            )
        )

        coEvery { UriUtils.getNameByUri(any()) } returns ""
        coEvery { ejercicioSnapshotRepo.isUseNameImg(any()) } returns true
        coEvery { StorageFileService.insertImg(
            context = any(),
            galeryUri = any(),
            dir = any()
        ) } returns ""

        updateEjercicioUserCase(
            ejercicio = ejercicioInfoDto,
            deleteImgUri = Uri.EMPTY,
            materialListOrigin = materialListOrigin,
            materialListUpdate = materialListUpdate,
            context = InstrumentationRegistry.getInstrumentation().targetContext
        )

        coVerify {
            StorageFileService.insertImg(
                context = any(),
                galeryUri = any(),
                dir = any()
            )
        }
        coVerify { ejercicioRepo.update(any()) }
        coVerify { crossRepo.insertList(any()) }
        coVerify { crossRepo.deleteList(any()) }
    }

    @Test
    fun `test updateEjercicioUserCase con eliminacion de imagen`() = runTest {
        val ejercicioInfoDto = EjercicioInfoDto(
            idEjercicio = 0,
            idUser = 1,
            nombre = "",
            isSimetria = true,
            musculoSet = setOf(Musculo.TRAPECIO),
            nivel = Nivel.MEDIO,
            descripcion = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        val materialListOrigin = setOf(
            RowMaterialDto(
                idMaterial = 1,
                idUser = 1,
                nombre = "",
                photoUri = Uri.EMPTY
            )
        )

        val materialListUpdate = setOf(
            RowMaterialDto(
                idMaterial = 1,
                idUser = 1,
                nombre = "",
                photoUri = Uri.EMPTY
            )
        )

        coEvery { UriUtils.getNameByUri(any()) } returns ""
        coEvery { ejercicioSnapshotRepo.isUseNameImg(any()) } returns false
        coEvery { StorageFileService.updateImg(
            context = any(),
            galeryUri = any(),
            deleteImgUri = any(),
            dir = any()
        ) } returns ""

        updateEjercicioUserCase(
            ejercicio = ejercicioInfoDto,
            deleteImgUri = "${DRAWABLE_URI}ejer2_000_000".toUri(),
            materialListOrigin = materialListOrigin,
            materialListUpdate = materialListUpdate,
            context = InstrumentationRegistry.getInstrumentation().targetContext
        )

        coVerify {
            StorageFileService.updateImg(
                context = any(),
                galeryUri = any(),
                deleteImgUri = any(),
                dir = any()
            )
        }
        coVerify { ejercicioRepo.update(any()) }
        coVerify { crossRepo.insertList(any()) }
        coVerify { crossRepo.deleteList(any()) }
    }
}