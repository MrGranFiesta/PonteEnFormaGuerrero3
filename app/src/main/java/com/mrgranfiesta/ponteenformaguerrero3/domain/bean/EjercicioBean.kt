package com.mrgranfiesta.ponteenformaguerrero3.domain.bean

import android.net.Uri
import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import kotlinx.parcelize.Parcelize


@Parcelize
data class EjercicioBean(
    var idEjercicio: Long,
    var idUser: Long,
    var nombre: String,
    var isSimetria: Boolean,
    var musculoSet: Set<Musculo>,
    var nivel: Nivel,
    var descripcion: String,
    var photoUri: Uri
) : Parcelable