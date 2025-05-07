package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mrgranfiesta.ponteenformaguerrero3.data.datastore.UserDataStore
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.user.UserWithRolDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user.LoginUseCase
import io.mockk.MockKAnnotations
import io.mockk.coVerify
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
import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Rule

@OptIn(ExperimentalCoroutinesApi::class)
class LoginVMTest {
    lateinit var viewModel: LoginVM

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @RelaxedMockK
    private lateinit var userDataStore: UserDataStore

    @RelaxedMockK
    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun onBefore() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        CurrentUser.user = null
        viewModel = LoginVM(
            userDataStore = userDataStore,
            loginUseCase = loginUseCase
        )
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun testIsCompleteLoginUserCorrect() {
        every {
            loginUseCase.invoke(
                email = any(),
                password = any()
            )
        } returns UserWithRolDto(
            idUser = 1L,
            username = "testUser",
            email = "test@email.com",
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )

        val result = viewModel.isCompleteLoginUser()

        verify {
            loginUseCase.invoke(
                email = any(),
                password = any()
            )
        }
        assertTrue(result)
    }

    @Test
    fun testIsCompleteLoginUserIncorrect() {
        every {
            loginUseCase.invoke(
                email = any(),
                password = any()
            )
        } returns null

        val result = viewModel.isCompleteLoginUser()

        verify {
            loginUseCase.invoke(
                email = any(),
                password = any()
            )
        }
        coVerify(exactly = 0) { userDataStore.saveUser(any()) }
        assertFalse(result)
    }
}