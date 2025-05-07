package com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.rutina

import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios.MaxLenght
import com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios.NotEmpty
import com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios.SecuredCharSQL
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.AnswerState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import kotlinx.parcelize.Parcelize

@Parcelize
data class RutinaInfoDto(
    var idRutina: Long,
    var idUser: Long,
    @MaxLenght(50, "nombre")
    @NotEmpty("nombre")
    @SecuredCharSQL("nombre")
    var nombre: String,
    @MaxLenght(500, "descripcion")
    @SecuredCharSQL("descripcion")
    var descripcion: String,
    var musculoSet: Set<Musculo>,
    var nivel: Nivel,
    var calentamiento: AnswerState,
    var estiramiento: AnswerState,
    var movArticular: AnswerState,
    var descanso: Int,
    val rol: Rol
) : Parcelable