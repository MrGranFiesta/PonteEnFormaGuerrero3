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
fun FaqArticle15() {
    Text("En el formulario de edición de un ejercicio, se debe pulsar el botón “Seleccionar materiales”.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man15_1".toUri())
    Spacer(modifier = Modifier.height(8.dp))
    Text("Después se debe pulsar sobre los materiales que se quieran agregar o eliminar, estarán marcados con un check los materiales seleccionados. Para confirmar se debe pulsar el botón “Seleccionar”.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man15_2".toUri())
    Text("• 1: Material seleccionable con check.")
    Text("• 2: Botón “Seleccionar” para confirmar los materiales del ejercicio.")
}