package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.stat

import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.stat.StatRutinaSearchDB
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.StatRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.stat.StatRutinaSearchDto
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.time.LocalDateTime

@RunWith(RobolectricTestRunner::class)
class GetStatRutinaSearchUseCaseTest {
    @RelaxedMockK
    private lateinit var repo: StatRepository

    private lateinit var getStatRutinaSearchUseCase: GetStatRutinaSearchUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getStatRutinaSearchUseCase = GetStatRutinaSearchUseCase(
            repo = repo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test getStatRutinaSearchUseCase`() = runTest {
        val listStatRutinaSearchDB = listOf(
            StatRutinaSearchDB(
                idStat = 1,
                idRutinaSnapshot = 1,
                idRutina = 1,
                dateInit = LocalDateTime.of(2023, 1, 1, 10, 0, 0),
                dateEnd = LocalDateTime.of(2023, 1, 1, 10, 0, 0),
                isCompleted = true,
                nombre = "Autocargas",
                nivel = Nivel.MEDIO,
                musculoSet = setOf(Musculo.TRAPECIO),
                isPremium = true,
                rol = Rol.INIT_DATA_USER
            )
        )

        val listStatRutinaSearchDtoSolution = listOf(
            StatRutinaSearchDto(
                idStat = 1,
                idRutinaSnapshot = 1,
                idRutina = 1,
                dateInit = LocalDateTime.of(2023, 1, 1, 10, 0, 0),
                dateEnd = LocalDateTime.of(2023, 1, 1, 10, 0, 0),
                isCompleted = true,
                nombre = "Autocargas",
                nivel = Nivel.MEDIO,
                musculoSet = setOf(Musculo.TRAPECIO),
                isPremium = true
            )
        )
        coEvery { repo.getAllStatRutina(any()) } returns flowOf(listStatRutinaSearchDB)

        val listStatRutinaSearchDtoResult = getStatRutinaSearchUseCase().first()

        assertEquals(listStatRutinaSearchDtoSolution, listStatRutinaSearchDtoResult)
    }
}