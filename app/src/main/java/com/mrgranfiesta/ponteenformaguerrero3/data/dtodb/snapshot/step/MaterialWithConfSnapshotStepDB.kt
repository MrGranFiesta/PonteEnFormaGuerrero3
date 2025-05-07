package com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.snapshot.step

import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import kotlinx.parcelize.Parcelize

@Parcelize
data class MaterialWithConfSnapshotStepDB(
    var idConfMaterialSnapshot: Long = 0,
    var idConfMaterial: Long,
    var idMaterialSnapshot: Long,
    var idMaterial: Long,
    var idStepSnapshot: Long,
    var idStep: Long,
    var confValue: Double,
    var nombre : String,
    var isMaterialWeight: Boolean,
    var nameImg: String,
    var rol: Rol
): Parcelable