package com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.stat

import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class StatRutinaSearchDB(
    var idStat: Long,
    var idRutinaSnapshot: Int,
    var idRutina: Long,
    var dateInit: LocalDateTime,
    var dateEnd: LocalDateTime,
    var isCompleted: Boolean,
    var nombre: String,
    var nivel: Nivel,
    var musculoSet: Set<Musculo>,
    var isPremium : Boolean,
    var rol : Rol
) : Parcelable