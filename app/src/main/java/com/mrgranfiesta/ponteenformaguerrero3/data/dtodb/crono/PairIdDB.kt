package com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.crono

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PairIdDB(
    val idSnapshot : Long,
    val idOrigin : Long,
) : Parcelable
