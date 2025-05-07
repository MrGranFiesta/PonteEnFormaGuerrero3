package com.mrgranfiesta.ponteenformaguerrero3.domain.factory

import androidx.core.net.toUri
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DRAWABLE_URI
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.StepEntrenamientoDto

class CronoBatchFactory {
    companion object {
        fun getEstiramientos(
            musculoSet: MutableSet<Musculo>
        ): List<StepEntrenamientoDto> {
            val stepList = mutableListOf<StepEntrenamientoDto>()

            if (musculoSet.contains(Musculo.ESPALDA)
                || musculoSet.contains(Musculo.ABDOMINALES)
                || musculoSet.contains(Musculo.OBLICUOS)
            ) {
                stepList.add(
                    StepEntrenamientoDto(
                        idEjercicio = -1,
                        idStep = 1,
                        nombre = "Estiramiento de cobra",
                        photoUri = "${DRAWABLE_URI}est1".toUri(),
                        isSimetria = false,
                        cantidad = 30,
                        serie = 1,
                        ordenExecution = 1,
                        tipo = TipoEsfuerzo.CRONO,
                        musculoSet = mutableSetOf()
                    )
                )
            }

            if (musculoSet.contains(Musculo.ESPALDA)) {
                stepList.add(
                    StepEntrenamientoDto(
                        idEjercicio = -2,
                        idStep = 2,
                        nombre = "Postura de bebe",
                        photoUri = "${DRAWABLE_URI}est2".toUri(),
                        isSimetria = false,
                        cantidad = 30,
                        serie = 1,
                        ordenExecution = 1,
                        tipo = TipoEsfuerzo.CRONO,
                        musculoSet = mutableSetOf()
                    )
                )
            }

            if (musculoSet.contains(Musculo.TRAPECIO)) {
                stepList.add(
                    StepEntrenamientoDto(
                        idEjercicio = -3,
                        idStep = 3,
                        nombre = "Estiramiento de trapecio superior",
                        photoUri = "${DRAWABLE_URI}est3".toUri(),
                        isSimetria = false,
                        cantidad = 30,
                        serie = 1,
                        ordenExecution = 1,
                        tipo = TipoEsfuerzo.CRONO,
                        musculoSet = mutableSetOf()
                    )
                )

                stepList.add(
                    StepEntrenamientoDto(
                        idEjercicio = -4,
                        idStep = 4,
                        nombre = "Estiramiento lateral del cuello",
                        photoUri = "${DRAWABLE_URI}est4".toUri(),
                        isSimetria = true,
                        cantidad = 30,
                        serie = 1,
                        ordenExecution = 1,
                        tipo = TipoEsfuerzo.CRONO,
                        musculoSet = mutableSetOf()
                    )
                )
            }

            if (musculoSet.contains(Musculo.PIERNA)) {
                stepList.add(
                    StepEntrenamientoDto(
                        idEjercicio = -5,
                        idStep = 5,
                        nombre = "Estiramiento de adupctores",
                        photoUri = "${DRAWABLE_URI}est5".toUri(),
                        isSimetria = false,
                        cantidad = 30,
                        serie = 1,
                        ordenExecution = 1,
                        tipo = TipoEsfuerzo.CRONO,
                        musculoSet = mutableSetOf()
                    )
                )

                stepList.add(
                    StepEntrenamientoDto(
                        idEjercicio = -6,
                        idStep = 6,
                        nombre = "Estiramiento de gluteo",
                        photoUri = "${DRAWABLE_URI}est6".toUri(),
                        isSimetria = true,
                        cantidad = 30,
                        serie = 1,
                        ordenExecution = 1,
                        tipo = TipoEsfuerzo.CRONO,
                        musculoSet = mutableSetOf()
                    )
                )

                stepList.add(
                    StepEntrenamientoDto(
                        idEjercicio = -7,
                        idStep = 7,
                        nombre = "Estiramiento de cuadriceps",
                        photoUri = "${DRAWABLE_URI}est7".toUri(),
                        isSimetria = true,
                        cantidad = 30,
                        serie = 1,
                        ordenExecution = 1,
                        tipo = TipoEsfuerzo.CRONO,
                        musculoSet = mutableSetOf()
                    )
                )
            }


            if (musculoSet.contains(Musculo.PECHO)) {
                stepList.add(
                    StepEntrenamientoDto(
                        idEjercicio = -8,
                        idStep = 8,
                        nombre = "Estiramiento de pecho",
                        photoUri = "${DRAWABLE_URI}est8".toUri(),
                        isSimetria = false,
                        cantidad = 30,
                        serie = 1,
                        ordenExecution = 1,
                        tipo = TipoEsfuerzo.CRONO,
                        musculoSet = mutableSetOf()
                    )
                )
            }

            if (musculoSet.contains(Musculo.TRICEPS)) {
                stepList.add(
                    StepEntrenamientoDto(
                        idEjercicio = -9,
                        idStep = 9,
                        nombre = "Estiramiento de triceps en pared",
                        photoUri = "${DRAWABLE_URI}est9".toUri(),
                        isSimetria = false,
                        cantidad = 30,
                        serie = 1,
                        ordenExecution = 1,
                        tipo = TipoEsfuerzo.CRONO,
                        musculoSet = mutableSetOf()
                    )
                )
                stepList.add(
                    StepEntrenamientoDto(
                        idEjercicio = -10,
                        idStep = 10,
                        nombre = "Estiramiento de triceps por encima de la cabeza",
                        photoUri = "${DRAWABLE_URI}est10".toUri(),
                        isSimetria = false,
                        cantidad = 30,
                        serie = 1,
                        ordenExecution = 1,
                        tipo = TipoEsfuerzo.CRONO,
                        musculoSet = mutableSetOf()
                    )
                )
            }

            if (musculoSet.contains(Musculo.HOMBRO)) {
                stepList.add(
                    StepEntrenamientoDto(
                        idEjercicio = -10,
                        idStep = 10,
                        nombre = "Estiramiento de biceps con tracción de brazo",
                        photoUri = "${DRAWABLE_URI}est10".toUri(),
                        isSimetria = false,
                        cantidad = 30,
                        serie = 1,
                        ordenExecution = 1,
                        tipo = TipoEsfuerzo.CRONO,
                        musculoSet = mutableSetOf()
                    )
                )
            }

            if (musculoSet.contains(Musculo.BICEPS)) {
                stepList.add(
                    StepEntrenamientoDto(
                        idEjercicio = -12,
                        idStep = 12,
                        nombre = "Estiramiento de biceps con tracción de brazo",
                        photoUri = "${DRAWABLE_URI}est12".toUri(),
                        isSimetria = false,
                        cantidad = 30,
                        serie = 1,
                        ordenExecution = 1,
                        tipo = TipoEsfuerzo.CRONO,
                        musculoSet = mutableSetOf()
                    )
                )

                stepList.add(
                    StepEntrenamientoDto(
                        idEjercicio = -13,
                        idStep = 13,
                        nombre = "Estiramiento de biceps en puerta",
                        photoUri = "${DRAWABLE_URI}est13".toUri(),
                        isSimetria = false,
                        cantidad = 30,
                        serie = 1,
                        ordenExecution = 1,
                        tipo = TipoEsfuerzo.CRONO,
                        musculoSet = mutableSetOf()
                    )
                )
            }

            if (musculoSet.contains(Musculo.ANTEBRAZO)) {
                stepList.add(
                    StepEntrenamientoDto(
                        idEjercicio = -14,
                        idStep = 14,
                        nombre = "Estiramiento de los flexores de la muñeca",
                        photoUri = "${DRAWABLE_URI}est14".toUri(),
                        isSimetria = false,
                        cantidad = 30,
                        serie = 1,
                        ordenExecution = 1,
                        tipo = TipoEsfuerzo.CRONO,
                        musculoSet = mutableSetOf()
                    )
                )

                stepList.add(
                    StepEntrenamientoDto(
                        idEjercicio = -15,
                        idStep = 15,
                        nombre = "Estiramiento de los extensores de la muñeca",
                        photoUri = "${DRAWABLE_URI}est15".toUri(),
                        isSimetria = false,
                        cantidad = 30,
                        serie = 1,
                        ordenExecution = 1,
                        tipo = TipoEsfuerzo.CRONO,
                        musculoSet = mutableSetOf()
                    )
                )
            }

            return stepList
        }

        fun getMovilidadArticular(): List<StepEntrenamientoDto> = listOf(
            StepEntrenamientoDto(
                idEjercicio = -1,
                idStep = -1,
                nombre = "Rotaciñon de cuello (izquierda)",
                photoUri = "${DRAWABLE_URI}mov_art1".toUri(),
                isSimetria = false,
                cantidad = 12,
                serie = 1,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.CRONO,
                musculoSet = mutableSetOf()
            ),
            StepEntrenamientoDto(
                idEjercicio = -2,
                idStep = -2,
                nombre = "Rotaciñon de cuello (derecha)",
                photoUri = "${DRAWABLE_URI}mov_art2".toUri(),
                isSimetria = false,
                cantidad = 12,
                serie = 1,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.CRONO,
                musculoSet = mutableSetOf()
            ),
            StepEntrenamientoDto(
                idEjercicio = -3,
                idStep = -3,
                nombre = "Rotaciñon de brazos",
                photoUri = "${DRAWABLE_URI}mov_art3".toUri(),
                isSimetria = false,
                cantidad = 12,
                serie = 1,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.CRONO,
                musculoSet = mutableSetOf()
            ),
            StepEntrenamientoDto(
                idEjercicio = -4,
                idStep = -4,
                nombre = "Rotaciñon de ambas muñecas (derecha)",
                photoUri = "${DRAWABLE_URI}mov_art3".toUri(),
                isSimetria = false,
                cantidad = 12,
                serie = 1,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.CRONO,
                musculoSet = mutableSetOf()
            ),
            StepEntrenamientoDto(
                idEjercicio = -5,
                idStep = -5,
                nombre = "Rotaciñon de ambas muñecas (izquierda)",
                photoUri = "${DRAWABLE_URI}mov_art5".toUri(),
                isSimetria = false,
                cantidad = 12,
                serie = 1,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.CRONO,
                musculoSet = mutableSetOf()
            ),
            StepEntrenamientoDto(
                idEjercicio = -6,
                idStep = -6,
                nombre = "Circulos de cadera (derecha)",
                photoUri = "${DRAWABLE_URI}mov_art6".toUri(),
                isSimetria = false,
                cantidad = 12,
                serie = 1,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.CRONO,
                musculoSet = mutableSetOf()
            ),
            StepEntrenamientoDto(
                idEjercicio = -7,
                idStep = -7,
                nombre = "Circulos de cadera (izquierda)",
                photoUri = "${DRAWABLE_URI}mov_art7".toUri(),
                isSimetria = false,
                cantidad = 12,
                serie = 1,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.CRONO,
                musculoSet = mutableSetOf()
            ),
            StepEntrenamientoDto(
                idEjercicio = -8,
                idStep = -8,
                nombre = "Balanceo lateral de cadera",
                photoUri = "${DRAWABLE_URI}mov_art8".toUri(),
                isSimetria = false,
                cantidad = 12,
                serie = 1,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.CRONO,
                musculoSet = mutableSetOf()
            ),
            StepEntrenamientoDto(
                idEjercicio = -9,
                idStep = -9,
                nombre = "Balanceo delantero de rodilla",
                photoUri = "${DRAWABLE_URI}mov_art9".toUri(),
                isSimetria = false,
                cantidad = 12,
                serie = 1,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.CRONO,
                musculoSet = mutableSetOf()
            ),
            StepEntrenamientoDto(
                idEjercicio = -10,
                idStep = -10,
                nombre = "Circulo de rodilla (derecha)",
                photoUri = "${DRAWABLE_URI}mov_art10".toUri(),
                isSimetria = false,
                cantidad = 12,
                serie = 1,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.CRONO,
                musculoSet = mutableSetOf()
            ),
            StepEntrenamientoDto(
                idEjercicio = -11,
                idStep = -11,
                nombre = "Circulo de rodilla (izquierda)",
                photoUri = "${DRAWABLE_URI}mov_art11".toUri(),
                isSimetria = false,
                cantidad = 12,
                serie = 1,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.CRONO,
                musculoSet = mutableSetOf()
            ),
            StepEntrenamientoDto(
                idEjercicio = -12,
                idStep = -12,
                nombre = "Rotación del tobillo derecho (derecha)",
                photoUri = "${DRAWABLE_URI}mov_art12".toUri(),
                isSimetria = false,
                cantidad = 12,
                serie = 1,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.CRONO,
                musculoSet = mutableSetOf()
            ),
            StepEntrenamientoDto(
                idEjercicio = -13,
                idStep = -13,
                nombre = "Rotación del tobillo derecho (izquierda)",
                photoUri = "${DRAWABLE_URI}mov_art13".toUri(),
                isSimetria = false,
                cantidad = 12,
                serie = 1,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.CRONO,
                musculoSet = mutableSetOf()
            ),
            StepEntrenamientoDto(
                idEjercicio = -14,
                idStep = -14,
                nombre = "Rotación del tobillo izquierdo (derecha)",
                photoUri = "${DRAWABLE_URI}mov_art14".toUri(),
                isSimetria = false,
                cantidad = 12,
                serie = 1,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.CRONO,
                musculoSet = mutableSetOf()
            ),
            StepEntrenamientoDto(
                idEjercicio = -15,
                idStep = -15,
                nombre = "Rotación del tobillo izquierdo (izquierda)",
                photoUri = "${DRAWABLE_URI}mov_art15".toUri(),
                isSimetria = false,
                cantidad = 12,
                serie = 1,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.CRONO,
                musculoSet = mutableSetOf()
            )
        )
    }
}