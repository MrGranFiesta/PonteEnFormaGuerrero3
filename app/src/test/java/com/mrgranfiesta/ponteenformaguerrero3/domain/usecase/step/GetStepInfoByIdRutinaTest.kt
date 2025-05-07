package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.step

import android.net.Uri
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.confmaterial.RowConfMaterialInfoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.step.StepInfoWithConfMaterialDB
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.StepRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialInfoWithConfDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.step.RowStepInfoWithConfMaterialDto
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
class GetStepInfoByIdRutinaTest {
    @RelaxedMockK
    private lateinit var repo: StepRepository

    private lateinit var getStepInfoByIdRutinaUseCase: GetStepInfoByIdRutinaUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getStepInfoByIdRutinaUseCase = GetStepInfoByIdRutinaUseCase(
            repo = repo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test getStepInfoByIdRutina`() = runTest {
        val confMaterialList = listOf(
            RowConfMaterialInfoDB(
                idMaterial = 1,
                nameImg = "",
                rol = Rol.INIT_DATA_USER,
                nombre = "Banco",
                isMaterialWeight = true,
                confValue = 1.0
            )
        )
        val listStepInfoWithConfMaterialDB = listOf(
            StepInfoWithConfMaterialDB(
                idStep = 1,
                idEjercicio = 1,
                cantidad = 5,
                serie = 10,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.REPETICION,
                confMaterial = confMaterialList
            )
        )

        val confMaterialListSolution = listOf(
            RowMaterialInfoWithConfDto(
                idMaterial = 1,
                photoUri = Uri.EMPTY,
                nombre = "Banco",
                isMaterialWeight = true,
                confValue = 1.0
            )
        )
        val listRowStepInfoWithConfMaterialDto = listOf(
            RowStepInfoWithConfMaterialDto(
                idStep = 1,
                idEjercicio = 1,
                cantidad = 5,
                serie = 10,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.REPETICION,
                confMaterialList = confMaterialListSolution
            )
        )

        coEvery { repo.getStepInfoWithMaterialConfListByIdRutina(any()) } returns flowOf(
            listStepInfoWithConfMaterialDB
        )

        val listRowStepInfoWithConfMaterialDtoResult = getStepInfoByIdRutinaUseCase(1).first()
        assertEquals(listRowStepInfoWithConfMaterialDto, listRowStepInfoWithConfMaterialDtoResult)
    }
}