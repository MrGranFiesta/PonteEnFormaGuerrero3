package com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.subScreem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EMPEZAR_BT
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ENHORABUENA
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_FINALIZAR_BT
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_QUESTION_ESTIRAMIENTOS
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.StateBatchCrono
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.EntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.StepEntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.CronoVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem.EndCronoVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.DualButton
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.subcomponents.StadisticsEndRutina

@Composable
fun QuestionEstirameintoCronoScreem(
    stepList: List<StepEntrenamientoDto>,
    entrenamiento: EntrenamientoDto,
    endOnClick: () -> Unit,
    cronoVM: CronoVM,
    endCronoVM: EndCronoVM = hiltViewModel()
) {
    endCronoVM.soundVictory(LocalContext.current)
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = LABEL_ENHORABUENA,
                    fontSize = 30.sp
                )
                Icon(
                    imageVector = Icons.Filled.EmojiEvents,
                    contentDescription = "Trofeo Icon",
                    modifier = Modifier
                        .width(128.dp)
                        .height(128.dp)
                )
                Text(text = LABEL_QUESTION_ESTIRAMIENTOS)
            }
            Column(
                modifier = Modifier.wrapContentHeight()
            ) {
                StadisticsEndRutina(
                    stepList = stepList,
                    entrenamiento = entrenamiento,
                    cronoVM = cronoVM
                )
            }
        }
        DualButton(
            textLeft = LABEL_FINALIZAR_BT,
            textRight = LABEL_EMPEZAR_BT,
            leftAction = {
                endOnClick()
            },
            rightAction = {
                cronoVM.setStateBatchCrono(StateBatchCrono.ESTIRAMIENTOS)
            }
        )
    }
}