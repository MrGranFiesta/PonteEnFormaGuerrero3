package com.mrgranfiesta.ponteenformaguerrero3.presentation.components.subcomponents

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.StateSerie
import com.mrgranfiesta.ponteenformaguerrero3.domain.template.IManagerSerie
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.CountdownVM

@Composable
fun RepsCard(
    modifier: Modifier,
    managerVM: IManagerSerie,
    state: StateSerie,
    countdownVM: CountdownVM,
    isInverse: Boolean
) {
    Card(
        modifier = modifier
            .height(48.dp)
    ) {
        if (managerVM.isShow(state)) {
            RepsAndRestTimer(
                managerVM = managerVM,
                isActive = managerVM.isActive(state),
                countdownVM = countdownVM,
                isInverse = isInverse
            )
        } else {
            RowStateSerieEmpty()
        }
    }
}
