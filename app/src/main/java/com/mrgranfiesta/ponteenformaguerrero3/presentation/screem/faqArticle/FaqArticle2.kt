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
fun FaqArticle2() {
    Text("Abre la aplicación y pulsa el botón de “Crear cuenta”.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man2_1".toUri())
    Spacer(modifier = Modifier.height(8.dp))
    Text("Rellena todos los campos necesarios y pulsa el botón “Registrarse”.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man2_2".toUri())
    Text("• 1: Campo foto de perfil del usuario, es opcional.")
    Text("• 2: Campo nombre del usuario.")
    Text("• 3: Campo correo electrónico del usuario.")
    Text("• 4: Campo contraseña del usuario.")
    Text("• 5: Campo confirmar contraseña del usuario.")
    Text("• 6: Botón “Registrarse”.")
}