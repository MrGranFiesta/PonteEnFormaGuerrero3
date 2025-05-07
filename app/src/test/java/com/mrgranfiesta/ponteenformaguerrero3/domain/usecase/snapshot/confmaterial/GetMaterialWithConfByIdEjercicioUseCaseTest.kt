package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.snapshot.confmaterial

import android.net.Uri
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.snapshot.confmaterial.MaterialWithConfSnapshotDB
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.ConfMaterialSnapshotRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.snapshot.MaterialWithConfSnapshotDto
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
class GetMaterialWithConfByIdEjercicioUseCaseTest {
    @RelaxedMockK
    private lateinit var repo: ConfMaterialSnapshotRepository

    private lateinit var getMaterialWithConfByIdEjercicioUseCase: GetMaterialWithConfByIdEjercicioUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getMaterialWithConfByIdEjercicioUseCase = GetMaterialWithConfByIdEjercicioUseCase(
            repo = repo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test getMaterialWithConfByIdEjercicioUseCase`() = runTest {
        val listMaterialWithConfSnapshotDB = listOf(
            MaterialWithConfSnapshotDB(
                idConfMaterialSnapshot = 1,
                idConfMaterial = 1,
                idMaterialSnapshot = 1,
                idMaterial = 1,
                idEjercicioSnapshot = 1,
                idEjercicio = 1,
                confValue = 1.0,
                nombre = "Banco",
                isMaterialWeight = true,
                nameImg = "",
                rol = Rol.INIT_DATA_USER
            )
        )

        val listMaterialWithConfSnapshotDtoSolution = listOf(
            MaterialWithConfSnapshotDto(
                idConfMaterialSnapshot = 1,
                idConfMaterial = 1,
                idMaterialSnapshot = 1,
                idMaterial = 1,
                idEjercicioSnapshot = 1,
                idEjercicio = 1,
                confValue = 1.0,
                nombre = "Banco",
                isMaterialWeight = true,
                photoUri = Uri.EMPTY
            )
        )
        coEvery { repo.getMaterialWithConfByIdEjercicio(any()) } returns flowOf(
            listMaterialWithConfSnapshotDB
        )

        val listMaterialWithConfSnapshotDtoResult =
            getMaterialWithConfByIdEjercicioUseCase(1).first()

        assertEquals(listMaterialWithConfSnapshotDtoSolution, listMaterialWithConfSnapshotDtoResult)
    }
}