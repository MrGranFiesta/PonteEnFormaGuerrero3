package com.mrgranfiesta.ponteenformaguerrero3.domain.constans

enum class Rol(
    var id: Int
) {
    INIT_DATA_USER(1), PREMIUN_USER(2), STANDAR_USER(3), GUEST(4);

    companion object {
        fun isEditable(rol: Rol?): Boolean = when (rol) {
            INIT_DATA_USER -> false
            PREMIUN_USER -> true
            STANDAR_USER -> true
            GUEST -> false
            null -> false
        }

        fun fromId(id: Int): Rol {
            return Rol.entries.find { it.id == id } ?: STANDAR_USER
        }
    }
}