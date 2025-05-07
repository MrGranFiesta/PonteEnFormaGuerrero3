package com.mrgranfiesta.ponteenformaguerrero3.domain.singleton

import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.FloatingButtonVM

class FloatingButtonVMSingle {
    companion object {
        private var singleton: FloatingButtonVM? = null

        @Synchronized
        fun getFloatingButtonVM(): FloatingButtonVM {
            if (singleton == null) {
                singleton = FloatingButtonVM()
            }
            return singleton!!
        }
    }
}