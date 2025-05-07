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
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_FILTRO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_GRUPOS_MUSCULARES
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_NIVEL
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.dialog.EjercicioDialogDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.dialog.FilterEjercicioDialogVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.CheckMusculoList
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.CheckNivelList


@Composable
fun FilterEjercicioDialog(
    dismissDialog: () -> Unit,
    params: EjercicioDialogDto,
    filterEjercicioDialogVM: FilterEjercicioDialogVM = hiltViewModel()
) {
    val musculoSetTemp by filterEjercicioDialogVM.musculoSetTemp.collectAsState()
    val nivelSetTemp by filterEjercicioDialogVM.nivelSetTemp.collectAsState()

    filterEjercicioDialogVM.initData(
        musculoSet = params.musculoSet,
        nivelSet = params.nivelSet
    )

    Dialog(
        onDismissRequest = { filterEjercicioDialogVM.onDismissDialog(dismissDialog) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = LABEL_FILTRO,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 16.dp,
                        top = 8.dp
                    ),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = LABEL_GRUPOS_MUSCULARES,
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
                onChangeMusculoSet = { filterEjercicioDialogVM.setMusculoSetTemp(it) }
            )

            Text(
                text = LABEL_NIVEL,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 16.dp,
                        top = 8.dp
                    ),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall
            )
            CheckNivelList(
                nivelSetTemp = nivelSetTemp,
                onChangeNivelSet = { filterEjercicioDialogVM.setNivelSetTemp(it) }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = LABEL_CANCELAR_UPPER,
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                            start = 8.dp,
                            bottom = 8.dp
                        )
                        .clickable { filterEjercicioDialogVM.onDismissDialog(dismissDialog) }
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
                            filterEjercicioDialogVM.onAcept(
                                onChangeMusculoSet = params.onChangeMusculoSet,
                                onChangeNivelSet = params.onChangeNivelSet,
                                dismissDialog = dismissDialog
                            )
                        },
                    text = LABEL_ACEPTAR_UPPER,
                )
            }
        }
    }
}