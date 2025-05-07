package com.mrgranfiesta.ponteenformaguerrero3.presentation.components.subcomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.template.IManagerSerie
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.CountdownVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.Countdown

@Composable
fun TimerWithButtons(
    managerVM: IManagerSerie,
    countdownVM: CountdownVM = hiltViewModel(),
    isInverse: Boolean
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { countdownVM.minusEndTimeMin(10_000L) }) {
            Text(
                text = "-10"
            )
        }
        Countdown(
            countdownVM = countdownVM,
            fontSize = 30.sp,
            autoPlay = managerVM.isAutoPlay(isInverse),
            initMiliseconds = managerVM.getInitTime(isInverse),
            actionFinish = { managerVM.iteratorTimer(isInverse, context) },
        )
        IconButton(onClick = { countdownVM.plusEndTime(10_000L) }) {
            Text(
                text = "+10"
            )
        }
    }
}