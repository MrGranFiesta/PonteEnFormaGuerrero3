package com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot

import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.snapshot.confmaterial.MaterialWithConfSnapshotDB
import kotlinx.coroutines.flow.Flow

fun interface ConfMaterialSnapshotRepository {
    fun getMaterialWithConfByIdEjercicio(idEjercicioSnapshot: Long) : Flow<List<MaterialWithConfSnapshotDB>>
}