package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import androidx.navigation.NavController
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material.GetMaterialByIdRutinaUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse

class StartRutinaQuestionVMTest {
    lateinit var viewModel: StartRutinaQuestionVM

    @RelaxedMockK
    private lateinit var getMaterialByIdRutinaUseCase: GetMaterialByIdRutinaUseCase

    @RelaxedMockK
    private lateinit var navController: NavController

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = StartRutinaQuestionVM(getMaterialByIdRutinaUseCase)
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun testStartRutinaCorrect() {
        assertTrue(viewModel.isEnableOneClick)
        viewModel.startRutina(
            navController = navController,
            idRutina = 1
        )
        assertFalse(viewModel.isEnableOneClick)
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