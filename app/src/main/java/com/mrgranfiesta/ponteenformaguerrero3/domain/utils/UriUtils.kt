package com.mrgranfiesta.ponteenformaguerrero3.domain.utils

import android.net.Uri
import androidx.core.net.toFile
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DRAWABLE_URI
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.EXTERNAL_STORAGE_URI
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.RAW_URI
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.SoundFile
import androidx.core.net.toUri

class UriUtils {
    companion object {
        fun getUriResource(
            dir: String,
            nameImg: String,
            rol: Rol
        ): Uri {
            return if (rol == Rol.INIT_DATA_USER) {
                if (nameImg.isNotEmpty()) {
                    "$DRAWABLE_URI${nameImg}".toUri()
                } else {
                    Uri.EMPTY
                }
            } else {
                if (nameImg.isNotEmpty()) {
                    "${EXTERNAL_STORAGE_URI}${dir}/${nameImg}".toUri()
                } else {
                    Uri.EMPTY
                }
            }
        }

        fun getNameByUri(
            photoUri: Uri,
            rol: Rol
        ): String {
            var nameImg = ""
            try {
                if (photoUri.path != Uri.EMPTY.path) {
                    if (rol == Rol.INIT_DATA_USER) {
                        val pathSegment = photoUri.lastPathSegment
                        if (pathSegment != null) {
                            nameImg = pathSegment
                        }
                    } else {
                        val fileImg = photoUri.toFile()
                        if (fileImg.isFile) {
                            nameImg = fileImg.name
                        }
                    }
                }
            } catch (_: Exception) {
                nameImg = ""
            }
            return nameImg
        }

        fun getUriResource(
            dir: String,
            nameImg: String
        ): Uri = if (nameImg.isNotEmpty()) {
            "${EXTERNAL_STORAGE_URI}${dir}/${nameImg}".toUri()
        } else {
            Uri.EMPTY
        }

        fun getNameByUri(photoUri: Uri) : String {
            return when {
                photoUri.scheme == "android.resource" -> {
                    photoUri.lastPathSegment ?: ""
                }
                photoUri.path != Uri.EMPTY.path -> {
                    try {
                        val fileImg = photoUri.toFile()
                        if (fileImg.isFile) fileImg.name else ""
                    } catch (_: IllegalArgumentException) {
                        ""
                    }
                }
                else -> ""
            }
        }

        fun getUriAudio(sound: SoundFile): Uri =
            "$RAW_URI${sound.nameFile}".toUri()
    }
}