package com.mrgranfiesta.ponteenformaguerrero3.presentation.components.subcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem.ManagerDualRepsVM

@Composable
fun RepsDual(
    modifier: Modifier = Modifier,
    managerVM: ManagerDualRepsVM
) {
    val state1 by managerVM.stateSerie1.collectAsState()
    val state2 by managerVM.stateSerie2.collectAsState()

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RepsCard(
                modifier = Modifier.weight(1f),
                managerVM = managerVM,
                state = state2,
                countdownVM = managerVM.countdownVM2,
                isInverse = true
            )
            Spacer(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.onBackground
                    )
                    .width(2.dp)
            )
            RepsCard(
                modifier = Modifier.weight(1f),
                managerVM = managerVM,
                state = state1,
                countdownVM = managerVM.countdownVM1,
                isInverse = false
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            CompletionButton(
                managerVM = managerVM,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .weight(0.5f),
                state = state2,
                isInverse = true
            )
            Spacer(
                modifier = Modifier
                    .width(2.dp)
            )
            CompletionButton(
                managerVM = managerVM,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .weight(0.5f),
                state = state1,
                isInverse = false
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            if (managerVM.isDescanso(state1, state2)) {
                ToggleTimerIcon(
                    onClick = { managerVM.changeStateByCtrlTimer() },
                    isPaused = managerVM.isPausedTimer(
                        stateSerie1 = state1,
                        stateSerie2 = state2
                    )
                )
            } else {
                Spacer(
                    modifier = Modifier
                        .height(32.dp)
                )
            }
        }
    }
}