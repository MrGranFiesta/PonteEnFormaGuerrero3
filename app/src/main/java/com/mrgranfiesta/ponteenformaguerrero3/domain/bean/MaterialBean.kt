package com.mrgranfiesta.ponteenformaguerrero3.domain.bean

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MaterialBean(
    var idMaterial: Long,
    var idUser: Long,
    var nombre: String,
    var isMaterialWeight: Boolean,
    var descripcion: String,
    var photoUri: Uri,
    var confValue: Double
) : Parcelable
