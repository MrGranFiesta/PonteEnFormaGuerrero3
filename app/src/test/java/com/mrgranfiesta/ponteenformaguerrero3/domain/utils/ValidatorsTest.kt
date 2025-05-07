package com.mrgranfiesta.ponteenformaguerrero3.domain.utils

import android.net.Uri
import androidx.core.net.toUri
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.AnswerState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DRAWABLE_URI
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.MaterialInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialFormWithConfDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.rutina.RutinaInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.step.RowStepFormWithConfMaterialDto
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ValidatorsTest {

    private fun getEjercicioInfoDto() = EjercicioInfoDto(
        idEjercicio = 0,
        idUser = 0,
        nombre = "Salto a la comba",
        isSimetria = true,
        musculoSet = setOf(Musculo.OBLICUOS),
        nivel = Nivel.FACIL,
        descripcion = "Utiliza una cuerda para saltar",
        photoUri = Uri.EMPTY,
        rol = Rol.INIT_DATA_USER
    )

    private fun getRowMaterialDto() = RowMaterialDto(
        idMaterial = 0,
        idUser = 0,
        nombre = "",
        photoUri = Uri.EMPTY
    )

    @Test
    fun `test isChangedValuesEjercicio termina bien`() {
        val ejercicioOrigen = getEjercicioInfoDto()
        val ejercicioUpdate = getEjercicioInfoDto()

        val materialOrigen = setOf(
            getRowMaterialDto()
        )

        val materialUpdate = setOf(
            getRowMaterialDto()
        )

        assertFalse(
            Validators.isChangedValuesEjercicio(
                ejercicioOrigen = ejercicioOrigen,
                ejercicioUpdate = ejercicioUpdate,
                materialOrigen = materialOrigen,
                materialUpdate = materialUpdate
            )
        )
    }

    @Test
    fun `test isChangedValuesEjercicio no coincide isSimetria`() {
        val ejercicioOrigen = getEjercicioInfoDto().copy(isSimetria = false)
        val ejercicioUpdate = getEjercicioInfoDto().copy(isSimetria = true)

        val materialOrigen = setOf(
            getRowMaterialDto()
        )

        val materialUpdate = setOf(
            getRowMaterialDto()
        )

        assertTrue(
            Validators.isChangedValuesEjercicio(
                ejercicioOrigen = ejercicioOrigen,
                ejercicioUpdate = ejercicioUpdate,
                materialOrigen = materialOrigen,
                materialUpdate = materialUpdate
            )
        )
    }

    @Test
    fun `test isChangedValuesEjercicio no coincide descripcion`() {
        val ejercicioOrigen = getEjercicioInfoDto().copy(descripcion = "aaa")
        val ejercicioUpdate = getEjercicioInfoDto().copy(descripcion = "bbb")

        val materialOrigen = setOf(
            getRowMaterialDto()
        )

        val materialUpdate = setOf(
            getRowMaterialDto()
        )

        assertTrue(
            Validators.isChangedValuesEjercicio(
                ejercicioOrigen = ejercicioOrigen,
                ejercicioUpdate = ejercicioUpdate,
                materialOrigen = materialOrigen,
                materialUpdate = materialUpdate
            )
        )
    }

    @Test
    fun `test isChangedValuesEjercicio no coincide nivel`() {
        val ejercicioOrigen = getEjercicioInfoDto().copy(nivel = Nivel.FACIL)
        val ejercicioUpdate = getEjercicioInfoDto().copy(nivel = Nivel.MEDIO)

        val materialOrigen = setOf(
            getRowMaterialDto()
        )

        val materialUpdate = setOf(
            getRowMaterialDto()
        )

        assertTrue(
            Validators.isChangedValuesEjercicio(
                ejercicioOrigen = ejercicioOrigen,
                ejercicioUpdate = ejercicioUpdate,
                materialOrigen = materialOrigen,
                materialUpdate = materialUpdate
            )
        )
    }

    @Test
    fun `test isChangedValuesEjercicio no coincide nombre`() {
        val ejercicioOrigen = getEjercicioInfoDto().copy(nombre = "aaa")
        val ejercicioUpdate = getEjercicioInfoDto().copy(nombre = "bbb")

        val materialOrigen = setOf(
            getRowMaterialDto()
        )

        val materialUpdate = setOf(
            getRowMaterialDto()
        )

        assertTrue(
            Validators.isChangedValuesEjercicio(
                ejercicioOrigen = ejercicioOrigen,
                ejercicioUpdate = ejercicioUpdate,
                materialOrigen = materialOrigen,
                materialUpdate = materialUpdate
            )
        )
    }

    @Test
    fun `test isChangedValuesEjercicio no coincide musculoSet`() {
        val ejercicioOrigen = getEjercicioInfoDto().copy(musculoSet = setOf(Musculo.OBLICUOS))
        val ejercicioUpdate = getEjercicioInfoDto().copy(musculoSet = setOf(Musculo.TRAPECIO))

        val materialOrigen = setOf(
            getRowMaterialDto()
        )

        val materialUpdate = setOf(
            getRowMaterialDto()
        )

        assertTrue(
            Validators.isChangedValuesEjercicio(
                ejercicioOrigen = ejercicioOrigen,
                ejercicioUpdate = ejercicioUpdate,
                materialOrigen = materialOrigen,
                materialUpdate = materialUpdate
            )
        )
    }

    @Test
    fun `test isChangedValuesEjercicio no coincide photoUri`() {
        val ejercicioOrigen = getEjercicioInfoDto().copy(photoUri = Uri.EMPTY)
        val ejercicioUpdate =
            getEjercicioInfoDto().copy(photoUri = "${DRAWABLE_URI}ejer200_000".toUri())

        val materialOrigen = setOf(
            getRowMaterialDto()
        )

        val materialUpdate = setOf(
            getRowMaterialDto()
        )

        assertTrue(
            Validators.isChangedValuesEjercicio(
                ejercicioOrigen = ejercicioOrigen,
                ejercicioUpdate = ejercicioUpdate,
                materialOrigen = materialOrigen,
                materialUpdate = materialUpdate
            )
        )
    }

    @Test
    fun `test isChangedValuesEjercicio no coincide los materiales`() {
        val ejercicioOrigen = getEjercicioInfoDto()
        val ejercicioUpdate = getEjercicioInfoDto()

        val materialOrigen = setOf(
            getRowMaterialDto().copy(idMaterial = 1)
        )

        val materialUpdate = setOf(
            getRowMaterialDto().copy(idMaterial = 2)
        )

        assertTrue(
            Validators.isChangedValuesEjercicio(
                ejercicioOrigen = ejercicioOrigen,
                ejercicioUpdate = ejercicioUpdate,
                materialOrigen = materialOrigen,
                materialUpdate = materialUpdate
            )
        )
    }

    private fun getRutinaInfoDto() = RutinaInfoDto(
        idRutina = 0,
        idUser = 0,
        nombre = "Flexiones",
        musculoSet = setOf(Musculo.OBLICUOS),
        nivel = Nivel.FACIL,
        descripcion = "Ejercicio fisico",
        calentamiento = AnswerState.ASK_LATER,
        movArticular = AnswerState.ASK_LATER,
        estiramiento = AnswerState.ASK_LATER,
        descanso = 1,
        rol = Rol.INIT_DATA_USER
    )

    private fun getRowStepFormWithConfMaterialDto() = listOf(
        RowStepFormWithConfMaterialDto(
            idStep = 1,
            idRutina = 1,
            idEjercicio = 1,
            cantidad = 20,
            serie = 5,
            ordenExecution = 1,
            tipo = TipoEsfuerzo.CRONO,
            confMaterialList = getListRowMaterialFormWithConfDto()
        ),
        RowStepFormWithConfMaterialDto(
            idStep = 2,
            idRutina = 1,
            idEjercicio = 2,
            cantidad = 10,
            serie = 3,
            ordenExecution = 2,
            tipo = TipoEsfuerzo.REPETICION,
            confMaterialList = listOf()
        )
    )

    private fun getListRowMaterialFormWithConfDto() = listOf(
        RowMaterialFormWithConfDto(
            idMaterial = 1,
            idConfMaterial = 1,
            idStep = 1,
            nombre = "Mancuerna",
            isMaterialWeight = true,
            confValue = 7.0
        ),
        RowMaterialFormWithConfDto(
            idMaterial = 2,
            idConfMaterial = 2,
            idStep = 1,
            nombre = "Barra",
            isMaterialWeight = true,
            confValue = 4.0
        ),
        RowMaterialFormWithConfDto(
            idMaterial = 3,
            idConfMaterial = 3,
            idStep = 1,
            nombre = "Banco",
            isMaterialWeight = false,
            confValue = 1.0
        )
    )

    @Test
    fun `test isChangedValuesRutina termina bien`() {
        val rutinaOrigen = getRutinaInfoDto()
        val rutinaUpdate = getRutinaInfoDto()

        val stepOrigen = getRowStepFormWithConfMaterialDto()
        val stepUpdate = getRowStepFormWithConfMaterialDto()

        assertFalse(
            Validators.isChangedValuesRutina(
                rutinaOrigen = rutinaOrigen,
                rutinaUpdate = rutinaUpdate,
                stepOrigen = stepOrigen,
                stepUpdate = stepUpdate
            )
        )
    }

    @Test
    fun `test isChangedValuesRutina no coincide nombre`() {
        val rutinaOrigen = getRutinaInfoDto().copy(nombre = "aaa")
        val rutinaUpdate = getRutinaInfoDto().copy(nombre = "bbb")

        val stepOrigen = getRowStepFormWithConfMaterialDto()
        val stepUpdate = getRowStepFormWithConfMaterialDto()

        assertTrue(
            Validators.isChangedValuesRutina(
                rutinaOrigen = rutinaOrigen,
                rutinaUpdate = rutinaUpdate,
                stepOrigen = stepOrigen,
                stepUpdate = stepUpdate
            )
        )
    }

    @Test
    fun `test isChangedValuesRutina no coincide descripcion`() {
        val rutinaOrigen = getRutinaInfoDto().copy(descripcion = "aaa")
        val rutinaUpdate = getRutinaInfoDto().copy(descripcion = "bbb")

        val stepOrigen = getRowStepFormWithConfMaterialDto()
        val stepUpdate = getRowStepFormWithConfMaterialDto()

        assertTrue(
            Validators.isChangedValuesRutina(
                rutinaOrigen = rutinaOrigen,
                rutinaUpdate = rutinaUpdate,
                stepOrigen = stepOrigen,
                stepUpdate = stepUpdate
            )
        )
    }

    @Test
    fun `test isChangedValuesRutina no coincide musculoSet`() {
        val rutinaOrigen = getRutinaInfoDto().copy(musculoSet = setOf(Musculo.OBLICUOS))
        val rutinaUpdate = getRutinaInfoDto().copy(musculoSet = setOf(Musculo.TRAPECIO))

        val stepOrigen = getRowStepFormWithConfMaterialDto()
        val stepUpdate = getRowStepFormWithConfMaterialDto()

        assertTrue(
            Validators.isChangedValuesRutina(
                rutinaOrigen = rutinaOrigen,
                rutinaUpdate = rutinaUpdate,
                stepOrigen = stepOrigen,
                stepUpdate = stepUpdate
            )
        )
    }

    @Test
    fun `test isChangedValuesRutina no coincide nivel`() {
        val rutinaOrigen = getRutinaInfoDto().copy(nivel = Nivel.FACIL)
        val rutinaUpdate = getRutinaInfoDto().copy(nivel = Nivel.MEDIO)

        val stepOrigen = getRowStepFormWithConfMaterialDto()
        val stepUpdate = getRowStepFormWithConfMaterialDto()

        assertTrue(
            Validators.isChangedValuesRutina(
                rutinaOrigen = rutinaOrigen,
                rutinaUpdate = rutinaUpdate,
                stepOrigen = stepOrigen,
                stepUpdate = stepUpdate
            )
        )
    }

    @Test
    fun `test isChangedValuesRutina no coincide calentamiento`() {
        val rutinaOrigen = getRutinaInfoDto().copy(calentamiento = AnswerState.ASK_LATER)
        val rutinaUpdate = getRutinaInfoDto().copy(calentamiento = AnswerState.YES)

        val stepOrigen = getRowStepFormWithConfMaterialDto()
        val stepUpdate = getRowStepFormWithConfMaterialDto()

        assertTrue(
            Validators.isChangedValuesRutina(
                rutinaOrigen = rutinaOrigen,
                rutinaUpdate = rutinaUpdate,
                stepOrigen = stepOrigen,
                stepUpdate = stepUpdate
            )
        )
    }

    @Test
    fun `test isChangedValuesRutina no coincide estiramiento`() {
        val rutinaOrigen = getRutinaInfoDto().copy(estiramiento = AnswerState.ASK_LATER)
        val rutinaUpdate = getRutinaInfoDto().copy(estiramiento = AnswerState.YES)

        val stepOrigen = getRowStepFormWithConfMaterialDto()
        val stepUpdate = getRowStepFormWithConfMaterialDto()

        assertTrue(
            Validators.isChangedValuesRutina(
                rutinaOrigen = rutinaOrigen,
                rutinaUpdate = rutinaUpdate,
                stepOrigen = stepOrigen,
                stepUpdate = stepUpdate
            )
        )
    }

    @Test
    fun `test isChangedValuesRutina no coincide movArticular`() {
        val rutinaOrigen = getRutinaInfoDto().copy(movArticular = AnswerState.ASK_LATER)
        val rutinaUpdate = getRutinaInfoDto().copy(movArticular = AnswerState.YES)

        val stepOrigen = getRowStepFormWithConfMaterialDto()
        val stepUpdate = getRowStepFormWithConfMaterialDto()

        assertTrue(
            Validators.isChangedValuesRutina(
                rutinaOrigen = rutinaOrigen,
                rutinaUpdate = rutinaUpdate,
                stepOrigen = stepOrigen,
                stepUpdate = stepUpdate
            )
        )
    }

    @Test
    fun `test isChangedValuesRutina no coincide descanso`() {
        val rutinaOrigen = getRutinaInfoDto().copy(descanso = 1)
        val rutinaUpdate = getRutinaInfoDto().copy(descanso = 2)

        val stepOrigen = getRowStepFormWithConfMaterialDto()
        val stepUpdate = getRowStepFormWithConfMaterialDto()

        assertTrue(
            Validators.isChangedValuesRutina(
                rutinaOrigen = rutinaOrigen,
                rutinaUpdate = rutinaUpdate,
                stepOrigen = stepOrigen,
                stepUpdate = stepUpdate
            )
        )
    }

    @Test
    fun `test isChangedValuesRutina no coincide lista de steps`() {
        val rutinaOrigen = getRutinaInfoDto()
        val rutinaUpdate = getRutinaInfoDto()

        val stepOrigen = getRowStepFormWithConfMaterialDto().toMutableList()
        val stepUpdate = getRowStepFormWithConfMaterialDto().toMutableList()

        stepOrigen[0] = stepOrigen[0].copy(cantidad = 15)
        stepUpdate[0] = stepUpdate[0].copy(cantidad = 25)

        assertTrue(
            Validators.isChangedValuesRutina(
                rutinaOrigen = rutinaOrigen,
                rutinaUpdate = rutinaUpdate,
                stepOrigen = stepOrigen,
                stepUpdate = stepUpdate
            )
        )
    }

    private fun getMaterialInfoDto() = MaterialInfoDto(
        idMaterial = 1,
        idUser = 1,
        nombre = "Banco",
        isMaterialWeight = true,
        descripcion = "Banco de ejercicio",
        photoUri = Uri.EMPTY,
        rol = Rol.INIT_DATA_USER
    )

    @Test
    fun `test isChangedValuesMaterial termina bien`() {
        val materialOrigen = getMaterialInfoDto()
        val materialUpdate = getMaterialInfoDto()

        assertFalse(
            Validators.isChangedValuesMaterial(
                materialOrigen = materialOrigen,
                materialUpdate = materialUpdate
            )
        )
    }

    @Test
    fun `test isChangedValuesMaterial no coincide nombre`() {
        val materialOrigen = getMaterialInfoDto().copy(nombre = "aaa")
        val materialUpdate = getMaterialInfoDto().copy(nombre = "bbb")

        assertTrue(
            Validators.isChangedValuesMaterial(
                materialOrigen = materialOrigen,
                materialUpdate = materialUpdate
            )
        )
    }

    @Test
    fun `test isChangedValuesMaterial no coincide descripcion`() {
        val materialOrigen = getMaterialInfoDto().copy(descripcion = "aaa")
        val materialUpdate = getMaterialInfoDto().copy(descripcion = "bbb")

        assertTrue(
            Validators.isChangedValuesMaterial(
                materialOrigen = materialOrigen,
                materialUpdate = materialUpdate
            )
        )
    }

    @Test
    fun `test isChangedValuesMaterial no coincide photoUri`() {
        val materialOrigen = getMaterialInfoDto().copy(photoUri = Uri.EMPTY)
        val materialUpdate = getMaterialInfoDto().copy(photoUri = "${DRAWABLE_URI}ejer2_000_000".toUri())

        assertTrue(
            Validators.isChangedValuesMaterial(
                materialOrigen = materialOrigen,
                materialUpdate = materialUpdate
            )
        )
    }

    @Test
    fun `test isChangedValuesMaterial no coincide isMaterialWeight`() {
        val materialOrigen = getMaterialInfoDto().copy(isMaterialWeight = false)
        val materialUpdate = getMaterialInfoDto().copy(isMaterialWeight = true)

        assertTrue(
            Validators.isChangedValuesMaterial(
                materialOrigen = materialOrigen,
                materialUpdate = materialUpdate
            )
        )
    }
}