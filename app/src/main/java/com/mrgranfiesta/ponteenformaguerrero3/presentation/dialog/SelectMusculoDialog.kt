package com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ACEPTAR_UPPER
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CANCELAR_UPPER
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_SELECT_MUSCULOS
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.dialog.SelectMusculoDialogVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.CheckMusculoList

@Composable
fun SelectMusculoDialog(
    dismissDialog: () -> Unit,
    musculoSetOrigen: Set<Musculo>,
    onChangeMusculoSet: (Set<Musculo>) -> Unit = {},
    selectMusculoDialogVM: SelectMusculoDialogVM = hiltViewModel()
) {
    val musculoSetTemp by selectMusculoDialogVM.musculoSet.collectAsState()
    selectMusculoDialogVM.initData(musculoSetOrigen)
    Dialog(
        onDismissRequest = { selectMusculoDialogVM.onDismiss(dismissDialog) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = LABEL_SELECT_MUSCULOS,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 16.dp,
                        top = 8.dp
                    ),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall
            )
            CheckMusculoList(
                musculoSetTemp = musculoSetTemp,
                onChangeMusculoSet = { selectMusculoDialogVM.setMusculoSetTemp(it) }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = LABEL_CANCELAR_UPPER,
                    modifier = Modifier
                        .clickable {
                            selectMusculoDialogVM.onDismiss(dismissDialog)
                        }
                        .padding(
                            top = 16.dp,
                            start = 8.dp,
                            bottom = 8.dp
                        )
                    //style = MaterialTheme.typography.caption
                )
                Text(
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                            end = 8.dp,
                            bottom = 8.dp
                        )
                        .clickable {
                            selectMusculoDialogVM.onAcept(
                                onChangeMusculoSet = onChangeMusculoSet,
                                dismissDialog = dismissDialog,
                            )
                        },
                    text = LABEL_ACEPTAR_UPPER,
                    //style = MaterialTheme.typography.caption
                )
            }
        }
    }
}