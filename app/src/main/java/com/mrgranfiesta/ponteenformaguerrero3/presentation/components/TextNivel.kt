package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.CastingClass

@Composable
fun TextNivel(
    nivel: Nivel,
    modifier: Modifier = Modifier,
    clickEvent: () -> Unit = {},
) {
    val gradientNivel = CastingClass.brushByNivel(nivel)
    val colorNivel = CastingClass.colorByNivel(nivel)

    Text(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(gradientNivel)
            .width(160.dp)
            .clickable {
                clickEvent()
            }
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 3.dp,
                bottom = 3.dp
            )
            .wrapContentSize(Alignment.Center),
        text = nivel.toString(),
        color = colorNivel,
    )
}