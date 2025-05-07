package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.FloatingButtonVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.FloatingButtonVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FloatingButton(
    floatingButtonVM: FloatingButtonVM = FloatingButtonVMSingle.getFloatingButtonVM()
) {
    val isShow by floatingButtonVM.isShow.collectAsState()
    val contentDescripcion by floatingButtonVM.contentDescripcion.collectAsState()
    val icon by floatingButtonVM.icon.collectAsState()
    val tooltipText by floatingButtonVM.tooltipText.collectAsState()

    val positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider()
    val tooltipState = rememberTooltipState()

    if (isShow) {
        FloatingActionButton(
            contentColor = Color.White,
            containerColor = MaterialTheme.colorScheme.primary,
            onClick = { floatingButtonVM.actionButton() },
        ) {
            TooltipBox(
                positionProvider = positionProvider,
                tooltip = {
                    PlainTooltip(
                        contentColor = MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.background,
                        shadowElevation = 5.dp
                    ) {
                        Text(tooltipText)
                    }
                },
                state = tooltipState
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = contentDescripcion,
                    tint = Color.White
                )
            }
        }
    }
}