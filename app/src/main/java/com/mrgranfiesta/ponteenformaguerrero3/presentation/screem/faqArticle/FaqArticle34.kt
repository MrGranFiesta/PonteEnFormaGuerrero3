package com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DRAWABLE_URI
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.FAQImagenFromUri

@Composable
fun FaqArticle34() {
    Text("La funcionalidad de estadísticas esta solo disponible para usuarios premium. En la pantalla principal de estadísticas, pulsa la estadística que quiere visualizar.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man34_1".toUri())
    Spacer(modifier = Modifier.height(8.dp))
    Text("Se visualizará de la siguiente manera.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man34_2".toUri())
    Spacer(modifier = Modifier.height(8.dp))
}