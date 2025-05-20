package com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.faqs

import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.CategoryFaqs

data class Faq(
    val id: Int,
    val category : CategoryFaqs,
    val question : String
)
