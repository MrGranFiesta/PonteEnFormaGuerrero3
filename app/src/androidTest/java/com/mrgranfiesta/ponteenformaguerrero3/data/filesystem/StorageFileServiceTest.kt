package com.mrgranfiesta.ponteenformaguerrero3.data.filesystem

import android.content.Context
import androidx.core.net.toUri
import androidx.test.core.app.ApplicationProvider
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_EJERCICIO_IMG
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DRAWABLE_URI
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import java.io.File

class StorageFileServiceTest {

    private lateinit var context: Context

    @Before
    fun onBefore() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun testInsertImg() {
        val nameImg = StorageFileService.insertImg(
            context = context,
            dir = DIR_EJERCICIO_IMG,
            galeryUri = "${DRAWABLE_URI}ejer2_000_000".toUri()
        )

        assertTrue(nameImg.isNotEmpty())

        val fileImg = File(context.getExternalFilesDir(null), "$DIR_EJERCICIO_IMG/$nameImg")

        assertTrue(fileImg.exists())
    }

    @Test
    fun testUpdateImg() {
        val nameInsertImg = StorageFileService.insertImg(
            context = context,
            dir = DIR_EJERCICIO_IMG,
            galeryUri = "${DRAWABLE_URI}ejer2_000_000".toUri()
        )

        assertTrue(nameInsertImg.isNotEmpty())

        val filInserteImg = File(context.getExternalFilesDir(null), "$DIR_EJERCICIO_IMG/$nameInsertImg")

        assertTrue(filInserteImg.exists())

        val nameUpdateImg = StorageFileService.updateImg(
            context = context,
            galeryUri = "${DRAWABLE_URI}ejer1_999_999".toUri(),
            dir = DIR_EJERCICIO_IMG,
            deleteImgUri = filInserteImg.toUri()
        )

        assertTrue(nameUpdateImg.isNotEmpty())

        val fileUpdateImg = File(context.getExternalFilesDir(null), "$DIR_EJERCICIO_IMG/$nameUpdateImg")

        assertFalse(filInserteImg.exists())
        assertTrue(fileUpdateImg.exists())
    }

    @Test
    fun testDeleteImg() {
        val nameImg = StorageFileService.insertImg(
            context = context,
            dir = DIR_EJERCICIO_IMG,
            galeryUri = "${DRAWABLE_URI}ejer2_000_000".toUri()
        )

        assertTrue(nameImg.isNotEmpty())

        val fileImg = File(context.getExternalFilesDir(null), "$DIR_EJERCICIO_IMG/$nameImg")

        assertTrue(fileImg.exists())

        val result = StorageFileService.deleteImg(
            context = context,
            dir = DIR_EJERCICIO_IMG,
            deleteImgUri = fileImg.toUri()
        )

        assertTrue(result)
    }
}
