package com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot

interface MaterialSnapshotRepository {
    suspend fun isUseNameImg(nameImg : String) : Boolean
    suspend fun isNotUseNameImg(nameImg : String) : Boolean
    suspend fun getNameImgByRutinaId(idRutina: Long) : List<String>
    suspend fun isNotUseNameImgGlobal(nameImg : String) : Boolean
}