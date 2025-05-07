package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material

import android.net.Uri
import androidx.core.net.toUri
import androidx.test.platform.app.InstrumentationRegistry
import com.mrgranfiesta.ponteenformaguerrero3.data.filesystem.StorageFileService
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.MaterialRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DRAWABLE_URI
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.MaterialInfoDto
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CreateMaterialUseCaseTest {
    @RelaxedMockK
    private lateinit var repo: MaterialRepository

    private lateinit var createMaterialUseCase: CreateMaterialUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        mockkObject(StorageFileService)
        createMaterialUseCase = CreateMaterialUseCase(repo)
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test createMaterialUseCase sin insercion de imagenes`() {
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
            StorageFileService.insertImg(
                context = any(),
                dir = any(),
                galeryUri = any()
            )
        } returns ""

        createMaterialUseCase(
            context = InstrumentationRegistry.getInstrumentation().targetContext,
            material = materialInfoDto
        )
    }

    @Test
    fun `test createMaterialUseCase con insercion de imagenes`() {
        val materialInfoDto = MaterialInfoDto(
            idMaterial = 1,
            idUser = 1,
            nombre = "Banco",
            isMaterialWeight = true,
            descripcion = "",
            photoUri = "${DRAWABLE_URI}ejer2_000_000".toUri(),
            rol = Rol.INIT_DATA_USER
        )
        coEvery {
            StorageFileService.insertImg(
                context = any(),
                dir = any(),
                galeryUri = any()
            )
        } returns "mate2_000_000"

        createMaterialUseCase(
            context = InstrumentationRegistry.getInstrumentation().targetContext,
            material = materialInfoDto
        )
    }
}