package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_DELETE_IMG_TOOLSTIP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_UPLOAD_IMG_TOOLSTIP
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.ImageFromUriVM
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.Black
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.GrayLight
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.Transparent
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.White
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ImageFromUri(
    modifier: Modifier = Modifier,
    photoUri: Uri,
    onClickUpload: () -> Unit = {},
    onClickDelete: () -> Unit = {},
    isActiveEdit: Boolean = false,
    contentDescription: String,
    imageFromUriVM: ImageFromUriVM = hiltViewModel()
) {
    val interationIconNav = imageFromUriVM.interationUpdBt

    LaunchedEffect(interationIconNav) {
        interationIconNav.interactions.collectLatest { interaction ->
            imageFromUriVM.actionUpdateButton(
                onClickUpload = onClickUpload,
                interaction = interaction
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .background(
                GrayLight,
                RoundedCornerShape(8.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .border(
                    2.dp, Black, RoundedCornerShape(8.dp)
                )
                .background(
                    Transparent,
                    RoundedCornerShape(8.dp)
                )
                .height(
                    height = 300.dp
                )
        ) {
            if (photoUri.path != Uri.EMPTY.path) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            White,
                            RoundedCornerShape(8.dp)
                        )
                        .clip(
                            RoundedCornerShape(8.dp)
                        ),
                    contentScale = ContentScale.Fit,
                    model = photoUri,
                    contentDescription = contentDescription
                )
            } else {
                NoImgUri()
            }
        }
        if (isActiveEdit) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButtonWithTooltip(
                    tooltipText = LABEL_UPLOAD_IMG_TOOLSTIP,
                    onClick = {},
                    interactionSource = interationIconNav
                ) {
                    Icon(
                        modifier = Modifier
                            .size(48.dp),
                        imageVector = Icons.Filled.ArrowUpward,
                        contentDescription = "Subir imagen",
                    )
                }
                IconButtonWithTooltip(
                    tooltipText = LABEL_DELETE_IMG_TOOLSTIP,
                    onClick = { onClickDelete() },
                ) {
                    Icon(
                        modifier = Modifier
                            .size(48.dp),
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Eliminar Imagen",
                    )
                }
            }
        }
    }
}