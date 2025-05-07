package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.Red200

@Composable
fun ErrorRowItem(
    modifier: Modifier = Modifier,
    errorTxt: String = ""
) {
    Card(
        elevation = cardElevation(8.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(64.dp)
                    .padding(
                        top = 8.dp
                    ),
                tint = Red200,
                imageVector = Icons.Filled.ErrorOutline,
                contentDescription = "Error"
            )
            Text(
                text = errorTxt,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 8.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 8.dp
                    )
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )
        }
    }
}
