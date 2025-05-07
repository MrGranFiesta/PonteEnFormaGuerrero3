package com.mrgranfiesta.ponteenformaguerrero3.domain.constans

enum class TipoEsfuerzo(
    val id : Int,
    val unidad: String,
    val abreviatura: String
) {
    REPETICION(id = 0, unidad = "Repeticiones", abreviatura = "reps"),
    CRONO(id = 1, unidad = "Segundos", abreviatura = "seg");

    companion object {
        fun fromId(id: Int): TipoEsfuerzo {
            return TipoEsfuerzo.entries.find { it.id == id } ?: REPETICION
        }
    }
}