package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.snapshot.step

import android.net.Uri
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.snapshot.step.MaterialWithConfSnapshotStepDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.snapshot.step.StepSnapshotInfoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.StepSnapshotRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.snapshot.StatConfMaterialSnapshotDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.snapshot.StepSnapshotInfoDto
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GetStepSnapshotUseCaseTest {
    @RelaxedMockK
    private lateinit var repo: StepSnapshotRepository

    private lateinit var getStepSnapshotUseCase : GetStepSnapshotUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getStepSnapshotUseCase = GetStepSnapshotUseCase(
            repo = repo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test getStepSnapshotUseCase`() = runTest {
        val confMaterialListDB = listOf(
            MaterialWithConfSnapshotStepDB(
                idMaterial = 1,
                idMaterialSnapshot = 1,
                idConfMaterial = 1,
                idConfMaterialSnapshot = 1,
                idStep = 1,
                idStepSnapshot = 1,
                nombre = "Banco",
                nameImg = "",
                isMaterialWeight = true,
                confValue = 1.0,
                rol = Rol.INIT_DATA_USER
            )
        )

        val listStepSnapshotInfoDB = listOf(
            StepSnapshotInfoDB(
                idStep = 1,
                idStepSnapshot = 1,
                idEjercicio = 1,
                idEjercicioSnapshot = 1,
                idRutinaSnapshot = 1,
                cantidad = 20,
                serie = 5,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.CRONO,
                rol = Rol.INIT_DATA_USER,
                musculoSet = setOf(Musculo.TRAPECIO),
                confMaterialList = confMaterialListDB
            )
        )

        val listStatConfMaterialSnapshotDtoSolution = listOf(
            StatConfMaterialSnapshotDto(
                idMaterial = 1,
                idMaterialSnapshot = 1,
                idConfMaterial = 1,
                idConfMaterialSnapshot = 1,
                idStep = 1,
                idStepSnapshot = 1,
                nombre = "Banco",
                isMaterialWeight = true,
                confValue = 1.0,
                photoUri = Uri.EMPTY
            )
        )

        val listStepSnapshotInfoDtoSolution = listOf(
            StepSnapshotInfoDto(
                idStep = 1,
                idStepSnapshot = 1,
                idEjercicio = 1,
                idEjercicioSnapshot = 1,
                idRutinaSnapshot = 1,
                cantidad = 20,
                serie = 5,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.CRONO,
                musculoSet = setOf(Musculo.TRAPECIO),
                confMaterialList = listStatConfMaterialSnapshotDtoSolution
            )
        )

        coEvery { repo.getStepSnapshotInfo(any()) } returns flowOf(listStepSnapshotInfoDB)

        val listStepSnapshotInfoDtoResult = getStepSnapshotUseCase(1).first()

        assertEquals(listStepSnapshotInfoDtoSolution, listStepSnapshotInfoDtoResult)
    }
}