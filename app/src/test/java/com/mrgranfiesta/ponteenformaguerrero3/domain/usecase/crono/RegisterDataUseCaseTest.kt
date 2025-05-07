package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.crono

import android.net.Uri
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.crono.PairIdDB
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.EjercicioEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.MaterialEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.RutinaEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.MaterialRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.RutinaRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.SnapshotCronoRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.AnswerState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.EntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.MaterialEntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.StepEntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.objects.AnswerStateLoading
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class RegisterDataUseCaseTest {
    private lateinit var registerDataUseCase: RegisterDataUseCase

    @RelaxedMockK
    private lateinit var rutinaRepo: RutinaRepository

    @RelaxedMockK
    private lateinit var ejercicioRepo: EjercicioRepository

    @RelaxedMockK
    private lateinit var materialRepo: MaterialRepository

    @RelaxedMockK
    private lateinit var snapshotRepo: SnapshotCronoRepository

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        registerDataUseCase = RegisterDataUseCase(
            rutinaRepo = rutinaRepo,
            ejercicioRepo = ejercicioRepo,
            materialRepo = materialRepo,
            snapshotRepo = snapshotRepo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test registerDataUseCase`() = runTest {
        val entrenamientoDB = EntrenamientoDto(
            idRutina = 0,
            calentamiento = AnswerStateLoading.ASK_LATER,
            estiramiento = AnswerState.ASK_LATER,
            movArticular = AnswerState.ASK_LATER,
            descanso = 0,
            musculoSet = mutableSetOf()
        )
        val listStepDB = listOf(
            StepEntrenamientoDto(
                idEjercicio = 1,
                idStep = 1,
                nombre = "Press Banca",
                photoUri = Uri.EMPTY,
                isSimetria = true,
                cantidad = 1,
                serie = 1,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.CRONO,
                musculoSet = mutableSetOf(Musculo.TRAPECIO)
            )
        )
        val materialDB = listOf(
            MaterialEntrenamientoDto(
                idMaterial = 0,
                idConfMaterial = 0,
                idStep = 0,
                nombre = "Banco",
                photoUri = Uri.EMPTY,
                isMaterialWeight = true,
                confValue = 1.0
            )
        )

        val listEjercicio = listOf(
            EjercicioEntity(
                idEjercicio = 1,
                idUser = 1,
                nombre = "Banco",
                isSimetria = true,
                musculoSet = setOf(Musculo.TRAPECIO),
                nivel = Nivel.MEDIO,
                descripcion = "",
                nameImg = ""
            )
        )

        coEvery { ejercicioRepo.getListByPkList(any()) } returns listEjercicio

        val rutinaEntity = RutinaEntity(
            idRutina = 0,
            idUser = 0,
            nombre = "Banco",
            descripcion = "",
            musculoSet = setOf(Musculo.TRAPECIO),
            nivel = Nivel.MEDIO,
            calentamiento = AnswerState.ASK_LATER,
            estiramiento = AnswerState.ASK_LATER,
            movArticular = AnswerState.ASK_LATER,
            descanso = 0,
            isPremium = true
        )
        coEvery { rutinaRepo.getByPkNoFlow(any()) } returns rutinaEntity

        val listMaterial = listOf(
            MaterialEntity(
                idMaterial = 1,
                idUser = 1,
                nombre = "Banco",
                isMaterialWeight = true,
                descripcion = "",
                nameImg = ""
            )
        )

        coEvery { materialRepo.getListByPkListNoFlow(any()) } returns listMaterial

        val pairEjercicioId = listOf(PairIdDB(1L, 1L))

        coEvery { snapshotRepo.getEjercicioListPairIdSnpashotByPk(any()) } returns pairEjercicioId

        val pairStep = listOf(PairIdDB(1L, 1L))
        val pairMaterial = listOf(PairIdDB(1L, 1L))

        coEvery { snapshotRepo.getStepListPairIdSnpashotByPk(any()) } returns pairStep
        coEvery { snapshotRepo.getMaterialListPairIdSnpashotByPk(any()) } returns pairMaterial

        registerDataUseCase(
            entrenamientoDB = entrenamientoDB,
            stepDB = listStepDB,
            materialDB = materialDB
        )

        coVerify { snapshotRepo.insertEjercicioListSnapshot(any()) }
        coVerify { snapshotRepo.insertRutinaSnapshot(any()) }
        coVerify { snapshotRepo.insertMaterialListSnapshot(any()) }
        coVerify { snapshotRepo.insertStepListSnapshot(any()) }
        coVerify { snapshotRepo.insertConfMaterialListSnapshot(any()) }
        coVerify { snapshotRepo.insertEjercicioMaterialCrossListSnapshot(any()) }
    }
}