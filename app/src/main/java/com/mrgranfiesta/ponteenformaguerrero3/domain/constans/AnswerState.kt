package com.mrgranfiesta.ponteenformaguerrero3.domain.constans

enum class AnswerState {
    YES, NO, ASK_LATER;

    companion object {
        fun getOptionTxt(answer: AnswerState): String {
            return when (answer) {
                YES -> "Si"
                NO -> "No"
                ASK_LATER -> "Preguntar después"
            }
        }

        fun getListOpt() = listOf(YES, NO, ASK_LATER)
    }
}