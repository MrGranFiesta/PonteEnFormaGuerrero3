package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio

import android.net.Uri
import androidx.test.platform.app.InstrumentationRegistry
import com.mrgranfiesta.ponteenformaguerrero3.data.filesystem.StorageFileService
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioMaterialCrossRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialDto
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
class CreateEjercicioUseCaseTest {

    private lateinit var createEjercicioUseCase: CreateEjercicioUseCase

    @RelaxedMockK
    private lateinit var ejercicioRepo: EjercicioRepository

    @RelaxedMockK
    private lateinit var crossRepo: EjercicioMaterialCrossRepository

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        mockkObject(StorageFileService)
        createEjercicioUseCase = CreateEjercicioUseCase(
            ejercicioRepo = ejercicioRepo,
            crossRepo = crossRepo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test createEjercicioUseCase`() {
        val ejercicioInfoDto = EjercicioInfoDto(
            idEjercicio = 0,
            idUser = 0,
            nombre = "Press Banca",
            isSimetria = true,
            musculoSet = setOf(Musculo.TRAPECIO),
            nivel = Nivel.MEDIO,
            descripcion = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        val materialList = setOf(
            RowMaterialDto(
                idMaterial = 0,
                idUser = 0,
                nombre = "",
                photoUri = Uri.EMPTY
            )
        )

        coEvery {
            StorageFileService.insertImg(
                context = any(),
                galeryUri = any(),
                dir = any()
            )
        } returns ""

        createEjercicioUseCase(
            ejercicio = ejercicioInfoDto,
            materialList = materialList,
            context = InstrumentationRegistry.getInstrumentation().targetContext
        )

        coVerify { ejercicioRepo.insert(any()) }
        coVerify { crossRepo.insertList(any()) }
    }
}