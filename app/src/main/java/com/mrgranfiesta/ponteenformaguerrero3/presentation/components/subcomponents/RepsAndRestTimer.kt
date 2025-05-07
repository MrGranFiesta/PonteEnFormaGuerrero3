package com.mrgranfiesta.ponteenformaguerrero3.presentation.components.subcomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_REPS
import com.mrgranfiesta.ponteenformaguerrero3.domain.template.IManagerSerie
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.CountdownVM

@Composable
fun RepsAndRestTimer(
    managerVM: IManagerSerie,
    isActive: Boolean,
    countdownVM: CountdownVM,
    isInverse: Boolean
) {
    val cantidad = managerVM.getCantidad()

    if (isActive) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$cantidad $LABEL_REPS",
                fontSize = 30.sp
            )
        }
    } else {
        TimerWithButtons(
            managerVM = managerVM,
            countdownVM = countdownVM,
            isInverse = isInverse
        )
    }
}