package com.mrgranfiesta.ponteenformaguerrero3.utilstest.datasource.snapshot

import com.mrgranfiesta.ponteenformaguerrero3.data.dao.snapshot.SnapshotDao
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.RutinaSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import javax.inject.Inject
import kotlin.collections.forEach

class GenerateRutinaSnapshot @Inject constructor(
    private val dao: SnapshotDao
) {
    suspend operator fun invoke() {
        getDefaultRutinaSnapshot().forEach { rut ->
            dao.insertRutinaSnapshot(rut)
        }
    }

    private fun getDefaultRutinaSnapshot() : List<RutinaSnapshotEntity> {
        return listOf(
            RutinaSnapshotEntity(
                idRutina = 1,
                idUser = 1,
                nombre = "Brazos fuertes",
                musculoSet = setOf(Musculo.HOMBRO, Musculo.BICEPS),
                nivel = Nivel.FACIL,
                descanso = 20,
                descripcion = "Ejemplo 1",
                isPremium = true
            ),
            RutinaSnapshotEntity(
                idRutina = 2,
                idUser = 1,
                nombre = "Espalda ancha",
                musculoSet = setOf(Musculo.ESPALDA),
                nivel = Nivel.MEDIO,
                descanso = 10,
                descripcion = "Ejemplo 2",
                isPremium = false
            ),
            RutinaSnapshotEntity(
                idRutina = 3,
                idUser = 1,
                nombre = "Pierna completa",
                musculoSet = setOf(Musculo.PIERNA),
                nivel = Nivel.DIFICIL,
                descanso = 5,
                descripcion = "Ejemplo 3",
                isPremium = true
            )
        )
    }
}