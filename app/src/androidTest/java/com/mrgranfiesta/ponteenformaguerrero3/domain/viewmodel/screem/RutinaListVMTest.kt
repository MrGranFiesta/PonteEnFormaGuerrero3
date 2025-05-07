package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.rutina.RowRutinaDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.rutina.GetRowRutinaUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RutinaListVMTest {
    lateinit var viewModel: RutinaListVM

    @RelaxedMockK
    private lateinit var getRowRutinaUseCase: GetRowRutinaUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = RutinaListVM(getRowRutinaUseCase)
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    private fun getSetRutina() = mutableSetOf(
        RowRutinaDto(
            idRutina = 1,
            idUser = 1,
            nombre = "Flexiones normal",
            nivel = Nivel.MEDIO,
            musculoSet = setOf(Musculo.PECHO, Musculo.HOMBRO),
            isPremium = true
        ),
        RowRutinaDto(
            idRutina = 2,
            idUser = 1,
            nombre = "Flexiones en diamante",
            nivel = Nivel.DIFICIL,
            musculoSet = setOf(Musculo.ABDOMINALES, Musculo.ESPALDA),
            isPremium = true
        ),
        RowRutinaDto(
            idRutina = 3,
            idUser = 1,
            nombre = "Carrera",
            nivel = Nivel.FACIL,
            musculoSet = setOf(Musculo.PIERNA),
            isPremium = true
        ),
        RowRutinaDto(
            idRutina = 4,
            idUser = 1,
            nombre = "Saltos",
            nivel = Nivel.NINGUNO,
            musculoSet = setOf(Musculo.TRICEPS),
            isPremium = true
        ),
        RowRutinaDto(
            idRutina = 5,
            idUser = 1,
            nombre = "|@#~€",
            nivel = Nivel.MEDIO,
            musculoSet = setOf(Musculo.BICEPS),
            isPremium = true
        ),
    )

    @Test
    fun testInitDateIlegalSearchText() = runTest {
        viewModel.initData(
            searchText = "|@#~€",
            musculoSet = setOf(),
            nivelSet = setOf(),
            rutinaListDB = getSetRutina()
        )

        val result = viewModel.rutinaList.first().isEmpty()
        assertTrue(result)
    }

    @Test
    fun testInitDateFilterSearchText() = runTest {
        viewModel.initData(
            searchText = "Carrera",
            musculoSet = setOf(),
            nivelSet = setOf(),
            rutinaListDB = getSetRutina()
        )

        val result = viewModel.rutinaList.first().isNotEmpty()
        assertTrue(result)
    }

    @Test
    fun testInitDateFilterSearchTextAutocomplete() = runTest {
        viewModel.initData(
            searchText = "Flexi",
            musculoSet = setOf(),
            nivelSet = setOf(),
            rutinaListDB = getSetRutina()
        )

        val result = viewModel.rutinaList.first().size == 2
        assertTrue(result)
    }

    @Test
    fun testInitDateFilterSearchTextRE() = runTest {
        viewModel.initData(
            searchText = "*xion*",
            musculoSet = setOf(),
            nivelSet = setOf(),
            rutinaListDB = getSetRutina()
        )

        val result = viewModel.rutinaList.first().size == 2
        assertTrue(result)
    }

    @Test
    fun testInitDateFilterMusculoSet() = runTest {
        viewModel.initData(
            searchText = "",
            musculoSet = setOf(Musculo.PECHO),
            nivelSet = setOf(),
            rutinaListDB = getSetRutina()
        )

        val result = viewModel.rutinaList.first().isNotEmpty()
        assertTrue(result)
    }

    @Test
    fun testInitDateFilterNivelSet() = runTest {
        viewModel.initData(
            searchText = "",
            musculoSet = setOf(),
            nivelSet = setOf(Nivel.FACIL),
            rutinaListDB = getSetRutina()
        )

        val result = viewModel.rutinaList.first().isNotEmpty()
        assertTrue(result)
    }
}