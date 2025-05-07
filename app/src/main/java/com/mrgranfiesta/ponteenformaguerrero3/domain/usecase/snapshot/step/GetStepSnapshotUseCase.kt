package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.snapshot.step

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.StepSnapshotRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.snapshot.StepSnapshotInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.DtoMapper
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.map

class GetStepSnapshotUseCase @Inject constructor(
    private val repo: StepSnapshotRepository
) {
    operator fun invoke(idStat : Long) : Flow<List<StepSnapshotInfoDto>> {
        return repo.getStepSnapshotInfo(idStat).map { list ->
            list.map { obj ->
                DtoMapper.toStepSnapshotInfoDto(obj)
            }
        }
    }
}