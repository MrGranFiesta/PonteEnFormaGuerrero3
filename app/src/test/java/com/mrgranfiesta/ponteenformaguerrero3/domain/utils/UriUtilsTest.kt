package com.mrgranfiesta.ponteenformaguerrero3.domain.utils

import android.net.Uri
import androidx.core.net.toUri
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_EJERCICIO_IMG
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_PROFILE_USER_IMG
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DRAWABLE_URI
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.EXTERNAL_STORAGE_URI
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.RAW_URI
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.SoundFile
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UriUtilsTest {
    @Test
    fun `test getUriResource obtener Uri con un usuario que no sea init_user y que no sea vacio el nombre`() {
        val uriResult = UriUtils.getUriResource(
            dir = DIR_EJERCICIO_IMG,
            nameImg = "ejer200_000",
            rol = Rol.GUEST
        )
        val uriSolution = "${EXTERNAL_STORAGE_URI}${DIR_EJERCICIO_IMG}/ejer200_000".toUri()
        assertEquals(uriSolution, uriResult)
    }

    @Test
    fun `test getUriResource obtener Uri con el usuario init_user y que no sea vacio el nombre`() {
        val uriResult = UriUtils.getUriResource(
            dir = DIR_EJERCICIO_IMG,
            nameImg = "ejer200_000",
            rol = Rol.INIT_DATA_USER
        )
        val uriSolution = "${DRAWABLE_URI}ejer200_000".toUri()
        assertEquals(uriSolution, uriResult)
    }

    @Test
    fun `test getUriResource obtener Uri con un usuario que no sea init_user y con el nombre vacio`() {
        val uriResult = UriUtils.getUriResource(
            dir = DIR_EJERCICIO_IMG,
            nameImg = "",
            rol = Rol.GUEST
        )
        val uriSolution = Uri.EMPTY
        assertEquals(uriSolution, uriResult)
    }

    @Test
    fun `test getUriResource obtener Uri con el usuario init_user y con el nombre vacio`() {
        val uriResult = UriUtils.getUriResource(
            dir = DIR_EJERCICIO_IMG,
            nameImg = "",
            rol = Rol.INIT_DATA_USER
        )
        val uriSolution = Uri.EMPTY
        assertEquals(uriSolution, uriResult)
    }

    /*TODO */
    @Test
    fun `test getNameByUri obtener NameImg con un usuario que no sea init_user y la uri no empty`() {
        val uri = "${EXTERNAL_STORAGE_URI}${DIR_EJERCICIO_IMG}/ejer200_000".toUri()
        val nameImg = UriUtils.getNameByUri(
            photoUri = uri,
            rol = Rol.GUEST
        )
        assertEquals("ejer200_000", nameImg)
    }

    @Test
    fun `test getNameByUri obtener Uri con el usuario init_user y la uri empty`() {
        val nameImg = UriUtils.getNameByUri(
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )
        assertEquals("", nameImg)
    }

    @Test
    fun `test getNameByUri obtener NameImg con un usuario que no sea init_user y una uri`() {
        val uri = "${DRAWABLE_URI}ejer200_000".toUri()
        val nameImg = UriUtils.getNameByUri(
            photoUri = uri,
            rol = Rol.INIT_DATA_USER
        )
        assertEquals("ejer200_000", nameImg)
    }

    @Test
    fun `test getNameByUri obtener NameImg con el usuario init_user y la uri empty`() {
        val nameImg = UriUtils.getNameByUri(
            photoUri = Uri.EMPTY,
            rol = Rol.INIT_DATA_USER
        )
        assertEquals("", nameImg)
    }

    @Test
    fun `test getUriResource obtener Uri sin rol y que no sea empty`() {
        val uriResult = UriUtils.getUriResource(
            dir = DIR_PROFILE_USER_IMG,
            nameImg = "ejer200_000"
        )
        val uriSolution = "${EXTERNAL_STORAGE_URI}${DIR_PROFILE_USER_IMG}/ejer200_000".toUri()
        assertEquals(uriResult, uriSolution)
    }

    @Test
    fun `test getUriResource obtener Uri sin rol y que sea empty`() {
        val uriResult = UriUtils.getUriResource(
            dir = DIR_PROFILE_USER_IMG,
            nameImg = ""
        )
        val uriSolution = Uri.EMPTY
        assertEquals(uriResult, uriSolution)
    }

    @Test
    fun `test getNameByUri obtener NameImg sin rol y que no sea una cadena vacia`() {
        val uri = "${DRAWABLE_URI}ejer2_000_000".toUri()

        val nameImg = UriUtils.getNameByUri(uri)
        assertEquals("ejer200_000", nameImg)
    }

    @Test
    fun `test getNameByUri obtener NameImg sin rol y que sea una cadena vacia`() {
        val nameImg = UriUtils.getNameByUri(Uri.EMPTY)
        assertEquals("", nameImg)
    }

    @Test
    fun `test getUriAudio obtener la uri de un audio`() {
        val uriResultAudio1 = UriUtils.getUriAudio(SoundFile.SWEET_ALERT)
        val uriSolutionAudio1 = "$RAW_URI${SoundFile.SWEET_ALERT.nameFile}".toUri()
        assertEquals(uriSolutionAudio1, uriResultAudio1)

        val uriResultAudio2 = UriUtils.getUriAudio(SoundFile.SWEET_ALERT)
        val uriSolutionAudio2 = "$RAW_URI${SoundFile.SWEET_ALERT.nameFile}".toUri()
        assertEquals(uriSolutionAudio2, uriResultAudio2)

    }
}