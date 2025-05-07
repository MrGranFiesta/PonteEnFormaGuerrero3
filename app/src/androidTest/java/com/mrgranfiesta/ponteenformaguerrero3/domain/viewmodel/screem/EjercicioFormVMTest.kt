package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.material3.SnackbarHostState
import androidx.test.platform.app.InstrumentationRegistry
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.user.UserWithRolDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio.CreateEjercicioUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio.GetEjercicioInfoByIdUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio.UpdateEjercicioUserCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material.GetMaterialByIdEjercicioUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material.GetMaterialByIdListUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
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
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EjercicioFormVMTest {
    lateinit var viewModel: EjercicioFormVM

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @RelaxedMockK
    lateinit var getMaterialByIdList: GetMaterialByIdListUseCase

    @RelaxedMockK
    lateinit var getMaterialByIdEjercicio: GetMaterialByIdEjercicioUseCase

    @RelaxedMockK
    lateinit var getEjercicioInfoByIdUseCase: GetEjercicioInfoByIdUseCase

    @RelaxedMockK
    lateinit var createEjercicioUseCase: CreateEjercicioUseCase

    @RelaxedMockK
    lateinit var updateEjercicioUserCase: UpdateEjercicioUserCase

    private lateinit var snackbarHostState: SnackbarHostState
    private var popBackStackCalled = false

    @Before
    fun onBefore() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        mockkObject(CurrentUser)
        viewModel = EjercicioFormVM(
            getMaterialByIdList = getMaterialByIdList,
            getMaterialByIdEjercicio = getMaterialByIdEjercicio,
            getEjercicioInfoByIdUseCase = getEjercicioInfoByIdUseCase,
            createEjercicioUseCase = createEjercicioUseCase,
            updateEjercicioUserCase = updateEjercicioUserCase
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
    fun testCreatedBackupEjercicioCorrect() = runTest {
        val ejercicioFirst = EjercicioInfoDto(
            idEjercicio = 1,
            idUser = 1,
            nombre = "Flexiones",
            isSimetria = true,
            musculoSet = setOf(Musculo.OBLICUOS),
            nivel = Nivel.MEDIO,
            descripcion = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        viewModel.createdBackupEjercicio(
            ejercicio = ejercicioFirst,
            materialDB = listOf(),
            id = 1L
        )

        val resultFirst = viewModel.nombre.first()

        assertEquals("Flexiones", resultFirst)

        viewModel.createdBackupEjercicio(
            ejercicio = ejercicioFirst.copy(nombre = "Correr"),
            materialDB = listOf(),
            id = 1L
        )

        val resultSecond = viewModel.nombre.first()

        assertEquals("Flexiones", resultSecond)
    }

    @Test
    fun testCreatedBackupEjercicioIsModeNewEjercicio() = runTest {
        val ejercicioFirst = EjercicioInfoDto(
            idEjercicio = 1,
            idUser = 1,
            nombre = "Flexiones",
            isSimetria = true,
            musculoSet = setOf(Musculo.OBLICUOS),
            nivel = Nivel.MEDIO,
            descripcion = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        viewModel.createdBackupEjercicio(
            ejercicio = ejercicioFirst,
            materialDB = listOf(),
            id = 0L
        )

        val resultFirst = viewModel.nombre.first()

        assertNotEquals("Flexiones", resultFirst)
    }

    @Test
    fun testCrudActionFormModeCreate() = runTest {
        val ejercicioFirst = EjercicioInfoDto(
            idEjercicio = 0,
            idUser = 1,
            nombre = "Flexiones",
            isSimetria = true,
            musculoSet = setOf(Musculo.OBLICUOS),
            nivel = Nivel.MEDIO,
            descripcion = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        viewModel.crudActionForm(
            ejercicioDB = ejercicioFirst,
            context = InstrumentationRegistry.getInstrumentation().targetContext
        )

        coVerify {
            createEjercicioUseCase.invoke(
                ejercicio = any(),
                materialList = any(),
                context = any()
            )
        }
    }

    @Test
    fun testCrudActionFormUpdate() = runTest {
        val ejercicioFirst = EjercicioInfoDto(
            idEjercicio = 1,
            idUser = 1,
            nombre = "Flexiones",
            isSimetria = true,
            musculoSet = setOf(Musculo.OBLICUOS),
            nivel = Nivel.MEDIO,
            descripcion = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        viewModel.crudActionForm(
            ejercicioDB = ejercicioFirst,
            context = InstrumentationRegistry.getInstrumentation().targetContext
        )

        coVerify {
            updateEjercicioUserCase.invoke(
                ejercicio = any(),
                materialListOrigin = any(),
                materialListUpdate = any(),
                deleteImgUri = any(),
                context = any()
            )
        }
    }

    @Test
    fun testActivateDialogChangeCorrectWithPopBackStack() = runTest {
        val ejercicioInfoDto = EjercicioInfoDto(
            idEjercicio = 1,
            idUser = 1,
            nombre = "Flexiones",
            isSimetria = true,
            musculoSet = setOf(Musculo.OBLICUOS),
            nivel = Nivel.MEDIO,
            descripcion = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        viewModel.setNombre("Flexiones")
        viewModel.setIsSimetria(true)
        viewModel.setMusculoSet(setOf(Musculo.OBLICUOS))
        viewModel.setNivel(Nivel.MEDIO)
        viewModel.setDescripcion("")
        viewModel.setPhotoUri(Uri.EMPTY)

        coEvery { CurrentUser.user } returns UserWithRolDto(
            idUser = 0,
            username = "",
            email = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        coEvery { CurrentUser.user?.idUser } returns 1

        viewModel.activateDialogChange(
            ejercicioDB = ejercicioInfoDto,
            snackbarHost = snackbarHostState,
            popBackStack = { popBackStackCalled = true }
        )

        assertTrue(popBackStackCalled)
    }

    @Test
    fun testActivateDialogChangeCorrectWithOpenDialog() = runTest {
        val ejercicioInfoDto = EjercicioInfoDto(
            idEjercicio = 1,
            idUser = 1,
            nombre = "Flexiones",
            isSimetria = true,
            musculoSet = setOf(Musculo.OBLICUOS),
            nivel = Nivel.MEDIO,
            descripcion = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        viewModel.setNombre("Correr")
        viewModel.setIsSimetria(true)
        viewModel.setMusculoSet(setOf(Musculo.OBLICUOS))
        viewModel.setNivel(Nivel.MEDIO)
        viewModel.setDescripcion("")
        viewModel.setPhotoUri(Uri.EMPTY)

        coEvery { CurrentUser.user } returns UserWithRolDto(
            idUser = 0,
            username = "",
            email = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        coEvery { CurrentUser.user?.idUser } returns 1

        viewModel.activateDialogChange(
            ejercicioDB = ejercicioInfoDto,
            snackbarHost = snackbarHostState,
            popBackStack = { popBackStackCalled = true }
        )

        val selectSaveDialog = viewModel.selectSaveDialog.first()

        assertFalse(popBackStackCalled)
        assertTrue(selectSaveDialog)
    }

    @Test
    fun testActivateDialogChangeErrorValid() = runTest {
        val ejercicioInfoDto = EjercicioInfoDto(
            idEjercicio = 1,
            idUser = 1,
            nombre = "Flexiones",
            isSimetria = true,
            musculoSet = setOf(Musculo.OBLICUOS),
            nivel = Nivel.MEDIO,
            descripcion = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        viewModel.setNombre("|@#$%")
        viewModel.setIsSimetria(true)
        viewModel.setMusculoSet(setOf(Musculo.OBLICUOS))
        viewModel.setNivel(Nivel.MEDIO)
        viewModel.setDescripcion("")
        viewModel.setPhotoUri(Uri.EMPTY)

        coEvery { CurrentUser.user } returns UserWithRolDto(
            idUser = 0,
            username = "",
            email = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        coEvery { CurrentUser.user?.idUser } returns 1

        viewModel.activateDialogChange(
            ejercicioDB = ejercicioInfoDto,
            snackbarHost = snackbarHostState,
            popBackStack = { popBackStackCalled = true }
        )

        val selectSaveDialog = viewModel.selectSaveDialog.first()

        assertFalse(popBackStackCalled)
        assertFalse(selectSaveDialog)
    }

    @Test
    fun testIsChangeValuesIncorrect() {
        val ejercicioInfoDto = EjercicioInfoDto(
            idEjercicio = 1,
            idUser = 1,
            nombre = "Flexiones",
            isSimetria = true,
            musculoSet = setOf(Musculo.OBLICUOS),
            nivel = Nivel.MEDIO,
            descripcion = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        viewModel.setNombre("|@#$%")
        viewModel.setIsSimetria(true)
        viewModel.setMusculoSet(setOf(Musculo.OBLICUOS))
        viewModel.setNivel(Nivel.MEDIO)
        viewModel.setDescripcion("")
        viewModel.setPhotoUri(Uri.EMPTY)

        coEvery { CurrentUser.user } returns UserWithRolDto(
            idUser = 0,
            username = "",
            email = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        coEvery { CurrentUser.user?.idUser } returns 1

        val result = viewModel.isChangeValues(
            ejercicioOrigen = ejercicioInfoDto
        )

        assertTrue(result)
    }

    @Test
    fun testIsChangeValuesCorrect() {
        val ejercicioInfoDto = EjercicioInfoDto(
            idEjercicio = 1,
            idUser = 1,
            nombre = "Flexiones",
            isSimetria = true,
            musculoSet = setOf(Musculo.OBLICUOS),
            nivel = Nivel.MEDIO,
            descripcion = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        viewModel.setNombre("Flexiones")
        viewModel.setIsSimetria(true)
        viewModel.setMusculoSet(setOf(Musculo.OBLICUOS))
        viewModel.setNivel(Nivel.MEDIO)
        viewModel.setDescripcion("")
        viewModel.setPhotoUri(Uri.EMPTY)

        coEvery { CurrentUser.user } returns UserWithRolDto(
            idUser = 0,
            username = "",
            email = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        coEvery { CurrentUser.user?.idUser } returns 1

        val result = viewModel.isChangeValues(
            ejercicioOrigen = ejercicioInfoDto
        )

        assertFalse(result)
    }
}