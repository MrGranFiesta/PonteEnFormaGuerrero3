package com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.crono

import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import kotlinx.parcelize.Parcelize

@Parcelize
data class StepEntrenamientoDB(
    var idEjercicio: Long,
    var idStep: Long = 0,
    var nombre: String,
    var nameImg: String,
    var isSimetria: Boolean,
    var cantidad: Int,
    var musculoSet: MutableSet<Musculo>,
    var serie: Int,
    var ordenExecution: Int,
    var tipo: TipoEsfuerzo,
    var rol: Rol
) : Parcelable
