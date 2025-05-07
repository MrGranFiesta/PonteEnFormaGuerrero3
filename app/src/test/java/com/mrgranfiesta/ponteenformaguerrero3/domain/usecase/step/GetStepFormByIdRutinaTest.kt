package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.step

import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.confmaterial.RowConfMaterialFormDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.step.StepFormWithConfMaterialDB
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.StepRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialFormWithConfDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.step.RowStepFormWithConfMaterialDto
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

class GetStepFormByIdRutinaTest {
    @RelaxedMockK
    private lateinit var repo: StepRepository

    private lateinit var getStepFormByIdRutinaUseCase: GetStepFormByIdRutinaUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getStepFormByIdRutinaUseCase = GetStepFormByIdRutinaUseCase(
            repo = repo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test getStepFormByIdRutina`() = runTest {
        val confMaterialList = listOf(
            RowConfMaterialFormDB(
                idMaterial = 1,
                idConfMaterial = 1,
                idStep = 1,
                nameImg = "",
                rol = Rol.INIT_DATA_USER,
                nombre = "Banco",
                isMaterialWeight = true,
                confValue = 1.0
            )
        )
        val listStepFormWithConfMaterialDB = listOf(
            StepFormWithConfMaterialDB(
                idStep = 1,
                idRutina = 1,
                idEjercicio = 1,
                cantidad = 5,
                serie = 10,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.REPETICION,
                confMaterial = confMaterialList
            )
        )
        val confMaterialListSolution = listOf(
            RowMaterialFormWithConfDto(
                idMaterial = 1,
                idConfMaterial = 1,
                idStep = 1,
                nombre = "Banco",
                isMaterialWeight = true,
                confValue = 1.0
            )
        )
        val listRowStepFormWithConfMaterialDtoSolution = listOf(
            RowStepFormWithConfMaterialDto(
                idStep = 1,
                idRutina = 1,
                idEjercicio = 1,
                cantidad = 5,
                serie = 10,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.REPETICION,
                confMaterialList = confMaterialListSolution
            )
        )
        coEvery { repo.getStepFormWithMaterialConfListByIdRutina(any()) } returns flowOf(
            listStepFormWithConfMaterialDB
        )

        val listRowStepFormWithConfMaterialDtoResult = getStepFormByIdRutinaUseCase(1).first()

        assertEquals(
            listRowStepFormWithConfMaterialDtoSolution,
            listRowStepFormWithConfMaterialDtoResult
        )
    }
}