package com.mrgranfiesta.ponteenformaguerrero3.domain.utils

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.SoundFile
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SoundUtilsTest {

    @RelaxedMockK
    private lateinit var context : Context

    @RelaxedMockK
    private var mediaPlayer : MediaPlayer? = null

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        mockkStatic(UriUtils::class)
        mockkStatic(MediaPlayer::class)
        mockkObject(SoundUtils)
    }

    @After
    fun onAfter(){
        unmockkAll()
    }

    @Test
    fun `test playSound reproduce sonido sin detener ninguno antes`() {
        every { mediaPlayer?.isPlaying } returns false

        SoundUtils.playSound(
            sound = SoundFile.SWEET_ALERT,
            context = context
        )

        verify { MediaPlayer.create(any(), any<Uri>()) }
    }

    @Test
    fun `test playSound detiene sonido y reproduce un nuevo sonido`() {
        every { mediaPlayer?.isPlaying } returns true

        SoundUtils.playSound(
            sound = SoundFile.SWEET_ALERT,
            context = context
        )

        verify { MediaPlayer.create(any(), any<Uri>()) }
    }
}