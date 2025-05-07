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
fun FaqArticle20() {
    Text("En la pantalla principal de rutinas, selecciona el botón que contiene un \"+”.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man20_1".toUri())
    Spacer(modifier = Modifier.height(8.dp))
    Text("Rellena los datos del formulario. Pulsa el botón con forma de check para guardar la nueva rutina.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man20_2".toUri())
    Text("• 1: Campo para introducir el nombre de la rutina.")
    Text("• 2: Campo para seleccionar el nivel de la rutina.")
    Text("• 3: Campo para seleccionar los músculos que se quieren entrenar con la rutina.")
    Text("• 4: Campo para configurar si se desea realizar la fase de calentamiento, omitir o posponer la decisión para más adelante durante la rutina.")
    Text("• 5: Campo para configurar si se desea realizar la fase de movilidad articular, omitir o posponer la decisión para más adelante durante la rutina. ")
    Text("• 6: Campo para configurar si se desea realizar la fase de estiramiento, omitir los o posponer la decisión para más adelante durante la rutina.")
    Text("• 7: Campo para introducir cuanto se desea descansar entre series.")
    Text("• 8: Campo para introducir la descripción de la rutina.")
    Text("• 9: Campo para agregar ejercicios a la rutina.")
    Text("• 10: Icono de guardado.")
    Spacer(modifier = Modifier.height(8.dp))
    Text("Pulsa “Si” para confirmar la nueva rutina.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man20_3".toUri())
    Spacer(modifier = Modifier.height(8.dp))
}