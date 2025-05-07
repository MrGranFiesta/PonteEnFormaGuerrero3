package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material

import android.net.Uri
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.material.MaterialInfoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.MaterialRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.MaterialInfoDto
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GetMaterialByIdUseCaseTest {
    @RelaxedMockK
    private lateinit var repo: MaterialRepository

    private lateinit var getMaterialByIdUseCase: GetMaterialByIdUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getMaterialByIdUseCase = GetMaterialByIdUseCase(repo)
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test getMaterialByIdUseCase`() = runTest {
        val materialInfoDB = MaterialInfoDB(
            idMaterial = 1,
            idUser = 1,
            nombre = "Banco",
            isMaterialWeight = true,
            descripcion = "",
            nameImg = "",
            rol = Rol.INIT_DATA_USER
        )
        val materialInfoDtoSolution = MaterialInfoDto(
            idMaterial = 1,
            idUser = 1,
            nombre = "Banco",
            isMaterialWeight = true,
            descripcion = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )
        coEvery { repo.getMaterialInfoByPk(any()) } returns flowOf(materialInfoDB)
        val materialInfoDtoResult = getMaterialByIdUseCase(1).first()
        assertEquals(materialInfoDtoResult, materialInfoDtoSolution)
    }
}