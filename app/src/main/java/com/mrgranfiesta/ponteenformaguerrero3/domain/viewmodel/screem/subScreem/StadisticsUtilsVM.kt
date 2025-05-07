package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem

import androidx.lifecycle.ViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.StepEntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.CollectionUtil
import java.time.LocalDateTime

class StadisticsUtilsVM : ViewModel() {

    val initEndTime: LocalDateTime = LocalDateTime.now()

    fun calculateMuscleRutina(musculoSet: Set<Musculo>): List<Musculo> {
        return musculoSet.sortedBy { it.name }
    }

    fun calculateMuscleEjercicio(
        musculoSetSustraendo: Set<Musculo>,
        stepList: List<StepEntrenamientoDto>
    ): List<Musculo> {
        return minusMusculoAndSorted (
            minuendo = stepList.flatMap { it.musculoSet }.toSet(),
            sustraendo = musculoSetSustraendo
        )
    }

    fun minusMusculoAndSorted(
        minuendo : Set<Musculo>,
        sustraendo : Set<Musculo>,
    ): List<Musculo> {
        return CollectionUtil.minus<Musculo>(
            minuendo = minuendo,
            sustraendo = sustraendo
        ).sortedBy { it.name }
    }

    fun isDoneTxt(isDone: Boolean) = if (isDone) "Realizado" else "No realizado"

}