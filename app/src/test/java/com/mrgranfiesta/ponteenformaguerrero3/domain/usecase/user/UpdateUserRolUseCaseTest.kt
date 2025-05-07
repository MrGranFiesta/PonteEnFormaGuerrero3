package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user

import android.net.Uri
import com.mrgranfiesta.ponteenformaguerrero3.data.datastore.UserDataStore
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
import org.junit.Assert.assertTrue
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UpdateUserRolUseCaseTest {
    @RelaxedMockK
    private lateinit var repo: UserRepository

    @RelaxedMockK
    private lateinit var userDataStore: UserDataStore

    private lateinit var updateUserRolUseCase: UpdateUserRolUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        mockkObject(CurrentUser)
        updateUserRolUseCase = UpdateUserRolUseCase(
            repo = repo,
            userDataStore = userDataStore
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test updateUserRolUseCase`() {
        val userWithRolDto = UserWithRolDto(
            idUser = 1,
            username = "standar",
            email = "standar@gmail.com",
            photoUri = Uri.EMPTY,
            rol = Rol.STANDAR_USER
        )

        coEvery { CurrentUser.user } returns userWithRolDto

        updateUserRolUseCase(
            idUser = 1,
            rol = Rol.PREMIUN_USER
        )

        coVerify {
            repo.updateRol(
                idUser = any(),
                rol = any()
            )
        }

        coVerify {
            userDataStore.updateRol(any())
        }

        assertTrue(CurrentUser.user?.rol == Rol.PREMIUN_USER)
    }
}