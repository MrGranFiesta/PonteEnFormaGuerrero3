package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.snapshot.DeleteSnapshotUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.snapshot.ejercicio.GetEjercicioSnapshotNameAndPhotoByIdUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.snapshot.step.GetStepSnapshotUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.stat.GetStatRutinaInfoUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class StatRutinaInfoVMTest {
    lateinit var viewModel: StatRutinaInfoVM

    @RelaxedMockK
    private lateinit var deleteSnapshotUseCase: DeleteSnapshotUseCase

    @RelaxedMockK
    private lateinit var getEjercicioSnapshotNameAndPhotoByIdUseCase: GetEjercicioSnapshotNameAndPhotoByIdUseCase

    @RelaxedMockK
    private lateinit var getStepSnapshotUseCase: GetStepSnapshotUseCase

    @RelaxedMockK
    private lateinit var getStatRutinaInfoUseCase: GetStatRutinaInfoUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = StatRutinaInfoVM(
            deleteSnapshotUseCase = deleteSnapshotUseCase,
            getEjercicioSnapshotNameAndPhotoByIdUseCase = getEjercicioSnapshotNameAndPhotoByIdUseCase,
            getStepSnapshotUseCase = getStepSnapshotUseCase,
            getStatRutinaInfoUseCase = getStatRutinaInfoUseCase

        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun testInitDataCorrect() {
        val statusField = viewModel.javaClass.getDeclaredField("status")
        statusField.isAccessible = true

        assertTrue(statusField.getBoolean(viewModel))

        viewModel.initData(1)

        assertFalse(statusField.getBoolean(viewModel))
    }
}