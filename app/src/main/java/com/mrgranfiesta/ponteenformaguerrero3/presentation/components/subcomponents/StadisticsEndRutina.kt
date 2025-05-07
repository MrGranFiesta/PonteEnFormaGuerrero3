package com.mrgranfiesta.ponteenformaguerrero3.presentation.components.subcomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CALENTAMIENTO_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EJERCICOS_REALIZADOS
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ENFOCADOS_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ESTIRAMIENTOS_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_MOVILIDAD_ARTICULAR_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_MUSCULOS_ENTRENADOS_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_OTROS_MUSCULOS_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_STAT_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_TIEMPO_TOTAL
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.EntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.StepEntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.DateUtils
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.CronoVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem.StadisticsUtilsVM

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StadisticsEndRutina(
    stepList: List<StepEntrenamientoDto>,
    entrenamiento: EntrenamientoDto,
    cronoVM: CronoVM,
    stadisticsUtilsVM: StadisticsUtilsVM = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = LABEL_STAT_COLON,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "$LABEL_TIEMPO_TOTAL ${
                DateUtils.getMinusFormat(
                    initTime = cronoVM.initRutinaTime,
                    endTime = stadisticsUtilsVM.initEndTime
                )
            }:",
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "$LABEL_EJERCICOS_REALIZADOS ${stepList.size}",
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = LABEL_MUSCULOS_ENTRENADOS_COLON,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = LABEL_ENFOCADOS_COLON,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            stadisticsUtilsVM.calculateMuscleRutina(entrenamiento.musculoSet).forEach {
                AssistChip(
                    modifier = Modifier.wrapContentWidth(),
                    onClick = { },
                    label = { Text(it.toString()) }
                )
            }
        }
        if (
            stadisticsUtilsVM.calculateMuscleEjercicio(
                musculoSetSustraendo = entrenamiento.musculoSet,
                stepList = stepList
            ).isNotEmpty()
        ) {
            Text(
                text = LABEL_OTROS_MUSCULOS_COLON,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                stadisticsUtilsVM.calculateMuscleEjercicio(
                    musculoSetSustraendo = entrenamiento.musculoSet,
                    stepList = stepList
                ).forEach {
                    AssistChip(
                        modifier = Modifier.wrapContentWidth(),
                        onClick = { },
                        label = { Text(it.toString()) }
                    )
                }
            }
        }
        Text(
            text = "$LABEL_CALENTAMIENTO_COLON ${stadisticsUtilsVM.isDoneTxt(cronoVM.calentamientoDone)}",
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "$LABEL_MOVILIDAD_ARTICULAR_COLON ${stadisticsUtilsVM.isDoneTxt(cronoVM.movArticularDone)}",
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "$LABEL_ESTIRAMIENTOS_COLON: ${stadisticsUtilsVM.isDoneTxt(cronoVM.estiramientoDone)}",
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}