package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem

import android.net.Uri
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.StepEntrenamientoDto
import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

class StadisticsUtilsVMTest {
    lateinit var viewModel: StadisticsUtilsVM

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = StadisticsUtilsVM()
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun testCalculateMuscleEjercicio() {
        val stepBD = listOf(
            StepEntrenamientoDto(
                idEjercicio = 1,
                idStep = 1,
                nombre = "Press Banca",
                photoUri = Uri.EMPTY,
                isSimetria = true,
                cantidad = 1,
                musculoSet = mutableSetOf(Musculo.TRAPECIO, Musculo.ABDOMINALES),
                serie = 1,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.CRONO
            )
        )

        val result = viewModel.calculateMuscleEjercicio(
            musculoSetSustraendo = setOf(Musculo.ABDOMINALES),
            stepList = stepBD
        )

        assertEquals(listOf(Musculo.TRAPECIO), result)
    }
}