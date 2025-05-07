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
fun FaqArticle5() {
    Text("Si se encuentra usando la cuenta de invitado no aparecerá ninguna opción para cambiar la contraseña. En la pantalla de información del usuario, pulsa el botón “Cambiar contraseña”.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man5_1".toUri())
    Spacer(modifier = Modifier.height(8.dp))
    Text("Rellena los datos y pulsa el botón de aceptar.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man5_2".toUri())
    Spacer(modifier = Modifier.height(8.dp))
    Text("• 1: Campo para introducir la contraseña anterior.")
    Text("• 2: Campo para introducir la contraseña nueva.")
    Text("• 3: Campo para confirmar la contraseña nueva.")
    Text("• 4: Icono de guardado.")
}