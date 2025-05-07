package com.mrgranfiesta.ponteenformaguerrero3.utilstest.datasource.snapshot

import com.mrgranfiesta.ponteenformaguerrero3.data.dao.snapshot.SnapshotDao
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.EjercicioSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import javax.inject.Inject

class GenerateEjercicioSnapshot @Inject constructor(
    private val dao: SnapshotDao
) {
    suspend operator fun invoke() {
        dao.insertEjercicioListSnapshot(getDefaultEjercicioSnapshot())
    }

    private fun getDefaultEjercicioSnapshot(): List<EjercicioSnapshotEntity> {
        return listOf(
            EjercicioSnapshotEntity(
                idEjercicioSnapshot = 1,
                idEjercicio = 1,
                idUser = 1,
                nombre = "Flexiones",
                isSimetria = true,
                musculoSet = setOf(Musculo.HOMBRO, Musculo.BICEPS),
                nivel = Nivel.FACIL,
                descripcion = "Ejemplo 1",
                nameImg = ""
            ),
            EjercicioSnapshotEntity(
                idEjercicioSnapshot = 2,
                idEjercicio = 2,
                idUser = 2,
                nombre = "Abdominales",
                isSimetria = true,
                musculoSet = setOf(Musculo.ABDOMINALES),
                nivel = Nivel.FACIL,
                descripcion = "Ejemplo 2",
                nameImg = "ejer_200_000"
            ),
            EjercicioSnapshotEntity(
                idEjercicioSnapshot = 3,
                idEjercicio = 3,
                idUser = 3,
                nombre = "Salto a la comba",
                isSimetria = true,
                musculoSet = setOf(Musculo.PIERNA),
                nivel = Nivel.MEDIO,
                descripcion = "Ejemplo 3",
                nameImg = ""
            )
        )
    }
}