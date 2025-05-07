package com.mrgranfiesta.ponteenformaguerrero3.utilstest

import com.mrgranfiesta.ponteenformaguerrero3.utilstest.datasource.GenerateConfMaterial
import com.mrgranfiesta.ponteenformaguerrero3.utilstest.datasource.GenerateEjercicio
import com.mrgranfiesta.ponteenformaguerrero3.utilstest.datasource.GenerateEjercicioMaterialCross
import com.mrgranfiesta.ponteenformaguerrero3.utilstest.datasource.GenerateMaterial
import com.mrgranfiesta.ponteenformaguerrero3.utilstest.datasource.GenerateRutina
import com.mrgranfiesta.ponteenformaguerrero3.utilstest.datasource.snapshot.GenerateRutinaSnapshot
import com.mrgranfiesta.ponteenformaguerrero3.utilstest.datasource.GenerateStat
import com.mrgranfiesta.ponteenformaguerrero3.utilstest.datasource.GenerateStep
import com.mrgranfiesta.ponteenformaguerrero3.utilstest.datasource.GenerateUser
import com.mrgranfiesta.ponteenformaguerrero3.utilstest.datasource.snapshot.GenerateConfMaterialSnapshot
import com.mrgranfiesta.ponteenformaguerrero3.utilstest.datasource.snapshot.GenerateEjercicioMaterialCrossSnapshot
import com.mrgranfiesta.ponteenformaguerrero3.utilstest.datasource.snapshot.GenerateEjercicioSnapshot
import com.mrgranfiesta.ponteenformaguerrero3.utilstest.datasource.snapshot.GenerateMaterialSnapshot
import com.mrgranfiesta.ponteenformaguerrero3.utilstest.datasource.snapshot.GenerateStepSnapshot
import kotlinx.coroutines.test.runTest
import javax.inject.Inject

class GenerateDataDefault @Inject constructor(
    private val generateUser : GenerateUser,
    private val generateEjercicio : GenerateEjercicio,
    private val generateRutina : GenerateRutina,
    private val generateMaterial : GenerateMaterial,
    private val generateEjercicioMaterialCross : GenerateEjercicioMaterialCross,
    private val generateStep : GenerateStep,
    private val generateConfMaterial : GenerateConfMaterial,
    private val generateRutinaSnapshot : GenerateRutinaSnapshot,
    private val generateStat : GenerateStat,
    private val generateEjercicioSnapshot : GenerateEjercicioSnapshot,
    private val generateMaterialSnapshot : GenerateMaterialSnapshot,
    private val generateStepSnapshot : GenerateStepSnapshot,
    private val generateConfMaterialSnapshot : GenerateConfMaterialSnapshot,
    private val generateEjercicioMaterialCrossSnapshot : GenerateEjercicioMaterialCrossSnapshot
){
    operator fun invoke() = runTest {
        generateUser()
        generateEjercicio()
        generateRutina()
        generateMaterial()
        generateEjercicioMaterialCross()
        generateStep()
        generateConfMaterial()
        generateRutinaSnapshot()
        generateStat()
        generateEjercicioSnapshot()
        generateMaterialSnapshot()
        generateEjercicioMaterialCrossSnapshot()
        generateStepSnapshot()
        generateConfMaterialSnapshot()
    }
}