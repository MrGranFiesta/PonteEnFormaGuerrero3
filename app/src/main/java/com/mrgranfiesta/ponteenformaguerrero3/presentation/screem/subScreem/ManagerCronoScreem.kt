package com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.subScreem

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_LADO_DERECHO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_LADO_IZQUIERDO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_SERIE_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.StateSerie
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.StepEntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.template.IManagerSerie
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.CronoVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem.ConfManagerVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem.ManagerDualCronoVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem.ManagerDualRepsVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem.ManagerSimpleCronoVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem.ManagerSimpleRepsVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.ImageFromUri
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.subcomponents.ChromoDual
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.subcomponents.ChromoSimple
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.subcomponents.CtrlNavigationRutina
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.subcomponents.RepsDual
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.subcomponents.RepsSimple

@Composable
fun ConfManagerCronoScreem(
    nextStateBatch: () -> Unit,
    cronoVM: CronoVM,
    confVM: ConfManagerVM = hiltViewModel(),
    stepList: List<StepEntrenamientoDto>,
    descanso: Int
) {
    val cursorStep by confVM.cursorStep.collectAsState()
    val config = LocalConfiguration.current

    val managerVM = confVM.getOrCreateImagerSerie(
        stepList = stepList,
        descanso = descanso,
        cursorStep = cursorStep
    )

    if (managerVM.initData(
            isPrevious = confVM.isLastSeriePrevius,
            executionExtra = { confVM.setIsLastSeriePrevius(false) },
            nextStateBatch = nextStateBatch
        )
    ) {
        cronoVM.updateExtrasOnPause {
            managerVM.onPause()
        }

        cronoVM.updateExtrasOnResume {
            managerVM.onResume()
        }

        when (config.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                ManagerCronoScreemPortrait(managerVM)
            }

            Configuration.ORIENTATION_LANDSCAPE -> {
                ManagerCronoScreemLandscape(managerVM)
            }

            else -> {
                ManagerCronoScreemPortrait(managerVM)
            }
        }
    }
}

@Composable
fun ManagerCronoScreemPortrait(
    managerVM: IManagerSerie
) {
    val serie1 by managerVM.serie1.collectAsState()
    val serie2 by managerVM.serie2.collectAsState()
    val stateSerie1 by managerVM.stateSerie1.collectAsState()
    val stateSerie2 by managerVM.stateSerie2.collectAsState()
    val isSimetria by managerVM.isSimetria.collectAsState()
    val nombre by managerVM.nombre.collectAsState()
    val photoUri by managerVM.photoUri.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.weight(0.05f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = nombre
        )

        ImageFromUri(
            modifier = Modifier.weight(0.35f),
            photoUri = photoUri,
            contentDescription = "Imagen del ejercicio"
        )
        Column(
            modifier = Modifier.weight(0.6f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isSimetria) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(text = LABEL_LADO_IZQUIERDO)
                    Text(text = LABEL_LADO_DERECHO)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                if (isSimetria) {
                    Text(text = "$LABEL_SERIE_COLON ${serie2}/${managerVM.getNumberOfSerie()}")
                }
                Text(text = "$LABEL_SERIE_COLON ${serie1}/${managerVM.getNumberOfSerie()}")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                if (isSimetria) {
                    RowSerieState(stateSerie2)
                }
                RowSerieState(stateSerie1)

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                when (managerVM) {
                    is ManagerDualCronoVM -> {
                        ChromoDual(
                            modifier = Modifier.weight(1f),
                            managerVM = managerVM
                        )
                    }

                    is ManagerDualRepsVM -> {
                        RepsDual(
                            modifier = Modifier.weight(1f),
                            managerVM = managerVM
                        )
                    }

                    is ManagerSimpleCronoVM -> {
                        ChromoSimple(
                            modifier = Modifier.weight(1f),
                            managerVM = managerVM
                        )
                    }

                    is ManagerSimpleRepsVM -> {
                        RepsSimple(
                            modifier = Modifier.weight(1f),
                            managerVM = managerVM
                        )
                    }
                }
            }
        }
        CtrlNavigationRutina(
            modifier = Modifier.wrapContentHeight(),
            managerVM = managerVM
        )
    }
}

@Composable
fun RowSerieState(state: StateSerie) {
    Row {
        Text(
            textAlign = TextAlign.Center,
            text = StateSerie.getTextState(state)
        )
        Icon(imageVector = state.icon, contentDescription = "State Coundown 1")
    }
}

@Composable
fun ManagerCronoScreemLandscape(managerVM: IManagerSerie) {
    val serie1 by managerVM.serie1.collectAsState()
    val serie2 by managerVM.serie2.collectAsState()
    val stateSerie1 by managerVM.stateSerie1.collectAsState()
    val stateSerie2 by managerVM.stateSerie2.collectAsState()
    val isSimetria by managerVM.isSimetria.collectAsState()
    val nombre by managerVM.nombre.collectAsState()
    val photoUri by managerVM.photoUri.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.4f)
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = nombre
            )
            ImageFromUri(
                photoUri = photoUri,
                contentDescription = "Imagen del ejercicio"
            )
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.6f)
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (isSimetria) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(text = LABEL_LADO_IZQUIERDO)
                    Text(text = LABEL_LADO_DERECHO)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                if (isSimetria) {
                    Text(text = "$LABEL_SERIE_COLON ${serie2}/${managerVM.getNumberOfSerie()}")
                }
                Text(text = "$LABEL_SERIE_COLON ${serie1}/${managerVM.getNumberOfSerie()}")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                if (isSimetria) {
                    RowSerieState(stateSerie2)
                }
                RowSerieState(stateSerie1)

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                when (managerVM) {
                    is ManagerDualCronoVM -> {
                        ChromoDual(
                            modifier = Modifier.weight(1f),
                            managerVM = managerVM
                        )
                    }

                    is ManagerDualRepsVM -> {
                        RepsDual(
                            modifier = Modifier.weight(1f),
                            managerVM = managerVM
                        )
                    }

                    is ManagerSimpleCronoVM -> {
                        ChromoSimple(
                            modifier = Modifier.weight(1f),
                            managerVM = managerVM
                        )
                    }

                    is ManagerSimpleRepsVM -> {
                        RepsSimple(
                            modifier = Modifier.weight(1f),
                            managerVM = managerVM
                        )
                    }
                }
            }
            CtrlNavigationRutina(
                modifier = Modifier.wrapContentHeight(),
                managerVM = managerVM
            )
        }
    }
}