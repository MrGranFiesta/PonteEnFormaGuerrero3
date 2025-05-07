package com.mrgranfiesta.ponteenformaguerrero3.domain.utils

import android.content.Context
import android.media.MediaPlayer
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.SoundFile

class SoundUtils {
    companion object {
        private var mediaPlayer : MediaPlayer? = null

        fun playSound(context: Context, sound : SoundFile) {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.stop()
                mediaPlayer?.release()
                mediaPlayer = null
            }
            mediaPlayer = MediaPlayer.create(context, UriUtils.getUriAudio(sound))
            mediaPlayer?.start()
        }
    }
}