package com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.dialog

import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import kotlinx.parcelize.Parcelize

@Parcelize
data class StepEditDialogDto(
    var idStep: Long = 0,
    var idEjercicio: Long,
    var cantidad: Int,
    var serie: Int,
    var tipo: TipoEsfuerzo,
    var confMaterialList: List<MaterialNameDto>
) : Parcelable
