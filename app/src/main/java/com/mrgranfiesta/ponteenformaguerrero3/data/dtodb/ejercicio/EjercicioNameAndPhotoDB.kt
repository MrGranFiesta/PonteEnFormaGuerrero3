package com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.ejercicio

import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import kotlinx.parcelize.Parcelize

@Parcelize
data class EjercicioNameAndPhotoDB(
    var idEjercicio: Long,
    var nombre: String,
    var nameImg: String,
    var rol : Rol
) : Parcelable