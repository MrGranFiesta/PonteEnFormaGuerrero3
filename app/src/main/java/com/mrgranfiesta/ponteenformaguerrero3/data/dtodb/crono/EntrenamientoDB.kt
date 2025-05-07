package com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.crono

import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.AnswerState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import kotlinx.parcelize.Parcelize

@Parcelize
data class EntrenamientoDB(
    var idRutina: Long,
    var calentamiento: AnswerState,
    var estiramiento: AnswerState,
    var movArticular: AnswerState,
    var descanso: Int,
    var musculoSet: MutableSet<Musculo>
) : Parcelable
