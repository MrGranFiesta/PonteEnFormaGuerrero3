package com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DRAWABLE_URI
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.FAQImagenFromUri
import androidx.core.net.toUri

@Composable
fun FaqArticle7() {
    Text("Si se encuentra usando la cuenta de invitado no aparecerá ninguna opción para editar el nombre de usuario. En la pantalla de información del usuario, pulsa el icono con forma de lápiz.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man7_1".toUri())
    Spacer(modifier = Modifier.height(8.dp))
    Text("Escribe el nuevo nombre de usuario y pulsa el botón aceptar.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man7_2".toUri())
}