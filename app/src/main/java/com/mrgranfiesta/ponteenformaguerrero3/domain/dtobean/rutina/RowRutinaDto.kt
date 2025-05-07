package com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.rutina

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class RowRutinaDto(
    var idRutina : Long,
    var idUser : Long,
    var nombre : String,
    var nivel : Nivel,
    var musculoSet : Set<Musculo>,
    var isPremium : Boolean
) : Parcelable