package com.mrgranfiesta.ponteenformaguerrero3.data.repository

import com.mrgranfiesta.ponteenformaguerrero3.data.dao.StepDao
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.confmaterial.RowConfMaterialFormDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.confmaterial.RowConfMaterialInfoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.step.StepFormWithConfMaterialDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.step.StepFormWithConfMaterialTempDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.step.StepInfoWithConfMaterialTempDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.step.StepInfoWithConfMaterialDB
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.StepEntity
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StepRepositoryImpl @Inject constructor(
    private val daoStep: StepDao,
    val context: CoroutineDispatcher = Dispatchers.IO
) : StepRepository {
    override fun getStepInfoWithMaterialConfListByIdRutina(idRutina: Long): Flow<List<StepInfoWithConfMaterialDB>> {
        return daoStep.getStepInfoWithConfMaterialByRutinaId(idRutina)
            .map {
                it.toStepWithConfMaterialDBListByInfo()
            }
    }

    override fun getStepFormWithMaterialConfListByIdRutina(idRutina: Long): Flow<List<StepFormWithConfMaterialDB>> {
        return daoStep.getStepFormWithConfMaterialByRutinaId(idRutina)
            .map {
                it.toStepWithConfMaterialDBListByForm()
            }
    }

    fun List<StepInfoWithConfMaterialTempDB>.toStepWithConfMaterialDBListByInfo(): List<StepInfoWithConfMaterialDB> {
        return groupBy { it.idStep }.map { (_, group) ->
            val first = group.first()
            StepInfoWithConfMaterialDB(
                idStep = first.idStep,
                idEjercicio = first.idEjercicio,
                cantidad = first.cantidad,
                serie = first.serie,
                ordenExecution = first.ordenExecution,
                tipo = first.tipo,
                confMaterial = group.mapNotNull { result ->
                    result.idMaterial?.let { idMaterial ->
                        RowConfMaterialInfoDB(
                            idMaterial = idMaterial,
                            nombre = result.nombre ?: "",
                            isMaterialWeight = result.isMaterialWeight == true,
                            nameImg = result.nameImg ?: "",
                            rol = result.rol ?: Rol.STANDAR_USER,
                            confValue = result.confValue ?: 0.0
                        )
                    }
                }
            )
        }
    }

    fun List<StepFormWithConfMaterialTempDB>.toStepWithConfMaterialDBListByForm(): List<StepFormWithConfMaterialDB> {
        return groupBy { it.idStep }.map { (_, group) ->
            val first = group.first()
            StepFormWithConfMaterialDB(
                idStep = first.idStep,
                idRutina = first.idRutina,
                idEjercicio = first.idEjercicio,
                cantidad = first.cantidad,
                serie = first.serie,
                ordenExecution = first.ordenExecution,
                tipo = first.tipo,
                confMaterial = group.mapNotNull { result ->
                    result.idMaterial?.let { idMaterial ->
                        RowConfMaterialFormDB(
                            idMaterial = idMaterial,
                            idConfMaterial = result.idConfMaterial ?: 0,
                            idStep = result.idStep,
                            nombre = result.nombre ?: "",
                            isMaterialWeight = result.isMaterialWeight == true,
                            nameImg = result.nameImg ?: "",
                            rol = result.rol ?: Rol.STANDAR_USER,
                            confValue = result.confValue ?: 0.0
                        )
                    }
                }
            )
        }
    }

    override suspend fun insert(stepEntity: StepEntity): Long = withContext(context) {
        daoStep.insert(stepEntity)
    }

    override suspend fun update(stepEntity: StepEntity) = withContext(context) {
        daoStep.update(stepEntity)
    }

    override suspend fun delete(stepEntity: StepEntity) = withContext(context) {
        daoStep.delete(stepEntity)
    }
}