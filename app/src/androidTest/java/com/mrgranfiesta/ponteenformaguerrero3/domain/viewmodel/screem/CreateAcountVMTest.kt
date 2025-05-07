package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.test.platform.app.InstrumentationRegistry
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user.CreateAccountUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user.IsDuplicatedEmailUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CreateAcountVMTest {
    lateinit var viewModel: CreateAcountVM

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @RelaxedMockK
    lateinit var createAccountUseCase: CreateAccountUseCase

    @RelaxedMockK
    lateinit var isDuplicatedEmailUseCase: IsDuplicatedEmailUseCase

    @RelaxedMockK
    lateinit var navController: NavController

    private lateinit var snackbarHostState: SnackbarHostState

    @Before
    fun onBefore() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        viewModel = CreateAcountVM(
            createAccountUseCase = createAccountUseCase,
            isDuplicatedEmailUseCase = isDuplicatedEmailUseCase
        )

        snackbarHostState = SnackbarHostState()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun testRegisterUser() {
        viewModel.setNombre("Juan")
        viewModel.setEmail("juan@gmail.com")
        viewModel.setPassword("1234")
        viewModel.setPasswordConfirm("1234")
        viewModel.registerUser(
            context = InstrumentationRegistry.getInstrumentation().targetContext,
            snackbarHost = snackbarHostState,
            navController = navController
        )

        verify { navController.popBackStack() }
    }

    @Test
    fun testIsValidPasswordIsEmpty() {
        viewModel.setNombre("")
        viewModel.setEmail("")
        viewModel.setPassword("")
        viewModel.setPasswordConfirm("")

        val result = viewModel.isValid(snackbarHostState)

        assertFalse(result)
    }

    @Test
    fun testIsValidPasswordIsIlegartChar() {
        viewModel.setNombre("|@#$%")
        viewModel.setEmail("juan@gmail.com")
        viewModel.setPassword("1234")
        viewModel.setPasswordConfirm("1234")

        val result = viewModel.isValid(snackbarHostState)

        assertFalse(result)
    }

    @Test
    fun testIsValidPasswordIsDiferrentPassword() {
        viewModel.setNombre("|@#$%")
        viewModel.setEmail("juan@gmail.com")
        viewModel.setPassword("1234")
        viewModel.setPasswordConfirm("aaaa")

        val result = viewModel.isValid(snackbarHostState)

        assertFalse(result)
    }

    @Test
    fun testIsValidIsDuplicatedEmailUseCase() {
        viewModel.setNombre("|@#$%")
        viewModel.setEmail("juan@gmail.com")
        viewModel.setPassword("1234")
        viewModel.setPasswordConfirm("aaaa")

        coEvery { isDuplicatedEmailUseCase.invoke(any()) } returns true

        val result = viewModel.isValid(snackbarHostState)

        assertFalse(result)
    }

    @Test
    fun testIsValidIsEmailMalformed() {
        viewModel.setNombre("juan")
        viewModel.setEmail("no_email")
        viewModel.setPassword("1234")
        viewModel.setPasswordConfirm("aaaa")

        coEvery { isDuplicatedEmailUseCase.invoke(any()) } returns true

        val result = viewModel.isValid(snackbarHostState)

        assertFalse(result)
    }


    @Test
    fun testIsValidCorrect() {
        viewModel.setNombre("juan")
        viewModel.setEmail("juan@gmail.com")
        viewModel.setPassword("1234")
        viewModel.setPasswordConfirm("1234")

        val result = viewModel.isValid(snackbarHostState)

        assertTrue(result)
    }
}