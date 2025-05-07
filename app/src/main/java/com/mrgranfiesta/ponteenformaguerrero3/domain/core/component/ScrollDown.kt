package com.mrgranfiesta.ponteenformaguerrero3.domain.core.component

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.core.ScrollUtilVM

@Composable
fun ScrollDown(
    scrollState: ScrollState,
    scrollUtilVM: ScrollUtilVM = hiltViewModel()
) {
    val isScrollDown by scrollUtilVM.isScrollDown.collectAsState()

    val imeState = rememberImeState()
    LaunchedEffect(imeState.value) {
        if (imeState.value && isScrollDown) {
            scrollState.animateScrollTo(scrollState.maxValue, tween(300))
        }
    }

    LaunchedEffect(scrollState.maxValue) {
        if (imeState.value && isScrollDown) {
            scrollUtilVM.setScrollDown(false)
            scrollState.scrollTo(scrollState.maxValue)
        }
    }
}