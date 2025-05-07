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
fun FaqArticle1() {
    Text("Abre la aplicación y pulsa el texto “Entrar como invitado”.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man1_1".toUri())
    Text("• 1: Campo correo electrónico del usuario.")
    Text("• 2: Campo contraseña del usuario.")
    Text("• 3: Botón “Iniciar sesión”.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man1_2".toUri())
    Spacer(modifier = Modifier.height(8.dp))
    Text("Cuando un usuario inicia sesión, la próxima vez que abra la app no será necesario que vuelva a iniciar sesión.")
}