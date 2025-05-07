package com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MaterialEntrenamientoDto(
    var idMaterial: Long,
    var idConfMaterial: Long = 0,
    var idStep: Long,
    var nombre: String,
    var photoUri: Uri,
    var isMaterialWeight: Boolean,
    var confValue: Double
) : Parcelable
