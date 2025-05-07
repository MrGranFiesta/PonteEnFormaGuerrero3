package com.mrgranfiesta.ponteenformaguerrero3.domain.bean

import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import kotlinx.parcelize.Parcelize

@Parcelize
data class StepBean(
    var idStep: Long,
    var idEjercicio: Long,
    var idRutina: Long,
    var cantidad: Int,
    var serie: Int,
    var ordenExecution: Int,
    var tipo: TipoEsfuerzo
) : Parcelable