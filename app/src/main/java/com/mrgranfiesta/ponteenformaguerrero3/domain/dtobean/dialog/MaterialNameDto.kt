package com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.dialog

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
class MaterialNameDto(
    var idMaterial: Long,
    var idConfMaterial: Long,
    var nombre: String,
    var isMaterialWeight: Boolean,
    var confValue: Double = 0.0
) : Parcelable