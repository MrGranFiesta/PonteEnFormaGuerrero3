package com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class MaxLenght(val max: Int, val fieldName: String)
