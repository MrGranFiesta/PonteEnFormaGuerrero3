package com.mrgranfiesta.ponteenformaguerrero3.domain.utils

import android.icu.lang.UCharacter
import android.net.Uri
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.RowEjercicioDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.rutina.RowRutinaDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.stat.StatRutinaSearchDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.objects.OptionalLocalDate
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.time.LocalDate
import java.time.LocalDateTime

@RunWith(RobolectricTestRunner::class)
class FinderUtilsTest {
    @Before
    fun testUCharacter() {
        mockkStatic(UCharacter::class)
    }

    @Test
    fun `test filterSearchTxtRE buscar contener una palabra`() {
        val result = FinderUtils.filterSearchTxtRE("Test")
        assertEquals(".*Test.*", result)
    }

    @Test
    fun `test filterSearchTxtRE buscar con espacio vacio`() {
        val result = FinderUtils.filterSearchTxtRE("")
        assertEquals("", result)
    }

    @Test
    fun `test filterSearchTxtRE con asteriscos en los extremos`() {
        val result = FinderUtils.filterSearchTxtRE("*Test*")
        assertEquals(".*.*Test.*.*", result)
    }

    @Test
    fun `test filterSearchTxtRE con asterisco como separador`() {
        val result = FinderUtils.filterSearchTxtRE("Test*de*FinderUtils")
        assertEquals(".*Test.*de.*FinderUtils.*", result)
    }

    @Test
    fun `test filterSearchTxtRE eliminar por seguridad el simbolo suma`() {
        val result = FinderUtils.filterSearchTxtRE("Test+de+FinderUtils")
        assertEquals(".*TestdeFinderUtils.*", result)
    }

    @Test
    fun `test filterSearchTxtRE eliminar por seguridad el simbolo interrogacion`() {
        val result = FinderUtils.filterSearchTxtRE("Test?de?FinderUtils")
        assertEquals(".*TestdeFinderUtils.*", result)
    }

    @Test
    fun `test filterSearchTxtRE separar palabras por apipe sin espacios`() {
        val result = FinderUtils.filterSearchTxtRE("Test|de|FinderUtils")
        assertEquals(".*Test|de|FinderUtils.*", result)
    }

    @Test
    fun `test filterSearchTxtRE separar palabras por apipe con espacios`() {
        val result = FinderUtils.filterSearchTxtRE("Test | de | FinderUtils")
        assertEquals(".*Test|de|FinderUtils.*", result)
    }

    @Test
    fun `test filterSearchTxtRE sustituir los espacios por apipe para hacer una busqueda por palabras`() {
        val result = FinderUtils.filterSearchTxtRE("Test de FinderUtils")
        assertEquals(".*Test|de|FinderUtils.*", result)
    }

    private fun getDatasourceRowEjercicioDto(): Set<RowEjercicioDto> {
        return setOf(
            RowEjercicioDto(
                idEjercicio = 0,
                nombre = "Adbominales",
                musculoSet = setOf(Musculo.ABDOMINALES),
                nivel = Nivel.NINGUNO,
                photoUri = Uri.EMPTY
            ),
            RowEjercicioDto(
                idEjercicio = 1,
                nombre = "Flexiones con una mano",
                musculoSet = setOf(Musculo.HOMBRO, Musculo.PECHO),
                nivel = Nivel.FACIL,
                photoUri = Uri.EMPTY
            ),
            RowEjercicioDto(
                idEjercicio = 2,
                nombre = "Correr con una pierna",
                musculoSet = setOf(Musculo.PIERNA, Musculo.TRICEPS),
                nivel = Nivel.MEDIO,
                photoUri = Uri.EMPTY
            ),
            RowEjercicioDto(
                idEjercicio = 3,
                nombre = "Saltar a la comba",
                musculoSet = setOf(Musculo.BICEPS, Musculo.GLUTEOS, Musculo.TRAPECIO),
                nivel = Nivel.DIFICIL,
                photoUri = Uri.EMPTY
            ),
            RowEjercicioDto(
                idEjercicio = 5,
                nombre = "Correr descalzos",
                musculoSet = setOf(Musculo.PIERNA, Musculo.OBLICUOS),
                nivel = Nivel.MEDIO,
                photoUri = Uri.EMPTY
            )
        )
    }


    @Test
    fun `test filterSearchEjercicio filtra por cadena de busqueda`() {
        every { UCharacter.toUpperCase(any<String>()) } answers { firstArg<String>().uppercase() }

        var set = getDatasourceRowEjercicioDto()
        var searchRE = FinderUtils.filterSearchTxtRE("correr")

        val result = FinderUtils.filterSearchEjercicio(set, searchRE)

        assertEquals(true, result.isNotEmpty())
    }

    @Test
    fun `test filterSearchEjercicio filtra sin tener en cuenta mayusculas y minusculas`() {
        every { UCharacter.toUpperCase(any<String>()) } answers { firstArg<String>().uppercase() }

        var set = getDatasourceRowEjercicioDto()
        var searchRE = FinderUtils.filterSearchTxtRE("CoRrEr")

        val result = FinderUtils.filterSearchEjercicio(set, searchRE)

        assertEquals(true, result.isNotEmpty())
    }

    @Test
    fun `test filterMusculoEjercicio filtra por musculos`() {
        var set = getDatasourceRowEjercicioDto()
        var setMusculo = setOf(Musculo.OBLICUOS, Musculo.PIERNA)

        val result = FinderUtils.filterMusculoEjercicio(set, setMusculo)

        val bool = result.all { ejercicio ->
            ejercicio.musculoSet.contains(Musculo.OBLICUOS) || ejercicio.musculoSet.contains(Musculo.PIERNA)
        }

        assertEquals(true, bool)
    }

    @Test
    fun `test filterNivelEjercicio filtra por nivel`() {
        var set = getDatasourceRowEjercicioDto()
        var setMusculo = setOf(Nivel.FACIL, Nivel.MEDIO)

        val result = FinderUtils.filterNivelEjercicio(set, setMusculo)

        val bool = result.all { ejercicio ->
            ejercicio.nivel == Nivel.FACIL || ejercicio.nivel == Nivel.MEDIO
        }

        assertEquals(true, bool)
    }

    private fun getDatasourceRowRutinaDto(): Set<RowRutinaDto> {
        return setOf(
            RowRutinaDto(
                idRutina = 1,
                idUser = 1,
                nombre = "Adbominales facil",
                musculoSet = setOf(Musculo.ABDOMINALES),
                nivel = Nivel.FACIL,
                isPremium = false
            ),
            RowRutinaDto(
                idRutina = 2,
                idUser = 1,
                nombre = "Flexiones medio",
                musculoSet = setOf(Musculo.HOMBRO, Musculo.PECHO),
                nivel = Nivel.MEDIO,
                isPremium = false
            ),
            RowRutinaDto(
                idRutina = 3,
                idUser = 1,
                nombre = "Cardio",
                musculoSet = setOf(Musculo.PIERNA, Musculo.TRICEPS),
                nivel = Nivel.MEDIO,
                isPremium = true
            ),
            RowRutinaDto(
                idRutina = 4,
                idUser = 1,
                nombre = "Prueba de salto",
                musculoSet = setOf(Musculo.BICEPS, Musculo.GLUTEOS, Musculo.TRAPECIO),
                nivel = Nivel.DIFICIL,
                isPremium = false
            ),
            RowRutinaDto(
                idRutina = 5,
                idUser = 1,
                nombre = "Piernas fuertes",
                musculoSet = setOf(Musculo.PIERNA, Musculo.OBLICUOS),
                nivel = Nivel.NINGUNO,
                isPremium = true
            )
        )
    }

    @Test
    fun `test filterSearchRutina filtra por cadena de busqueda`() {
        every { UCharacter.toUpperCase(any<String>()) } answers { firstArg<String>().uppercase() }

        var set = getDatasourceRowRutinaDto()
        var searchRE = FinderUtils.filterSearchTxtRE("Cardio")

        val result = FinderUtils.filterSearchRutina(set, searchRE)

        assertEquals(true, result.isNotEmpty())
    }

    @Test
    fun `test filterSearchRutina filtra sin tener en cuenta mayusculas`() {
        every { UCharacter.toUpperCase(any<String>()) } answers { firstArg<String>().uppercase() }

        var set = getDatasourceRowRutinaDto()
        var searchRE = FinderUtils.filterSearchTxtRE("CaRdIo")

        val result = FinderUtils.filterSearchRutina(set, searchRE)

        assertEquals(true, result.isNotEmpty())
    }

    @Test
    fun `test filterMusculoRutina filtra por musculos`() {
        var set = getDatasourceRowRutinaDto()
        var setMusculo = setOf(Musculo.OBLICUOS, Musculo.PIERNA)

        val result = FinderUtils.filterMusculoRutina(set, setMusculo)

        val bool = result.all { ejercicio ->
            ejercicio.musculoSet.contains(Musculo.OBLICUOS) || ejercicio.musculoSet.contains(Musculo.PIERNA)
        }

        assertEquals(true, bool)
    }

    @Test
    fun `test filterNivelRutina filtra por nivel`() {
        var set = getDatasourceRowRutinaDto()
        var setMusculo = setOf(Nivel.FACIL, Nivel.MEDIO)

        val result = FinderUtils.filterNivelRutina(set, setMusculo)

        val bool = result.all { ejercicio ->
            ejercicio.nivel == Nivel.FACIL || ejercicio.nivel == Nivel.MEDIO
        }

        assertEquals(true, bool)
    }

    private fun getDatasourceStatRutinaSearchDto(): Set<StatRutinaSearchDto> {
        return setOf(
            StatRutinaSearchDto(
                idStat = 1,
                idRutina = 1,
                idRutinaSnapshot = 1,
                dateInit = LocalDateTime.of(2023, 1, 1, 10, 0, 0),
                dateEnd = LocalDateTime.of(2023, 1, 1, 10, 2, 10),
                isCompleted = true,
                nombre = "Adbominales facil",
                musculoSet = setOf(Musculo.ABDOMINALES),
                nivel = Nivel.FACIL,
                isPremium = false
            ),
            StatRutinaSearchDto(
                idStat = 2,
                idRutina = 2,
                idRutinaSnapshot = 2,
                dateInit = LocalDateTime.of(2025, 1, 1, 10, 10, 0),
                dateEnd = LocalDateTime.of(2025, 1, 1, 10, 12, 30),
                isCompleted = true,
                nombre = "Flexiones medio",
                musculoSet = setOf(Musculo.HOMBRO, Musculo.PECHO),
                nivel = Nivel.MEDIO,
                isPremium = false
            ),
            StatRutinaSearchDto(
                idStat = 3,
                idRutina = 3,
                idRutinaSnapshot = 3,
                dateInit = LocalDateTime.of(2024, 1, 1, 10, 0, 0),
                dateEnd = LocalDateTime.of(2024, 1, 1, 10, 2, 10),
                isCompleted = false,
                nombre = "Cardio",
                musculoSet = setOf(Musculo.PIERNA, Musculo.TRICEPS),
                nivel = Nivel.MEDIO,
                isPremium = true
            ),
            StatRutinaSearchDto(
                idStat = 4,
                idRutina = 4,
                idRutinaSnapshot = 4,
                dateInit = LocalDateTime.of(2023, 1, 1, 10, 18, 0),
                dateEnd = LocalDateTime.of(2023, 1, 1, 10, 20, 40),
                isCompleted = false,
                nombre = "Prueba de salto",
                musculoSet = setOf(Musculo.BICEPS, Musculo.GLUTEOS, Musculo.TRAPECIO),
                nivel = Nivel.DIFICIL,
                isPremium = false
            ),
            StatRutinaSearchDto(
                idStat = 5,
                idRutina = 5,
                idRutinaSnapshot = 5,
                dateInit = LocalDateTime.of(2023, 1, 1, 10, 13, 40),
                dateEnd = LocalDateTime.of(2023, 1, 1, 10, 14, 10),
                isCompleted = true,
                nombre = "Piernas fuertes",
                musculoSet = setOf(Musculo.PIERNA, Musculo.OBLICUOS),
                nivel = Nivel.NINGUNO,
                isPremium = true
            )
        )
    }

    @Test
    fun `test filterSearchStatRutina filtra por cadena de busqueda`() {
        every { UCharacter.toUpperCase(any<String>()) } answers { firstArg<String>().uppercase() }

        var set = getDatasourceStatRutinaSearchDto()
        var searchRE = FinderUtils.filterSearchTxtRE("Cardio")

        val result = FinderUtils.filterSearchStatRutina(set, searchRE)

        assertEquals(true, result.isNotEmpty())
    }

    @Test
    fun `test filterSearchStatRutina filtra sin tener en cuenta mayusculas y minusculas`() {
        every { UCharacter.toUpperCase(any<String>()) } answers { firstArg<String>().uppercase() }

        var set = getDatasourceStatRutinaSearchDto()
        var searchRE = FinderUtils.filterSearchTxtRE("CaRdIo")

        val result = FinderUtils.filterSearchStatRutina(set, searchRE)

        assertEquals(true, result.isNotEmpty())
    }

    @Test
    fun `test filterMusculoStatRutina filtra por musculos`() {
        var set = getDatasourceStatRutinaSearchDto()
        var setMusculo = setOf(Musculo.OBLICUOS, Musculo.PIERNA)

        val result = FinderUtils.filterMusculoStatRutina(set, setMusculo)

        val bool = result.all { ejercicio ->
            ejercicio.musculoSet.contains(Musculo.OBLICUOS) || ejercicio.musculoSet.contains(Musculo.PIERNA)
        }

        assertEquals(true, bool)
    }

    @Test
    fun `test filterNivelStatRutina filtra por nivel`() {
        var set = getDatasourceStatRutinaSearchDto()
        var setMusculo = setOf(Nivel.FACIL, Nivel.MEDIO)

        val result = FinderUtils.filterNivelStatRutina(set, setMusculo)

        val bool = result.all { ejercicio ->
            ejercicio.nivel == Nivel.FACIL || ejercicio.nivel == Nivel.MEDIO
        }

        assertEquals(true, bool)
    }

    @Test
    fun `test filterSearchStatStartDate filtra por fecha despues`() {
        var set = getDatasourceStatRutinaSearchDto()
        val localDate = LocalDate.of(2023, 1, 2)

        val result = FinderUtils.filterSearchStatStartDate(set, localDate)

        val bool = result.all { stat ->
            stat.dateInit.toLocalDate().isAfter(localDate)
        }

        assertEquals(true, bool)
    }

    @Test
    fun `test filterSearchStatEndDate filtra por fecha anterior`() {
        var set = getDatasourceStatRutinaSearchDto()
        val localDate = LocalDate.of(2024, 1, 2)

        val result = FinderUtils.filterSearchStatEndDate(set, localDate)

        val bool = result.all { stat ->
            stat.dateInit.toLocalDate().isBefore(localDate)
        }

        assertEquals(true, bool)
    }

    @Test
    fun `test filterSearchStatEndDate filtra por un rango de fecha`() {
        var set = getDatasourceStatRutinaSearchDto()
        val localDateInit = OptionalLocalDate.getOptionalByString("dd/MM/yyyy", "12/4/2020")
        val localDateEnd = OptionalLocalDate.getOptionalByString("dd/MM/yyyy", "12/4/2025")

        val result = FinderUtils.filterSearchStatRange(set, Pair(localDateInit, localDateEnd))

        assertEquals(true, result.isNotEmpty())
    }

    @Test
    fun `test filterSearchStatEndDate filtra por un rango de fecha con inicio vacio`() {
        var set = getDatasourceStatRutinaSearchDto()
        val localDateInit = OptionalLocalDate.Empty
        val localDateEnd = OptionalLocalDate.getOptionalByString("dd/MM/yyyy", "12/4/2025")

        val result = FinderUtils.filterSearchStatRange(set, Pair(localDateInit, localDateEnd))

        assertEquals(true, result.isNotEmpty())
    }

    @Test
    fun `test filterSearchStatEndDate filtra por un rango de fecha con fin vacio`() {
        var set = getDatasourceStatRutinaSearchDto()
        val localDateInit = OptionalLocalDate.getOptionalByString("dd/MM/yyyy", "12/4/2020")
        val localDateEnd = OptionalLocalDate.Empty

        val result = FinderUtils.filterSearchStatRange(set, Pair(localDateInit, localDateEnd))

        assertEquals(true, result.isNotEmpty())
    }

    private fun getDatasourceRowMaterialDto(): Set<RowMaterialDto> {
        return setOf(
            RowMaterialDto(
                idMaterial = 1,
                idUser = 1,
                nombre = "Comba",
                photoUri = Uri.EMPTY
            ),
            RowMaterialDto(
                idMaterial = 2,
                idUser = 1,
                nombre = "Mancuerna",
                photoUri = Uri.EMPTY
            ),
            RowMaterialDto(
                idMaterial = 3,
                idUser = 1,
                nombre = "Balon",
                photoUri = Uri.EMPTY
            )
        )
    }

    @Test
    fun `test filterSearchMaterial filtra por cadena de busqueda`() {
        every { UCharacter.toUpperCase(any<String>()) } answers { firstArg<String>().uppercase() }

        var set = getDatasourceRowMaterialDto()
        var searchRE = FinderUtils.filterSearchTxtRE("Mancuerna")

        val result = FinderUtils.filterSearchMaterial(set, searchRE)

        assertEquals(true, result.isNotEmpty())
    }

    @Test
    fun `test filterSearchMaterial filtra sin tener en cuenta mayusculas y minusculas`() {
        every { UCharacter.toUpperCase(any<String>()) } answers { firstArg<String>().uppercase() }

        var set = getDatasourceRowMaterialDto()
        var searchRE = FinderUtils.filterSearchTxtRE("MaNcUeRnA")

        val result = FinderUtils.filterSearchMaterial(set, searchRE)

        assertEquals(true, result.isNotEmpty())
    }
}