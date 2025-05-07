package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_RUTINA_PREMIUN_TOOLSTIP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.StringUtils.Companion.musculosSetToString

@Composable
fun RowRutina(
    nombre: String,
    nivel: Nivel,
    musculoSet: Set<Musculo>,
    isPremium: Boolean,
    navigation: () -> Unit
) {
    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(Color.Magenta, Color.Cyan)
    )

    val trasparentBrush = Brush.horizontalGradient(
        colors = listOf(Color.Transparent, Color.Transparent)
    )

    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp
            )
            .border(
                width = 2.dp,
                brush = if (isPremium) gradientBrush else trasparentBrush,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                navigation()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(
                        start = 16.dp,
                        bottom = 8.dp
                    )
                    .weight(1f)
            ) {
                Text(
                    text = nombre,
                    modifier = Modifier
                        .padding(top = 8.dp)
                )
                TextNivel(
                    nivel = nivel,
                    clickEvent = {
                        navigation()
                    },
                    modifier = Modifier.padding(
                        top = 16.dp,
                    )
                )
                if (musculoSet.isNotEmpty()) {
                    Text(
                        text = musculosSetToString(musculoSet),
                        modifier = Modifier
                            .padding(
                                top = 16.dp,
                            )
                    )
                }
            }
            if (isPremium) {
                Box {
                    IconButtonWithTooltip(
                        tooltipText = LABEL_RUTINA_PREMIUN_TOOLSTIP,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.WorkspacePremium,
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }
}
