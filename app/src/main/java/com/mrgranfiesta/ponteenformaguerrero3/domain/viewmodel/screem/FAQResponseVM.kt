package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import androidx.lifecycle.ViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.ListManager

class FAQResponseVM : ViewModel() {
    fun getTitle(idFaq : Int)  = ListManager
        .getFaqsFactory()
        .find { it.id == idFaq }?.question ?: ""

}