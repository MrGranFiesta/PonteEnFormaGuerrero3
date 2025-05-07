package com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_BORRAR
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CANCELAR_UPPER
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_GALERY
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_SELECT_OPTION_DIALOG
import kotlin.Unit

@Composable
fun SelectOptionPhotoDialog(
    dismissDialog: () -> Unit,
    onClickDelete: () -> Unit,
    onClickOpenAlbum: () -> Unit
) {
    Dialog(
        onDismissRequest = { dismissDialog() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = LABEL_SELECT_OPTION_DIALOG,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconWithBorder(
                    text = LABEL_BORRAR,
                    icon = Icons.Filled.Delete,
                    onClick = {
                        onClickDelete()
                        dismissDialog()
                    }
                )

                VerticalDivider(
                    modifier = Modifier
                        .height(100.dp)
                        .padding(vertical = 8.dp),
                    color = Color.Gray,
                    thickness = 1.dp
                )

                IconWithBorder(
                    text = LABEL_GALERY,
                    icon = Icons.Filled.PhotoLibrary,
                    onClick = {
                        onClickOpenAlbum()
                        dismissDialog()
                    }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
            ) {
                Text(
                    text = LABEL_CANCELAR_UPPER,
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                            start = 8.dp,
                            bottom = 8.dp
                        )
                        .clickable { dismissDialog() }
                    //style = MaterialTheme.typography.caption
                )

            }
        }
    }
}

@Composable
fun IconWithBorder(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(120.dp)
            .border(width = 2.dp, color = Color.Gray, shape = RectangleShape)
            .clickable {
                onClick()
            }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(72.dp),
                imageVector = icon,
                contentDescription = ""
            )
            Text(text)
        }
    }
}