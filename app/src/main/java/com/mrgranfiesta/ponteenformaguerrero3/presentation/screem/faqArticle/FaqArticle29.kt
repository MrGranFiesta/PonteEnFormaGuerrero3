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
fun FaqArticle29() {
    Text("Las barras de navegación se encuentran debajo de los ejercicios, permiten avanzar o retroceder series y ejercicios de dentro de una rutina que se está ejercitando en el cronómetro.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man29_1".toUri())
    Spacer(modifier = Modifier.height(8.dp))
    Text("• 1: Botón de retroceso de series. Este botón permite ir hacia atrás en la secuencia de series.")
    Text("• 2: Botón de avance de series. Este botón sirve para avanzar a la siguiente serie. Automáticamente al completar una serie se avanzara a la siguiente, por lo que este botón es útil cuando se desea saltar muchas series.")
    Text("• 3: Botón de retroceso de ejercicio. Este botón permite retroceder al ejercicio anterior dentro de una misma serie.")
    Text("• 4:  Botón de avance de ejercicio. Este botón permite avanzar al siguiente ejercicio dentro de la serie actual. Automáticamente al completar la ultima serie del ejercicio se avanzara a la siguiente, por lo que este botón es útil cuando se desea saltar muchos ejercicios.")
}