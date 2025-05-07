package com.mrgranfiesta.ponteenformaguerrero3.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converter {
    @TypeConverter
    fun toRol(value: Int) = Rol.fromId(value)

    @TypeConverter
    fun fromRol(value: Rol) = value.id

    @TypeConverter
    fun toNivel(value: Int) = Nivel.fromId(value)

    @TypeConverter
    fun fromNivel(value: Nivel) = value.id

    @TypeConverter
    fun toTipoEsfuerzo(value: Int) = TipoEsfuerzo.fromId(value)

    @TypeConverter
    fun fromTipoEsfuerzo(value: TipoEsfuerzo) = value.id

    @TypeConverter
    fun toMusculoSet(value: String): Set<Musculo> {
        val typeList = object : TypeToken<Set<Musculo>>() {}.type
        return Gson().fromJson(value, typeList)
    }

    @TypeConverter
    fun fromMusculoSet(set: Set<Musculo>): String {
        return Gson().toJson(set.sortedBy { it.name }.toSet())
    }

    @TypeConverter
    fun toMusculoMutableSet(value: String): MutableSet<Musculo> {
        val typeList = object : TypeToken<Set<Musculo>>() {}.type
        return Gson().fromJson(value, typeList)
    }

    @TypeConverter
    fun fromLocalDateTime(localDateTime: LocalDateTime): String {
        return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

    @TypeConverter
    fun toLocalDateTime(value: String): LocalDateTime {
        return LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }
}