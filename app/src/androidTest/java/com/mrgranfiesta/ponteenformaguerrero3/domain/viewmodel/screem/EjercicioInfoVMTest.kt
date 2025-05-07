package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio.DeleteEjercicioUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio.GetEjercicioInfoByIdUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material.GetMaterialByIdEjercicioUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EjercicioInfoVMTest {
    lateinit var viewModel: EjercicioInfoVM

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var getEjercicioInfoByIdUseCase: GetEjercicioInfoByIdUseCase

    @RelaxedMockK
    lateinit var deleteEjercicioUseCase: DeleteEjercicioUseCase

    @RelaxedMockK
    lateinit var getMaterialByIdEjercicioUseCase: GetMaterialByIdEjercicioUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = EjercicioInfoVM(
            getEjercicioInfoByIdUseCase = getEjercicioInfoByIdUseCase,
            deleteEjercicioUseCase = deleteEjercicioUseCase,
            getMaterialByIdEjercicioUseCase = getMaterialByIdEjercicioUseCase
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun testInitDataCorrect() = runTest {
        val statusField = viewModel.javaClass.getDeclaredField("status")
        statusField.isAccessible = true

        assertTrue(statusField.getBoolean(viewModel))

        viewModel.initData(1)

        assertFalse(statusField.getBoolean(viewModel))
    }
}