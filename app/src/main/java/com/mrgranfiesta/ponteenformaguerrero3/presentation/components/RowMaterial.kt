package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.RowSelectMaterialVM

@Composable
fun RowMaterial(
    nombre : String,
    photoUri : Uri,
    onClickItem: () -> Unit = {},
    isSelectionable: Boolean = false,
    initialSelect: Boolean = false,
    onClickCheck: (Boolean) -> Unit = {},
    rowSelectMaterialVM: RowSelectMaterialVM = hiltViewModel(),
) {
    rowSelectMaterialVM.initData(initialSelect)

    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp,
            )
            .clickable {
                onClickItem()
                rowSelectMaterialVM.onClickItem(onClickCheck)
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp,
                    end = 8.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
                .clickable {
                    onClickItem()
                    rowSelectMaterialVM.onClickItem(onClickCheck)
                },
        ) {
            if (isSelectionable) {
                val isChecked by rowSelectMaterialVM.isChecked.collectAsState()
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {
                        rowSelectMaterialVM.onClickItem(onClickCheck)
                    },
                    colors = CheckboxDefaults.colors(MaterialTheme.colorScheme.primary)
                )
            }
            ImageFromUriMini(
                photoUri = photoUri,
                contentDescription = ""
            )
            Text(
                text = nombre,
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                    )
            )
        }
    }
}