package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio

import android.net.Uri
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.EjercicioBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
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
class InitializeEjercicioUseCaseTest {
    @RelaxedMockK
    private lateinit var repo: EjercicioRepository

    private lateinit var initializeEjercicioUseCase: InitializeEjercicioUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        initializeEjercicioUseCase = InitializeEjercicioUseCase(
            repo = repo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test getRowEjercicioUseCase`() = runTest {
        val ejercicioBean = EjercicioBean(
            idEjercicio = 1,
            idUser = 1,
            nombre = "Flexiones",
            isSimetria = true,
            musculoSet = setOf(Musculo.OBLICUOS),
            nivel = Nivel.MEDIO,
            photoUri = Uri.EMPTY,
            descripcion = "",
        )

        val result = initializeEjercicioUseCase(ejercicioBean).await()

        coVerify { repo.insert(any()) }

        assertEquals(true, result)
    }
}