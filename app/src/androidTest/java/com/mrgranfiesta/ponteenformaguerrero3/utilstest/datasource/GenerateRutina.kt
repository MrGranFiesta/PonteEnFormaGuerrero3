package com.mrgranfiesta.ponteenformaguerrero3.utilstest.datasource

import com.mrgranfiesta.ponteenformaguerrero3.data.dao.RutinaDao
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.RutinaEntity
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.AnswerState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import javax.inject.Inject

class GenerateRutina @Inject constructor(
    private val dao: RutinaDao
) {
    suspend operator fun invoke() {
        getDefaultEjercicio().forEach { rut ->
            dao.insert(rut)
        }
    }

    private fun getDefaultEjercicio() : List<RutinaEntity> {
        return listOf(
            RutinaEntity(
                idRutina = 1,
                idUser = 1,
                nombre = "Brazos fuertes",
                musculoSet = setOf(Musculo.HOMBRO, Musculo.BICEPS),
                nivel = Nivel.FACIL,
                calentamiento = AnswerState.YES,
                movArticular = AnswerState.NO,
                estiramiento = AnswerState.ASK_LATER,
                descanso = 20,
                descripcion = "Ejemplo 1",
                isPremium = true
            ),
            RutinaEntity(
                idRutina = 2,
                idUser = 1,
                nombre = "Espalda ancha",
                musculoSet = setOf(Musculo.ESPALDA),
                nivel = Nivel.MEDIO,
                calentamiento = AnswerState.ASK_LATER,
                movArticular = AnswerState.ASK_LATER,
                estiramiento = AnswerState.ASK_LATER,
                descanso = 10,
                descripcion = "Ejemplo 2",
                isPremium = false
            ),
            RutinaEntity(
                idRutina = 3,
                idUser = 1,
                nombre = "Pierna completa",
                musculoSet = setOf(Musculo.PIERNA),
                nivel = Nivel.DIFICIL,
                calentamiento = AnswerState.NO,
                movArticular = AnswerState.NO,
                estiramiento = AnswerState.NO,
                descanso = 5,
                descripcion = "Ejemplo 3",
                isPremium = true
            )
        )
    }
}