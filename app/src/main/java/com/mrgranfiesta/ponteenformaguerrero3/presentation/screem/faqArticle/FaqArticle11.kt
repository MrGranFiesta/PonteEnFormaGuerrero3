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
fun FaqArticle11() {
    Text("En la pantalla principal de ejercicios, selecciona el botón que contiene un \"+”.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man11_1".toUri())
    Spacer(modifier = Modifier.height(8.dp))
    Text("Rellena los datos del formulario. Pulsa el botón con forma de check para guardar el nuevo ejercicio.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man11_2".toUri())
    Text("• 1: Campo para introducir el nombre del ejercicio.")
    Text("• 2: Campo para introducir la foto del ejercicio.")
    Text("• 3: Campo para seleccionar el nivel del ejercicio.")
    Text("• 4: Campo para activar o desactivar la simetría del ejercicio. La simetría implica que el ejercicio se debe realizar en 2 partes del cuerpo.")
    Text("• 5: Campo para seleccionar los músculos que se entrenan con el ejercicio.")
    Text("• 6: Campo para introducir la descripción del ejercicio.")
    Text("• 7: Campo para seleccionar materiales que se usan para realizar el ejercicio.")
    Text("• 8: Icono de guardado.")
    Spacer(modifier = Modifier.height(8.dp))
    Text("Pulsa “Si” para confirmar el nuevo ejercicio.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man11_3".toUri())
}