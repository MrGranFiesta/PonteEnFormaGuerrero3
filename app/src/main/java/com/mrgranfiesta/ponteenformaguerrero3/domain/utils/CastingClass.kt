package com.mrgranfiesta.ponteenformaguerrero3.domain.utils

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.Black
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.Blue500
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.Blue700
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.Blue900
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.Green600
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.Green700
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.Green900
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.Red500
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.Red700
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.Red900
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.White
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.Yellow500
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.Yellow700
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.Yellow900

class CastingClass {
    companion object {
        fun brushByNivel(nivel: Nivel): Brush {
            return when (nivel) {
                Nivel.DIFICIL -> Brush.horizontalGradient(
                    0f to Red500,
                    0.5f to Red700,
                    1f to Red900
                )

                Nivel.MEDIO -> Brush.horizontalGradient(
                    0f to Yellow500,
                    0.5f to Yellow700,
                    1f to Yellow900
                )

                Nivel.FACIL -> Brush.horizontalGradient(
                    0f to Green600,
                    0.5f to Green700,
                    1f to Green900
                )

                Nivel.NINGUNO -> Brush.horizontalGradient(
                    0f to Blue500,
                    0.5f to Blue700,
                    1f to Blue900
                )
            }
        }

        fun colorByNivel(nivel: Nivel): Color {
            return when (nivel) {
                Nivel.FACIL, Nivel.DIFICIL, Nivel.NINGUNO -> White
                Nivel.MEDIO -> Black
            }
        }

        fun unidadToTipoEsfuerzo(str: String?): TipoEsfuerzo {
            return when (str) {
                TipoEsfuerzo.REPETICION.unidad -> TipoEsfuerzo.REPETICION
                TipoEsfuerzo.CRONO.unidad -> TipoEsfuerzo.CRONO
                else -> TipoEsfuerzo.REPETICION
            }
        }
    }
}