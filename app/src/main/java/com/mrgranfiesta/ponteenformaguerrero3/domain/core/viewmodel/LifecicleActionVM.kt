package com.mrgranfiesta.ponteenformaguerrero3.domain.core.viewmodel

class LifecicleActionVM {
    companion object {
        private var singleton: LifecicleActionVM? = null

        @Synchronized
        fun getLifecicleActionVM(): LifecicleActionVM {
            if (singleton == null) {
                singleton = LifecicleActionVM()
            }
            return singleton!!
        }
    }

    private var onPause: () -> Unit = {}
    private var onResume: () -> Unit = {}

    fun setOnPause(onPause: () -> Unit) {
        this.onPause = onPause
    }

    fun setOnResume(onResume: () -> Unit) {
        this.onResume = onResume
    }

    fun executionOnPause() {
        this.onPause()
    }

    fun executionOnResume() {
        this.onResume()
    }

    fun reset() {
        this.onPause = {}
        this.onResume = {}
    }

}