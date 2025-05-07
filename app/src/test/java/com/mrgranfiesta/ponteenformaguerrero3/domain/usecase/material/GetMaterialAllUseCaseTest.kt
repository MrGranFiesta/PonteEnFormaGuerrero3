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
import org.junit.Before
import org.junit.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GetMaterialAllUseCaseTest {
    @RelaxedMockK
    private lateinit var repo: MaterialRepository

    private lateinit var getMaterialAllUseCase: GetMaterialAllUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getMaterialAllUseCase = GetMaterialAllUseCase(repo)
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test getMaterialAllUseCase`() = runTest {
        val listRowMaterialDB = listOf(
            RowMaterialDB(
                idMaterial = 0,
                idUser = 0,
                nombre = "",
                nameImg = "",
                rol = Rol.INIT_DATA_USER
            )
        )

        val listRowMaterialDtoSolution = listOf(
            RowMaterialDto(
                idMaterial = 0,
                idUser = 0,
                nombre = "",
                photoUri = Uri.EMPTY
            )
        )

        coEvery { repo.getRowMaterialDBAll(any()) } returns flowOf(listRowMaterialDB)

        val listRowMaterialDtoResult = getMaterialAllUseCase().first()

        assertEquals(listRowMaterialDtoSolution, listRowMaterialDtoResult)
    }
}