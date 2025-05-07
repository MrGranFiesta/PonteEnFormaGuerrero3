package com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CANCELAR_UPPER
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_SELECT_NIVEL
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.ListManager
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.CastingClass

@Composable
fun SelectNivelDialog(
    dismissDialog: () -> Unit,
    changeNivel: (Nivel) -> Unit,
    nivelList: List<Nivel> = ListManager.getNivelList()
) {
    Dialog(
        onDismissRequest = { dismissDialog() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = LABEL_SELECT_NIVEL,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, top = 8.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall
            )
            nivelList.forEach {
                NivelBrush(nivel = it) { x ->
                    changeNivel(x)
                    dismissDialog()
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = LABEL_CANCELAR_UPPER,
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                            start = 16.dp,
                            bottom = 16.dp
                        )
                        .clickable { dismissDialog() },
                    textAlign = TextAlign.Start,
                    //style = MaterialTheme.typography.h2
                )
            }
        }
    }
}

@Composable
fun NivelBrush(nivel: Nivel, clickEvent: (Nivel) -> Unit) {
    Text(
        text = nivel.toString(),
        modifier = Modifier
            .fillMaxWidth()
            .background(CastingClass.brushByNivel(nivel))
            .clickable { clickEvent(nivel) },
        color = CastingClass.colorByNivel(nivel),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleSmall
    )
}