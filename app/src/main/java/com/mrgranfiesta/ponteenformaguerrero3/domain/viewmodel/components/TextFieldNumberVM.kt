package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components


import androidx.lifecycle.ViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.RegexExpresion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TextFieldNumberVM : ViewModel() {
    private var status = true

    private val defaultDigits = 1
    private val defaultString = "1"

    private val _cantidadTxt = MutableStateFlow(defaultString)
    val cantidadTxt: StateFlow<String> = _cantidadTxt
    fun setCantidadTxt(cantidadTxt: String) {
        _cantidadTxt.value = cantidadTxt
    }

    @Synchronized
    fun initData(cantidad: Int) {
        if (status) {
            setCantidadTxt(cantidad.toString())
            status = false
        }
    }

    fun resetStatus() {
        status = true
    }

    private val _hasFocus = MutableStateFlow(false)
    private val hasFocus: StateFlow<Boolean> = _hasFocus
    fun setHasFocus(hasFocus: Boolean) {
        _hasFocus.value = hasFocus
    }

    fun onChangeTextField(it: String) {
        if (hasFocus.value) {
            this.setCantidadTxt(it.replace(RegexExpresion.NO_DIGITS_REMPLACE, ""))
        }
    }

    fun onChangeFocus(changeContNumber: (Int) -> Unit) {
        if (!hasFocus.value) {
            val tempTxt = _cantidadTxt.value.replace(RegexExpresion.NO_DIGITS_REMPLACE, "")
            if (tempTxt.trim().isNotEmpty()) {
                try {
                    val temp = numberLimit(tempTxt)
                    changeContNumber(temp)
                    this.setCantidadTxt(temp.toString())
                } catch (_: NumberFormatException) {
                    changeContNumber(defaultDigits)
                    this.setCantidadTxt(defaultString)
                }
            } else {
                changeContNumber(defaultDigits)
                this.setCantidadTxt(defaultString)
            }
            val temp = numberLimit(cantidadTxt.value)
            changeContNumber(temp)
            this.setCantidadTxt(temp.toString())
        }
    }

    fun onClickIconPlus(
        changeContNumber: (Int) -> Unit,
        cont: Int,
        amount: Int
    ) {
        val result = calculateButtonAdd(cont, amount)
        changeContNumber(result)
        this.setCantidadTxt(result.toString())
    }

    fun onClickIconLess(
        changeContNumber: (Int) -> Unit,
        cont: Int,
        amount: Int
    ) {
        val result = calculateButtonless(cont, amount)
        changeContNumber(result)
        this.setCantidadTxt(result.toString())
    }

    private fun numberLimit(cantidadTxt: String): Int {
        return try {
            if (!cantidadTxt.contains(RegexExpresion.IS_DIGITS)) {
                defaultDigits
            } else {
                val temp = cantidadTxt.replace(RegexExpresion.NO_DIGITS_REMPLACE, "").toInt()
                if (temp > 0) {
                    temp
                } else {
                    defaultDigits
                }
            }
        } catch (_: NumberFormatException) {
            Int.MAX_VALUE
        }
    }

    private fun calculateButtonAdd(accumulator: Int, amount: Int): Int {
        return try {
            val temp: Long = accumulator.toLong() + amount
            if (temp < 0) {
                amount
            } else if (temp > Int.MAX_VALUE) {
                accumulator
            } else {
                accumulator + amount
            }
        } catch (_: NumberFormatException) {
            Int.MAX_VALUE
        }
    }

    private fun calculateButtonless(accumulator: Int, amount: Int): Int {
        return try {
            val temp = accumulator - amount
            return if (temp < defaultDigits) {
                defaultDigits
            } else {
                temp
            }
        } catch (_: NumberFormatException) {
            Int.MAX_VALUE
        }
    }
}