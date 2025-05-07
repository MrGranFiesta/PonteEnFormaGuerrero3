package com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.snapshot.step

import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import kotlinx.parcelize.Parcelize

@Parcelize
data class StepSnapshotInfoDB(
    var idStep: Long = 0,
    var idStepSnapshot: Long,
    var idEjercicioSnapshot: Long,
    var idEjercicio: Long,
    var idRutinaSnapshot: Long,
    var cantidad: Int,
    var serie: Int,
    var ordenExecution: Int,
    var tipo: TipoEsfuerzo,
    var rol: Rol,
    var musculoSet : Set<Musculo>,
    var confMaterialList: List<MaterialWithConfSnapshotStepDB>
) : Parcelable