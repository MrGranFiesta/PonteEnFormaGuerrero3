package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.snapshot.confmaterial.GetMaterialWithConfByIdEjercicioUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.snapshot.ejercicio.GetEjercicioSnapshotByIdUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class StatEjercicioInfoVMTest {
    lateinit var viewModel: StatEjercicioInfoVM

    @RelaxedMockK
    private lateinit var getEjercicioSnapshotByIdUseCase : GetEjercicioSnapshotByIdUseCase

    @RelaxedMockK
    private lateinit var getMaterialWithConfByIdEjercicioUseCase : GetMaterialWithConfByIdEjercicioUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = StatEjercicioInfoVM(
            getEjercicioSnapshotByIdUseCase = getEjercicioSnapshotByIdUseCase,
            getMaterialWithConfByIdEjercicioUseCase = getMaterialWithConfByIdEjercicioUseCase
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun testInitDataCorrect() {
        val statusField = viewModel.javaClass.getDeclaredField("status")
        statusField.isAccessible = true

        assertTrue(statusField.getBoolean(viewModel))

        viewModel.initData(1)

        assertFalse(statusField.getBoolean(viewModel))
    }
}