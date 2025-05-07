package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ERROR_INIT_DATE_UPPER_THAN_END_DATE_FIELD
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_FECHA_FIN
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_FECHA_FIN_CAP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_FECHA_INICIO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_FECHA_INICIO_CAP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_MODO_CALENDARIO_TOOLSTIP
import com.mrgranfiesta.ponteenformaguerrero3.domain.core.vtf.DateVisualTransformation
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.DateUtils
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.DateRangeFieldsVM
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.Red200

@Composable
fun DateRangeFields(
    onClickCalendarInitDate: () -> Unit,
    onClickCalendarEndDate: () -> Unit,
    dateRangeFields: DateRangeFieldsVM = hiltViewModel()
) {
    val initDate by dateRangeFields.initDate.collectAsState()
    val endDate by dateRangeFields.endDate.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = LABEL_FECHA_INICIO_CAP,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 16.dp,
                    top = 8.dp
                ),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall
        )
        DateFields(
            date = initDate,
            dateOnChange = {
                dateRangeFields.setInitDate(it)
            },
            label = LABEL_FECHA_INICIO,
            onClickCalendar = onClickCalendarInitDate
        )
        Text(
            text = LABEL_FECHA_FIN_CAP,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 16.dp,
                    top = 8.dp
                ),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall
        )
        DateFields(
            date = endDate,
            dateOnChange = {
                dateRangeFields.setEndDate(it)
            },
            label = LABEL_FECHA_FIN,
            onClickCalendar = onClickCalendarEndDate
        )
        if (dateRangeFields.isAfter(initDate, endDate)) {
            Text(
                text = LABEL_ERROR_INIT_DATE_UPPER_THAN_END_DATE_FIELD,
                color = Red200,
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}

@Composable
fun DateFields(
    date: String,
    dateOnChange: (String) -> Unit,
    label: String,
    onClickCalendar: () -> Unit
) {
    val dateTransformation = remember { DateVisualTransformation() }
    val isNotValidDate = {
        DateUtils.isNotValidFormat(
            date = date,
            format = "ddMMyyyy"
        )
    }
    OutlinedTextField(
        value = date,
        onValueChange = {
            val trimmed = it.trim()
            val isDigits = trimmed.all { it2 ->
                it2.isDigit()
            }
            if (isDigits && trimmed.length <= 8) {
                dateOnChange(it)
            }
        },
        keyboardOptions = KeyboardOptions(
            autoCorrectEnabled = false,
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = label) },
        maxLines = 1,
        visualTransformation = dateTransformation,
        singleLine = true,
        trailingIcon = {
            IconButtonWithTooltip(
                tooltipText = LABEL_MODO_CALENDARIO_TOOLSTIP,
                onClick = { onClickCalendar() }
            ) {
                Icon(
                    imageVector = Icons.Filled.CalendarToday,
                    contentDescription = "Calendario"
                )
            }
        }
    )
    if (date.length == 8 && isNotValidDate()) {
        Text(
            text = "Error, la fecha no es valida",
            color = Red200,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}