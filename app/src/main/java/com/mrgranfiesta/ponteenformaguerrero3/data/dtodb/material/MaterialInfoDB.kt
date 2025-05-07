package com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.material

import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import kotlinx.parcelize.Parcelize

@Parcelize
data class MaterialInfoDB(
    var idMaterial: Long = 0,
    var idUser: Long,
    var nombre: String,
    var isMaterialWeight: Boolean,
    var descripcion: String,
    var nameImg: String,
    var rol: Rol
) : Parcelable