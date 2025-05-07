package com.mrgranfiesta.ponteenformaguerrero3.presentation.components.subcomponents

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_COMPLETADO_BT
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.StateSerie
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem.ManagerDualRepsVM

@Composable
fun CompletionButton(
    managerVM: ManagerDualRepsVM,
    modifier: Modifier,
    state: StateSerie,
    isInverse: Boolean,
) {
    val context = LocalContext.current

    if (managerVM.isActiveNoWait(state)) {
        Button(
            modifier = modifier
                .height(48.dp),
            onClick = { managerVM.iteratorTimer(isInverse, context) }
        ) {
            Icon(imageVector = Icons.Filled.Done, contentDescription = "Ejercicio Completado")
            Text(
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = LABEL_COMPLETADO_BT
            )
        }
    } else {
        Spacer(
            modifier = modifier
                .height(48.dp)
        )
    }
}