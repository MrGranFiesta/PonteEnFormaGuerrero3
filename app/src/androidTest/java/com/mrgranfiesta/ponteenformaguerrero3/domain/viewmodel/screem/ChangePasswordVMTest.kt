package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.material3.SnackbarHostState
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user.IsNotCorrectPasswordUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user.UpdatePasswordByIdUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ChangePasswordVMTest {
    lateinit var viewModel: ChangePasswordVM

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @RelaxedMockK
    lateinit var isNotCorrectPasswordUseCase: IsNotCorrectPasswordUseCase

    @RelaxedMockK
    lateinit var updatePasswordByIdUseCase: UpdatePasswordByIdUseCase

    private lateinit var snackbarHostState: SnackbarHostState
    private var popBackStackCalled = false

    @Before
    fun onBefore() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        viewModel = ChangePasswordVM(
            isNotCorrectPasswordUseCase = isNotCorrectPasswordUseCase,
            updatePasswordByIdUseCase = updatePasswordByIdUseCase
        )

        snackbarHostState = SnackbarHostState()
        popBackStackCalled = false
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun testIsValidPasswordIsEmpty() {
        viewModel.setPasswordOld("")
        viewModel.setPasswordNew("")
        viewModel.setPasswordNewConfirm("")

        val result = viewModel.isValid(snackbarHostState)

        assertFalse(result)
    }

    @Test
    fun testIsValidPasswordIsIlegartChar() {
        viewModel.setPasswordOld("|@#$%")
        viewModel.setPasswordNew("aaaa")
        viewModel.setPasswordNewConfirm("aaaa")

        val result = viewModel.isValid(snackbarHostState)

        assertFalse(result)
    }

    @Test
    fun testIsValidPasswordIsDiferrentPassword() {
        viewModel.setPasswordOld("aaaa")
        viewModel.setPasswordNew("1234")
        viewModel.setPasswordNewConfirm("bbbb")

        val result = viewModel.isValid(snackbarHostState)

        assertFalse(result)
    }

    @Test
    fun testIsValidCorrect() {
        viewModel.setPasswordOld("1234")
        viewModel.setPasswordNew("1234")
        viewModel.setPasswordNewConfirm("1234")

        val result = viewModel.isValid(snackbarHostState)

        assertTrue(result)
    }

    @Test
    fun testUpdatePaswordCorrect() {
        viewModel.setPasswordOld("aaaa")
        viewModel.setPasswordNew("1234")
        viewModel.setPasswordNewConfirm("1234")

        every { isNotCorrectPasswordUseCase(any()) } returns false

        viewModel.updatePasword(
            snackbarHost = snackbarHostState,
            popBackStack = { popBackStackCalled = true }
        )

        verify { updatePasswordByIdUseCase("1234") }

        assertTrue(popBackStackCalled)
    }

    @Test
    fun testUpdatePaswordValidationFail() {
        viewModel.setPasswordOld("aaaa")
        viewModel.setPasswordNew("1234")
        viewModel.setPasswordNewConfirm("123456")

        every { isNotCorrectPasswordUseCase(any()) } returns false

        viewModel.updatePasword(
            snackbarHost = snackbarHostState,
            popBackStack = { popBackStackCalled = true }
        )

        verify(exactly = 0) { updatePasswordByIdUseCase(any()) }

        assertFalse(popBackStackCalled)
    }

}