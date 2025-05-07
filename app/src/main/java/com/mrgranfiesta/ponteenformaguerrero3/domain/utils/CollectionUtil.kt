package com.mrgranfiesta.ponteenformaguerrero3.domain.utils

class CollectionUtil {
    companion object {
        fun <T> copyCollectionsSet(
            set: MutableSet<T>,
            setTemp: Set<T>
        ): Set<T> {
            set.clear()
            set.addAll(setTemp)
            return set
        }

        fun <T> minus(
            minuendo : Set<T>,
            sustraendo : Set<T>,
        ) = minuendo.minus(sustraendo)
    }
}