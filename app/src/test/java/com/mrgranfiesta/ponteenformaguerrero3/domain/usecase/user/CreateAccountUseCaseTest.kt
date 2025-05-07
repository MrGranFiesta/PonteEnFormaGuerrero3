package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user

import android.net.Uri
import androidx.core.net.toUri
import androidx.test.platform.app.InstrumentationRegistry
import com.mrgranfiesta.ponteenformaguerrero3.data.filesystem.StorageFileService
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.UserRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.UserBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_PROFILE_USER_IMG
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DRAWABLE_URI
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CreateAccountUseCaseTest {
    @RelaxedMockK
    private lateinit var repo: UserRepository

    private lateinit var createAccountUseCase: CreateAccountUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        mockkObject(StorageFileService)
        createAccountUseCase = CreateAccountUseCase(
            repo = repo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test createAccountUseCase sin inserccion de imagen`() {
        val user = UserBean(
            idUser = 1,
            rol = Rol.INIT_DATA_USER,
            username = "Invitado",
            password = "1234",
            email = "guest@gmail.com",
            photoUri = Uri.EMPTY
        )

        every {
            StorageFileService.insertImg(
                context = InstrumentationRegistry.getInstrumentation().targetContext,
                galeryUri = user.photoUri,
                dir = DIR_PROFILE_USER_IMG
            )
        } returns ""
        createAccountUseCase(
            context = InstrumentationRegistry.getInstrumentation().targetContext,
            user = user
        )

        coVerify { repo.insert(any()) }
    }

    @Test
    fun `test createAccountUseCase con inserccion de imagen`() {
        val user = UserBean(
            idUser = 1,
            rol = Rol.INIT_DATA_USER,
            username = "Invitado",
            password = "1234",
            email = "guest@gmail.com",
            photoUri = "${DRAWABLE_URI}photo_user_1234".toUri()
        )

        every {
            StorageFileService.insertImg(
                context = InstrumentationRegistry.getInstrumentation().targetContext,
                galeryUri = user.photoUri,
                dir = DIR_PROFILE_USER_IMG
            )
        } returns "photo_user_1234"
        createAccountUseCase(
            context = InstrumentationRegistry.getInstrumentation().targetContext,
            user = user
        )

        coVerify { repo.insert(any()) }
    }
}