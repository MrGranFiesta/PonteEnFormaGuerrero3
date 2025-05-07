package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.material3.SnackbarHostState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.StateBatchCrono
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.MaterialEntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.StepEntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.crono.GetEntrenamientoUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.crono.GetMaterialEntrenamientoUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.crono.GetStepEntrenamientoUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.crono.RegisterDataUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.stat.CreateStatUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse

@OptIn(ExperimentalCoroutinesApi::class)
class CronoVMTest {
    lateinit var viewModel: CronoVM

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @RelaxedMockK
    lateinit var getEntrenamientoUseCase: GetEntrenamientoUseCase

    @RelaxedMockK
    lateinit var getStepEntrenamientoUseCase: GetStepEntrenamientoUseCase

    @RelaxedMockK
    lateinit var getMaterialEntrenamientoUseCase: GetMaterialEntrenamientoUseCase

    @RelaxedMockK
    lateinit var registerDataUseCase: RegisterDataUseCase

    @RelaxedMockK
    lateinit var createStatUseCase: CreateStatUseCase

    private lateinit var snackbarHostState: SnackbarHostState

    @Before
    fun onBefore() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        viewModel = CronoVM(
            getEntrenamientoUseCase = getEntrenamientoUseCase,
            getStepEntrenamientoUseCase = getStepEntrenamientoUseCase,
            getMaterialEntrenamientoUseCase = getMaterialEntrenamientoUseCase,
            registerDataUseCase = registerDataUseCase,
            createStatUseCase = createStatUseCase
        )

        snackbarHostState = SnackbarHostState()
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun tesInitDataCorrect() {
        val statusField = viewModel.javaClass.getDeclaredField("status")
        statusField.isAccessible = true

        assertTrue(statusField.getBoolean(viewModel))

        viewModel.initData(1)

        assertFalse(statusField.getBoolean(viewModel))
    }

    @Test
    fun testIsEndRutinaCorrect() {
        val statusField = viewModel.javaClass.getDeclaredField("status")
        statusField.isAccessible = true

        assertTrue(statusField.getBoolean(viewModel))

        viewModel.initData(1)

        assertFalse(statusField.getBoolean(viewModel))
    }

    @Test
    fun testIsStepContentMaterialIndexOutOfBoundsException(){
        val stepBD = listOf(
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

        val materialDB = listOf(
            MaterialEntrenamientoDto(
                idMaterial = 1,
                idConfMaterial = 1,
                idStep = 1,
                nombre = "Banco",
                photoUri = Uri.EMPTY,
                isMaterialWeight = true,
                confValue = 1.0
            )
        )

        val result = viewModel.isStepContentMaterial(
            stateBatchCrono = StateBatchCrono.STEPS,
            stepDB = stepBD,
            materialDB = materialDB,
            cursor = 100
        )
        assertFalse(result)
    }

    @Test
    fun testIsStepContentMaterialStateIsNotSteps(){
        val result = viewModel.isStepContentMaterial(
            stateBatchCrono = StateBatchCrono.CALENTAMIENTO,
            stepDB = listOf(),
            materialDB = listOf(),
            cursor = 0
        )

        assertFalse(result)
    }

    @Test
    fun testIsStepContentMaterialNotFindMaterial(){
        val stepBD = listOf(
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

        val materialDB = listOf(
            MaterialEntrenamientoDto(
                idMaterial = 1,
                idConfMaterial = 1,
                idStep = 100,
                nombre = "Banco",
                photoUri = Uri.EMPTY,
                isMaterialWeight = true,
                confValue = 1.0
            )
        )

        val result = viewModel.isStepContentMaterial(
            stateBatchCrono = StateBatchCrono.STEPS,
            stepDB = stepBD,
            materialDB = materialDB,
            cursor = 0
        )

        assertFalse(result)
    }

    @Test
    fun testIsStepContentMaterialCorrect(){
        val stepBD = listOf(
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

        val materialDB = listOf(
            MaterialEntrenamientoDto(
                idMaterial = 1,
                idConfMaterial = 1,
                idStep = 1,
                nombre = "Banco",
                photoUri = Uri.EMPTY,
                isMaterialWeight = true,
                confValue = 1.0
            )
        )

        val result = viewModel.isStepContentMaterial(
            stateBatchCrono = StateBatchCrono.STEPS,
            stepDB = stepBD,
            materialDB = materialDB,
            cursor = 0
        )

        assertTrue(result)
    }
}