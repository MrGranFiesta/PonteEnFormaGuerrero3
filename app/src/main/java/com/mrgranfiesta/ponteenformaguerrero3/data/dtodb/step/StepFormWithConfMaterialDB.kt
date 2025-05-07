package com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.step

import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.confmaterial.RowConfMaterialFormDB
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import kotlinx.parcelize.Parcelize

@Parcelize
data class StepFormWithConfMaterialDB (
    var idStep: Long = 0,
    var idRutina: Long,
    var idEjercicio: Long,
    var cantidad: Int,
    var serie: Int,
    var ordenExecution: Int,
    var tipo: TipoEsfuerzo,
    var confMaterial : List<RowConfMaterialFormDB> = listOf()
) : Parcelable