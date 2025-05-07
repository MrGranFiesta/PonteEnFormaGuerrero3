package com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ACEPTAR_UPPER
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CANCELAR_UPPER
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CONF_EJERCICIO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_MATERIALES
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_SERIE
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_UNIDAD
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.dialog.StepEditDialogDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.ListManager
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.CastingClass.Companion.unidadToTipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TextFieldNumberVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TextFieldTimerVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.dialog.EditStepDialogVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.ComboBox
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.TextFieldNumber
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.TextFieldNumberDouble
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.TextFieldTimer

@Composable
fun EditStepDialog(
    dismissDialog: () -> Unit,
    onAcceptAction: (StepEditDialogDto) -> Unit = {},
    step: StepEditDialogDto,
    editStepDialogVM: EditStepDialogVM = hiltViewModel()
) {
    if (editStepDialogVM.initData(step)) {
        val textFieldTimerVM = viewModel<TextFieldTimerVM>(key = "timer${step.idStep}")
        val textFieldRepsVM = viewModel<TextFieldNumberVM>(key = "reps${step.idStep}")
        val textFieldSerieVM = viewModel<TextFieldNumberVM>(key = "serie${step.idStep}")

        Dialog(
            onDismissRequest = {
                dismissDialog()
                textFieldTimerVM.resetStatus()
                textFieldRepsVM.resetStatus()
                textFieldSerieVM.resetStatus()
                editStepDialogVM.resetStatus()
            },
        ) {
            EditStepDialogBody(
                step = step,
                dismissDialog = dismissDialog,
                onAcceptAction = onAcceptAction,
                textFieldTimerVM = textFieldTimerVM,
                textFieldRepsVM = textFieldRepsVM,
                textFieldSerieVM = textFieldSerieVM
            )
        }
    }
}

@Composable
fun EditStepDialogBody(
    dismissDialog: () -> Unit,
    onAcceptAction: (StepEditDialogDto) -> Unit = {},
    step: StepEditDialogDto,
    textFieldTimerVM: TextFieldTimerVM,
    textFieldRepsVM: TextFieldNumberVM,
    textFieldSerieVM: TextFieldNumberVM
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = LABEL_CONF_EJERCICIO,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium
        )
        EditStepDialogContent(
            textFieldTimerVM = textFieldTimerVM,
            textFieldRepsVM = textFieldRepsVM,
            textFieldSerieVM = textFieldSerieVM
        )
        EditStepDialogActions(
            step = step,
            dismissDialog = dismissDialog,
            onAcceptAction = onAcceptAction,
            textFieldTimerVM = textFieldTimerVM,
            textFieldRepsVM = textFieldRepsVM,
            textFieldSerieVM = textFieldSerieVM
        )
    }
}

@Composable
fun EditStepDialogContent(
    editStepDialogVM: EditStepDialogVM = hiltViewModel(),
    stepUnidList: List<String> = ListManager.getListStepUnid(),
    textFieldTimerVM: TextFieldTimerVM,
    textFieldRepsVM: TextFieldNumberVM,
    textFieldSerieVM: TextFieldNumberVM
) {
    val cantidad by editStepDialogVM.cantidad.collectAsState()
    val serie by editStepDialogVM.serie.collectAsState()
    val tipoEsfuerzo by editStepDialogVM.tipoEsfuerzo.collectAsState()
    val materialList by editStepDialogVM.materialNameDtoList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        if (tipoEsfuerzo == TipoEsfuerzo.CRONO) {
            TextFieldTimer(
                time = cantidad,
                changeTime = { editStepDialogVM.setCantidad(it) },
                textFieldTimerVM = textFieldTimerVM
            )
        } else {
            TextFieldNumber(
                cont = cantidad,
                changeContNumber = { editStepDialogVM.setCantidad(it) },
                labelTxt = editStepDialogVM.generateLabelTxtUnid(tipoEsfuerzo),
                amount = editStepDialogVM.typeAmount(tipoEsfuerzo),
                textFieldNumberVM = textFieldRepsVM
            )
        }
        TextFieldNumber(
            cont = serie,
            changeContNumber = { editStepDialogVM.setSerie(it) },
            labelTxt = LABEL_SERIE,
            textFieldNumberVM = textFieldSerieVM
        )
        ComboBox(
            selectedOptions = tipoEsfuerzo.unidad,
            changeSelectOption = {
                editStepDialogVM.setTipoEsfuerzo(
                    unidadToTipoEsfuerzo(it)
                )
            },
            options = stepUnidList,
            labelText = LABEL_UNIDAD,
            changeExtras = { editStepDialogVM.setCantidad(1) }
        )
        LazyColumn {
            item {
                if (materialList.isNotEmpty() && materialList.any { it.isMaterialWeight }) {
                    Text(
                        text = LABEL_MATERIALES,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 8.dp,
                                bottom = 8.dp
                            ),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            items(materialList) { material ->
                if (material.isMaterialWeight) {
                    TextFieldNumberDouble(
                        cont = material.confValue,
                        changeContNumber = {
                            material.confValue = it
                        },
                        labelTxt = material.nombre,
                        textFieldNumberDoubleVM = viewModel(key = material.toString())
                    )
                }
            }
        }
    }
}

@Composable
fun EditStepDialogActions(
    step: StepEditDialogDto,
    dismissDialog: () -> Unit,
    onAcceptAction: (StepEditDialogDto) -> Unit = {},
    editStepDialogVM: EditStepDialogVM = hiltViewModel(),
    textFieldTimerVM: TextFieldTimerVM,
    textFieldRepsVM: TextFieldNumberVM,
    textFieldSerieVM: TextFieldNumberVM
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = LABEL_CANCELAR_UPPER,
            modifier = Modifier
                .clickable {
                    dismissDialog()
                    textFieldTimerVM.resetStatus()
                    textFieldRepsVM.resetStatus()
                    textFieldSerieVM.resetStatus()
                    editStepDialogVM.resetStatus()
                }
                .padding(
                    top = 16.dp,
                    start = 8.dp,
                    bottom = 8.dp
                )
            //style = MaterialTheme.typography.caption
        )
        Text(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                    end = 8.dp,
                    bottom = 8.dp
                )
                .clickable {
                    onAcceptAction(editStepDialogVM.getStepEditDialogDto(step))
                    editStepDialogVM.resetStatus()
                    textFieldTimerVM.resetStatus()
                    textFieldRepsVM.resetStatus()
                    textFieldSerieVM.resetStatus()
                    dismissDialog()
                },
            text = LABEL_ACEPTAR_UPPER,
            //style = MaterialTheme.typography.caption
        )
    }
}