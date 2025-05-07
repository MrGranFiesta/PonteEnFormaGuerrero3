package com.mrgranfiesta.ponteenformaguerrero3.domain.utils

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios.FormatEmail
import com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios.MaxLenght
import com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios.NotEmpty
import com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios.SecuredCharEmailSQL
import com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios.SecuredCharSQL
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CERRAR
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.SnackbarHostText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.reflect.Field

object ValidationAnnotations {
    fun isValid(
        bean: Any,
        snackbarHost: SnackbarHostState,
        viewModelScope: CoroutineScope
    ) : Boolean {
        val clazz = bean::class.java
        val fields = clazz.declaredFields
        val validator = Validator(snackbarHost, viewModelScope, bean)
        for (field in fields) {
            field.isAccessible = true

            val isFinish = when {
                validator.ruleValidationMaxLenghtInvalid(field) -> true
                validator.ruleValidationNotEmptyInvalid(field) -> true
                validator.ruleValidationSecuredCharSQLInvalid(field) -> true
                validator.ruleValidationSecuredCharEmailSQLInvalid(field) -> true
                validator.ruleValidationFormatEmailInvalid(field) -> true
                else -> false
            }

            if (isFinish) {
                return false
            }
        }
        return true
    }

    private data class Validator(
        private var snackbarHost: SnackbarHostState,
        private var viewModelScope: CoroutineScope,
        private var bean: Any
    ) {
        fun ruleValidationMaxLenghtInvalid(
            field: Field
        ) : Boolean {
            field.getAnnotation(MaxLenght::class.java)?.let { annotation ->
                val value = field[bean] as? String
                if (value != null && value.length > annotation.max) {
                    this.makeSnackBar(
                        msg = SnackbarHostText.snackbarHostErrorMaxLength(annotation.fieldName, annotation.max),
                        snackbarHost = snackbarHost,
                        viewModelScope = viewModelScope
                    )
                    return true
                }
            }
            return false
        }

        fun ruleValidationNotEmptyInvalid(
            field: Field
        ) : Boolean {
            field.getAnnotation(NotEmpty::class.java)?.let {
                val value = field[bean] as? String
                if (value != null && value.trim().isEmpty()) {
                    this.makeSnackBar(
                        msg = SnackbarHostText.snackbarHostErrorEmpty(it.fieldName),
                        snackbarHost = snackbarHost,
                        viewModelScope = viewModelScope
                    )
                    return true
                }
            }
            return false
        }

        fun ruleValidationSecuredCharSQLInvalid(
            field: Field
        ) : Boolean {
            field.getAnnotation(SecuredCharSQL::class.java)?.let {
                val value = field[bean] as? String
                if (!value.isNullOrEmpty() && !value.contains(RegexExpresion.SECURED_SQL)) {
                    this.makeSnackBar(
                        msg = SnackbarHostText.snackbarHostErrorIlegalChar(it.fieldName),
                        snackbarHost = snackbarHost,
                        viewModelScope = viewModelScope
                    )
                    return true
                }
            }
            return false
        }

        fun ruleValidationSecuredCharEmailSQLInvalid(
            field: Field
        ) : Boolean {
            field.getAnnotation(SecuredCharEmailSQL::class.java)?.let {
                val value = field[bean] as? String
                if (!value.isNullOrEmpty() && !value.contains(RegexExpresion.SECURED_SQL_EMAIL)) {
                    this.makeSnackBar(
                        msg = SnackbarHostText.snackbarHostErrorIlegalChar(it.fieldName),
                        snackbarHost = snackbarHost,
                        viewModelScope = viewModelScope
                    )
                    return true
                }
            }
            return false
        }

        fun ruleValidationFormatEmailInvalid(
            field: Field
        ) : Boolean {
            field.getAnnotation(FormatEmail::class.java)?.let { annotation ->
                val value = field[bean] as? String
                if (!value.isNullOrEmpty() && !RegexExpresion.EMAIL_FORMAT.matches(value)) {
                    this.makeSnackBar(
                        msg = SnackbarHostText.snackbarHostErrorMalformetEmail(annotation.fieldName),
                        snackbarHost = snackbarHost,
                        viewModelScope = viewModelScope
                    )
                    return true
                }
            }
            return false
        }

        private fun makeSnackBar(
            msg: String,
            snackbarHost: SnackbarHostState,
            viewModelScope: CoroutineScope
        ) {
            viewModelScope.launch {
                snackbarHost.showSnackbar(
                    message = msg,
                    actionLabel = LABEL_CERRAR,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
}