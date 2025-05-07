package com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.stat

import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class StatRutinaInfoDB(
    var idStat: Long = 0,
    var idRutinaSnapshot: Long,
    var idRutina: Long,
    var dateInit: LocalDateTime,
    var dateEnd: LocalDateTime,
    var isCalentamientoDone: Boolean,
    var isMovArticularDone: Boolean,
    var isEstiramientoDone: Boolean,
    var isCompleted: Boolean,
    var nombre: String,
    var nivel: Nivel,
    var musculoSet: Set<Musculo>,
    var descripcion : String,
    var rol: Rol
) : Parcelable