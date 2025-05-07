package com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.snapshot

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class StepSnapshotInfoDto(
    var idStep: Long = 0,
    var idStepSnapshot: Long,
    var idEjercicioSnapshot: Long,
    var idEjercicio: Long,
    var idRutinaSnapshot: Long,
    var cantidad: Int,
    var serie: Int,
    var ordenExecution: Int,
    var tipo: TipoEsfuerzo,
    var musculoSet : Set<Musculo>,
    var confMaterialList: List<StatConfMaterialSnapshotDto>
) : Parcelable