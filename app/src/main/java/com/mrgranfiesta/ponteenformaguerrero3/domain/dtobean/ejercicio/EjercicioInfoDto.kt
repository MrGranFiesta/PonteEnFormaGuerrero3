package com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio

import android.net.Uri
import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios.MaxLenght
import com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios.NotEmpty
import com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios.SecuredCharSQL
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import kotlinx.parcelize.Parcelize

@Parcelize
data class EjercicioInfoDto(
    var idEjercicio: Long = 0,
    var idUser: Long,
    @MaxLenght(50, "nombre")
    @NotEmpty("nombre")
    @SecuredCharSQL("nombre")
    var nombre: String,
    var isSimetria: Boolean,
    var musculoSet: Set<Musculo>,
    var nivel: Nivel,
    @MaxLenght(500, "descripcion")
    @SecuredCharSQL("descripcion")
    var descripcion: String,
    var photoUri: Uri,
    var rol : Rol
) : Parcelable