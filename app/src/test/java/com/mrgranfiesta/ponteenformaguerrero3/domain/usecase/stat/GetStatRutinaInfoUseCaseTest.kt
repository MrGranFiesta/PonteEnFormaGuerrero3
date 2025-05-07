package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.stat

import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.stat.StatRutinaInfoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.StatRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.stat.StatRutinaInfoDto
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
import java.time.LocalDateTime

class GetStatRutinaInfoUseCaseTest {
    @RelaxedMockK
    private lateinit var repo: StatRepository

    private lateinit var getStatRutinaInfoUseCase: GetStatRutinaInfoUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getStatRutinaInfoUseCase = GetStatRutinaInfoUseCase(
            repo = repo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test getStatRutinaInfoUseCase`() = runTest {
        val statRutinaInfoDB = StatRutinaInfoDB(
            idStat = 1,
            idRutinaSnapshot = 1,
            idRutina = 1,
            dateInit = LocalDateTime.of(2023, 1, 1, 10, 0, 0),
            dateEnd = LocalDateTime.of(2023, 1, 1, 10, 0, 0),
            isCalentamientoDone = true,
            isMovArticularDone = true,
            isEstiramientoDone = true,
            isCompleted = true,
            nombre = "Autocargas",
            nivel = Nivel.MEDIO,
            musculoSet = setOf(Musculo.TRAPECIO),
            descripcion = "",
            rol = Rol.INIT_DATA_USER
        )
        coEvery { repo.getByPkStatRutina(any()) } returns flowOf(statRutinaInfoDB)

        val statRutinaInfoDtoSolution = StatRutinaInfoDto(
            idStat = 1,
            idRutinaSnapshot = 1,
            idRutina = 1,
            dateInit = LocalDateTime.of(2023, 1, 1, 10, 0, 0),
            dateEnd = LocalDateTime.of(2023, 1, 1, 10, 0, 0),
            isCalentamientoDone = true,
            isMovArticularDone = true,
            isEstiramientoDone = true,
            isCompleted = true,
            nombre = "Autocargas",
            nivel = Nivel.MEDIO,
            musculoSet = setOf(Musculo.TRAPECIO),
            descripcion = "",
        )
        val statRutinaInfoDtoResult = getStatRutinaInfoUseCase(1).first()

        assertEquals(statRutinaInfoDtoSolution, statRutinaInfoDtoResult)
    }
}