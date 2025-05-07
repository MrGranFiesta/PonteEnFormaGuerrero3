package com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.ejercicio

import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import kotlinx.parcelize.Parcelize

@Parcelize
data class RowEjercicioDB (
    var idEjercicio : Long,
    var nombre: String,
    var musculoSet: Set<Musculo>,
    var nivel: Nivel,
    var nameImg: String,
    var rol : Rol
) : Parcelable