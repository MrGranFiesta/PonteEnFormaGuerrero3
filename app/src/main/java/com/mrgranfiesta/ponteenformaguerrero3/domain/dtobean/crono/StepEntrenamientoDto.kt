package com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono

import android.net.Uri
import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import kotlinx.parcelize.Parcelize

@Parcelize
data class StepEntrenamientoDto(
    var idEjercicio: Long,
    var idStep: Long,
    var nombre: String,
    var photoUri: Uri,
    var isSimetria: Boolean,
    var cantidad: Int,
    var serie: Int,
    var ordenExecution: Int,
    var tipo: TipoEsfuerzo,
    var musculoSet: MutableSet<Musculo>
) : Parcelable
