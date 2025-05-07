package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.crono

import android.net.Uri
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.crono.MaterialEntrenamientoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EntrenamientoRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.MaterialEntrenamientoDto
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GetMaterialEntrenamientoUseCaseTest {
    private lateinit var getMaterialEntrenamientoUseCase: GetMaterialEntrenamientoUseCase

    @RelaxedMockK
    private lateinit var repo: EntrenamientoRepository

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getMaterialEntrenamientoUseCase = GetMaterialEntrenamientoUseCase(repo)
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test getMaterialEntrenamientoUseCase`() = runTest {
        val listMaterialDB = listOf(
            MaterialEntrenamientoDB(
                idMaterial = 1,
                idConfMaterial = 1,
                idStep = 1,
                nombre = "Banco",
                nameImg = "",
                isMaterialWeight = true,
                confValue = 1.0,
                rol = Rol.INIT_DATA_USER
            )
        )

        val listMaterialDtoSolution = listOf(
            MaterialEntrenamientoDto(
                idMaterial = 1,
                idConfMaterial = 1,
                idStep = 1,
                nombre = "Banco",
                photoUri = Uri.EMPTY,
                isMaterialWeight = true,
                confValue = 1.0
            )
        )

        coEvery { repo.getMaterialEntrenamiento(any()) } returns flowOf(listMaterialDB)

        val listMaterialDtoResult = getMaterialEntrenamientoUseCase(1).first()

        assertEquals(listMaterialDtoSolution, listMaterialDtoResult)
    }
}