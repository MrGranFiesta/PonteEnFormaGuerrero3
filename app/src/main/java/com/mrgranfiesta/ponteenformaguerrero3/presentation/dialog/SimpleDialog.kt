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

@Composable
fun SimpleDialog(
    dismissDialog: (Boolean) -> Unit,
    title: String,
    posOpt: String,
    negOpt: String,
    posOptActions: () -> Unit
) {
    Dialog(
        onDismissRequest = { dismissDialog(false) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, top = 8.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = negOpt,
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            bottom = 16.dp
                        )
                        .clickable {
                            dismissDialog(false)
                        }
                )
                Text(
                    text = posOpt,
                    modifier = Modifier
                        .padding(
                            end = 16.dp,
                            bottom = 16.dp
                        )
                        .clickable {
                            dismissDialog(false)
                            posOptActions()
                        },
                )
            }
        }
    }
}