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
fun FaqArticle24() {
    Text("En el formulario de edición de una rutina, se debe pulsar el botón “Agregar ejercicio”.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man24_1".toUri())
    Spacer(modifier = Modifier.height(8.dp))
    Text("Después se debe seleccionar el ejercicio que se quiere configurar.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man24_2".toUri())
    Spacer(modifier = Modifier.height(8.dp))
    Text("Una vez seleccionado aparecerá un cuadro de diálogo que permite agregar la configuración del ejercicio. Existen 2 configuraciones posibles: repetición y tiempo. Si el ejercicio es de repeticiones se debe usar la unidad de repeticiones, si tu ejercicio es de tiempo, como unidad se debe usar segundos. Al finalizar debemos pulsar el botón de “Aceptar”.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man24_3".toUri())
    Spacer(modifier = Modifier.height(8.dp))
}