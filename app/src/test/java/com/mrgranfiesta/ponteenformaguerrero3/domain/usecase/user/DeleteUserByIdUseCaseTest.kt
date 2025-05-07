package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user

import android.net.Uri
import androidx.test.platform.app.InstrumentationRegistry
import com.mrgranfiesta.ponteenformaguerrero3.data.filesystem.StorageFileService
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.MaterialRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.UserRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_EJERCICIO_IMG
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_MATERIAL_IMG
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_PROFILE_USER_IMG
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
class DeleteUserByIdUseCaseTest {
    @RelaxedMockK
    private lateinit var repoUser: UserRepository

    @RelaxedMockK
    private lateinit var repoEjercicio: EjercicioRepository

    @RelaxedMockK
    private lateinit var repoMaterial: MaterialRepository

    private lateinit var deleteUserByIdUseCase: DeleteUserByIdUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        mockkObject(StorageFileService)
        mockkObject(UriUtils)
        deleteUserByIdUseCase = DeleteUserByIdUseCase(
            repoUser = repoUser,
            repoEjercicio = repoEjercicio,
            repoMaterial = repoMaterial
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test deleteUserByIdUseCase`() {
        coEvery { repoUser.getNameImg(any()) } returns "profile_img"
        coEvery { repoEjercicio.getListNameImgByIdUser(any()) } returns listOf("ejer200_000")
        coEvery { repoMaterial.getListNameImgByIdUser(any()) } returns listOf("mat200_000")

        coEvery {
            UriUtils.getUriResource(
                dir = any(),
                nameImg = any()
            )
        } returns Uri.EMPTY

        coEvery {
            StorageFileService.deleteImg(
                dir = any(),
                context = any(),
                deleteImgUri = any()
            )
        } returns true

        deleteUserByIdUseCase(
            idUser = 1,
            context = InstrumentationRegistry.getInstrumentation().targetContext,
        )

        coVerify {
            StorageFileService.deleteImg(
                dir = eq(DIR_PROFILE_USER_IMG),
                context = any(),
                deleteImgUri = any()
            )
        }

        coVerify {
            StorageFileService.deleteImg(
                dir = eq(DIR_EJERCICIO_IMG),
                context = any(),
                deleteImgUri = any()
            )
        }

        coVerify {
            StorageFileService.deleteImg(
                dir = eq(DIR_MATERIAL_IMG),
                context = any(),
                deleteImgUri = any()
            )
        }

        coVerify {
            StorageFileService.deleteImg(
                dir = any(),
                context = any(),
                deleteImgUri = any()
            )
        }
        coVerify { repoUser.deleteById(any()) }
    }
}