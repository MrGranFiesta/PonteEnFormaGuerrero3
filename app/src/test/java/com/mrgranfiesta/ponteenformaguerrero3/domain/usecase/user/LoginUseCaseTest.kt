package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user

import android.net.Uri
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.user.UserWithRolDB
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.UserRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.user.UserWithRolDto
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LoginUseCaseTest {
    @RelaxedMockK
    private lateinit var repo: UserRepository

    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        loginUseCase = LoginUseCase(
            repo = repo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test loginUseCase init User no puede logear`() {
        val result = loginUseCase(
            email = "init@gmail.com",
            password = "1234"
        )

        coVerify(exactly = 0) { repo.loginUser(any(), any()) }
        assertEquals(null, result)
    }

    @Test
    fun `test loginUseCase loggin correcto`() {
        val userWithRolDB = UserWithRolDB(
            idUser = 1,
            username = "Guest",
            email = "guest@gmail.com",
            nameImg = "",
            rol = Rol.GUEST
        )

        val userWithRolDtoSolution = UserWithRolDto(
            idUser = 1,
            username = "Guest",
            email = "guest@gmail.com",
            photoUri = Uri.EMPTY,
            rol = Rol.GUEST
        )

        coEvery {
            repo.loginUser(
                email = any(),
                password = any()
            )
        } returns userWithRolDB

        val userWithRolDtoResult = loginUseCase(
            email = "guest@gmail.com",
            password = "1234"
        )

        coVerify(exactly = 1) { repo.loginUser(any(), any()) }
        assertEquals(userWithRolDtoSolution, userWithRolDtoResult)
    }

    @Test
    fun `test loginUseCase loggin no correcto`() {
        val userWithRolDB = null

        coEvery {
            repo.loginUser(
                email = any(),
                password = any()
            )
        } returns userWithRolDB

        val userWithRolDtoResult = loginUseCase(
            email = "guest@gmail.com",
            password = "1234"
        )

        coVerify(exactly = 1) { repo.loginUser(any(), any()) }
        assertEquals(null, userWithRolDtoResult)
    }
}