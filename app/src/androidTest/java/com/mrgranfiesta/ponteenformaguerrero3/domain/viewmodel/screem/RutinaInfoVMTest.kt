package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio.GetEjercicioNameAndPhotoByIdUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.rutina.DeleteRutinaUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.rutina.GetRutinaByIdUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.step.GetStepInfoByIdRutinaUseCase
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

class RutinaInfoVMTest {
    lateinit var viewModel: RutinaInfoVM

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var getRutinaByIdUseCase: GetRutinaByIdUseCase

    @RelaxedMockK
    lateinit var deleteEjercicioUseCase: DeleteRutinaUseCase

    @RelaxedMockK
    lateinit var getStepInfoByIdRutinaUseCase: GetStepInfoByIdRutinaUseCase

    @RelaxedMockK
    lateinit var getEjercicioNameAndPhotoByIdUseCase: GetEjercicioNameAndPhotoByIdUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = RutinaInfoVM(
            getRutinaByIdUseCase = getRutinaByIdUseCase,
            deleteRutinaUseCase = deleteEjercicioUseCase,
            getStepInfoByIdRutinaUseCase = getStepInfoByIdRutinaUseCase,
            getEjercicioNameAndPhotoByIdUseCase = getEjercicioNameAndPhotoByIdUseCase
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