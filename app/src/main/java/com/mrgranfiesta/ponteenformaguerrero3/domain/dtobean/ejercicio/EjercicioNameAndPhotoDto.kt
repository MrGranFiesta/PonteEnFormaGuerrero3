package com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EjercicioNameAndPhotoDto(
    var idEjercicio: Long = 0,
    var nombre: String,
    var photoUri : Uri
) : Parcelable
