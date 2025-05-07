package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.core

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class SetWrapperParcelable<T>(val setC: Set<@RawValue T>) : Parcelable