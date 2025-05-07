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
fun FaqArticle27() {
    Text("Se debe mantener pulsado el ejercicio que se quiera reordenar y arrastrar hacia arriba o abajo el ejercicio hasta la posición que se le quiera asignar.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man27_1".toUri())
}