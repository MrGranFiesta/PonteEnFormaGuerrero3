package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.material3.SnackbarHostState
import androidx.test.platform.app.InstrumentationRegistry
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.MaterialInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.user.UserWithRolDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material.CreateMaterialUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material.GetMaterialByIdUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material.UpdateMaterialUseCase
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
class MaterialFormVMTest {
    lateinit var viewModel: MaterialFormVM

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @RelaxedMockK
    lateinit var getMaterialByIdUseCase: GetMaterialByIdUseCase

    @RelaxedMockK
    lateinit var updateMaterialUseCase: UpdateMaterialUseCase

    @RelaxedMockK
    lateinit var createMaterialUseCase: CreateMaterialUseCase

    private lateinit var snackbarHostState: SnackbarHostState
    private var popBackStackCalled = false

    @Before
    fun onBefore() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        mockkObject(CurrentUser)
        viewModel = MaterialFormVM(
            getMaterialByIdUseCase = getMaterialByIdUseCase,
            updateMaterialUseCase = updateMaterialUseCase,
            createMaterialUseCase = createMaterialUseCase
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
    fun testInitDataCorrect() = runTest {
        val statusField = viewModel.javaClass.getDeclaredField("status")
        statusField.isAccessible = true

        assertTrue(statusField.getBoolean(viewModel))

        viewModel.initData(1)

        assertFalse(statusField.getBoolean(viewModel))
    }

    @Test
    fun testInitModeUpdCreateMode() = runTest {
        val materialFirst = MaterialInfoDto(
            idMaterial = 1,
            idUser = 1,
            nombre = "Banco",
            isMaterialWeight = true,
            descripcion = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        viewModel.initModeUpd(
            material = materialFirst,
            id = 1
        )

        val resultFirst = viewModel.nombre.first()

        assertEquals("Banco", resultFirst)

        val materialSecornd = MaterialInfoDto(
            idMaterial = 1,
            idUser = 1,
            nombre = "Mancuerna",
            isMaterialWeight = true,
            descripcion = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        viewModel.initModeUpd(
            material = materialSecornd,
            id = 1
        )

        val resultSecond = viewModel.nombre.first()

        assertEquals("Banco", resultSecond)
    }

    @Test
    fun testInitModeUpdModeCreate() = runTest {
        val materialInfoDtoFirst = MaterialInfoDto(
            idMaterial = 1,
            idUser = 1,
            nombre = "Banco",
            isMaterialWeight = true,
            descripcion = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        viewModel.initModeUpd(
            material = materialInfoDtoFirst,
            id = 0
        )

        val resultFirst = viewModel.nombre.first()

        assertNotEquals("Banco", resultFirst)

        val materialInfoDtoSecornd = MaterialInfoDto(
            idMaterial = 1,
            idUser = 1,
            nombre = "Mancuerna",
            isMaterialWeight = true,
            descripcion = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        viewModel.initModeUpd(
            material = materialInfoDtoSecornd,
            id = 0
        )

        val resultSecond = viewModel.nombre.first()

        assertNotEquals("Banco", resultSecond)
    }

    @Test
    fun testCrudActionFormModeUpdate() = runTest {
        val material = MaterialInfoDto(
            idMaterial = 1,
            idUser = 1,
            nombre = "Banco",
            isMaterialWeight = true,
            descripcion = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        viewModel.crudActionForm(
            context = InstrumentationRegistry.getInstrumentation().targetContext,
            material = material
        )

        coVerify {
            updateMaterialUseCase.invoke(
                context = any(),
                deleteImgUri = any(),
                material = any()
            )
        }
    }

    @Test
    fun testCrudActionFormModeCreate() = runTest {
        val material = MaterialInfoDto(
            idMaterial = 0,
            idUser = 1,
            nombre = "Banco",
            isMaterialWeight = true,
            descripcion = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        viewModel.crudActionForm(
            context = InstrumentationRegistry.getInstrumentation().targetContext,
            material = material
        )

        coVerify {
            createMaterialUseCase.invoke(
                context = any(),
                material = any()
            )
        }
    }

    @Test
    fun testActivateDialogChangeCorrectWithPopBackStack() {
        val material = MaterialInfoDto(
            idMaterial = 0,
            idUser = 1,
            nombre = "Banco",
            isMaterialWeight = true,
            descripcion = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        viewModel.setNombre("Banco")
        viewModel.setMaterialWeight(true)
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
            materialDB = material,
            snackbarHost = snackbarHostState,
            popBackStack = { popBackStackCalled = true }
        )

        assertTrue(popBackStackCalled)
    }

    @Test
    fun testActivateDialogChangeCorrectWithOpenDialog() = runTest {
        val material = MaterialInfoDto(
            idMaterial = 0,
            idUser = 1,
            nombre = "Banco",
            isMaterialWeight = true,
            descripcion = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        viewModel.setNombre("Mancuerna")
        viewModel.setMaterialWeight(true)
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
            materialDB = material,
            snackbarHost = snackbarHostState,
            popBackStack = { popBackStackCalled = true }
        )

        val result = viewModel.selectSaveDialog.first()

        assertFalse(popBackStackCalled)
        assertTrue(result)
    }

    @Test
    fun testActivateDialogChangeErrorValid() = runTest {
        val material = MaterialInfoDto(
            idMaterial = 0,
            idUser = 1,
            nombre = "Banco",
            isMaterialWeight = true,
            descripcion = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        viewModel.setNombre("|@#$%")
        viewModel.setMaterialWeight(true)
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
            materialDB = material,
            snackbarHost = snackbarHostState,
            popBackStack = { popBackStackCalled = true }
        )

        val result = viewModel.selectSaveDialog.first()

        assertFalse(popBackStackCalled)
        assertFalse(result)
    }

    @Test
    fun testIsChangeValuesIncorrect() {
        val material = MaterialInfoDto(
            idMaterial = 0,
            idUser = 1,
            nombre = "Banco",
            isMaterialWeight = true,
            descripcion = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        viewModel.setNombre("Mancuerna")
        viewModel.setMaterialWeight(true)
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
            materialDB = material
        )

        assertTrue(result)
    }

    @Test
    fun testIsChangeValuesCorrect() {
        val material = MaterialInfoDto(
            idMaterial = 0,
            idUser = 1,
            nombre = "Banco",
            isMaterialWeight = true,
            descripcion = "",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        viewModel.setNombre("Banco")
        viewModel.setMaterialWeight(true)
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
            materialDB = material
        )

        assertFalse(result)
    }
}