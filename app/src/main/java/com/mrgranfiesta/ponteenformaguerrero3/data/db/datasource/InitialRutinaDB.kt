package com.mrgranfiesta.ponteenformaguerrero3.data.db.datasource

import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.RutinaBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.AnswerState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel

class InitialRutinaDB {
    companion object {
        fun getListDataDefaultRutina(): List<RutinaBean> {
            return listOf(
                RutinaBean(
                    idRutina = 2_000_000,
                    idUser = 1,
                    nombre = "Flexiones basicas",
                    descripcion = "Flexiones a un nivel basico.",
                    musculoSet = mutableSetOf(Musculo.TRICEPS, Musculo.PECHO),
                    nivel = Nivel.FACIL,
                    calentamiento = AnswerState.YES,
                    movArticular = AnswerState.YES,
                    estiramiento = AnswerState.YES,
                    descanso = 75,
                    isPremium = false
                ),
                RutinaBean(
                    idRutina = 1_999_999,
                    idUser = 1,
                    nombre = "Flexiones media",
                    descripcion = "Flexiones a un nivel medio.",
                    musculoSet = mutableSetOf(
                        Musculo.TRICEPS,
                        Musculo.PECHO,
                        Musculo.PECHO,
                        Musculo.HOMBRO
                    ),
                    nivel = Nivel.MEDIO,
                    calentamiento = AnswerState.YES,
                    movArticular = AnswerState.YES,
                    estiramiento = AnswerState.YES,
                    descanso = 75,
                    isPremium = false
                ),
                RutinaBean(
                    idRutina = 1_999_998,
                    idUser = 1,
                    nombre = "Flexiones dificiles",
                    descripcion = "Flexiones a un nivel dificil.",
                    musculoSet = mutableSetOf(Musculo.TRICEPS, Musculo.PECHO, Musculo.HOMBRO),
                    nivel = Nivel.DIFICIL,
                    calentamiento = AnswerState.YES,
                    movArticular = AnswerState.YES,
                    estiramiento = AnswerState.YES,
                    descanso = 75,
                    isPremium = true
                ),
                RutinaBean(
                    idRutina = 1_999_997,
                    idUser = 1,
                    nombre = "Abdominales basicas",
                    descripcion = "Abdominales a un nivel basico.",
                    musculoSet = mutableSetOf(Musculo.ABDOMINALES, Musculo.ABDOMINALES),
                    nivel = Nivel.FACIL,
                    calentamiento = AnswerState.YES,
                    movArticular = AnswerState.YES,
                    estiramiento = AnswerState.YES,
                    descanso = 75,
                    isPremium = false
                ),
                RutinaBean(
                    idRutina = 1_999_996,
                    idUser = 1,
                    nombre = "Abdominales media",
                    descripcion = "Abdominales a un nivel medio.",
                    musculoSet = mutableSetOf(Musculo.ABDOMINALES, Musculo.ABDOMINALES),
                    nivel = Nivel.MEDIO,
                    calentamiento = AnswerState.YES,
                    movArticular = AnswerState.YES,
                    estiramiento = AnswerState.YES,
                    descanso = 75,
                    isPremium = false
                ),
                RutinaBean(
                    idRutina = 1_999_995,
                    idUser = 1,
                    nombre = "Abdominales dificiles",
                    descripcion = "Abdominales a un nivel dificil.",
                    musculoSet = mutableSetOf(Musculo.ABDOMINALES, Musculo.ABDOMINALES),
                    nivel = Nivel.DIFICIL,
                    calentamiento = AnswerState.YES,
                    movArticular = AnswerState.YES,
                    estiramiento = AnswerState.YES,
                    descanso = 75,
                    isPremium = true
                ),
                RutinaBean(
                    idRutina = 1_999_994,
                    idUser = 1,
                    nombre = "Pierna basicas",
                    descripcion = "Pierna a un nivel basico.",
                    musculoSet = mutableSetOf(Musculo.PIERNA),
                    nivel = Nivel.FACIL,
                    calentamiento = AnswerState.YES,
                    movArticular = AnswerState.YES,
                    estiramiento = AnswerState.YES,
                    descanso = 75,
                    isPremium = false
                ),
                RutinaBean(
                    idRutina = 1_999_993,
                    idUser = 1,
                    nombre = "Pierna media",
                    descripcion = "Pierna a un nivel medio.",
                    musculoSet = mutableSetOf(Musculo.PIERNA),
                    nivel = Nivel.MEDIO,
                    calentamiento = AnswerState.YES,
                    movArticular = AnswerState.YES,
                    estiramiento = AnswerState.YES,
                    descanso = 75,
                    isPremium = false
                ),
                RutinaBean(
                    idRutina = 1_999_992,
                    idUser = 1,
                    nombre = "Pierna dificiles",
                    descripcion = "Pierna a un nivel dificil.",
                    musculoSet = mutableSetOf(Musculo.PIERNA),
                    nivel = Nivel.DIFICIL,
                    calentamiento = AnswerState.YES,
                    movArticular = AnswerState.YES,
                    estiramiento = AnswerState.YES,
                    descanso = 75,
                    isPremium = true
                ),
                RutinaBean(
                    idRutina = 1_999_991,
                    idUser = 1,
                    nombre = "Dominadas facil",
                    descripcion = "Ejercicio de iniciación de ejercicios con barra de dominadas.",
                    musculoSet = mutableSetOf(
                        Musculo.BICEPS, Musculo.ESPALDA, Musculo.PECHO,
                        Musculo.TRICEPS, Musculo.ANTEBRAZO
                    ),
                    nivel = Nivel.FACIL,
                    calentamiento = AnswerState.YES,
                    movArticular = AnswerState.YES,
                    estiramiento = AnswerState.YES,
                    descanso = 75,
                    isPremium = false
                ),
                RutinaBean(
                    idRutina = 1_999_990,
                    idUser = 1,
                    nombre = "Muscle Up",
                    descripcion = "Rutina para practicar y hacer Muscle Up.",
                    musculoSet = mutableSetOf(
                        Musculo.BICEPS, Musculo.ESPALDA, Musculo.PECHO,
                        Musculo.TRICEPS
                    ),
                    nivel = Nivel.MEDIO,
                    calentamiento = AnswerState.YES,
                    movArticular = AnswerState.YES,
                    estiramiento = AnswerState.YES,
                    descanso = 75,
                    isPremium = false
                ),
                RutinaBean(
                    idRutina = 1_999_989,
                    idUser = 1,
                    nombre = "Preparación para bandera",
                    descripcion = "Rutina para prepararse para el ejercicio de bandera.",
                    musculoSet = mutableSetOf(
                        Musculo.BICEPS, Musculo.ESPALDA, Musculo.PECHO,
                        Musculo.TRICEPS, Musculo.ABDOMINALES, Musculo.OBLICUOS,
                        Musculo.TRAPECIO, Musculo.ESPALDA
                    ),
                    nivel = Nivel.MEDIO,
                    calentamiento = AnswerState.YES,
                    movArticular = AnswerState.YES,
                    estiramiento = AnswerState.YES,
                    descanso = 75,
                    isPremium = false
                ),
                RutinaBean(
                    idRutina = 1_999_988,
                    idUser = 1,
                    nombre = "Bandera",
                    descripcion = "Ejercicio complicado, se necesita una preparación previa. Este ejercicio entrena prácticamente todas las partes del cuerpo, así que se necesita estar preparado prácticamente de cuerpo completo.",
                    musculoSet = mutableSetOf(
                        Musculo.BICEPS, Musculo.ESPALDA, Musculo.PECHO,
                        Musculo.TRICEPS, Musculo.ABDOMINALES, Musculo.OBLICUOS,
                        Musculo.TRAPECIO, Musculo.ESPALDA
                    ),
                    nivel = Nivel.DIFICIL,
                    calentamiento = AnswerState.YES,
                    movArticular = AnswerState.YES,
                    estiramiento = AnswerState.YES,
                    descanso = 75,
                    isPremium = true
                ),
                RutinaBean(
                    idRutina = 1_999_987,
                    idUser = 1,
                    nombre = "Dominadas media",
                    descripcion = "Multiples ejercicio para entrenar todo el cuerpo con ayuda de barras de dominadas",
                    musculoSet = mutableSetOf(
                        Musculo.BICEPS, Musculo.ESPALDA, Musculo.PECHO,
                        Musculo.TRICEPS, Musculo.ABDOMINALES, Musculo.OBLICUOS,
                        Musculo.TRAPECIO, Musculo.ESPALDA, Musculo.PIERNA
                    ),
                    nivel = Nivel.MEDIO,
                    calentamiento = AnswerState.YES,
                    movArticular = AnswerState.YES,
                    estiramiento = AnswerState.YES,
                    descanso = 75,
                    isPremium = false
                ),
                RutinaBean(
                    idRutina = 1_999_986,
                    idUser = 1,
                    nombre = "Dominadas dificiles",
                    descripcion = "Multiples ejercicio para entrenar todo el cuerpo a un nivel dificil con ayuda de barras de dominadas.",
                    musculoSet = mutableSetOf(
                        Musculo.BICEPS, Musculo.ESPALDA, Musculo.PECHO,
                        Musculo.TRICEPS, Musculo.ABDOMINALES, Musculo.OBLICUOS,
                        Musculo.TRAPECIO, Musculo.ESPALDA, Musculo.PIERNA
                    ),
                    nivel = Nivel.DIFICIL,
                    calentamiento = AnswerState.YES,
                    movArticular = AnswerState.YES,
                    estiramiento = AnswerState.YES,
                    descanso = 75,
                    isPremium = true
                )
            )
        }
    }
}