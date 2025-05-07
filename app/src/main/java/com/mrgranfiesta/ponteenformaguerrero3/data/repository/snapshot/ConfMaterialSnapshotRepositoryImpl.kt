package com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot

import com.mrgranfiesta.ponteenformaguerrero3.data.dao.snapshot.ConfMaterialSnapshotDao
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.snapshot.confmaterial.MaterialWithConfSnapshotDB
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConfMaterialSnapshotRepositoryImpl @Inject constructor(
    private val dao: ConfMaterialSnapshotDao
) : ConfMaterialSnapshotRepository {
    override fun getMaterialWithConfByIdEjercicio(
        idEjercicioSnapshot: Long
    ) : Flow<List<MaterialWithConfSnapshotDB>> {
        return dao.getMaterialWithConfByIdEjercicio(idEjercicioSnapshot)
    }
}