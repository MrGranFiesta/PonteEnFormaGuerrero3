package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem

import android.net.Uri
import androidx.compose.ui.test.junit4.createComposeRule
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.StepEntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.template.IManagerSerie
import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ConfManagerVMTest {
    lateinit var viewModel: ConfManagerVM

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = ConfManagerVM()
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    private fun getListStep() = listOf(
        StepEntrenamientoDto(
            idEjercicio = 1,
            idStep = 1,
            nombre = "Press Banca",
            photoUri = Uri.EMPTY,
            isSimetria = true,
            cantidad = 1,
            musculoSet = mutableSetOf(Musculo.TRAPECIO),
            serie = 1,
            ordenExecution = 1,
            tipo = TipoEsfuerzo.CRONO
        )
    )

    @Test
    fun testGetOrCreateImagerSerieManagerDualCronoVM() {
        var result: IManagerSerie? = null

        composeTestRule.setContent {
            result = viewModel.getOrCreateImagerSerie(
                stepList = getListStep().map {
                    it.apply {
                        it.tipo = TipoEsfuerzo.CRONO
                        it.isSimetria = true
                    }
                },
                descanso = 10,
                cursorStep = 0
            )
        }
        assertTrue(result is ManagerDualCronoVM)
    }

    @Test
    fun testGetOrCreateImagerSerieManagerDualRepsVM() {
        var result: IManagerSerie? = null

        composeTestRule.setContent {
            result = viewModel.getOrCreateImagerSerie(
                stepList = getListStep().map {
                    it.apply {
                        it.tipo = TipoEsfuerzo.REPETICION
                        it.isSimetria = true
                    }
                },
                descanso = 10,
                cursorStep = 0
            )
        }
        assertTrue(result is ManagerDualRepsVM)
    }

    @Test
    fun testGetOrCreateImagerSerieManagerSimpleCronoVM() {
        var result: IManagerSerie? = null

        composeTestRule.setContent {
            result = viewModel.getOrCreateImagerSerie(
                stepList = getListStep().map {
                    it.apply {
                        it.tipo = TipoEsfuerzo.CRONO
                        it.isSimetria = false
                    }
                },
                descanso = 10,
                cursorStep = 0
            )
        }
        assertTrue(result is ManagerSimpleCronoVM)
    }

    @Test
    fun testGetOrCreateImagerSerieManagerSimpleRepsVM() {
        var result: IManagerSerie? = null

        composeTestRule.setContent {
            result = viewModel.getOrCreateImagerSerie(
                stepList = getListStep().map {
                    it.apply {
                        it.tipo = TipoEsfuerzo.REPETICION
                        it.isSimetria = false
                    }
                },
                descanso = 10,
                cursorStep = 0
            )
        }
        assertTrue(result is ManagerSimpleRepsVM)
    }
}