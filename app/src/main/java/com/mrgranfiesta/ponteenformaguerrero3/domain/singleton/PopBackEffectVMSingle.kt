package com.mrgranfiesta.ponteenformaguerrero3.domain.singleton

import com.mrgranfiesta.ponteenformaguerrero3.domain.core.viewmodel.PopBackEffectVM


class PopBackEffectVMSingle {
    companion object {
        private var singleton: PopBackEffectVM? = null

        @Synchronized
        fun getPopBackEffectVM(): PopBackEffectVM {
            if (singleton == null) {
                singleton = PopBackEffectVM()
            }
            return singleton!!
        }
    }
}