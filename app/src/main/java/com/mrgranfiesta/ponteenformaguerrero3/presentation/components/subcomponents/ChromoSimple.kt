package com.mrgranfiesta.ponteenformaguerrero3.presentation.components.subcomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem.ManagerSimpleCronoVM

@Composable
fun ChromoSimple(
    modifier: Modifier = Modifier,
    managerVM: ManagerSimpleCronoVM
) {
    val stateSerie1 by managerVM.stateSerie1.collectAsState()
    val stateSerie2 by managerVM.stateSerie2.collectAsState()

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            Spacer(modifier = Modifier.weight(0.5f))
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            ) {
                TimerWithButtons(
                    managerVM = managerVM,
                    countdownVM = managerVM.countdownVM1,
                    isInverse = true
                )
            }
            Spacer(modifier = Modifier.weight(0.5f))
        }
        ToggleTimerIcon(
            onClick = { managerVM.changeStateByCtrlTimer() },
            isPaused = managerVM.isPausedTimer(
                stateSerie1 = stateSerie1,
                stateSerie2 = stateSerie2
            )
        )
    }
}