package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_COMPLETADO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_FALLIDO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_RUTINA_PREMIUN_TOOLSTIP
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.stat.StatRutinaSearchDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.DateUtils
import com.mrgranfiesta.ponteenformaguerrero3.presentation.navigations.AppScreem
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.Green700
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.Red700

@Composable
fun RowStat(
    stat: StatRutinaSearchDto,
    navController: NavController
) {
    val navigate = {
        navController.navigate(
            route = AppScreem.StatRutinaInfoScreem.createRoute(stat.idStat)
        )
    }

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
                top = 8.dp,
            )
            .border(
                width = 2.dp,
                brush = if (stat.isPremium) gradientBrush else trasparentBrush,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { navigate() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(
                        top = 8.dp,
                        start = 16.dp,
                        bottom = 8.dp
                    )
                    .weight(1f)
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Filled.CalendarMonth,
                        contentDescription = "Fecha"
                    )
                    Text(text = DateUtils.toStringFormat(stat.dateInit, "dd-MM-yyyy"))
                }
                Text(
                    text = stat.nombre,
                    modifier = Modifier
                        .padding(top = 8.dp)
                )
                TextNivel(
                    nivel = stat.nivel,
                    clickEvent = {
                        navigate()
                    },
                    modifier = Modifier.padding(
                        top = 16.dp,
                    )
                )
            }
            Column(
                modifier = Modifier
                    .wrapContentWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Row(
                    modifier = Modifier
                        .padding(
                            top = 8.dp,
                            end = 8.dp,
                            start = 8.dp
                        )
                ) {
                    if (stat.isCompleted) {
                        Icon(
                            tint = Green700,
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Completado"
                        )
                        Text(
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            text = LABEL_COMPLETADO
                        )
                    } else {
                        Icon(
                            tint = Red700,
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Fallido"
                        )
                        Text(
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            text = LABEL_FALLIDO
                        )
                    }
                }
                if (stat.isPremium) {
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