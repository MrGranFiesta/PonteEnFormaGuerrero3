package com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ACEPTAR_UPPER
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ATRAS
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CANCELAR_UPPER
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_FECHA_FIN_CAP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_FECHA_INICIO_CAP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_FILTRO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_GRUPOS_MUSCULARES
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_MODO_TEXTO_TOOLSTIP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_NIVEL
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_SELECIONAR
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.dialog.StatDialogDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.DateUtils
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.DateRangeFieldsVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.dialog.FilterStatDialogVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.CheckMusculoList
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.CheckNivelList
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.DateRangeFields
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.IconButtonWithTooltip

@Composable
fun FilterStatDialog(
    dismissDialog: () -> Unit,
    params: StatDialogDto,
    dateRangeFields: DateRangeFieldsVM = hiltViewModel(),
    filterStatDialogVM: FilterStatDialogVM = hiltViewModel()
) {
    val isInitDateModeCalendar by filterStatDialogVM.isInitDateModeCalendar.collectAsState()
    val isEndDateModeCalendar by filterStatDialogVM.isEndDateModeCalendar.collectAsState()

    val initDate by dateRangeFields.initDate.collectAsState()
    val endDate by dateRangeFields.endDate.collectAsState()

    dateRangeFields.initData(
        rangeDate = params.rangeDate
    )

    filterStatDialogVM.initData(
        musculoSet = params.musculoSet,
        nivelSet = params.nivelSet
    )

    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = { filterStatDialogVM.onDismissDialog(dismissDialog) }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = LABEL_FILTRO,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 16.dp,
                        top = 8.dp
                    ),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall
            )
            if (isInitDateModeCalendar) {
                DatePickerConfig(
                    title = LABEL_FECHA_INICIO_CAP,
                    date = initDate,
                    onChangeDate = { dateRangeFields.setInitDate(it) }

                )
            } else if (isEndDateModeCalendar) {
                DatePickerConfig(
                    title = LABEL_FECHA_FIN_CAP,
                    date = endDate,
                    onChangeDate = { dateRangeFields.setEndDate(it) }
                )
            } else {
                BodyFilterStatDialog(
                    dismissDialog = { dismissDialog() },
                    onChangeMusculoSet = { params.onChangeMusculoSet(it) },
                    onChangeNivelSet = { params.onChangeNivelSet(it) },
                    onChangeRangeDate = { params.onChangeRangeDate(dateRangeFields.getPairOptional()) },
                    dateRangeFields = dateRangeFields
                )
            }
        }
    }
}

@Composable
fun BodyFilterStatDialog(
    dismissDialog: () -> Unit,
    onChangeMusculoSet: (Set<Musculo>) -> Unit,
    onChangeNivelSet: (Set<Nivel>) -> Unit,
    onChangeRangeDate: () -> Unit,
    dateRangeFields: DateRangeFieldsVM,
    filterStatDialogVM: FilterStatDialogVM = hiltViewModel()
) {
    val musculoSetTemp by filterStatDialogVM.musculoSetTemp.collectAsState()
    val nivelSetTemp by filterStatDialogVM.nivelSetTemp.collectAsState()

    DateRangeFields(
        onClickCalendarInitDate = {
            filterStatDialogVM.setInitDateModeCalendar(true)
        },
        onClickCalendarEndDate = {
            filterStatDialogVM.setEndDateModeCalendar(true)
        },
        dateRangeFields = dateRangeFields
    )
    Text(
        text = LABEL_GRUPOS_MUSCULARES,
        modifier = Modifier
            .padding(
                bottom = 16.dp,
                top = 8.dp
            ),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleSmall
    )

    CheckMusculoList(
        musculoSetTemp = musculoSetTemp,
        onChangeMusculoSet = { filterStatDialogVM.setMusculoSetTemp(it) }
    )

    Text(
        text = LABEL_NIVEL,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 16.dp,
                top = 8.dp
            ),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleSmall
    )
    CheckNivelList(
        nivelSetTemp = nivelSetTemp,
        onChangeNivelSet = { filterStatDialogVM.setNivelSetTemp(it) }
    )
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = LABEL_CANCELAR_UPPER,
            modifier = Modifier
                .padding(
                    top = 16.dp,
                    start = 8.dp,
                    bottom = 8.dp
                )
                .clickable { filterStatDialogVM.onDismissDialog(dismissDialog) }
            //style = MaterialTheme.typography.caption
        )
        Text(
            text = LABEL_ACEPTAR_UPPER,
            modifier = Modifier
                .padding(
                    top = 16.dp,
                    end = 8.dp,
                    bottom = 8.dp
                )
                .clickable {
                    filterStatDialogVM.onAcept(
                        onChangeMusculoSet = onChangeMusculoSet,
                        onChangeNivelSet = onChangeNivelSet,
                        dismissDialog = dismissDialog
                    )
                    onChangeRangeDate()
                }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerConfig(
    title: String,
    date: String,
    onChangeDate: (String) -> Unit,
    filterStatDialogVM: FilterStatDialogVM = hiltViewModel()
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = DateUtils.getStringToMillis(date, "ddMMyyyy"),
        initialDisplayMode = DisplayMode.Picker
    )

    DatePicker(
        state = datePickerState,
        showModeToggle = false,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = title,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(
                            bottom = 16.dp,
                            top = 8.dp
                        ),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall
                )
                IconButtonWithTooltip(
                    tooltipText = LABEL_MODO_TEXTO_TOOLSTIP,
                    onClick = {
                        filterStatDialogVM.setInitDateModeCalendar(false)
                    }
                ) {
                    Icon(imageVector = Icons.Filled.Edit, contentDescription = "Modo Texto")
                }
            }
        }
    )

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = LABEL_ATRAS,
            modifier = Modifier
                .padding(
                    top = 16.dp,
                    start = 8.dp,
                    bottom = 8.dp
                )
                .clickable {
                    filterStatDialogVM.setInitDateModeCalendar(false)
                    filterStatDialogVM.setEndDateModeCalendar(false)
                }
            //style = MaterialTheme.typography.caption
        )
        Text(
            text = LABEL_SELECIONAR,
            modifier = Modifier
                .padding(
                    top = 16.dp,
                    end = 8.dp,
                    bottom = 8.dp
                )
                .clickable {
                    onChangeDate(datePickerState.selectedDateMillis?.let {
                        DateUtils.getMillisToLocalDateTime(
                            format = "ddMMyyyy",
                            millis = it
                        )
                    } ?: "")
                    filterStatDialogVM.setInitDateModeCalendar(false)
                    filterStatDialogVM.setEndDateModeCalendar(false)
                }
        )
    }
}