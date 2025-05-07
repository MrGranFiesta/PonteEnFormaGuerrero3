package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.rutina

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.ConfMaterialRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.RutinaRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.StepRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.AnswerState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialFormWithConfDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.rutina.RutinaInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.step.RowStepFormWithConfMaterialDto
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test

class UpdateRutinaWithStepUseCaseTest {
    @RelaxedMockK
    private lateinit var rutinaRepo: RutinaRepository

    @RelaxedMockK
    private lateinit var stepRepo: StepRepository

    @RelaxedMockK
    private lateinit var confMaterialRepo: ConfMaterialRepository

    private lateinit var updateRutinaWithStepUseCase: UpdateRutinaWithStepUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        updateRutinaWithStepUseCase = UpdateRutinaWithStepUseCase(
            rutinaRepo = rutinaRepo,
            stepRepo = stepRepo,
            confMaterialRepo = confMaterialRepo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test updateRutinaWithStepUseCase`() {
        val rutinaInfoDto = RutinaInfoDto(
            idRutina = 1,
            idUser = 1,
            nombre = "Flexiones intermedias",
            descripcion = "",
            musculoSet = setOf(Musculo.PECHO),
            nivel = Nivel.MEDIO,
            calentamiento = AnswerState.ASK_LATER,
            estiramiento = AnswerState.ASK_LATER,
            movArticular = AnswerState.ASK_LATER,
            descanso = 20,
            rol = Rol.INIT_DATA_USER
        )
        val confMaterialListUpdate = listOf(
            RowMaterialFormWithConfDto(
                idMaterial = 1,
                idConfMaterial = 1,
                idStep = 1,
                nombre = "Banco",
                isMaterialWeight = true,
                confValue = 1.0
            ),
            RowMaterialFormWithConfDto(
                idMaterial = 2,
                idConfMaterial = 2,
                idStep = 0,
                nombre = "Mancuerna",
                isMaterialWeight = true,
                confValue = 1.0
            )
        )
        val stepListUpdate = listOf(
            RowStepFormWithConfMaterialDto(
                idStep = 1,
                idRutina = 1,
                idEjercicio = 1,
                cantidad = 1,
                serie = 10,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.REPETICION,
                confMaterialList = confMaterialListUpdate
            ),
            RowStepFormWithConfMaterialDto(
                idStep = 0,
                idRutina = 1,
                idEjercicio = 2,
                cantidad = 5,
                serie = 10,
                ordenExecution = 2,
                tipo = TipoEsfuerzo.REPETICION,
                confMaterialList = confMaterialListUpdate
            )
        )
        val confMaterialListDelete = listOf(
            RowMaterialFormWithConfDto(
                idMaterial = 1,
                idConfMaterial = 1,
                idStep = 1,
                nombre = "Banco",
                isMaterialWeight = true,
                confValue = 1.0
            )
        )
        val stepListDelete = listOf(
            RowStepFormWithConfMaterialDto(
                idStep = 1,
                idRutina = 1,
                idEjercicio = 1,
                cantidad = 1,
                serie = 10,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.REPETICION,
                confMaterialList = confMaterialListDelete
            )
        )
        updateRutinaWithStepUseCase(
            rutina = rutinaInfoDto,
            stepListUpdate = stepListUpdate,
            stepListDelete = stepListDelete
        )

        coVerify { rutinaRepo.update(any()) }
        coVerify { stepRepo.insert(any()) }
        coVerify(exactly = 2) { confMaterialRepo.insert(any()) }
        coVerify { stepRepo.update(any()) }
        coVerify { confMaterialRepo.update(any()) }
        coVerify { stepRepo.delete(any()) }
    }
}