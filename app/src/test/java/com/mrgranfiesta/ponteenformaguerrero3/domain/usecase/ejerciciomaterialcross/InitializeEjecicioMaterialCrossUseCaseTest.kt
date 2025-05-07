package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejerciciomaterialcross

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioMaterialCrossRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.EjercicioMaterialCrossBean
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test

class InitializeEjecicioMaterialCrossUseCaseTest{
    private lateinit var initializeEjecicioMaterialCrossUseCase : InitializeEjecicioMaterialCrossUseCase

    @RelaxedMockK
    private lateinit var repo : EjercicioMaterialCrossRepository

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        initializeEjecicioMaterialCrossUseCase = InitializeEjecicioMaterialCrossUseCase(repo)
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test initializeEjecicioMaterialCrossUseCase`() {
        val ejercicioMaterialCrossBean = EjercicioMaterialCrossBean(
            idEjercicio = 0,
            idMaterial = 0
        )
        initializeEjecicioMaterialCrossUseCase(ejercicioMaterialCrossBean)

        coVerify { repo.insert(any()) }
    }
}