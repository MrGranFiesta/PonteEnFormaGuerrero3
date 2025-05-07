package com.mrgranfiesta.ponteenformaguerrero3.utilstest.datasource

import com.mrgranfiesta.ponteenformaguerrero3.data.dao.StatDao
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.StatEntity
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.collections.forEach

class GenerateStat @Inject constructor(
    private val dao: StatDao
) {
    suspend operator fun invoke() {
        getDefaultStat().forEach { stat ->
            dao.insert(stat)
        }
    }

    private fun getDefaultStat() : List<StatEntity> {
        return listOf(
            StatEntity(
                idStat = 1,
                idRutina = 1,
                idRutinaSnapshot = 1,
                dateInit = LocalDateTime.of(2023, 1, 1, 10, 0, 0),
                dateEnd = LocalDateTime.of(2023, 1, 1, 12, 0, 0),
                idUser = 1,
                isCalentamientoDone = true,
                isMovArticularDone = true,
                isEstiramientoDone = true,
                isCompleted = true
            ),
            StatEntity(
                idStat = 2,
                idRutina = 1,
                idRutinaSnapshot = 2,
                dateInit = LocalDateTime.of(2023, 1, 1, 10, 0, 0),
                dateEnd = LocalDateTime.of(2023, 1, 1, 11, 0, 0),
                idUser = 1,
                isCalentamientoDone = true,
                isMovArticularDone = false,
                isEstiramientoDone = false,
                isCompleted = false
            ),
            StatEntity(
                idStat = 3,
                idRutina = null,
                idRutinaSnapshot = 3,
                dateInit = LocalDateTime.of(2023, 1, 1, 10, 0, 0),
                dateEnd = LocalDateTime.of(2023, 1, 1, 13, 0, 0),
                idUser = 1,
                isCalentamientoDone = false,
                isMovArticularDone = true,
                isEstiramientoDone = true,
                isCompleted = true
            )
        )
    }
}