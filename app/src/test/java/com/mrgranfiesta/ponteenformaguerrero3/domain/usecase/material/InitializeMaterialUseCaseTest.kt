package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material

import android.net.Uri
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.MaterialRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.MaterialBean
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class InitializeMaterialUseCaseTest {
    @RelaxedMockK
    private lateinit var repo: MaterialRepository

    private lateinit var initializeMaterialUseCase: InitializeMaterialUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        initializeMaterialUseCase = InitializeMaterialUseCase(repo)
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test initializeMaterialUseCase`() = runTest {
        val materialBean = MaterialBean(
            idMaterial = 1,
            idUser = 1,
            nombre = "",
            isMaterialWeight = true,
            descripcion = "",
            photoUri = Uri.EMPTY,
            confValue = 1.0
        )
        val result = initializeMaterialUseCase(materialBean).await()
        coVerify { repo.insert(any()) }
        assertEquals(true, result)
    }
}