package com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono

import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.AnswerState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.objects.AnswerStateLoading
import kotlinx.parcelize.Parcelize

@Parcelize
data class EntrenamientoDto(
    var idRutina: Long,
    var calentamiento: AnswerStateLoading,
    var estiramiento: AnswerState,
    var movArticular: AnswerState,
    var descanso: Int,
    var musculoSet: MutableSet<Musculo>
) : Parcelable
