package com.mrgranfiesta.ponteenformaguerrero3.domain.utils

import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import java.util.concurrent.TimeUnit

class StringUtils {
    companion object {
        fun musculosSetToString(musculosSet: Set<Musculo>): String {
            var txt = ""
            for (i in musculosSet) {
                txt += "$i, "
            }
            if (txt.isNotEmpty()) {
                txt = txt.substring(0, txt.length - 2)
            }
            return txt
        }

        fun segToMinForrmatter(timer: Int): String {
            val minutos = timer / 60
            val segundos = timer % 60
            return if (minutos > 0) {
                "$minutos min $segundos seg"
            } else {
                "$segundos seg"
            }
        }

        fun timeMinDigitalFormat(miliseconds: Long): String {
            val segTimer = miliseconds / 1000
            val minutos = segTimer / 60
            val segundos = segTimer % 60
            return if (minutos > 0) {
                "${letfPad(minutos)} : ${letfPad(segundos)}"
            } else {
                letfPad(segundos)
            }
        }

        fun timeHourDigitalFormat(miliseconds: Long): String {
            val hour = TimeUnit.MILLISECONDS.toHours(miliseconds)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(miliseconds) % 60
            val seconds = TimeUnit.MILLISECONDS.toSeconds(miliseconds) % 60
            return "${letfPad(hour)} : ${letfPad(minutes)} : ${
                letfPad(seconds)
            }"
        }

        fun toTipoEsfuerzoForrmatter(
            cantidad: Int,
            tipo: TipoEsfuerzo
        ): String {
            return when (tipo) {
                TipoEsfuerzo.CRONO -> {
                    this.segToMinForrmatter(cantidad)
                }

                else -> {
                    "$cantidad ${tipo.abreviatura}"
                }
            }
        }

        private fun letfPad(it: Number): String = "%02d".format(it.toLong())
    }
}