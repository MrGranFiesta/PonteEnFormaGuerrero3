package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import androidx.lifecycle.ViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.objects.OptionalLocalDate
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.DateUtils
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.RegexExpresion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DateRangeFieldsVM : ViewModel() {
    private val _initDate = MutableStateFlow("")
    val initDate: StateFlow<String> = _initDate
    fun setInitDate(initDate: String) {
        if (initDate.matches(RegexExpresion.IS_DIGITS)) {
            _initDate.value = initDate.trim()
        }
    }

    private val _endDate = MutableStateFlow("")
    val endDate: StateFlow<String> = _endDate
    fun setEndDate(endDate: String) {
        if (endDate.matches(RegexExpresion.IS_DIGITS)) {
            _endDate.value = endDate.trim()
        }
    }

    fun getPairOptional(): Pair<OptionalLocalDate, OptionalLocalDate> = Pair(
        first = OptionalLocalDate.getOptionalByString("ddMMyyyy", _initDate.value),
        second = OptionalLocalDate.getOptionalByString("ddMMyyyy", _endDate.value)
    )


    fun initData(
        rangeDate: Pair<OptionalLocalDate, OptionalLocalDate>
    ) {
        _initDate.value = rangeDate.first.getStringDateOrEmpty("ddMMyyyy")
        _endDate.value = rangeDate.second.getStringDateOrEmpty("ddMMyyyy")
    }

    fun isAfter(
        initDate: String,
        endDate: String
    ): Boolean = try {
        val init = DateUtils.getLocalDate("ddMMyyyy", initDate)
        val end = DateUtils.getLocalDate("ddMMyyyy", endDate)
        init.isAfter(end)
    } catch (_: Exception) {
        false
    }
}