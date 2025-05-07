package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_KG

@Composable
fun RowMaterialWithConf(
    photoUri : Uri,
    nombre : String,
    isMaterialWeight : Boolean,
    confValue : Double
) {
    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp,
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            ImageFromUriMini(
                photoUri = photoUri,
                contentDescription = ""
            )
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = nombre,
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                        )
                )
                if (isMaterialWeight) {
                    Text(
                        text = "$confValue $LABEL_KG",
                        modifier = Modifier
                            .padding(
                                start = 8.dp,
                            )
                    )
                }
            }
        }
    }
}