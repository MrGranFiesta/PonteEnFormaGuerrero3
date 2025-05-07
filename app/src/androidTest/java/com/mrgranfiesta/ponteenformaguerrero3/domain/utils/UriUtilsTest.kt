package com.mrgranfiesta.ponteenformaguerrero3.domain.utils

import androidx.core.net.toUri
import androidx.test.platform.app.InstrumentationRegistry
import com.mrgranfiesta.ponteenformaguerrero3.data.filesystem.StorageFileService
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_EJERCICIO_IMG
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DRAWABLE_URI
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.EXTERNAL_STORAGE_URI
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import org.junit.Assert
import org.junit.Test

class UriUtilsTest {
    /*
    * Necesita este test crear un fichero por que valida si es y existe
    * */
    @Test
    fun testGetNameByUri_GeNameImgWithNotUserInitAndUriNotEmpty() {
        val nameImgInsert = StorageFileService.insertImg(
            context = InstrumentationRegistry.getInstrumentation().targetContext,
            dir = DIR_EJERCICIO_IMG,
            galeryUri = "${DRAWABLE_URI}ejer1_999_998".toUri()
        )

        val uri = "${EXTERNAL_STORAGE_URI}${DIR_EJERCICIO_IMG}/$nameImgInsert".toUri()

        val nameImgResult = UriUtils.getNameByUri(
            photoUri = uri,
            rol = Rol.GUEST
        )

        Assert.assertEquals(nameImgInsert, nameImgResult)
    }
}