package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.stat.StatRutinaSearchDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.objects.OptionalLocalDate
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.stat.GetStatRutinaSearchUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class StatListVMTest {
    lateinit var viewModel: StatListVM

    @RelaxedMockK
    private lateinit var getStatRutinaSearchUseCase: GetStatRutinaSearchUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = StatListVM(
            getStatRutinaSearchUseCase = getStatRutinaSearchUseCase
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    private fun getSetStat() = mutableSetOf(
        StatRutinaSearchDto(
            idStat = 1,
            idRutinaSnapshot = 1,
            idRutina = 1,
            dateInit = LocalDateTime.of(2023, 1, 1, 10, 0, 0),
            dateEnd = LocalDateTime.of(2023, 1, 1, 12, 0, 0),
            isCompleted = true,
            nombre = "Flexiones normal",
            nivel = Nivel.MEDIO,
            musculoSet = setOf(Musculo.PECHO, Musculo.HOMBRO),
            isPremium = true
        ),
        StatRutinaSearchDto(
            idStat = 2,
            idRutinaSnapshot = 2,
            idRutina = 2,
            dateInit = LocalDateTime.of(2023, 10, 2, 10, 0, 0),
            dateEnd = LocalDateTime.of(2023, 10, 2, 12, 0, 0),
            isCompleted = true,
            nombre = "Flexiones en diamante",
            nivel = Nivel.DIFICIL,
            musculoSet = setOf(Musculo.ABDOMINALES, Musculo.ESPALDA),
            isPremium = true
        ),
        StatRutinaSearchDto(
            idStat = 3,
            idRutinaSnapshot = 3,
            idRutina = 3,
            dateInit = LocalDateTime.of(2023, 11, 12, 15, 0, 0),
            dateEnd = LocalDateTime.of(2023, 11, 12, 17, 0, 0),
            isCompleted = true,
            nombre = "Carrera",
            nivel = Nivel.FACIL,
            musculoSet = setOf(Musculo.PIERNA),
            isPremium = true
        ),
        StatRutinaSearchDto(
            idStat = 4,
            idRutinaSnapshot = 4,
            idRutina = 4,
            dateInit = LocalDateTime.of(2023, 3, 12, 18, 0, 0),
            dateEnd = LocalDateTime.of(2023, 3, 12, 20, 0, 0),
            isCompleted = true,
            nombre = "Saltos",
            nivel = Nivel.NINGUNO,
            musculoSet = setOf(Musculo.TRICEPS),
            isPremium = true
        ),
        StatRutinaSearchDto(
            idStat = 5,
            idRutinaSnapshot = 5,
            idRutina = 5,
            dateInit = LocalDateTime.of(2023, 11, 11, 10, 0, 0),
            dateEnd = LocalDateTime.of(2023, 11, 11, 12, 0, 0),
            isCompleted = true,
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
            rangeDate = Pair(OptionalLocalDate.Empty, OptionalLocalDate.Empty),
            statListDB = getSetStat()
        )

        val result = viewModel.statList.first().isEmpty()
        assertTrue(result)
    }

    @Test
    fun testInitDateFilterSearchText() = runTest {
        viewModel.initData(
            searchText = "Carrera",
            musculoSet = setOf(),
            nivelSet = setOf(),
            rangeDate = Pair(OptionalLocalDate.Empty, OptionalLocalDate.Empty),
            statListDB = getSetStat()
        )

        val result = viewModel.statList.first().isNotEmpty()
        assertTrue(result)
    }

    @Test
    fun testInitDateFilterSearchTextAutocomplete() = runTest {
        viewModel.initData(
            searchText = "Flexi",
            musculoSet = setOf(),
            nivelSet = setOf(),
            rangeDate = Pair(OptionalLocalDate.Empty, OptionalLocalDate.Empty),
            statListDB = getSetStat()
        )

        val result = viewModel.statList.first().size == 2
        assertTrue(result)
    }

    @Test
    fun testInitDateFilterSearchTextRE() = runTest {
        viewModel.initData(
            searchText = "*xion*",
            musculoSet = setOf(),
            nivelSet = setOf(),
            rangeDate = Pair(OptionalLocalDate.Empty, OptionalLocalDate.Empty),
            statListDB = getSetStat()
        )

        val result = viewModel.statList.first().size == 2
        assertTrue(result)
    }

    @Test
    fun testInitDateFilterMusculoSet() = runTest {
        viewModel.initData(
            searchText = "",
            musculoSet = setOf(Musculo.PECHO),
            nivelSet = setOf(),
            rangeDate = Pair(OptionalLocalDate.Empty, OptionalLocalDate.Empty),
            statListDB = getSetStat()
        )

        val result = viewModel.statList.first().isNotEmpty()
        assertTrue(result)
    }

    @Test
    fun testInitDateFilterNivelSet() = runTest {
        viewModel.initData(
            searchText = "",
            musculoSet = setOf(),
            nivelSet = setOf(Nivel.FACIL),
            rangeDate = Pair(OptionalLocalDate.Empty, OptionalLocalDate.Empty),
            statListDB = getSetStat()
        )

        val result = viewModel.statList.first().isNotEmpty()
        assertTrue(result)
    }

    @Test
    fun testInitDateFilterBeforeDate() = runTest {
        viewModel.initData(
            searchText = "",
            musculoSet = setOf(),
            nivelSet = setOf(Nivel.FACIL),
            rangeDate = Pair(
                OptionalLocalDate.getOptionalByString(
                    format = "ddMMyyyy",
                    date = "11062022"
                ), OptionalLocalDate.Empty
            ),
            statListDB = getSetStat()
        )

        val result = viewModel.statList.first().isNotEmpty()
        assertTrue(result)
    }

    @Test
    fun testInitDateFilterAfterDate() = runTest {
        viewModel.initData(
            searchText = "",
            musculoSet = setOf(),
            nivelSet = setOf(Nivel.FACIL),
            rangeDate = Pair(
                OptionalLocalDate.getOptionalByString(
                    format = "ddMMyyyy",
                    date = "11062022"
                ), OptionalLocalDate.Empty
            ),
            statListDB = getSetStat()
        )

        val result = viewModel.statList.first().isNotEmpty()
        assertTrue(result)
    }

    @Test
    fun testInitDateFilterBetweenDate() = runTest {
        viewModel.initData(
            searchText = "",
            musculoSet = setOf(),
            nivelSet = setOf(Nivel.FACIL),
            rangeDate = Pair(
                OptionalLocalDate.getOptionalByString(
                    format = "ddMMyyyy",
                    date = "11062022"
                ),
                OptionalLocalDate.getOptionalByString(
                    format = "ddMMyyyy",
                    date = "11062024"
                )
            ),
            statListDB = getSetStat()
        )

        val result = viewModel.statList.first().isNotEmpty()
        assertTrue(result)
    }
}