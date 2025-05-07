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
fun FaqArticle28() {
    Text("El flujo de un entrenamiento se divide en 4 etapas ordenadas.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man28_1".toUri())
    Spacer(modifier = Modifier.height(8.dp))
    Text("Dependiendo de la configuración de la rutina, las etapas de calentamiento, movilidad articular y estiramiento se pueden realizar, omitir o preguntar antes de hacerla.")
    Text("Cada etapa se divide de un conjunto de ejercicio y a su vez cada ejercicio se divide en varias series, todos los ejercicios se deben realizar para avanzar a la siguiente etapa. Los ejercicios simétricos tienen que hacerse en ambas partes del cuerpo por lo que habrá 2 contadores de series, una para cada lado del cuerpo.")
}