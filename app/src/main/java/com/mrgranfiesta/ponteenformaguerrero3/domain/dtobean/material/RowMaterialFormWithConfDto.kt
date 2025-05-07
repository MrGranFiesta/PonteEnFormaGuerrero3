package com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RowMaterialFormWithConfDto(
    var idMaterial : Long,
    var idConfMaterial : Long,
    var idStep : Long,
    var nombre : String,
    var isMaterialWeight : Boolean,
    var confValue : Double
) : Parcelable
