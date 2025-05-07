package com.mrgranfiesta.ponteenformaguerrero3.presentation.components.subcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EJERCICIO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_SERIE
import com.mrgranfiesta.ponteenformaguerrero3.domain.core.component.getColorDisableBy
import com.mrgranfiesta.ponteenformaguerrero3.domain.template.IManagerSerie

@Composable
fun CtrlNavigationRutina(
    managerVM: IManagerSerie,
    modifier: Modifier = Modifier
) {
    val cursor = managerVM.cursorStep
    val serie1 by managerVM.serie1.collectAsState()
    val iconColorSerie = getColorDisableBy(condiccion = serie1 == 0 && cursor == 0)
    val iconColorEjercicio = getColorDisableBy(condiccion = cursor == 0)

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { managerVM.previousSerie() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "previus Serie",
                        tint = iconColorSerie
                    )
                }
                Text(text = LABEL_SERIE)
                IconButton(onClick = { managerVM.nextSerie() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "next Serie"
                    )
                }
            }
        }
        Spacer(
            modifier = Modifier.background(
                MaterialTheme.colorScheme.onBackground
            ).height(2.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { managerVM.previousStep(isResetLastSerie = false) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "previus Ejercicio",
                        tint = iconColorEjercicio
                    )
                }
                Text(text = "$LABEL_EJERCICIO ${cursor + 1}/${managerVM.getNumbersOfStep()}")
                IconButton(onClick = { managerVM.nextStep() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "next Ejercicio",
                    )
                }
            }
        }
    }
}