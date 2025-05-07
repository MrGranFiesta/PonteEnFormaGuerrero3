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
fun FaqArticle26() {
    Text("En el formulario de edición de una rutina, en la tarjeta del paso se debe pulsar el botón de lápiz.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man26_1".toUri())
    Spacer(modifier = Modifier.height(8.dp))
    Text("Después aparecerá un cuadro de diálogo para editar la configuración del ejercicio. Para confirmar los cambios se debe pulsar en el botón “Aceptar”.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man26_2".toUri())
}