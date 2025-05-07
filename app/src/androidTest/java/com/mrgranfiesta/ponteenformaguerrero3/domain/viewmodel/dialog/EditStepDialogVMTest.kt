package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.dialog

import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.dialog.MaterialNameDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.dialog.StepEditDialogDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material.GetMaterialNameAndConfListUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class EditStepDialogVMTest {
    lateinit var viewModel: EditStepDialogVM

    @RelaxedMockK
    lateinit var getMaterialNameAndConfListUseCase: GetMaterialNameAndConfListUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = EditStepDialogVM(getMaterialNameAndConfListUseCase)
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun testInitDataCorrect() = runTest {
        coEvery { getMaterialNameAndConfListUseCase.invoke(any(), any()) } returns flowOf(listOf<MaterialNameDto>())
        val step = StepEditDialogDto(
            idStep = 1,
            idEjercicio = 1,
            cantidad = 1,
            serie = 1,
            tipo = TipoEsfuerzo.REPETICION,
            confMaterialList = listOf()
        )
        val result1 = viewModel.initData(step.copy(cantidad = 5))
        assertTrue(result1)

        val cantidad1 = viewModel.cantidad.first()
        assertEquals(5, cantidad1)

        val result2 = viewModel.initData(step.copy(cantidad = 3))
        assertTrue(result2)

        val cantidad2 = viewModel.cantidad.first()
        assertNotEquals(3, cantidad2)
    }
}