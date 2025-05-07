package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import android.net.Uri
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.RowEjercicioDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio.GetRowEjercicioUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class EjercicioListVMTest {
    lateinit var viewModel: EjercicioListVM

    @RelaxedMockK
    private lateinit var getRowEjercicioUseCase: GetRowEjercicioUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = EjercicioListVM(getRowEjercicioUseCase)
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    private fun getSetEjercicio() = mutableSetOf(
        RowEjercicioDto(
            idEjercicio = 1,
            nombre = "Flexion normal",
            musculoSet = setOf(Musculo.PECHO, Musculo.HOMBRO),
            nivel = Nivel.DIFICIL,
            photoUri = Uri.EMPTY
        ),
        RowEjercicioDto(
            idEjercicio = 2,
            nombre = "Flexion en diamente",
            musculoSet = setOf(Musculo.PECHO, Musculo.HOMBRO),
            nivel = Nivel.DIFICIL,
            photoUri = Uri.EMPTY
        ),
        RowEjercicioDto(
            idEjercicio = 3,
            nombre = "Correr",
            musculoSet = setOf(Musculo.TRICEPS),
            nivel = Nivel.MEDIO,
            photoUri = Uri.EMPTY
        ),
        RowEjercicioDto(
            idEjercicio = 4,
            nombre = "Saltar",
            musculoSet = setOf(Musculo.PIERNA, Musculo.BICEPS),
            nivel = Nivel.FACIL,
            photoUri = Uri.EMPTY
        ),
        RowEjercicioDto(
            idEjercicio = 5,
            nombre = "|@#~€",
            musculoSet = setOf(Musculo.ABDOMINALES),
            nivel = Nivel.NINGUNO,
            photoUri = Uri.EMPTY
        )
    )

    @Test
    fun testInitDateIlegalSearchText() = runTest {
        viewModel.initData(
            searchText = "|@#~€",
            musculoSet = setOf(),
            nivelSet = setOf(),
            ejercicioListDB = getSetEjercicio()
        )

        val result = viewModel.ejercicioList.first().isEmpty()
        assertTrue(result)
    }

    @Test
    fun testInitDateFilterSearchText() = runTest {
        viewModel.initData(
            searchText = "Correr",
            musculoSet = setOf(),
            nivelSet = setOf(),
            ejercicioListDB = getSetEjercicio()
        )

        val result = viewModel.ejercicioList.first().isNotEmpty()
        assertTrue(result)
    }

    @Test
    fun testInitDateFilterSearchTextAutocomplete() = runTest {
        viewModel.initData(
            searchText = "Flexion",
            musculoSet = setOf(),
            nivelSet = setOf(),
            ejercicioListDB = getSetEjercicio()
        )

        val result = viewModel.ejercicioList.first().size == 2
        assertTrue(result)
    }

    @Test
    fun testInitDateFilterSearchTextRE() = runTest {
        viewModel.initData(
            searchText = "*lexi*",
            musculoSet = setOf(),
            nivelSet = setOf(),
            ejercicioListDB = getSetEjercicio()
        )

        val result = viewModel.ejercicioList.first().size == 2
        assertTrue(result)
    }

    @Test
    fun testInitDateFilterMusculoSet() = runTest {
        viewModel.initData(
            searchText = "",
            musculoSet = setOf(Musculo.HOMBRO),
            nivelSet = setOf(),
            ejercicioListDB = getSetEjercicio()
        )

        val result = viewModel.ejercicioList.first().isNotEmpty()
        assertTrue(result)
    }

    @Test
    fun testInitDateFilterNivelSet() = runTest {
        viewModel.initData(
            searchText = "",
            musculoSet = setOf(),
            nivelSet = setOf(Nivel.FACIL),
            ejercicioListDB = getSetEjercicio()
        )

        val result = viewModel.ejercicioList.first().isNotEmpty()
        assertTrue(result)
    }
}