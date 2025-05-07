package com.mrgranfiesta.ponteenformaguerrero3.domain.utils

import android.icu.lang.UCharacter
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.RowEjercicioDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.rutina.RowRutinaDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.stat.StatRutinaSearchDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.objects.OptionalLocalDate
import java.time.LocalDate
import java.util.regex.Pattern

class FinderUtils {
    companion object {
        fun filterSearchTxtRE(searchTxt: String): String {
            var resultSearch = searchTxt.trim()
            if (resultSearch.contains('*')) {
                resultSearch = resultSearch.replace("*", ".*")
            }
            if (resultSearch.contains('+')) {
                resultSearch = resultSearch.replace("+", "")
            }
            if (resultSearch.contains('?')) {
                resultSearch = resultSearch.replace("?", "")
            }
            if (resultSearch.contains("|")) {
                resultSearch = resultSearch.replace(Regex("\\s*\\|\\s*"), "|")
                if (resultSearch.contains(Regex("\\|$"))) {
                    resultSearch = resultSearch.dropLast(1)
                }
            }
            if (resultSearch.contains(Regex("\\s"))) {
                resultSearch = resultSearch.replace(Regex("\\s+"), "|")
            }
            if (resultSearch.isNotEmpty()) {
                resultSearch = ".*$resultSearch.*"
            }
            return resultSearch
        }

        fun filterSearchEjercicio(
            ejercicioListDB: Set<RowEjercicioDto>,
            searchRE: String
        ): List<RowEjercicioDto> {
            return ejercicioListDB.filter {
                val pattern = Pattern.compile(UCharacter.toUpperCase(searchRE))
                val matcher = pattern.matcher(UCharacter.toUpperCase(it.nombre))
                matcher.matches()
            }
        }

        fun filterMusculoEjercicio(
            ejercicioListDB: Set<RowEjercicioDto>,
            musculoSetSearch: Set<Musculo>,
        ): List<RowEjercicioDto> {
            return ejercicioListDB.filter {
                Validators.isIntersectContent(musculoSetSearch, it.musculoSet)
            }
        }

        fun filterNivelEjercicio(
            ejercicioListDB: Set<RowEjercicioDto>,
            nivelSetSearch: Set<Nivel>,
        ): List<RowEjercicioDto> {
            return ejercicioListDB.filter {
                Validators.isContent(nivelSetSearch, it.nivel)
            }
        }

        fun filterSearchRutina(
            rutinaListDB: Set<RowRutinaDto>,
            searchRE: String
        ): List<RowRutinaDto> {
            return rutinaListDB.filter {
                val pattern = Pattern.compile(UCharacter.toUpperCase(searchRE))
                val matcher = pattern.matcher(UCharacter.toUpperCase(it.nombre))
                matcher.matches()
            }
        }

        fun filterSearchStatRutina(
            statListDB: Set<StatRutinaSearchDto>,
            searchRE: String
        ): List<StatRutinaSearchDto> {
            return statListDB.filter {
                val pattern = Pattern.compile(UCharacter.toUpperCase(searchRE))
                val matcher = pattern.matcher(UCharacter.toUpperCase(it.nombre))
                matcher.matches()
            }
        }

        fun filterMusculoRutina(
            rutinaListDB: Set<RowRutinaDto>,
            musculoSetSearch: Set<Musculo>,
        ): List<RowRutinaDto> {
            return rutinaListDB.filter {
                Validators.isIntersectContent(musculoSetSearch, it.musculoSet)
            }
        }

        fun filterMusculoStatRutina(
            statRutinaListDB: Set<StatRutinaSearchDto>,
            musculoSetSearch: Set<Musculo>,
        ): List<StatRutinaSearchDto> {
            return statRutinaListDB.filter {
                Validators.isIntersectContent(musculoSetSearch, it.musculoSet)
            }
        }

        fun filterNivelRutina(
            rutinaListDB: Set<RowRutinaDto>,
            nivelSetSearch: Set<Nivel>,
        ): List<RowRutinaDto> {
            return rutinaListDB.filter {
                Validators.isContent(nivelSetSearch, it.nivel)
            }
        }

        fun filterNivelStatRutina(
            statRutinaListDB: Set<StatRutinaSearchDto>,
            nivelSetSearch: Set<Nivel>,
        ): List<StatRutinaSearchDto> {
            return statRutinaListDB.filter {
                Validators.isContent(nivelSetSearch, it.nivel)
            }
        }

        fun filterSearchMaterial(
            materialListDB: Set<RowMaterialDto>,
            searchRE: String
        ): List<RowMaterialDto> {
            return materialListDB.filter {
                val pattern = Pattern.compile(UCharacter.toUpperCase(searchRE))
                val matcher = pattern.matcher(UCharacter.toUpperCase(it.nombre))
                matcher.matches()
            }
        }

        fun filterSearchStatStartDate(
            statListDB: Set<StatRutinaSearchDto>,
            date: LocalDate
        ): List<StatRutinaSearchDto> {
            return statListDB.filter {
                it.dateInit.toLocalDate().isAfter(date)
            }
        }

        fun filterSearchStatEndDate(
            statListDB: Set<StatRutinaSearchDto>,
            date: LocalDate
        ): List<StatRutinaSearchDto> {
            return statListDB.filter {
                it.dateInit.toLocalDate().isBefore(date)
            }
        }

        fun filterSearchStatRange(
            statListDB: Set<StatRutinaSearchDto>,
            rangeDate: Pair<OptionalLocalDate, OptionalLocalDate>
        ): List<StatRutinaSearchDto> {
            val startDate = (rangeDate.first as? OptionalLocalDate.Date)?.date
            val endDate = (rangeDate.second as? OptionalLocalDate.Date)?.date
            return if (startDate != null && endDate != null) {
                statListDB.filter { it.dateInit.toLocalDate() in startDate..endDate }
            } else {
                statListDB.toList()
            }
        }
    }
}