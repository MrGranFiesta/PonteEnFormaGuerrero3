package com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.crono

import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import kotlinx.parcelize.Parcelize

@Parcelize
data class MaterialEntrenamientoDB(
    var idMaterial: Long,
    var idConfMaterial: Long = 0,
    var idStep: Long,
    var nombre: String,
    var nameImg: String,
    var isMaterialWeight: Boolean,
    var confValue: Double,
    var rol: Rol
) : Parcelable
