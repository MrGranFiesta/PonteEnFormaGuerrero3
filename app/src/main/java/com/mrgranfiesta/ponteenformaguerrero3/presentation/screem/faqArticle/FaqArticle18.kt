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
fun FaqArticle18() {
    Text("En la pantalla de información del material que quiere borrar, pulsa el botón con forma de papelera.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man18_1".toUri())
    Spacer(modifier = Modifier.height(8.dp))
    Text("Aparecerá un diálogo de confirmación, pulsa en la opción “Si” para confirmar.")
    Spacer(modifier = Modifier.height(8.dp))
    FAQImagenFromUri("${DRAWABLE_URI}man18_2".toUri())
    Spacer(modifier = Modifier.height(8.dp))
    Text("Cuando un usuario inicia sesión, la próxima vez que abra la app no será necesario que vuelva a iniciar sesión.")
}