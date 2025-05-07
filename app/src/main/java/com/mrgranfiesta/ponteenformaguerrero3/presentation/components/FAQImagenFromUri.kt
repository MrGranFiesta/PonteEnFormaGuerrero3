package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.White

@Composable
fun FAQImagenFromUri(
    photoUri: Uri
){
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .clip(RoundedCornerShape(8.dp))
            .background(
                White,
                RoundedCornerShape(8.dp)
            )
            .clip(
                RoundedCornerShape(8.dp)
            ),
        contentScale = ContentScale.Fit,
        model = photoUri,
        placeholder = ColorPainter(Color(0xFFE0E0E0)), // Placeholder gris claro
        error = ColorPainter(Color(0xFFE57373)), // Fondo rojizo en caso de error
        contentDescription = ""
    )
}