package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.confmaterial

import android.net.Uri
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.ConfMaterialRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.ConfMaterialBean
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class InitializeConfMaterialUseCaseTest {

    private lateinit var initializeConfMaterialUseCase: InitializeConfMaterialUseCase

    @RelaxedMockK
    private lateinit var repo: ConfMaterialRepository

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        initializeConfMaterialUseCase = InitializeConfMaterialUseCase(repo)
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test initializeConfMaterialUseCase`() {
        coEvery { repo.insert(any()) } returns 1

        val confMaterial = ConfMaterialBean(
            idConfMaterial = 1,
            idMaterial = 1,
            idStep = 1,
            confValue = 1.0,
            nombre = "Banco",
            isMaterialWeight = true,
            photoUri = Uri.EMPTY
        )
        initializeConfMaterialUseCase(confMaterial)

        coVerify { repo.insert(any()) }
    }
}