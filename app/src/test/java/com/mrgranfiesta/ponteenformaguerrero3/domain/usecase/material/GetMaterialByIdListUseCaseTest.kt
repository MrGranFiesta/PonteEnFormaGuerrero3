package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material

import android.net.Uri
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.material.RowMaterialDB
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.MaterialRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialDto
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
class GetMaterialByIdListUseCaseTest {
    @RelaxedMockK
    private lateinit var repo: MaterialRepository

    private lateinit var getMaterialByIdListUseCase: GetMaterialByIdListUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getMaterialByIdListUseCase = GetMaterialByIdListUseCase(repo)
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test getMaterialByIdListUseCase`() = runTest {
        val listRowMaterialDB = listOf(
            RowMaterialDB(
                idMaterial = 1,
                idUser = 1,
                nombre = "Banco",
                nameImg = "",
                rol = Rol.INIT_DATA_USER
            )
        )
        val listRowMaterialDtoSolution = listOf(
            RowMaterialDto(
                idMaterial = 1,
                idUser = 1,
                nombre = "Banco",
                photoUri = Uri.EMPTY
            )
        )
        coEvery { repo.getRowMaterialByPkList(any()) } returns flowOf(listRowMaterialDB)
        val listRowMaterialDtoResult = getMaterialByIdListUseCase(
            idsMaterial = listOf(1)
        ).first()
        assertEquals(listRowMaterialDtoSolution, listRowMaterialDtoResult)
    }
}