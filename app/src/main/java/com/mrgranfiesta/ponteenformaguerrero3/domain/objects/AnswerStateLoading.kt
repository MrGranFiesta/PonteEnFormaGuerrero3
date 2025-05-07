package com.mrgranfiesta.ponteenformaguerrero3.domain.objects

import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.AnswerState
import kotlinx.parcelize.Parcelize

@Parcelize
enum class AnswerStateLoading : Parcelable {
    YES, NO, ASK_LATER, ON_LOADING;

    companion object {
        fun convert(state: AnswerState): AnswerStateLoading {
            return when (state) {
                AnswerState.YES -> YES
                AnswerState.NO -> NO
                AnswerState.ASK_LATER -> ASK_LATER
            }
        }
    }
}
