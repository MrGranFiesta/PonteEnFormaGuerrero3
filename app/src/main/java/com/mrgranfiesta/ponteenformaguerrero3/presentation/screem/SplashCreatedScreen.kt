package com.mrgranfiesta.ponteenformaguerrero3.presentation.screem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.SplashCreatedScreenVM


@Composable
fun SplashCreatedScreen(
    splashCreatedScreenVM: SplashCreatedScreenVM = hiltViewModel()
) {
    val textInfo by splashCreatedScreenVM.textInfo.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Text(
            textAlign = TextAlign.Center,
            text = textInfo
        )
    }
    splashCreatedScreenVM.initializeApp()
}