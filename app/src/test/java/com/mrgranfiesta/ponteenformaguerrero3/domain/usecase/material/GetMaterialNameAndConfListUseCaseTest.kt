package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material

import com.mrgranfiesta.ponteenformaguerrero3.data.entity.ConfMaterialEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.MaterialEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.ConfMaterialRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.MaterialRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.dialog.MaterialNameDto
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GetMaterialNameAndConfListUseCaseTest {
    @RelaxedMockK
    private lateinit var materialRepo: MaterialRepository

    @RelaxedMockK
    private lateinit var confMaterialRepo: ConfMaterialRepository

    private lateinit var getMaterialNameAndConfListUseCase: GetMaterialNameAndConfListUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getMaterialNameAndConfListUseCase = GetMaterialNameAndConfListUseCase(
            materialRepo = materialRepo,
            confMaterialRepo = confMaterialRepo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test getMaterialNameAndConfListUseCase`() = runTest {
        val listMaterialEntity = listOf(
            MaterialEntity(
                idMaterial = 1,
                idUser = 1,
                nombre = "Banco",
                isMaterialWeight = true,
                descripcion = "",
                nameImg = ""
            )
        )

        val listConfMaterialEntity = listOf(
            ConfMaterialEntity(
                idConfMaterial = 1,
                idMaterial = 1,
                idStep = 1,
                confValue = 1.0
            )
        )
        val listMaterialNameDtoSolution = listOf(
            MaterialNameDto(
                idMaterial = 1,
                idConfMaterial = 1,
                nombre = "Banco",
                isMaterialWeight = true,
                confValue = 1.0
            )
        )

        coEvery { materialRepo.getListNoFlowByIdEjercicio(any()) } returns listMaterialEntity
        coEvery { confMaterialRepo.getListNoFlowByIdStep(any()) } returns listConfMaterialEntity

        val listMaterialNameDtoResult = getMaterialNameAndConfListUseCase(
            idEjercicio = 1,
            idStep = 1
        ).first()

        val aaa =
            listMaterialNameDtoSolution.map { "${it.idMaterial}, ${it.idConfMaterial}, ${it.nombre}, ${it.isMaterialWeight}, ${it.confValue}" }
        val bbb =
            listMaterialNameDtoResult.map { "${it.idMaterial}, ${it.idConfMaterial}, ${it.nombre}, ${it.isMaterialWeight}, ${it.confValue}" }
        assertEquals(
            """
            listMaterialNameDtoSolution $aaa
            listMaterialNameDtoResult $bbb
        """.trimIndent(),
            true,
            isEqualsMaterialNameDto(
                materialNameDto1 = listMaterialNameDtoSolution.first(),
                materialNameDto2 = listMaterialNameDtoResult.first(),
            )
        )
    }

    private fun isEqualsMaterialNameDto(
        materialNameDto1 : MaterialNameDto,
        materialNameDto2 : MaterialNameDto
    ) : Boolean {
        return materialNameDto1.idMaterial == materialNameDto2.idMaterial
                && materialNameDto1.idConfMaterial == materialNameDto2.idConfMaterial
                && materialNameDto1.nombre == materialNameDto2.nombre
                && materialNameDto1.isMaterialWeight == materialNameDto2.isMaterialWeight
                && materialNameDto1.confValue == materialNameDto2.confValue
    }
}