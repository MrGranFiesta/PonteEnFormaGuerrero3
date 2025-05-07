package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import androidx.lifecycle.ViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.AnswerState

class TextFieldAnswerVM : ViewModel() {
    private var cursor: Int = 0
    private var status: Boolean = true
    private var listAnswer: List<AnswerState> = AnswerState.getListOpt()

    @Synchronized
    fun initData(answer: AnswerState) {
        if (status) {
            cursor = listAnswer.indexOf(answer)
            status = false
        }
    }

    fun changeLeft(changeAnswer: (AnswerState) -> Unit) {
        cursor = if (cursor == 0) {
            listAnswer.size - 1
        } else {
            cursor - 1
        }
        changeAnswer(listAnswer[cursor])
    }

    fun changeRight(changeAnswer: (AnswerState) -> Unit) {
        cursor = if (cursor == listAnswer.size - 1) {
            0
        } else {
            cursor + 1
        }
        changeAnswer(listAnswer[cursor])
    }
}