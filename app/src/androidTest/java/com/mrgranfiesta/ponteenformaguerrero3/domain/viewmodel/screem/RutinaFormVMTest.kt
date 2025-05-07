package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.AnswerState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.dialog.StepEditDialogDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.rutina.RutinaInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.user.UserWithRolDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio.GetEjercicioNameAndPhotoByIdUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.rutina.CreateRutinaWithStepUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.rutina.GetRutinaByIdUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.rutina.UpdateRutinaWithStepUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.step.GetStepFormByIdRutinaUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RutinaFormVMTest {
    lateinit var viewModel: RutinaFormVM

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @RelaxedMockK
    lateinit var getRutinaByIdUseCase: GetRutinaByIdUseCase

    @RelaxedMockK
    lateinit var updateRutinaWithStepUseCase: UpdateRutinaWithStepUseCase

    @RelaxedMockK
    lateinit var createRutinaWithStepUseCase: CreateRutinaWithStepUseCase

    @RelaxedMockK
    lateinit var getStepFormByIdRutinaUseCase: GetStepFormByIdRutinaUseCase

    @RelaxedMockK
    lateinit var getEjercicioNameAndPhotoByIdUseCase: GetEjercicioNameAndPhotoByIdUseCase

    private lateinit var snackbarHostState: SnackbarHostState
    private var popBackStackCalled = false

    @Before
    fun onBefore() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        mockkObject(CurrentUser)
        viewModel = RutinaFormVM(
            getRutinaByIdUseCase = getRutinaByIdUseCase,
            updateRutinaWithStepUseCase = updateRutinaWithStepUseCase,
            createRutinaWithStepUseCase = createRutinaWithStepUseCase,
            getStepFormByIdRutinaUseCase = getStepFormByIdRutinaUseCase,
            getEjercicioNameAndPhotoByIdUseCase = getEjercicioNameAndPhotoByIdUseCase,
        )

        snackbarHostState = SnackbarHostState()
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
        unmockkAll()
        popBackStackCalled = false
    }

    @Test
    fun testInitDateCorrect() {
        val statusField = viewModel.javaClass.getDeclaredField("status")
        statusField.isAccessible = true

        assertTrue(statusField.getBoolean(viewModel))

        viewModel.initData(1)

        assertFalse(statusField.getBoolean(viewModel))
    }

    @Test
    fun testCreatedBackupRutinaCorrect() = runTest {
        val rutinaFirst = RutinaInfoDto(
            idRutina = 1,
            idUser = 1,
            nombre = "Flexiones",
            musculoSet = setOf(Musculo.OBLICUOS),
            nivel = Nivel.FACIL,
            descripcion = "Ejercicio fisico",
            calentamiento = AnswerState.ASK_LATER,
            movArticular = AnswerState.ASK_LATER,
            estiramiento = AnswerState.ASK_LATER,
            descanso = 1,
            rol = Rol.INIT_DATA_USER
        )

        viewModel.createdBackupRutina(
            rutinaOrigen = rutinaFirst,
            stepListDB = listOf(),
            id = 1L
        )

        val resultFirst = viewModel.nombre.first()

        assertEquals("Flexiones", resultFirst)

        viewModel.createdBackupRutina(
            rutinaOrigen = rutinaFirst.copy(nombre = "Correr"),
            stepListDB = listOf(),
            id = 1L
        )

        val resultSecond = viewModel.nombre.first()

        assertEquals("Flexiones", resultSecond)
    }

    @Test
    fun testActivateDialogChangeCorrectWithPopBackStack() {
        val rutina = RutinaInfoDto(
            idRutina = 1,
            idUser = 1,
            nombre = "Flexiones",
            musculoSet = setOf(Musculo.OBLICUOS),
            nivel = Nivel.FACIL,
            descripcion = "Ejercicio fisico",
            calentamiento = AnswerState.ASK_LATER,
            movArticular = AnswerState.ASK_LATER,
            estiramiento = AnswerState.ASK_LATER,
            descanso = 1,
            rol = Rol.INIT_DATA_USER
        )

        viewModel.setNombre("Flexiones")
        viewModel.setMusculoSet(setOf(Musculo.OBLICUOS))
        viewModel.setNivel(Nivel.FACIL)
        viewModel.setDescripcion("Ejercicio fisico")
        viewModel.setCalentamiento(AnswerState.ASK_LATER)
        viewModel.setMovArticular(AnswerState.ASK_LATER)
        viewModel.setEstiramiento(AnswerState.ASK_LATER)
        viewModel.setDescanso(1)

        coEvery { CurrentUser.user } returns UserWithRolDto(
            idUser = 0,
            username = "",
            email = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        coEvery { CurrentUser.user?.idUser } returns 1

        viewModel.activateDialogChange(
            rutinaOrigen = rutina,
            popBackStack = { popBackStackCalled = true },
            snackbarHost = snackbarHostState
        )

        assertTrue(popBackStackCalled)
    }

    @Test
    fun testActivateDialogChangeCorrectWithOpenDialog() = runTest {
        val rutina = RutinaInfoDto(
            idRutina = 1,
            idUser = 1,
            nombre = "Flexiones",
            musculoSet = setOf(Musculo.OBLICUOS),
            nivel = Nivel.FACIL,
            descripcion = "Ejercicio fisico",
            calentamiento = AnswerState.ASK_LATER,
            movArticular = AnswerState.ASK_LATER,
            estiramiento = AnswerState.ASK_LATER,
            descanso = 1,
            rol = Rol.INIT_DATA_USER
        )

        viewModel.setNombre("Correr")
        viewModel.setMusculoSet(setOf(Musculo.OBLICUOS))
        viewModel.setNivel(Nivel.MEDIO)
        viewModel.setDescripcion("")
        viewModel.setCalentamiento(AnswerState.ASK_LATER)
        viewModel.setMovArticular(AnswerState.ASK_LATER)
        viewModel.setEstiramiento(AnswerState.ASK_LATER)
        viewModel.setDescanso(1)

        coEvery { CurrentUser.user } returns UserWithRolDto(
            idUser = 0,
            username = "",
            email = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        coEvery { CurrentUser.user?.idUser } returns 1

        viewModel.activateDialogChange(
            rutinaOrigen = rutina,
            popBackStack = { popBackStackCalled = true },
            snackbarHost = snackbarHostState
        )

        val selectSaveDialog = viewModel.selectSaveDialog.first()

        assertFalse(popBackStackCalled)
        assertTrue(selectSaveDialog)
    }

    @Test
    fun testActivateDialogChangeErrorValid() = runTest {
        val rutina = RutinaInfoDto(
            idRutina = 1,
            idUser = 1,
            nombre = "Flexiones",
            musculoSet = setOf(Musculo.OBLICUOS),
            nivel = Nivel.FACIL,
            descripcion = "Ejercicio fisico",
            calentamiento = AnswerState.ASK_LATER,
            movArticular = AnswerState.ASK_LATER,
            estiramiento = AnswerState.ASK_LATER,
            descanso = 1,
            rol = Rol.INIT_DATA_USER
        )

        viewModel.setNombre("|@#$%")
        viewModel.setMusculoSet(setOf(Musculo.OBLICUOS))
        viewModel.setNivel(Nivel.MEDIO)
        viewModel.setDescripcion("")
        viewModel.setCalentamiento(AnswerState.ASK_LATER)
        viewModel.setMovArticular(AnswerState.ASK_LATER)
        viewModel.setEstiramiento(AnswerState.ASK_LATER)
        viewModel.setDescanso(1)

        coEvery { CurrentUser.user } returns UserWithRolDto(
            idUser = 0,
            username = "",
            email = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        coEvery { CurrentUser.user?.idUser } returns 1

        viewModel.activateDialogChange(
            rutinaOrigen = rutina,
            popBackStack = { popBackStackCalled = true },
            snackbarHost = snackbarHostState
        )

        val selectSaveDialog = viewModel.selectSaveDialog.first()

        assertFalse(popBackStackCalled)
        assertFalse(selectSaveDialog)
    }

    @Test
    fun testIsChangeValuesIncorrect() {
        val rutina = RutinaInfoDto(
            idRutina = 1,
            idUser = 1,
            nombre = "Flexiones",
            musculoSet = setOf(Musculo.OBLICUOS),
            nivel = Nivel.FACIL,
            descripcion = "Ejercicio fisico",
            calentamiento = AnswerState.ASK_LATER,
            movArticular = AnswerState.ASK_LATER,
            estiramiento = AnswerState.ASK_LATER,
            descanso = 1,
            rol = Rol.INIT_DATA_USER
        )

        viewModel.setNombre("Correr")
        viewModel.setMusculoSet(setOf(Musculo.OBLICUOS))
        viewModel.setNivel(Nivel.MEDIO)
        viewModel.setDescripcion("")
        viewModel.setCalentamiento(AnswerState.ASK_LATER)
        viewModel.setMovArticular(AnswerState.ASK_LATER)
        viewModel.setEstiramiento(AnswerState.ASK_LATER)
        viewModel.setDescanso(1)

        coEvery { CurrentUser.user } returns UserWithRolDto(
            idUser = 0,
            username = "",
            email = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        coEvery { CurrentUser.user?.idUser } returns 1

        val result = viewModel.isChangeValues(
            rutinaOrigen = rutina
        )

        assertTrue(result)
    }

    @Test
    fun testIsChangeValuesCorrect() {
        val rutina = RutinaInfoDto(
            idRutina = 1,
            idUser = 1,
            nombre = "Flexiones",
            musculoSet = setOf(Musculo.OBLICUOS),
            nivel = Nivel.FACIL,
            descripcion = "Ejercicio fisico",
            calentamiento = AnswerState.ASK_LATER,
            movArticular = AnswerState.ASK_LATER,
            estiramiento = AnswerState.ASK_LATER,
            descanso = 1,
            rol = Rol.INIT_DATA_USER
        )

        viewModel.setNombre("Flexiones")
        viewModel.setMusculoSet(setOf(Musculo.OBLICUOS))
        viewModel.setNivel(Nivel.FACIL)
        viewModel.setDescripcion("Ejercicio fisico")
        viewModel.setCalentamiento(AnswerState.ASK_LATER)
        viewModel.setMovArticular(AnswerState.ASK_LATER)
        viewModel.setEstiramiento(AnswerState.ASK_LATER)
        viewModel.setDescanso(1)

        coEvery { CurrentUser.user } returns UserWithRolDto(
            idUser = 0,
            username = "",
            email = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        coEvery { CurrentUser.user?.idUser } returns 1

        val result = viewModel.isChangeValues(
            rutinaOrigen = rutina
        )

        assertFalse(result)
    }

    @Test
    fun testCrudActionFormModeCreate() = runTest {
        val rutina = RutinaInfoDto(
            idRutina = 0,
            idUser = 1,
            nombre = "Flexiones",
            musculoSet = setOf(Musculo.OBLICUOS),
            nivel = Nivel.FACIL,
            descripcion = "Ejercicio fisico",
            calentamiento = AnswerState.ASK_LATER,
            movArticular = AnswerState.ASK_LATER,
            estiramiento = AnswerState.ASK_LATER,
            descanso = 1,
            rol = Rol.INIT_DATA_USER
        )

        viewModel.crudActionForm(rutinaOrigen = rutina)

        coVerify {
            createRutinaWithStepUseCase.invoke(
                rutina = any(),
                stepList = any()
            )
        }
    }

    @Test
    fun testCrudActionFormModeUpdate() = runTest {
        val rutina = RutinaInfoDto(
            idRutina = 1,
            idUser = 1,
            nombre = "Flexiones",
            musculoSet = setOf(Musculo.OBLICUOS),
            nivel = Nivel.FACIL,
            descripcion = "Ejercicio fisico",
            calentamiento = AnswerState.ASK_LATER,
            movArticular = AnswerState.ASK_LATER,
            estiramiento = AnswerState.ASK_LATER,
            descanso = 1,
            rol = Rol.INIT_DATA_USER
        )

        viewModel.crudActionForm(rutinaOrigen = rutina)

        coVerify {
            updateRutinaWithStepUseCase.invoke(
                rutina = any(),
                stepListUpdate = any(),
                stepListDelete = any()
            )
        }
    }

    @Test
    fun testAddOptionalStepCorrect() = runTest {
        val navBackStackEntry: NavBackStackEntry = mockk(relaxed = true)
        val savedStateHandle: SavedStateHandle = mockk(relaxed = true)

        every { navBackStackEntry.savedStateHandle } returns savedStateHandle
        every { savedStateHandle.get<StepEditDialogDto>(any()) } returns StepEditDialogDto(
            idStep = 1,
            idEjercicio = 1,
            cantidad = 1,
            serie = 1,
            tipo = TipoEsfuerzo.REPETICION,
            confMaterialList = listOf()
        )

        viewModel.addOptionalStep(
            navBackStackEntry = navBackStackEntry,
            idRutina = 1
        )

        val result = viewModel.stepListOnRutina.first().isNotEmpty()

        assertTrue(result)
    }

    @Test
    fun testAddOptionalStepStepIsNull() = runTest {
        val navBackStackEntry: NavBackStackEntry = mockk(relaxed = true)
        val savedStateHandle: SavedStateHandle = mockk(relaxed = true)

        every { navBackStackEntry.savedStateHandle } returns savedStateHandle
        every { savedStateHandle.get<StepEditDialogDto>(any()) } returns null

        viewModel.addOptionalStep(
            navBackStackEntry = navBackStackEntry,
            idRutina = 1
        )

        val result = viewModel.stepListOnRutina.first().any { it.idStep == 1L }

        assertFalse(result)
    }
}