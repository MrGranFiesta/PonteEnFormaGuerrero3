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
fun FaqArticle17() {
    Text("En la pantalla de información del material que quiere editar, pulsa el botón con forma de lápiz.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man17_1".toUri())
    Spacer(modifier = Modifier.height(8.dp))
    Text("Rellena los datos del formulario. Pulsa el botón con forma de check para guardar los cambios.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man17_2".toUri())
    Spacer(modifier = Modifier.height(8.dp))
    Text("Aparecerá un diálogo de confirmación, pulsa en la opción “Si” para confirmar.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man17_3".toUri())
}