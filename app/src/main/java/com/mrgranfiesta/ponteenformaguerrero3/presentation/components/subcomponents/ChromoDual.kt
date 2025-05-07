package com.mrgranfiesta.ponteenformaguerrero3.presentation.components.subcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem.ManagerDualCronoVM

@Composable
fun ChromoDual(
    modifier: Modifier = Modifier,
    managerVM: ManagerDualCronoVM
) {
    val state1 by managerVM.stateSerie1.collectAsState()
    val state2 by managerVM.stateSerie2.collectAsState()

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            ) {
                if (managerVM.isShow(state2)) {
                    TimerWithButtons(
                        managerVM = managerVM,
                        countdownVM = managerVM.countdownVM2,
                        isInverse = true
                    )
                } else {
                    RowStateSerieEmpty()
                }
            }
            Spacer(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.onBackground
                    )
                    .width(2.dp)
            )
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            ) {
                if (managerVM.isShow(state1)) {
                    TimerWithButtons(
                        managerVM = managerVM,
                        countdownVM = managerVM.countdownVM1,
                        isInverse = false
                    )
                } else {
                    RowStateSerieEmpty()
                }
            }
        }
        ToggleTimerIcon(
            onClick = { managerVM.changeStateByCtrlTimer() },
            isPaused = managerVM.isPausedTimer(
                stateSerie1 = state1,
                stateSerie2 = state2
            )
        )
    }
}