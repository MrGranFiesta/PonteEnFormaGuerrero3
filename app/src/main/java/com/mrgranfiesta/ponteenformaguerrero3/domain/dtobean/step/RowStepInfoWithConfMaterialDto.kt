package com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.step

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialInfoWithConfDto
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class RowStepInfoWithConfMaterialDto(
    var idStep: Long = 0,
    var idEjercicio: Long,
    var cantidad: Int,
    var serie: Int,
    var ordenExecution: Int,
    var tipo: TipoEsfuerzo,
    var confMaterialList: List<RowMaterialInfoWithConfDto>
) : Parcelable