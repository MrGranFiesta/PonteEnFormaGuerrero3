package com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.subScreem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EMPEZAR_BT
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_OMITIR
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.StateBatchCrono
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.CountdownVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.CronoVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.Countdown
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.DualButton

@Composable
fun QuestionCronoScreem(
    cronoVM: CronoVM = hiltViewModel(),
    question: String,
    countdownVM : CountdownVM = hiltViewModel(),
    omissionState: StateBatchCrono,
    nextStateBatch: StateBatchCrono,
) {
    cronoVM.updateExtrasOnPause {
        countdownVM.stopCountdown()
    }

    cronoVM.updateExtrasOnResume {
        countdownVM.playCountdown()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Countdown(
                countdownVM = countdownVM,
                fontSize = 75.sp,
                autoPlay = true,
                initMiliseconds = 10_000L,
                actionFinish = { cronoVM.setStateBatchCrono(nextStateBatch) },
            )
            Text(
                textAlign = TextAlign.Center,
                text = question
            )
        }
        DualButton(
            leftAction = { cronoVM.setStateBatchCrono(omissionState) },
            rightAction = { cronoVM.setStateBatchCrono(nextStateBatch) },
            textLeft = LABEL_OMITIR,
            textRight = LABEL_EMPEZAR_BT
        )
    }
}