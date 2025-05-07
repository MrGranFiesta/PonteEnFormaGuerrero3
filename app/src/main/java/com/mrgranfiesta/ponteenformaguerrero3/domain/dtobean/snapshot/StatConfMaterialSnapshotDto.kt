package com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.snapshot

import android.net.Uri
import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class StatConfMaterialSnapshotDto (
    var idConfMaterialSnapshot: Long = 0,
    var idConfMaterial: Long,
    var idMaterialSnapshot: Long,
    var idMaterial: Long,
    var idStepSnapshot: Long,
    var idStep: Long,
    var confValue: Double,
    var nombre : String,
    var isMaterialWeight : Boolean,
    var photoUri : Uri
) : Parcelable