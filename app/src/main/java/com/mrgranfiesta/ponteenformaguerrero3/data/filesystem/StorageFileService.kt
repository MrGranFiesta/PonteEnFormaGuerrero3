package com.mrgranfiesta.ponteenformaguerrero3.data.filesystem

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.core.net.toFile
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_EJERCICIO_IMG
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_MATERIAL_IMG
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_PROFILE_USER_IMG
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_TEMP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.JPG_EXTENSION
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.WEBP_EXTENSION
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.StringFormat.Companion.TIMESTAMP_FORMAT
import okio.use
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class StorageFileService {
    companion object {
        private fun initialMkdir(context: Context): Boolean {
            return listOf(
                File(context.getExternalFilesDir(null), DIR_TEMP),
                File(context.getExternalFilesDir(null), DIR_EJERCICIO_IMG),
                File(context.getExternalFilesDir(null), DIR_MATERIAL_IMG),
                File(context.getExternalFilesDir(null), DIR_PROFILE_USER_IMG)
            ).map {
                if (!it.exists()) {
                    it.mkdir()
                } else {
                    true
                }
            }.all { it }
        }

        fun insertImg(
            context: Context,
            dir: String,
            galeryUri: Uri
        ): String {
            val fileDirTemp = File(context.getExternalFilesDir(null), DIR_TEMP)
            val fileDir = File(context.getExternalFilesDir(null), dir)
            if (!fileDir.exists() || !fileDirTemp.exists()) {
                this.initialMkdir(context)
            }
            val name = writeImg(
                context = context,
                galeryUri = galeryUri
            )
            val nameFinal = writeWebpImg(
                context = context,
                dir = dir,
                name = name,
            )
            this.clearTemp(
                context = context
            )
            return nameFinal
        }

        private fun clearTemp(
            context: Context
        ): Boolean {
            val fileDir = File(context.getExternalFilesDir(null), DIR_TEMP)
            return if (fileDir.exists()) {
                try {
                    return fileDir.listFiles()?.map {
                        it.delete()
                    }?.all { it } != false
                } catch (_: FileNotFoundException) {
                    false
                } catch (_: Exception) {
                    false
                }
            } else {
                false
            }
        }

        private fun writeImg(
            context: Context,
            galeryUri: Uri
        ): String {
            val dirTemp = DIR_TEMP
            val fileDir = File(context.getExternalFilesDir(null), dirTemp)
            val timeStamp = SimpleDateFormat(TIMESTAMP_FORMAT, Locale.getDefault()).format(Date())
            val fileImg = File(fileDir, "${timeStamp}${JPG_EXTENSION}")
            return if (fileDir.exists()) {
                try {
                    val img = context.contentResolver.openInputStream(galeryUri)?.use {
                        it.readBytes()
                    }
                    FileOutputStream(fileImg).use { out ->
                        out.write(img)
                    }
                    timeStamp
                } catch (_: FileNotFoundException) {
                    ""
                } catch (_: Exception) {
                    ""
                }
            } else {
                ""
            }
        }

        @Suppress("kotlin:S1874")
        private fun writeWebpImg(
            context: Context,
            dir: String,
            name: String,
        ): String {
            val fileDirCopy = File(context.getExternalFilesDir(null), dir)
            val fileDirTemp = File(context.getExternalFilesDir(null), DIR_TEMP)
            val fileTemp = File(fileDirTemp, "${name}${JPG_EXTENSION}")
            val nameFileWithExtension = "${name}${WEBP_EXTENSION}"
            val fileImgCopy = File(fileDirCopy, nameFileWithExtension)
            return if (fileDirCopy.exists() && fileDirTemp.exists()) {
                try {
                    val img = fileTemp.inputStream().use {
                        it.readBytes()
                    }
                    FileOutputStream(fileImgCopy).use { out ->
                        val original = BitmapFactory.decodeFile(fileTemp.path)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            original.compress(
                                Bitmap.CompressFormat.WEBP_LOSSY, 0, out
                            )
                        } else {
                            original.compress(Bitmap.CompressFormat.WEBP, 0, out)
                        }
                        out.write(img)
                    }
                    nameFileWithExtension
                } catch (_: FileNotFoundException) {
                    ""
                } catch (_: Exception) {
                    ""
                }
            } else {
                ""
            }
        }

        fun updateImg(
            context: Context,
            galeryUri: Uri,
            dir: String,
            deleteImgUri: Uri
        ): String {
            this.deleteImg(
                context = context,
                deleteImgUri = deleteImgUri,
                dir = dir
            )
            val name = writeImg(
                context = context,
                galeryUri = galeryUri
            )
            val nameFinal = writeWebpImg(
                context = context,
                dir = dir,
                name = name,
            )
            this.clearTemp(
                context = context
            )
            return nameFinal
        }

        fun deleteImg(
            context: Context,
            deleteImgUri: Uri,
            dir: String
        ): Boolean {
            val fileDir = File(context.getExternalFilesDir(null), dir)
            if (deleteImgUri.path == Uri.EMPTY.path) {
                return true
            }
            val fileImg = deleteImgUri.toFile()
            return if (fileDir.exists() && fileImg.exists() && fileImg.isFile) {
                try {
                    fileImg.delete()
                } catch (_: FileNotFoundException) {
                    false
                } catch (_: Exception) {
                    false
                }
            } else {
                true
            }
        }
    }
}