package com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RowMaterialWithConfDto(
    var photoUri : Uri,
    var nombre : String,
    var isMaterialWeight : Boolean,
    var confValue : Double
) : Parcelable
