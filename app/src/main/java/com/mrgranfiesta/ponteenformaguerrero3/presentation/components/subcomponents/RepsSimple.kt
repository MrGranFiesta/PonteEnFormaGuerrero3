package com.mrgranfiesta.ponteenformaguerrero3.presentation.components.subcomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_COMPLETADO_BT
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem.ManagerSimpleRepsVM

@Composable
fun RepsSimple(
    modifier: Modifier = Modifier,
    managerVM: ManagerSimpleRepsVM
) {
    val state by managerVM.stateSerie1.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            Spacer(modifier = Modifier.weight(0.5f))
            RepsCard(
                modifier = Modifier
                    .weight(1f),
                managerVM = managerVM,
                state = state,
                countdownVM = managerVM.countdownVM1,
                isInverse = true
            )
            Spacer(modifier = Modifier.weight(0.5f))
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            if (managerVM.isActive(state)) {
                Button(
                    modifier = Modifier
                        .padding(top = 12.dp),
                    onClick = { managerVM.iteratorTimer(false, context) }
                ) {
                    Icon(imageVector = Icons.Filled.Done, contentDescription = "Ejercio Completado")
                    Text(text = LABEL_COMPLETADO_BT)
                }
            } else {
                ToggleTimerIcon(
                    onClick = { managerVM.changeStateByCtrlTimer() },
                    isPaused = managerVM.isPausedTimer(
                        stateSerie1 = state,
                        stateSerie2 = state
                    )
                )
            }
        }
    }
}