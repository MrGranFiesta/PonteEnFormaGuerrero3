package com.mrgranfiesta.ponteenformaguerrero3.domain.constans

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.ui.graphics.vector.ImageVector

enum class StateSerie(var icon: ImageVector) {
    INITIAL(icon = Icons.Filled.RocketLaunch),
    DESCANSO_PLAY(icon = Icons.Filled.PlayArrow),
    DESCANSO_PAUSE(icon = Icons.Filled.Pause),
    ACTIVO_PAUSE(icon = Icons.Filled.Pause),
    ACTIVO_PLAY(icon = Icons.Filled.PlayArrow),
    ACTIVO_WAIT(icon = Icons.Filled.HourglassBottom),
    FINISHED(icon = Icons.Filled.CheckCircleOutline);

    companion object {
        fun getTextState(state: StateSerie): String {
            return when (state) {
                INITIAL -> {
                    "---"
                }

                FINISHED -> {
                    "Terminado"
                }

                DESCANSO_PLAY, DESCANSO_PAUSE -> {
                    "Descanso"
                }

                ACTIVO_PAUSE -> {
                    "Pause"
                }

                ACTIVO_PLAY -> {
                    "Activo"
                }

                ACTIVO_WAIT -> {
                    "Esperando"
                }
            }
        }

        fun isPlay(state: StateSerie): Boolean {
            return when (state) {
                DESCANSO_PLAY, ACTIVO_PLAY -> {
                    true
                }

                INITIAL, FINISHED, DESCANSO_PAUSE, ACTIVO_PAUSE, ACTIVO_WAIT -> {
                    false
                }
            }
        }

        fun isPause(state: StateSerie): Boolean {
            return when (state) {
                DESCANSO_PAUSE, ACTIVO_PAUSE -> {
                    true
                }

                INITIAL, ACTIVO_WAIT, FINISHED, DESCANSO_PLAY, ACTIVO_PLAY -> {
                    false
                }
            }
        }

        fun isStop(state: StateSerie): Boolean {
            return when (state) {
                DESCANSO_PAUSE, ACTIVO_PAUSE, ACTIVO_WAIT -> {
                    true
                }

                INITIAL, FINISHED, DESCANSO_PLAY, ACTIVO_PLAY -> {
                    false
                }
            }
        }

        fun isDescanso(state: StateSerie): Boolean {
            return when (state) {
                DESCANSO_PAUSE, DESCANSO_PLAY -> {
                    true
                }

                INITIAL, FINISHED, ACTIVO_PAUSE, ACTIVO_PLAY, ACTIVO_WAIT -> {
                    false
                }
            }
        }

        fun isActive(state: StateSerie): Boolean {
            return when (state) {
                ACTIVO_PAUSE, ACTIVO_PLAY, ACTIVO_WAIT -> {
                    true
                }

                INITIAL, FINISHED, DESCANSO_PAUSE, DESCANSO_PLAY -> {
                    false
                }
            }
        }

        fun changeReverseState(state: StateSerie): StateSerie {
            return when (state) {
                ACTIVO_PAUSE -> {
                    ACTIVO_PLAY
                }

                ACTIVO_PLAY -> {
                    ACTIVO_PAUSE
                }

                DESCANSO_PAUSE -> {
                    DESCANSO_PLAY
                }

                DESCANSO_PLAY -> {
                    DESCANSO_PAUSE
                }

                INITIAL -> {
                    INITIAL
                }

                ACTIVO_WAIT -> {
                    ACTIVO_WAIT
                }

                FINISHED -> {
                    FINISHED
                }
            }
        }

        fun isExtremeState(state: StateSerie): Boolean {
            return when (state) {
                INITIAL, FINISHED -> {
                    true
                }

                DESCANSO_PLAY, ACTIVO_PLAY, DESCANSO_PAUSE, ACTIVO_PAUSE, ACTIVO_WAIT -> {
                    false
                }
            }
        }

        fun isNotExtremeState(state: StateSerie): Boolean {
            return when (state) {
                DESCANSO_PLAY, ACTIVO_PLAY, DESCANSO_PAUSE, ACTIVO_PAUSE, ACTIVO_WAIT -> {
                    true
                }

                INITIAL, FINISHED -> {
                    false
                }
            }
        }
    }
}

