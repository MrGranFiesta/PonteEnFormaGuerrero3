package com.mrgranfiesta.ponteenformaguerrero3.domain.bean

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConfMaterialBean(
    var idConfMaterial: Long,
    var idMaterial: Long,
    var idStep: Long,
    var confValue: Double,
    var nombre: String,
    var isMaterialWeight: Boolean,
    var photoUri : Uri
) : Parcelable
