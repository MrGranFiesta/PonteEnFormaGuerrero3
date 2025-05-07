package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.StringUtils
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.CountdownVM

@Composable
fun Countdown(
    fontSize: TextUnit = 30.sp,
    autoPlay: Boolean = true,
    countdownVM: CountdownVM = hiltViewModel(),
    initMiliseconds: Long,
    actionFinish: () -> Unit
) {
    countdownVM.initData(initMiliseconds, autoPlay)

    val endTime by countdownVM.endTime.collectAsState()
    val isActive by countdownVM.isActive.collectAsState()
    val forzeRecomposition by countdownVM.forzeRecomposition.collectAsState()

    LaunchedEffect(key1 = endTime, key2 = isActive, key3 = forzeRecomposition) {
        countdownVM.iterationTime(
            actionFinish = actionFinish,
            this
        )
    }
    Text(
        maxLines = 1,
        fontSize = fontSize,
        text = StringUtils.timeMinDigitalFormat(endTime)
    )
}