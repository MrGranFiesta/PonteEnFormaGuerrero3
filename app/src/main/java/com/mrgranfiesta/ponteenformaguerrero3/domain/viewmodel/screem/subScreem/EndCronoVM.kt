package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem

import android.content.Context
import androidx.lifecycle.ViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.SoundFile
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.SoundUtils

class EndCronoVM : ViewModel() {
    private var status = true

    @Synchronized
    fun soundVictory(context : Context){
        if (status) {
            SoundUtils.playSound(context, SoundFile.VICTORY)
            status = false
        }
    }
}