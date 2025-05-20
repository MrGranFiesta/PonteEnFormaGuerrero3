package com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class MinLenght(val min: Int, val fieldName: String)
