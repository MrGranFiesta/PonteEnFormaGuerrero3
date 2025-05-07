package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.ConfMaterialRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.MaterialRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.dialog.MaterialNameDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMaterialNameAndConfListUseCase @Inject constructor(
    private val materialRepo: MaterialRepository,
    private val confMaterialRepo: ConfMaterialRepository
) {
    operator fun invoke(
        idEjercicio: Long,
        idStep: Long
    ): Flow<List<MaterialNameDto>> {
        val materialList =
            materialRepo.getListNoFlowByIdEjercicio(idEjercicio).map { materialEntity ->
                MaterialNameDto(
                    idMaterial = materialEntity.idMaterial,
                    nombre = materialEntity.nombre,
                    isMaterialWeight = materialEntity.isMaterialWeight,
                    idConfMaterial = 0,
                    confValue = 1.0,
                )
            }

        val confMaterialList =
            confMaterialRepo.getListNoFlowByIdStep(idStep).map { confMaterialEntity ->
                MaterialNameDto(
                    idMaterial = confMaterialEntity.idMaterial,
                    nombre = "",
                    isMaterialWeight = true,
                    idConfMaterial = confMaterialEntity.idConfMaterial,
                    confValue = confMaterialEntity.confValue,
                )
            }

        materialList.forEach { material ->
            confMaterialList.find { material.idMaterial == it.idMaterial }?.let {
                material.confValue = it.confValue
                material.idConfMaterial = it.idConfMaterial
            }
        }

        return flow {
            emit(materialList)
        }
    }
}