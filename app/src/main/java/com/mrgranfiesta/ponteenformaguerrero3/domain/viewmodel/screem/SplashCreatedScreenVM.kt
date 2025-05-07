package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrgranfiesta.ponteenformaguerrero3.data.datastore.UserDataStore
import com.mrgranfiesta.ponteenformaguerrero3.data.datastore.VersionDataStore
import com.mrgranfiesta.ponteenformaguerrero3.data.db.datasource.InitialConfMaterialDB
import com.mrgranfiesta.ponteenformaguerrero3.data.db.datasource.InitialEjercicioDB
import com.mrgranfiesta.ponteenformaguerrero3.data.db.datasource.InitialEjercicioMaterialCrossDB
import com.mrgranfiesta.ponteenformaguerrero3.data.db.datasource.InitialMaterialDB
import com.mrgranfiesta.ponteenformaguerrero3.data.db.datasource.InitialRutinaDB
import com.mrgranfiesta.ponteenformaguerrero3.data.db.datasource.InitialStepDB
import com.mrgranfiesta.ponteenformaguerrero3.data.db.datasource.InitialUserDB
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.SPLASH_CREATED_TXT_INITIAL
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.SPLASH_CREATED_TXT_PHASE_1
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.SPLASH_CREATED_TXT_PHASE_2
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.confmaterial.InitializeConfMaterialUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio.InitializeEjercicioUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejerciciomaterialcross.InitializeEjecicioMaterialCrossUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material.InitializeMaterialUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.rutina.InitializeRutinaUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.step.InitializeStepUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user.InitializeUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.CompletableFuture
import javax.inject.Inject

@HiltViewModel
class SplashCreatedScreenVM @Inject constructor(
    private val versionDataStore: VersionDataStore,
    private val userDataStore: UserDataStore,
    private val initializeUserUseCase: InitializeUserUseCase,
    private val initializeMaterialUseCase: InitializeMaterialUseCase,
    private val initializeEjercicioUseCase: InitializeEjercicioUseCase,
    private val initializeRutinaUseCase: InitializeRutinaUseCase,
    private val initializeStepUseCase: InitializeStepUseCase,
    private val initializeEjecicioMaterialCrossUseCase: InitializeEjecicioMaterialCrossUseCase,
    private val initializeConfMaterialUseCase: InitializeConfMaterialUseCase
) : ViewModel() {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val verionOfDataDefaultUserRoom = 1
    private val verionOfDataDefaultMaterialRoom = 1
    private val verionOfDataDefaultEjercicioRoom = 1
    private val verionOfDataDefaultRutinaRoom = 1
    private val verionOfDataDefaultEjercicioMaterialCrossRoom = 1
    private val verionOfDataDefaultStepRoom = 1
    private val verionOfDataDefaultConfMaterialRoom = 1

    private val _textInfo = MutableStateFlow(SPLASH_CREATED_TXT_INITIAL)
    val textInfo: StateFlow<String> = _textInfo

    private val _isNotFinish = MutableStateFlow(true)
    val isNotFinish: StateFlow<Boolean> = _isNotFinish

    @Synchronized
    fun initializeApp() {
        if (_isNotFinish.value) {
            ioScope.launch {
                _textInfo.value = SPLASH_CREATED_TXT_INITIAL
                val resultFutureForeingKeyStepTowardsUser = CompletableFuture<Boolean>()
                val resultFutureForeingKeyStepTowardsMaterial = CompletableFuture<Boolean>()
                val resultFutureForeingKeyStepTowardsEjercicio = CompletableFuture<Boolean>()
                val resultFutureForeingKeyStepTowardsRutina = CompletableFuture<Boolean>()
                val resultFutureForeingKeyStepTowardsStep = CompletableFuture<Boolean>()

                initializeUser(resultFutureForeingKeyStepTowardsUser)
                resultFutureForeingKeyStepTowardsUser.get()

                _textInfo.value = SPLASH_CREATED_TXT_PHASE_1
                initializeMaterial(resultFutureForeingKeyStepTowardsMaterial)
                initializeEjercicio(resultFutureForeingKeyStepTowardsEjercicio)
                initializeRutina(resultFutureForeingKeyStepTowardsRutina)

                resultFutureForeingKeyStepTowardsEjercicio.get()
                resultFutureForeingKeyStepTowardsMaterial.get()

                _textInfo.value = SPLASH_CREATED_TXT_PHASE_2
                initializeEjercicioMaterialCross()

                resultFutureForeingKeyStepTowardsRutina.get()
                initializeStep(resultFutureForeingKeyStepTowardsStep)

                resultFutureForeingKeyStepTowardsStep.get()
                initializeConfMaterial()

                CurrentUser.user = userDataStore.getUserWithRol()
                _isNotFinish.value = false
            }
        }
    }

    @Synchronized
    private fun initializeUser(
        resultFutureForeingKeyStepTowardsUser: CompletableFuture<Boolean>
    ) {
        ioScope.launch {
            val currentVersion = versionDataStore.getUserVersion()
            if (verionOfDataDefaultUserRoom > currentVersion) {
                createDataUser(resultFutureForeingKeyStepTowardsUser)
                versionDataStore.updateUserVersion(verionOfDataDefaultUserRoom)
            } else {
                resultFutureForeingKeyStepTowardsUser.complete(false)
            }
        }
    }

    @Synchronized
    private fun initializeMaterial(
        resultFutureForeingKeyStepTowardsMaterial: CompletableFuture<Boolean>
    ) {
        viewModelScope.launch {
            val currentVersion = versionDataStore.getMaterialVersion()
            if (verionOfDataDefaultMaterialRoom > currentVersion) {
                createDataMaterial(resultFutureForeingKeyStepTowardsMaterial)
                versionDataStore.updateMaterialVersion(verionOfDataDefaultMaterialRoom)
            } else {
                resultFutureForeingKeyStepTowardsMaterial.complete(false)
            }
        }
    }

    @Synchronized
    private fun initializeEjercicio(
        resultFutureForeingKeyStepTowardsEjercicio: CompletableFuture<Boolean>
    ) {
        ioScope.launch {
            val currentVersion = versionDataStore.getEjercicioVersion()
            if (verionOfDataDefaultEjercicioRoom > currentVersion) {
                createDataEjercicio(resultFutureForeingKeyStepTowardsEjercicio)
                versionDataStore.updateEjercicioVersion(verionOfDataDefaultEjercicioRoom)
            } else {
                resultFutureForeingKeyStepTowardsEjercicio.complete(false)
            }
        }
    }

    @Synchronized
    private fun initializeRutina(
        resultFutureForeingKeyStepTowardsRutina: CompletableFuture<Boolean>
    ) {
        ioScope.launch {
            val currentVersion = versionDataStore.getRutinaVersion()
            if (verionOfDataDefaultRutinaRoom > currentVersion) {
                createDataRutina(resultFutureForeingKeyStepTowardsRutina)
                versionDataStore.updateRutinaVersion(verionOfDataDefaultRutinaRoom)
            } else {
                resultFutureForeingKeyStepTowardsRutina.complete(false)
            }
        }
    }

    @Synchronized
    private fun initializeEjercicioMaterialCross() {
        ioScope.launch {
            val currentVersion = versionDataStore.getEjercicioMaterialCrossVersion()
            if (verionOfDataDefaultEjercicioMaterialCrossRoom > currentVersion) {

                createDataEjercicioMaterialCross()
                versionDataStore.updateEjercicioMaterialCrossVersion(
                    verionOfDataDefaultEjercicioMaterialCrossRoom
                )
            }
        }
    }

    @Synchronized
    private fun initializeStep(
        resultFutureForeingKeyStepTowardsStep: CompletableFuture<Boolean>
    ) {
        ioScope.launch {
            val currentVersion = versionDataStore.getStepVersion()
            if (verionOfDataDefaultStepRoom > currentVersion) {
                createDataStep(resultFutureForeingKeyStepTowardsStep)
                versionDataStore.updateStepVersion(verionOfDataDefaultStepRoom)
            } else {
                resultFutureForeingKeyStepTowardsStep.complete(false)
            }
        }
    }

    @Synchronized
    private fun initializeConfMaterial() {
        ioScope.launch {
            val currentVersion = versionDataStore.getConfMaterialVersion()
            if (verionOfDataDefaultConfMaterialRoom > currentVersion) {
                createDataConfMaterial()
                versionDataStore.updateConfMaterialVersion(verionOfDataDefaultConfMaterialRoom)
            }
        }
    }

    private suspend fun createDataUser(resultFuture: CompletableFuture<Boolean>) {
        val initUserList = InitialUserDB.getListDataDefaultUser()

        try {
            val deferredResults = mutableListOf<Deferred<Boolean>>()
            initUserList.forEach { user ->
                deferredResults.add(initializeUserUseCase(user))
            }
            val allSuccessful = deferredResults.all { it.await() }
            resultFuture.complete(allSuccessful)
        } catch (_: Exception) {
            resultFuture.complete(false)
        }
    }

    private suspend fun createDataMaterial(resultFuture: CompletableFuture<Boolean>) {
        val initMaterialList = InitialMaterialDB.getListDataDefaultMaterial()
        try {
            val deferredResults = mutableListOf<Deferred<Boolean>>()
            initMaterialList.forEach { material ->
                deferredResults.add(initializeMaterialUseCase(material))
            }
            val allSuccessful = deferredResults.all { it.await() }
            resultFuture.complete(allSuccessful)
        } catch (_: Exception) {
            resultFuture.complete(false)
        }
    }

    private suspend fun createDataEjercicio(
        resultFuture: CompletableFuture<Boolean>
    ) {
        val initEjercicioList = InitialEjercicioDB.getListDataDefaultEjercicio()
        try {
            val deferredResults = mutableListOf<Deferred<Boolean>>()
            initEjercicioList.forEach { ejercicio ->
                deferredResults.add(initializeEjercicioUseCase(ejercicio))
            }
            val allSuccessful = deferredResults.all { it.await() }
            resultFuture.complete(allSuccessful)
        } catch (_: Exception) {
            resultFuture.complete(false)
        }
    }

    private suspend fun createDataRutina(
        resultFuture: CompletableFuture<Boolean>
    ) {
        val initRutinaList = InitialRutinaDB.getListDataDefaultRutina()
        try {
            val deferredResults = mutableListOf<Deferred<Boolean>>()
            initRutinaList.forEach { rutina ->
                deferredResults.add(initializeRutinaUseCase(rutina))
            }
            val allSuccessful = deferredResults.all { it.await() }
            resultFuture.complete(allSuccessful)
        } catch (_: Exception) {
            resultFuture.complete(false)
        }
    }

    private suspend fun createDataStep(
        resultFuture: CompletableFuture<Boolean>
    ) {
        val initStepList = InitialStepDB.getListDataDefaultStep()

        try {
            val deferredResults = mutableListOf<Deferred<Boolean>>()
            initStepList.forEach { step ->
                deferredResults.add(initializeStepUseCase(step))
            }
            val allSuccessful = deferredResults.all { it.await() }

            resultFuture.complete(allSuccessful)
        } catch (_: Exception) {
            resultFuture.complete(false)
        }
    }

    private fun createDataEjercicioMaterialCross() {
        val initConfMaterialList = InitialEjercicioMaterialCrossDB.getListDataDefaultEjercicioMaterialCross()
        initConfMaterialList.forEach { cross ->
            initializeEjecicioMaterialCrossUseCase(cross)
        }
    }

    private fun createDataConfMaterial() {
        val initConfMaterialList = InitialConfMaterialDB.getListDataDefaulConfMaterial()
        initConfMaterialList.forEach { cross ->
            initializeConfMaterialUseCase(cross)
        }
    }
}