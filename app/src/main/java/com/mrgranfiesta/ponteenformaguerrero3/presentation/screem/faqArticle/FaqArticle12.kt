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
fun FaqArticle12() {
    Text("En la pantalla de información del ejercicio que se quiere editar, pulsa el botón con forma de lápiz.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man12_1".toUri())
    Spacer(modifier = Modifier.height(8.dp))
    Text("Modifica los campos necesarios en el formulario y guarda los cambios pulsando el icono con forma de check.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man12_2".toUri())
    Spacer(modifier = Modifier.height(8.dp))
    Text("Pulsa “Si” para confirmar los cambios realizados.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man12_3".toUri())
    Spacer(modifier = Modifier.height(8.dp))
}