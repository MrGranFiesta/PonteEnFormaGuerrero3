package com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.rutina

import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import kotlinx.parcelize.Parcelize

@Parcelize
data class RowRutinaDB(
    var idRutina : Long,
    var idUser : Long,
    var nombre : String,
    var nivel : Nivel,
    var musculoSet : Set<Musculo>,
    var isPremium : Boolean
) : Parcelable