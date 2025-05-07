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
fun FaqArticle9() {
    Text("Si se encuentra usando la cuenta de invitado no aparecerá ninguna opción para suscribirse al plan premium. En la pantalla de información del usuario, pulsa el botón “Probar premium gratis por 7 días”.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man9_1".toUri())
    Spacer(modifier = Modifier.height(8.dp))
    Text("Pulsa en el cuadro de diálogo la opción “Si”.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man9_2".toUri())
}