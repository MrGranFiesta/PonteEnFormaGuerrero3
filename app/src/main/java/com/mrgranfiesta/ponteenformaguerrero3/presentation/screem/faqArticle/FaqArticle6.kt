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
fun FaqArticle6() {
    Text("Si se encuentra usando la cuenta de invitado no aparecerá ninguna opción para editar la foto de perfil. En la pantalla de información del usuario, pulsa el icono de agregar foto de perfil.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man6_1".toUri())
    Spacer(modifier = Modifier.height(8.dp))
    Text("Elige la opción “Galería”. Si no tiene una foto de perfil con anterioridad, este paso se omite automáticamente.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man6_2".toUri())
    Spacer(modifier = Modifier.height(8.dp))
    Text("A continuación selecciona una foto.")
    FAQImagenFromUri("${DRAWABLE_URI}man6_3".toUri())
    Spacer(modifier = Modifier.height(8.dp))
}