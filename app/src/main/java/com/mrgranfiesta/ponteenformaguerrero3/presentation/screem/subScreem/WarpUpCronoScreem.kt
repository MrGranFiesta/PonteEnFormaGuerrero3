package com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.subScreem

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DRAWABLE_URI
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CALENTAMIENTO_CORRER
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_COMPLETADO_BT
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.SoundFile
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.StateSerie
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.SoundUtils
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.CountUpVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.CronoVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem.WarpUpVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.CountUp
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.ImageFromUri
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.subcomponents.ToggleTimerIcon
import androidx.core.net.toUri

@Composable
fun WarpUpScreem(
    nextStateBatch: () -> Unit,
    cronoVM: CronoVM,
    warnUpVM: WarpUpVM = hiltViewModel(),
    countUp: CountUpVM = hiltViewModel()
) {
    val config = LocalConfiguration.current

    cronoVM.updateExtrasOnPause {
        if (countUp.isPause()) {
            warnUpVM.setIsUserPauseManualy(true)
        } else {
            countUp.stopCountdown()
        }
    }

    cronoVM.updateExtrasOnResume {
        if (warnUpVM.isUserPauseManualy) {
            warnUpVM.setIsUserPauseManualy(false)
        } else {
            countUp.playCountdown()
        }
    }

    when(config.orientation){
        Configuration.ORIENTATION_PORTRAIT -> {
            WarpUpScreemPortrair(
                nextStateBatch = nextStateBatch,
                warnUpVM = warnUpVM,
                countUp = countUp
            )
        }
        Configuration.ORIENTATION_LANDSCAPE -> {
            WarpUpScreemLandScape(
                nextStateBatch = nextStateBatch,
                warnUpVM = warnUpVM,
                countUp = countUp
            )
        }
        else -> {
            //NOTHING TO DO
        }
    }
}

@Composable
fun WarpUpScreemPortrair(
    nextStateBatch: () -> Unit,
    warnUpVM: WarpUpVM = hiltViewModel(),
    countUp: CountUpVM = hiltViewModel()
){
    val state by warnUpVM.state.collectAsState()
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = LABEL_CALENTAMIENTO_CORRER)
        ImageFromUri(
            photoUri = "${DRAWABLE_URI}ejer2_000_000".toUri(),
            contentDescription = "Imagen del ejercicio"
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            RowSerieState(state)
        }
        CountUp(
            fontSize = 70.sp,
            countUpVM = countUp,
            timeAction = 900_000,
            actionByTime = {
                SoundUtils.playSound(context, SoundFile.SWEET_ALERT)
            },
        )
        Button(onClick = {
            nextStateBatch()
        }) {
            Icon(imageVector = Icons.Filled.Done, contentDescription = "Ejercicio Completado")
            Text(text = LABEL_COMPLETADO_BT)
        }
        ToggleTimerIcon(
            onClick = { warnUpVM.changeReverseState(countUp) },
            isPaused = StateSerie.isPause(state)
        )
        Spacer(modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun WarpUpScreemLandScape(
    nextStateBatch: () -> Unit,
    warnUpVM: WarpUpVM = hiltViewModel(),
    countUp: CountUpVM = hiltViewModel()
){
    val state by warnUpVM.state.collectAsState()
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.4f)
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = LABEL_CALENTAMIENTO_CORRER)
            ImageFromUri(
                photoUri = "${DRAWABLE_URI}ejer2_000_000".toUri(),
                contentDescription = "Imagen del ejercicio"
            )
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.6f)
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                RowSerieState(state)
            }
            CountUp(
                fontSize = 70.sp,
                countUpVM = countUp,
                timeAction = 900_000,
                actionByTime = {
                    SoundUtils.playSound(context, SoundFile.SWEET_ALERT)
                },
            )
            Button(onClick = {
                nextStateBatch()
            }) {
                Icon(imageVector = Icons.Filled.Done, contentDescription = "Ejercicio Completado")
                Text(text = LABEL_COMPLETADO_BT)
            }
            ToggleTimerIcon(
                onClick = { warnUpVM.changeReverseState(countUp) },
                isPaused = StateSerie.isPause(state)
            )
            Spacer(modifier = Modifier.padding(16.dp))
        }
    }
}