package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user

import android.net.Uri
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.UserRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.user.UserWithRolDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UpdatePasswordByIdUseCaseTest{
    @RelaxedMockK
    private lateinit var repo: UserRepository

    private lateinit var updatePasswordByIdUseCase : UpdatePasswordByIdUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        mockkObject(CurrentUser)
        updatePasswordByIdUseCase = UpdatePasswordByIdUseCase(
            repo = repo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test updatePasswordByIdUseCase`() {
        val userWithRolDto = UserWithRolDto(
            idUser = 1,
            username = "Guest",
            email = "guest@gmail.com",
            photoUri = Uri.EMPTY,
            rol = Rol.GUEST
        )

        coEvery { CurrentUser.user } returns userWithRolDto

        updatePasswordByIdUseCase(
            password = "1234"
        )

        coVerify {  repo.updatePassword(any(), any()) }
    }
}