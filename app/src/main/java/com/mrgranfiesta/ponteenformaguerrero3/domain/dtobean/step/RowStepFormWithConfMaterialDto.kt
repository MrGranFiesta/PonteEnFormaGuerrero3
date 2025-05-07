package com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.step

import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialFormWithConfDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class RowStepFormWithConfMaterialDto(
    var idStep: Long = 0,
    var idRutina: Long,
    var idEjercicio: Long,
    var cantidad: Int,
    var serie: Int,
    var ordenExecution: Int,
    var tipo: TipoEsfuerzo,
    var confMaterialList: List<RowMaterialFormWithConfDto>
) : Parcelable