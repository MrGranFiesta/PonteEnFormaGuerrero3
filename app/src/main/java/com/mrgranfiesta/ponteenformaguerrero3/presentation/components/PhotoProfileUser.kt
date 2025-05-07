package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.SizeProfile

@Composable
fun PhotoProfileUser(
    size: SizeProfile = SizeProfile.SMALL,
    uri: Uri,
    onClick: () -> Unit = {},
    isEditable: Boolean = false,
    onEditClick: () -> Unit = {},
    editableIcon: ImageVector = Icons.Filled.PersonAdd
) {
    val noPhotoIcon = rememberVectorPainter(Icons.Filled.Person)

    Box(
        modifier = Modifier
            .size(size.dp)
            .padding(8.dp)
    ) {
        if (uri == Uri.EMPTY) {
            Image(
                painter = noPhotoIcon,
                contentDescription = "Foto de perfil predeterminada",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .clickable { onClick() }
            )
        } else {
            AsyncImage(
                model = uri,
                contentDescription = "Foto de perfil",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .clickable { onClick() }
            )
        }

        if (isEditable) {
            IconButton(
                onClick = {
                    onEditClick()
                },
                modifier = Modifier
                    .size(size.dp / 4)
                    .align(Alignment.BottomEnd)
                    .offset((+8).dp, 8.dp)
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = CircleShape
                    )
                    .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
            ) {
                Icon(
                    imageVector = editableIcon,
                    contentDescription = "Editar foto de perfil",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
