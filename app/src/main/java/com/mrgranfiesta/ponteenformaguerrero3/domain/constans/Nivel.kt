package com.mrgranfiesta.ponteenformaguerrero3.domain.constans

enum class Nivel(
    val id : Int
) {
    NINGUNO(0),
    FACIL(1),
    MEDIO(2),
    DIFICIL(3);

    companion object {
        fun fromId(id: Int): Nivel {
            return entries.find { it.id == id } ?: NINGUNO
        }
    }

    override fun toString(): String {
        return when (this) {
            FACIL -> "FÁCIL"
            DIFICIL -> "DIFÍCIL"
            else -> name.uppercase()
        }
    }
}