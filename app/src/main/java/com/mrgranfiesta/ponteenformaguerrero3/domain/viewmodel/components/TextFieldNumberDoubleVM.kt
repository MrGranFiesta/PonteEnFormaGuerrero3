package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import android.icu.math.BigDecimal
import androidx.lifecycle.ViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.RegexExpresion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TextFieldNumberDoubleVM : ViewModel() {
    private var status = true
    private val minDouble = 0.0
    private val maxDouble = 9999999.99
    private val defaultDouble = 1.0
    private val defaultString = "1.0"

    private var cantidad = defaultDouble

    private val _cantidadTxt = MutableStateFlow(defaultString)
    val cantidadTxt: StateFlow<String> = _cantidadTxt
    fun setCantidadTxt(cantidadTxt: String) {
        _cantidadTxt.value = cantidadTxt
    }

    private val _hasFocus = MutableStateFlow(false)
    private val hasFocus: StateFlow<Boolean> = _hasFocus
    fun setHasFocus(hasFocus: Boolean) {
        _hasFocus.value = hasFocus
    }

    @Synchronized
    fun initData(cont: Double) {
        if (status) {
            this.setCantidadTxt(cont.toString())
            this.cantidad = cont
            status = false
        }
    }


    fun onChangeTextField(it: String) {
        if (hasFocus.value) {
            this.setCantidadTxt(it.replace(RegexExpresion.NO_DIGITS_DECIMAL_REMPLACE, ""))
        }
    }

    fun onChangeFocus(changeContNumber: (Double) -> Unit) {
        if (!hasFocus.value) {
            val tempTxt = _cantidadTxt.value.replace(RegexExpresion.NO_DIGITS_DECIMAL_REMPLACE, "")
            if (tempTxt.trim().isNotEmpty()) {
                try {
                    val temp = this.numberLimit(tempTxt)
                    changeContNumber(temp)
                    this.setCantidadTxt(temp.toString())
                    this.cantidad = temp
                } catch (_: NumberFormatException) {
                    changeContNumber(defaultDouble)
                    this.setCantidadTxt(defaultString)
                    this.cantidad = defaultDouble
                }
            } else {
                changeContNumber(defaultDouble)
                this.setCantidadTxt(defaultString)
                this.cantidad = defaultDouble
            }
        }
    }

    fun onClickIconPlus(
        changeContNumber: (Double) -> Unit,
        amount: Double
    ) {
        val temp = this.calculateButtonAdd(cantidad, amount)
        changeContNumber(temp)
        this.setCantidadTxt(temp.toString())
        this.cantidad = temp
    }

    fun onClickIconLess(
        changeContNumber: (Double) -> Unit,
        amount: Double
    ) {
        val temp = this.calculateButtonless(cantidad, amount)
        changeContNumber(temp)
        this.setCantidadTxt(temp.toString())
        this.cantidad = temp
    }

    private fun numberLimit(cantidadTxt: String): Double {
        try {
            if (!cantidadTxt.contains(RegexExpresion.IS_DIGITS_DOUBLE)) {
                return defaultDouble
            }
            val temp =
                BigDecimal(cantidadTxt.toDouble().toString()).setScale(2, BigDecimal.ROUND_DOWN)
                    .toDouble()
            if (temp.toString().length > 10) {
                return maxDouble
            }
            return if (temp >= minDouble) {
                temp
            } else {
                defaultDouble
            }
        } catch (_: NumberFormatException) {
            return maxDouble
        }
    }

    private fun calculateButtonAdd(accumulator: Double, amount: Double): Double {
        return try {
            val temp: Double = accumulator + amount
            if (temp < minDouble) {
                amount
            } else if (temp > maxDouble) {
                accumulator
            } else {
                accumulator + amount
            }
        } catch (_: NumberFormatException) {
            maxDouble
        }
    }

    private fun calculateButtonless(accumulator: Double, amount: Double): Double {
        return try {
            val temp = accumulator - amount
            return if (temp < minDouble) {
                defaultDouble
            } else {
                temp
            }
        } catch (_: NumberFormatException) {
            maxDouble
        }
    }
}