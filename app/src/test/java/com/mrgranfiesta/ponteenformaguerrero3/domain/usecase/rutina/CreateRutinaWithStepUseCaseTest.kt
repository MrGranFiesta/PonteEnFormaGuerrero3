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

class CreateRutinaWithStepUseCaseTest {
    @RelaxedMockK
    private lateinit var rutinaRepo: RutinaRepository

    @RelaxedMockK
    private lateinit var stepRepo: StepRepository

    @RelaxedMockK
    private lateinit var confMaterialRepo: ConfMaterialRepository

    private lateinit var createRutinaWithStepUseCase: CreateRutinaWithStepUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        createRutinaWithStepUseCase = CreateRutinaWithStepUseCase(
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
    fun `test createRutinaWithStepUseCase`() {
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
        val confMaterialList = listOf(
            RowMaterialFormWithConfDto(
                idMaterial = 1,
                idConfMaterial = 1,
                idStep = 1,
                nombre = "Banco",
                isMaterialWeight = true,
                confValue = 1.0
            )
        )
        val stepList = listOf(
            RowStepFormWithConfMaterialDto(
                idStep = 1,
                idRutina = 1,
                idEjercicio = 1,
                cantidad = 1,
                serie = 10,
                ordenExecution = 1,
                tipo = TipoEsfuerzo.REPETICION,
                confMaterialList = confMaterialList
            )
        )
        createRutinaWithStepUseCase(
            rutina = rutinaInfoDto,
            stepList = stepList
        )

        coVerify { rutinaRepo.insert(any()) }
        coVerify { stepRepo.insert(any()) }
        coVerify { confMaterialRepo.insert(any()) }
    }
}