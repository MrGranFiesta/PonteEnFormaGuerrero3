package com.mrgranfiesta.ponteenformaguerrero3.utilstest.datasource

import com.mrgranfiesta.ponteenformaguerrero3.data.dao.StepDao
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.StepEntity
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import javax.inject.Inject

class GenerateStep @Inject constructor(
    private val dao: StepDao
) {
    suspend operator fun invoke() {
        getDefaultEjercicio().forEach { step ->
            dao.insert(step)
        }
    }

    private fun getDefaultEjercicio(): List<StepEntity> {
        return listOf(
            StepEntity(
                idStep = 1,
                idEjercicio = 1,
                idRutina = 1,
                cantidad = 5,
                serie = 1,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.REPETICION
            ),
            StepEntity(
                idStep = 2,
                idEjercicio = 2,
                idRutina = 2,
                cantidad = 10,
                serie = 2,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.CRONO
            ),
            StepEntity(
                idStep = 3,
                idEjercicio = 3,
                idRutina = 3,
                cantidad = 7,
                serie = 3,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.REPETICION
            ),
            StepEntity(
                idStep = 4,
                idEjercicio = 1,
                idRutina = 1,
                cantidad = 5,
                serie = 1,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.REPETICION
            ),
            StepEntity(
                idStep = 5,
                idEjercicio = 1,
                idRutina = 1,
                cantidad = 5,
                serie = 1,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.REPETICION
            )
        )
    }
}