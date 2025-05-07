package com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio

import android.net.Uri
import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class RowEjercicioDto(
    var idEjercicio : Long,
    var nombre: String,
    var musculoSet: Set<Musculo>,
    var nivel: Nivel,
    var photoUri: Uri
) : Parcelable
