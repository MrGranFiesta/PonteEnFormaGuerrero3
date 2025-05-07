package com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.stat

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Immutable
@Parcelize
data class StatRutinaSearchDto(
    var idStat: Long = 0,
    var idRutinaSnapshot: Int,
    var idRutina: Long,
    var dateInit: LocalDateTime,
    var dateEnd: LocalDateTime,
    var isCompleted: Boolean,
    var nombre: String,
    var nivel: Nivel,
    var isPremium : Boolean,
    var musculoSet: Set<Musculo>
) : Parcelable