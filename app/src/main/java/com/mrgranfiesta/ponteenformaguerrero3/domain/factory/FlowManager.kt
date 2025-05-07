package com.mrgranfiesta.ponteenformaguerrero3.domain.factory


import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.MaterialEntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.StepEntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.snapshot.MaterialWithConfSnapshotDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.snapshot.StepSnapshotInfoDto
import kotlinx.coroutines.flow.flow

class FlowManager {
    companion object {
        fun getEjercicioInfoDtoDefault() = flow {
            emit(ObjectFactory.getEjercicioInfoDtoDefault())
        }

        fun getEjercicioInfoDefault() = flow {
            emit(ObjectFactory.getDefaultEjercicioInfoDtoBean())
        }

        fun getFlowRutinaInfoDefault() = flow {
            emit(ObjectFactory.getDefaultRutinaInfo())
        }

        fun <T> getEmptyList() = flow {
            emit(ArrayList<T>())
        }

        fun getFlowEntrenamientoDefault() = flow {
            emit(ObjectFactory.getDefaultEntrenamiento())
        }

        fun getListFlowStepEntrenamientoListDefault() = flow {
            emit(listOf<StepEntrenamientoDto>())
        }

        fun getListFlowMaterialEntrenamientoListDefault() = flow {
            emit(listOf<MaterialEntrenamientoDto>())
        }

        fun getStatRutinaInfoDtoBeanDefault() = flow {
            emit(ObjectFactory.getDefaultStatRutinaInfoDtoBean())
        }

        fun getListStepSnapshotDefault() = flow {
            emit(listOf<StepSnapshotInfoDto>())
        }

        fun getEjercicioSnapshotInfoDefault() = flow {
            emit(ObjectFactory.getDefaultEjercicioSnapshotInfo())
        }

        fun getMaterialWithConfSnapshotDefault() = flow {
            emit(listOf<MaterialWithConfSnapshotDto>())
        }

        /// ------------- Nuevo ----------- ///

        fun getFlowMaterialInfoDefault() = flow {
            emit(ObjectFactory.getDefaultMaterialInfo())
        }
    }
}