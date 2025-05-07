package com.mrgranfiesta.ponteenformaguerrero3.domain.core.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.mrgranfiesta.ponteenformaguerrero3.domain.core.viewmodel.PopBackEffectVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.PopBackEffectVMSingle

@Composable
fun PopBackEffect(
    navController: NavController,
    popBackEffectVM: PopBackEffectVM = PopBackEffectVMSingle.getPopBackEffectVM()
) {
    val isBack by popBackEffectVM.isBack.collectAsState()
    LaunchedEffect(key1 = isBack) {
        if (isBack) {
            navController.popBackStack()
            popBackEffectVM.setBack(false)
            popBackEffectVM.programEndTimeDelay(2000L)
        }
    }
}