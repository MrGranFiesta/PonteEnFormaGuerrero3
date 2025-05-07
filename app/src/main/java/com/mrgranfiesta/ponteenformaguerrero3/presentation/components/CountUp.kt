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
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.CountUpVM

@Composable
fun CountUp(
    fontSize: TextUnit = 30.sp,
    autoPlay: Boolean = true,
    timeAction: Long = Long.MAX_VALUE,
    countUpVM: CountUpVM = hiltViewModel(),
    initMiliseconds: Long = 0,
    actionByTime: () -> Unit = {}
) {
    countUpVM.initData(initMiliseconds, autoPlay)

    val acumulatorTime by countUpVM.acumulatorTime.collectAsState()
    val isActive by countUpVM.isActive.collectAsState()
    val forzeRecomposition by countUpVM.forzeRecomposition.collectAsState()

    LaunchedEffect(key1 = acumulatorTime, key2 = isActive, key3 = forzeRecomposition) {
        countUpVM.iterationTime(
            actionByTime = actionByTime,
            timeAction = timeAction,
            launcher = this
        )
    }
    Text(
        fontSize = fontSize,
        text = StringUtils.timeMinDigitalFormat(acumulatorTime)
    )
}