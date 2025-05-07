package com.mrgranfiesta.ponteenformaguerrero3.domain.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class StatBean(
    var idStatHistory: Long,
    var idRutinaSnapshot: Long,
    var idRutina: Long,
    var idUser: Long,
    var dateInit: LocalDateTime,
    var dateEnd: LocalDateTime,
    var isCalentamientoDone: Boolean,
    var isMovArticularDone: Boolean,
    var isEstiramientoDone: Boolean,
    var isCompleted: Boolean
) : Parcelable