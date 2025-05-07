package com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.step

import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import kotlinx.parcelize.Parcelize

@Parcelize
data class StepInfoWithConfMaterialTempDB (
    var idStep: Long = 0,
    var idEjercicio: Long,
    var idMaterial: Long?,
    var idConfMaterial: Long?,
    var serie : Int,
    var ordenExecution: Int,
    var tipo: TipoEsfuerzo,
    var cantidad: Int,
    var nombre: String?,
    var isMaterialWeight : Boolean?,
    var nameImg : String?,
    var rol : Rol?,
    var confValue : Double?
) : Parcelable