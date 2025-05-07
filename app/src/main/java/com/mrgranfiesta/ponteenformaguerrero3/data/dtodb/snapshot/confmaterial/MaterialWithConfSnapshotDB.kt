package com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.snapshot.confmaterial

import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import kotlinx.parcelize.Parcelize

@Parcelize
data class MaterialWithConfSnapshotDB(
    var idConfMaterialSnapshot: Long = 0,
    var idConfMaterial: Long,
    var idMaterialSnapshot: Long,
    var idMaterial: Long,
    var idEjercicioSnapshot: Long,
    var idEjercicio: Long,
    var confValue: Double,
    var nombre : String,
    var isMaterialWeight: Boolean,
    var nameImg: String,
    var rol: Rol
): Parcelable