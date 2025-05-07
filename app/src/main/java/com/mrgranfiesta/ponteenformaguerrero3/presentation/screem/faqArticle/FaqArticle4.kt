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
fun FaqArticle4() {
    Text("Abre el menú con el icono de hamburguesa. Si se encuentra usando la cuenta de invitado no aparecerá la opción de ver el perfil del usuario.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man4_1".toUri())
    Spacer(modifier = Modifier.height(8.dp))
    Text("Pulsa sobre la foto de perfil o en la opción de perfil del menú desplegable.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man4_2".toUri())
    Spacer(modifier = Modifier.height(8.dp))
    Text("Se visualizará de la siguiente manera la información del usuario.")
}