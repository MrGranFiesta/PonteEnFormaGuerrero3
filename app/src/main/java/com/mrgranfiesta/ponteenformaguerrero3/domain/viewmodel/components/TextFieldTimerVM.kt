package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import androidx.lifecycle.ViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.RegexExpresion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TextFieldTimerVM : ViewModel() {
    private var status = true

    private var _min = 0
    private fun setMin(min: Int) {
        _min = min
    }

    private fun plusMin() = _min++
    private fun minusMin() = _min--

    private val _minTxt = MutableStateFlow("0")
    val minTxt: StateFlow<String> = _minTxt
    private fun setMinTxt(minTxt: String) {
        _minTxt.value = minTxt
    }

    private var _seg = 1
    private fun setSeg(seg: Int) {
        _seg = seg
    }

    private fun plusSeg() = _seg++
    private fun minusSeg() = _seg--

    private val _segTxt = MutableStateFlow("1")
    val segTxt: StateFlow<String> = _segTxt
    private fun setSegTxt(segTxt: String) {
        _segTxt.value = segTxt
    }

    @Synchronized
    fun initData(time: Int) {
        if (status) {
            this.setMin((time / 60))
            this.setSeg((time % 60))
            this.setSegTxt(_seg.toString())
            this.setMinTxt(_min.toString())
            status = false
        }
    }

    fun resetStatus() {
        status = true
    }

    private fun calculateTotal(): Int = _min * 60 + _seg

    fun onClickPlusSeg(changeTimer: (Int) -> Unit) {
        if (_seg < 59) {
            this.plusSeg()
            this.setSegTxt(_seg.toString())
            changeTimer(this.calculateTotal())
        }
    }

    fun onClickMinusSeg(changeTimer: (Int) -> Unit) {
        if ((_min > 0 && _seg > 0) || (_min == 0 && _seg > 1)) {
            this.minusSeg()
            this.setSegTxt(_seg.toString())
            changeTimer(this.calculateTotal())
        }
    }

    fun onClickPlusMin(changeTimer: (Int) -> Unit) {
        if (_min < 99) {
            this.plusMin()
            this.setMinTxt(_min.toString())
            changeTimer(this.calculateTotal())
        }
    }

    fun onClickMinusMin(changeTimer: (Int) -> Unit) {
        if (_min > 0) {
            this.minusMin()
            this.setMinTxt(_min.toString())
            if (_seg == 0) {
                this.setSeg(1)
                this.setSegTxt("1")
            }
            changeTimer(this.calculateTotal())
        }
    }

    fun onChangeMin(txt: String) {
        val txtSecured = txt.trim()
        if (txtSecured.contains(RegexExpresion.IS_DIGITS) && txtSecured.length <= 2) {
            val minuts = txtSecured.toInt()
            if (minuts in 0..99) {
                this.setMinTxt(txtSecured)
                this.setMin(minuts)
                if (minuts == 0 && _seg == 0) {
                    this.setDefaultSeg()
                }
            }
        } else if (txtSecured.isEmpty()) {
            this.setMinTxt("")
        }
    }

    fun onChangeSeg(txt: String) {
        val txtSecured = txt.trim()
        if (txtSecured.contains(RegexExpresion.IS_DIGITS) && txtSecured.length <= 2 && txtSecured.isNotEmpty()) {
            val seconds = txtSecured.toInt()
            if ((seconds in 0..59 && _min > 0) || (seconds in 1..59 && _min == 0)) {
                this.setSegTxt(txtSecured)
                this.setSeg(seconds)
            }
        } else if (txtSecured.isEmpty()) {
            this.setSegTxt("")
        }
    }

    fun onChangeFocusMin(changeTimer: (Int) -> Unit) {
        val tempTxt = _minTxt.value.replace(RegexExpresion.NO_DIGITS_REMPLACE, "")
        if (tempTxt.trim().isNotEmpty()) {
            changeTimer(this.calculateTotal())
        } else {
            this.setDefaultMin(changeTimer)
        }
    }

    fun onChangeFocusSeg(changeTimer: (Int) -> Unit) {
        val tempTxt = _segTxt.value.replace(RegexExpresion.NO_DIGITS_REMPLACE, "")
        if (tempTxt.trim().isNotEmpty()) {
            changeTimer(this.calculateTotal())
        } else {
            changeTimer(0)
            this.setDefaultSeg()
        }
    }

    private fun setDefaultMin(changeTimer: (Int) -> Unit = {}) {
        this.setMin(0)
        this.setMinTxt("0")
        if (_seg == 0) {
            this.setSeg(1)
            this.setSegTxt("1")
        }
        changeTimer(this.calculateTotal())
    }

    private fun setDefaultSeg() {
        this.setSeg(1)
        this.setSegTxt("1")
    }
}