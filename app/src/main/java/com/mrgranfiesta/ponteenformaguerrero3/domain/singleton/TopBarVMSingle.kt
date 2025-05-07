package com.mrgranfiesta.ponteenformaguerrero3.domain.singleton

import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TopBarVM

class TopBarVMSingle {
    companion object {
        private var singleton: TopBarVM? = null

        @Synchronized
        fun getTopBarVM(): TopBarVM {
            if (singleton == null) {
                singleton = TopBarVM()
            }
            return singleton!!
        }
    }
}