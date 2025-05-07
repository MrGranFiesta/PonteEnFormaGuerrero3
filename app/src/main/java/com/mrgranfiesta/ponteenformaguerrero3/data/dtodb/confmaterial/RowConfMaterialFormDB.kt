package com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.confmaterial

import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import kotlinx.parcelize.Parcelize

@Parcelize
data class RowConfMaterialFormDB (
    var idMaterial : Long,
    var idConfMaterial : Long,
    var idStep : Long,
    var nameImg : String,
    var rol : Rol,
    var nombre : String,
    var isMaterialWeight : Boolean,
    var confValue : Double
) : Parcelable