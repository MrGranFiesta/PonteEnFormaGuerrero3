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
fun FaqArticle16() {
    Text("En la pantalla principal de materiales, selecciona el botón que contiene un \"+”.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man16_1".toUri())
    Spacer(modifier = Modifier.height(8.dp))
    Text("Rellena los datos del formulario. Pulsa el botón con forma de check para guardar el nuevo material.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man16_2".toUri())
    Text("• 1: Campo para introducir el nombre del material.")
    Text("• 2: Campo  para introducir la foto del material.")
    Text("• 3: Campo para catalogar el material con peso ajustable. Esto sirve para que se pueda configurar el peso en Kg a la hora de crear una rutina.")
    Text("• 4: Campo para introducir la descripción del material.")
    Text("• 5: Icono de guardado.")
}