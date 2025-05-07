package com.mrgranfiesta.ponteenformaguerrero3.utilstest.datasource.snapshot

import com.mrgranfiesta.ponteenformaguerrero3.data.dao.snapshot.SnapshotDao
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.StepSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import javax.inject.Inject

class GenerateStepSnapshot @Inject constructor(
    private val dao: SnapshotDao
) {
    suspend operator fun invoke() {
        dao.insertStepSnapshot(getDefaultStepSnapshot())
    }

    private fun getDefaultStepSnapshot(): List<StepSnapshotEntity> {
        return listOf(
            StepSnapshotEntity(
                idStepSnapshot = 1,
                idStep = 1,
                idEjercicioSnapshot = 1,
                idRutinaSnapshot = 1,
                cantidad = 5,
                serie = 1,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.REPETICION
            ),
            StepSnapshotEntity(
                idStepSnapshot = 2,
                idStep = 2,
                idEjercicioSnapshot = 2,
                idRutinaSnapshot = 2,
                cantidad = 10,
                serie = 2,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.CRONO
            ),
            StepSnapshotEntity(
                idStepSnapshot = 3,
                idStep = 3,
                idEjercicioSnapshot = 3,
                idRutinaSnapshot = 3,
                cantidad = 7,
                serie = 3,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.REPETICION
            ),
            StepSnapshotEntity(
                idStepSnapshot = 4,
                idStep = 4,
                idEjercicioSnapshot = 1,
                idRutinaSnapshot = 1,
                cantidad = 5,
                serie = 1,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.REPETICION
            ),
            StepSnapshotEntity(
                idStepSnapshot = 5,
                idStep = 5,
                idEjercicioSnapshot = 1,
                idRutinaSnapshot = 1,
                cantidad = 5,
                serie = 1,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.REPETICION
            )
        )
    }
}