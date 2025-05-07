package com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.dialog

import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.objects.OptionalLocalDate
import kotlinx.parcelize.Parcelize

@Parcelize
data class StatDialogDto(
    var musculoSet: Set<Musculo>,
    var onChangeMusculoSet: (Set<Musculo>) -> Unit,
    var nivelSet: Set<Nivel>,
    var onChangeNivelSet: (Set<Nivel>) -> Unit,
    var rangeDate: Pair<OptionalLocalDate, OptionalLocalDate>,
    var onChangeRangeDate: (Pair<OptionalLocalDate, OptionalLocalDate>) -> Unit
) : Parcelable