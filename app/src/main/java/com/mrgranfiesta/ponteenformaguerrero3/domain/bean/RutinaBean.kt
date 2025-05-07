package com.mrgranfiesta.ponteenformaguerrero3.domain.bean

import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.AnswerState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import kotlinx.parcelize.Parcelize

@Parcelize
data class RutinaBean(
    var idRutina: Long,
    var idUser: Long,
    var nombre: String,
    var descripcion: String,
    var musculoSet: Set<Musculo>,
    var nivel: Nivel,
    var calentamiento: AnswerState,
    var estiramiento: AnswerState,
    var movArticular: AnswerState,
    var descanso: Int,
    var isPremium: Boolean
) : Parcelable