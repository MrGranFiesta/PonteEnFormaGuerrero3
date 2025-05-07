package com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.rutina

import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.AnswerState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import kotlinx.parcelize.Parcelize

@Parcelize
data class RutinaInfoDB(
    var idRutina: Long,
    var idUser: Long = 0,
    var nombre: String,
    var descripcion: String,
    var musculoSet: Set<Musculo>,
    var nivel: Nivel,
    var calentamiento: AnswerState,
    var estiramiento: AnswerState,
    var movArticular: AnswerState,
    var descanso: Int,
    val rol: Rol
) : Parcelable