package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user

import android.net.Uri
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.UserRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.UserBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class InitializeUserUseCaseTest {
    @RelaxedMockK
    private lateinit var repo: UserRepository

    private lateinit var initializeUserUseCase: InitializeUserUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        initializeUserUseCase = InitializeUserUseCase(
            repo = repo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test initializeUserUseCase`() = runTest {
        val userBean = UserBean(
            idUser = 1,
            rol = Rol.GUEST,
            username = "Guest",
            password = "1234",
            email = "guest@gmail.com",
            photoUri = Uri.EMPTY
        )
        val result = initializeUserUseCase(userBean).await()

        assertEquals(true, result)
    }
}