package com.mrgranfiesta.ponteenformaguerrero3.utilstest.datasource

import com.mrgranfiesta.ponteenformaguerrero3.data.dao.EjercicioDao
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.EjercicioEntity
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import javax.inject.Inject

class GenerateEjercicio @Inject constructor(
    private val dao: EjercicioDao
) {
    suspend operator fun invoke() {
        getDefaultEjercicio().forEach { ejer ->
            dao.insert(ejer)
        }
    }

    private fun getDefaultEjercicio() : List<EjercicioEntity> {
        return listOf(
            EjercicioEntity(
                idEjercicio = 1,
                idUser = 1,
                nombre = "Flexiones",
                isSimetria = true,
                musculoSet = setOf(Musculo.HOMBRO, Musculo.BICEPS),
                nivel = Nivel.FACIL,
                descripcion = "Ejemplo 1",
                nameImg = ""
            ),
            EjercicioEntity(
                idEjercicio = 2,
                idUser = 2,
                nombre = "Abdominales",
                isSimetria = true,
                musculoSet = setOf(Musculo.ABDOMINALES),
                nivel = Nivel.FACIL,
                descripcion = "Ejemplo 2",
                nameImg = "ejer_200_000"
            ),
            EjercicioEntity(
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