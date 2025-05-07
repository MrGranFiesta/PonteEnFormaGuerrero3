package com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.dialog

import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import kotlinx.parcelize.Parcelize

@Parcelize
data class RutinaDialogDto(
    var musculoSet: Set<Musculo>,
    var onChangeMusculoSet: (Set<Musculo>) -> Unit,
    var nivelSet: Set<Nivel>,
    var onChangeNivelSet: (Set<Nivel>) -> Unit
) : Parcelable