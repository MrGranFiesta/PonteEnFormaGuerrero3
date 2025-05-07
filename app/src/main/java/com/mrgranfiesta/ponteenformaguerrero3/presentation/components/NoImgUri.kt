package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HideImage
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_PHOTO_URI
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.Black
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.GrayLight

@Composable
fun NoImgUri() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(
                RoundedCornerShape(8.dp)
            ).height(
                height = 200.dp
            ).border(2.dp, Black, RoundedCornerShape(8.dp))
            .background(GrayLight),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(48.dp),
            imageVector = Icons.Filled.HideImage,
            contentDescription = "No hay imagen seleccionada"
        )
        Text(
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(
                top = 8.dp
            ),
            text = LABEL_PHOTO_URI
        )
    }
}