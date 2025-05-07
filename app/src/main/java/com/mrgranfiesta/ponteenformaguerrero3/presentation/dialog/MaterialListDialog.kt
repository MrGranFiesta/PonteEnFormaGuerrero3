package com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CERRAR
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_MATERIAL_LIST_DIALOG
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialWithConfDto
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.RowMaterialWithConf

@Composable
fun MaterialListDialog(
    dismissDialog: () -> Unit,
    confMaterialList: List<RowMaterialWithConfDto>
) {
    Dialog(
        onDismissRequest = { dismissDialog() },
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = LABEL_MATERIAL_LIST_DIALOG,
                modifier = Modifier.fillMaxWidth().padding(
                    bottom = 16.dp,
                    top = 8.dp
                ),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall
            )
            LazyColumn(
                modifier = Modifier.padding(8.dp)
            ) {
                items(confMaterialList) {
                    RowMaterialWithConf(
                        photoUri = it.photoUri,
                        nombre = it.nombre,
                        isMaterialWeight = it.isMaterialWeight,
                        confValue = it.confValue
                    )
                }
                item {
                    Text(
                        text = LABEL_CERRAR,
                        modifier = Modifier
                            .padding(
                                top = 16.dp,
                                end = 8.dp,
                                bottom = 8.dp
                            )
                            .clickable { dismissDialog() }
                    )
                }
            }
        }
    }
}