package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.Black
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.White

@Composable
fun ImageFromUriMini(
    photoUri: Uri,
    contentDescription: String
) {
    if (photoUri.path != Uri.EMPTY.path) {
        AsyncImage(
            model = photoUri,
            contentDescription = contentDescription,
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .background(
                    White,
                    RoundedCornerShape(8.dp)
                )
                .clip(
                    RoundedCornerShape(8.dp)
                )
                .border(2.dp, Black, RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Fit,
        )
    }
}