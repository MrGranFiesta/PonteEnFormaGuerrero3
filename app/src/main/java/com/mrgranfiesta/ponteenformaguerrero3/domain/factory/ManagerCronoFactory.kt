package com.mrgranfiesta.ponteenformaguerrero3.domain.factory

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.StepEntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.template.IManagerSerie
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.CountdownVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem.ConfManagerVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem.ManagerDualCronoVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem.ManagerDualRepsVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem.ManagerSimpleCronoVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem.ManagerSimpleRepsVM
import java.util.UUID

@Composable
fun getManagerCronoDual(
    confVM: ConfManagerVM,
    stepList: List<StepEntrenamientoDto>,
    descanso: Int,
    cursorStep: Int
): ManagerDualCronoVM {
    val managerVM: ManagerDualCronoVM = viewModel(
        key = "3${UUID.randomUUID()}",
        factory = IManagerSerie.getManager(
            countdownVM1 = viewModel(
                key = "1${UUID.randomUUID()}"
            ),
            countdownVM2 = viewModel(
                key = "2${UUID.randomUUID()}"
            ),
            descanso = descanso,
            stepList = stepList,
            cursorStep = cursorStep,
            plusStep = { confVM.nextCursorStep() },
            minusStep = { confVM.previousCursorStep(it) }
        )
    )
    return managerVM
}

@Composable
fun getManagerCronoSimple(
    confVM: ConfManagerVM,
    stepList: List<StepEntrenamientoDto>,
    descanso: Int,
    cursorStep: Int
): ManagerSimpleCronoVM {
    val managerVM: ManagerSimpleCronoVM = viewModel(
        key = "3${UUID.randomUUID()}",
        factory = IManagerSerie.getManager(
            countdownVM1 = viewModel(
                key = "1${UUID.randomUUID()}",
                factory = CountdownVM.getCountdown()
            ),
            countdownVM2 = viewModel(
                key = "2${UUID.randomUUID()}",
                factory = CountdownVM.getCountdown()
            ),
            descanso = descanso,
            stepList = stepList,
            cursorStep = cursorStep,
            plusStep = { confVM.nextCursorStep() },
            minusStep = { confVM.previousCursorStep(it) }
        )
    )
    return managerVM
}

@Composable
fun getManagerRepsSimple(
    confVM: ConfManagerVM,
    stepList: List<StepEntrenamientoDto>,
    descanso: Int,
    cursorStep: Int
): ManagerSimpleRepsVM {
    val managerVM: ManagerSimpleRepsVM = viewModel(
        key = "3${UUID.randomUUID()}",
        factory = IManagerSerie.getManager(
            countdownVM1 = viewModel(
                key = "1${UUID.randomUUID()}",
                factory = CountdownVM.getCountdown()
            ),
            countdownVM2 = viewModel(
                key = "2${UUID.randomUUID()}",
                factory = CountdownVM.getCountdown()
            ),
            descanso = descanso,
            stepList = stepList,
            cursorStep = cursorStep,
            plusStep = { confVM.nextCursorStep() },
            minusStep = { confVM.previousCursorStep(it) }
        )
    )
    return managerVM
}

@Composable
fun getManagerRepsDual(
    confVM: ConfManagerVM,
    stepList: List<StepEntrenamientoDto>,
    descanso: Int,
    cursorStep: Int
): ManagerDualRepsVM {
    val managerVM: ManagerDualRepsVM = viewModel(
        key = "3${UUID.randomUUID()}",
        factory = IManagerSerie.getManager(
            countdownVM1 = viewModel(
                key = "1${UUID.randomUUID()}",
                factory = CountdownVM.getCountdown()
            ),
            countdownVM2 = viewModel(
                key = "2${UUID.randomUUID()}",
                factory = CountdownVM.getCountdown()
            ),
            descanso = descanso,
            stepList = stepList,
            cursorStep = cursorStep,
            plusStep = { confVM.nextCursorStep() },
            minusStep = { confVM.previousCursorStep(it) }
        )
    )
    return managerVM
}