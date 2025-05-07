package com.mrgranfiesta.ponteenformaguerrero3.domain.constans

enum class Musculo {
    ANTEBRAZO,
    HOMBRO,
    PECHO,
    BICEPS,
    TRICEPS,
    ABDOMINALES,
    OBLICUOS,
    ESPALDA,
    TRAPECIO,
    PIERNA,
    GLUTEOS;

    override fun toString(): String {
        return when (this) {
            BICEPS -> "BÍCEPS"
            TRICEPS -> "TRÍCEPS"
            else -> name.uppercase()
        }
    }
}