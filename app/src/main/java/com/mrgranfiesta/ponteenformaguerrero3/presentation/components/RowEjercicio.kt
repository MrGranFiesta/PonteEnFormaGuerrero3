package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel

@Composable
fun RowEjercicio(
    photoUri : Uri,
    nivel : Nivel,
    nombre : String,
    onClickItem: () -> Unit = {}
) {
    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp
            )
            .clickable { onClickItem() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp,
                    end = 8.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
        ) {
            ImageFromUriMini(
                photoUri = photoUri,
                contentDescription = ""
            )
            Column(
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
            ) {
                Text(
                    text = nombre,
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                        )
                )
                TextNivel(
                    nivel = nivel,
                    clickEvent = { onClickItem() },
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}