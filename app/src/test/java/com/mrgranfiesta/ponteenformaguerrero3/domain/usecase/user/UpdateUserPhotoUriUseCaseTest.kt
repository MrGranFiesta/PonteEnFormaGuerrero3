package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user

import android.net.Uri
import androidx.core.net.toUri
import androidx.test.platform.app.InstrumentationRegistry
import com.mrgranfiesta.ponteenformaguerrero3.data.datastore.UserDataStore
import com.mrgranfiesta.ponteenformaguerrero3.data.filesystem.StorageFileService
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.UserRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DRAWABLE_URI
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.user.UserWithRolDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.UriUtils
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
class UpdateUserPhotoUriUseCaseTest {
    @RelaxedMockK
    private lateinit var repo: UserRepository

    @RelaxedMockK
    private lateinit var userDataStore: UserDataStore

    private lateinit var updateUserPhotoUriUseCase: UpdateUserPhotoUriUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        mockkObject(CurrentUser)
        mockkObject(StorageFileService)
        mockkObject(UriUtils)
        updateUserPhotoUriUseCase = UpdateUserPhotoUriUseCase(
            repo = repo,
            userDataStore = userDataStore
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test updateUserPhotoUriUseCase no recuperas el nombre de la imagen actualizada por error`() {
        coEvery {
            StorageFileService.updateImg(
                context = any(),
                galeryUri = any(),
                dir = any(),
                deleteImgUri = any()
            )
        } returns ""

        updateUserPhotoUriUseCase(
            context = InstrumentationRegistry.getInstrumentation().targetContext,
            idUser = 1,
            photoUri = Uri.EMPTY,
            deleteImgUri = Uri.EMPTY
        )

        coVerify(exactly = 0) {
            repo.updatePhotoUri(
                idUser = any(),
                nameImg = any()
            )
        }
    }

    @Test
    fun `test updateUserPhotoUriUseCase`() {
        val userWithRolDto = UserWithRolDto(
            idUser = 1,
            username = "Guest",
            email = "guest@gmail.com",
            photoUri = Uri.EMPTY,
            rol = Rol.GUEST
        )

        coEvery {
            StorageFileService.updateImg(
                context = any(),
                galeryUri = any(),
                dir = any(),
                deleteImgUri = any()
            )
        } returns "profile_1234"

        coEvery { CurrentUser.user } returns userWithRolDto

        val photoUpdate = "${DRAWABLE_URI}profile_1234".toUri()
        updateUserPhotoUriUseCase(
            context = InstrumentationRegistry.getInstrumentation().targetContext,
            idUser = 1,
            photoUri = photoUpdate,
            deleteImgUri = "${DRAWABLE_URI}profile_8765".toUri()
        )

        coVerify {
            repo.updatePhotoUri(
                idUser = any(),
                nameImg = any()
            )
        }
        coVerify { userDataStore.updatePhotoUri(any()) }
        coVerify { UriUtils.getUriResource(any(), any()) }
    }
}