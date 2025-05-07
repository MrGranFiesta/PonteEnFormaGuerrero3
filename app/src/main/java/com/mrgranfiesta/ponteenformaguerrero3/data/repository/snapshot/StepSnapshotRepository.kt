package com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot

import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.snapshot.step.StepSnapshotInfoDB
import kotlinx.coroutines.flow.Flow

fun interface StepSnapshotRepository {
    fun getStepSnapshotInfo(idStat : Long) : Flow<List<StepSnapshotInfoDB>>
}