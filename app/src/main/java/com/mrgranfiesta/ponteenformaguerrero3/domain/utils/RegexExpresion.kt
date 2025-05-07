package com.mrgranfiesta.ponteenformaguerrero3.domain.utils

class RegexExpresion {
    companion object {
        val NO_DIGITS_REMPLACE = Regex("[^0-9]")
        val NO_DIGITS_DECIMAL_REMPLACE = Regex("[^0-9.]")
        val IS_DIGITS = Regex("^[0-9]+$")
        val IS_DIGITS_DOUBLE = Regex("^[0-9]+\\.[0-9]+$")
        val SECURED_RE = Regex("^[0-9a-zA-ZñÑáéíóú|?*+ _-]+$")
        val SECURED_SQL = Regex("^[0-9a-zA-ZñÑáéíóú _-]+$")
        val SECURED_SQL_EMAIL = Regex("^[0-9a-zA-ZñÑ@. _-]+$")
        val EMAIL_FORMAT = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        val CHAT_NOT_PERMIT_PASSWORD = Regex("[\\p{Cntrl}'\"<>\\\\`;\\p{So}]")
    }
}