package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_DELETE_MATERIAL_TOOLSTIP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EDITS_MATERIAL_TOOLSTIP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_VIEW_MATERIAL_TOOLSTIP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TimerFormmertText
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioNameAndPhotoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.RowStepVM
import kotlinx.coroutines.flow.Flow

@SuppressWarnings("kotlin:S107")
@Composable
fun RowStepView(
    modifier: Modifier = Modifier,
    serie : Int,
    cantidad : Int,
    tipo : TipoEsfuerzo,
    dataSource : () -> Flow<EjercicioNameAndPhotoDto>,
    onClickRow: (EjercicioNameAndPhotoDto) -> Unit,
    editOptions : EditOptionsRowStepView,
    rowStepVM: RowStepVM = hiltViewModel()
) {
    rowStepVM.initData(dataSource)
    val ejercicio by rowStepVM.ejercicio.collectAsState()

    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onClickRow(ejercicio)
                }
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 8.dp,
                        bottom = 8.dp,
                    )
            ) {
                ImageFromUriMini(
                    photoUri = ejercicio.photoUri,
                    contentDescription = ""
                )
                Column(
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            bottom = 8.dp
                        )
                ) {
                    Row {
                        Text(
                            text = ejercicio.nombre,
                            modifier = Modifier
                                .weight(0.65f)
                                .padding(top = 8.dp)
                        )
                        RowStepAction(
                            modifier = Modifier.weight(0.35f),
                            editOptions = editOptions
                        )

                    }
                    Text(
                        text = TimerFormmertText.getSeriesFormatter(
                            serie = serie,
                            cantidad = cantidad,
                            tipo = tipo
                        ),
                        modifier = Modifier
                            .padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

data class EditOptionsRowStepView(
    val isActiveEdit: Boolean = false,
    val isShowMaterial: Boolean = false,
    val onClickButtonDelete: () -> Unit = {},
    val onClickButtonUpdate: () -> Unit = {},
    val showMaterial: () -> Unit = {}
)

@Composable
fun RowStepAction(
    modifier: Modifier = Modifier,
    editOptions : EditOptionsRowStepView
) {
    Row(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.End
    ) {
        if (editOptions.isShowMaterial) {
            IconButtonWithTooltip(
                tooltipText = LABEL_VIEW_MATERIAL_TOOLSTIP,
                onClick = { editOptions.showMaterial() }
            ) {
                Icon(
                    imageVector = Icons.Filled.FitnessCenter,
                    contentDescription = "Ver Materiales",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        if (editOptions.isActiveEdit) {
            IconButtonWithTooltip(
                tooltipText = LABEL_EDITS_MATERIAL_TOOLSTIP,
                onClick = {editOptions.onClickButtonUpdate()}
            ){
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Editar elemento",
                    modifier = Modifier.padding(8.dp)
                )
            }
            IconButtonWithTooltip(
                tooltipText = LABEL_DELETE_MATERIAL_TOOLSTIP,
                onClick = {editOptions.onClickButtonDelete()}
            ){
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Eliminar elemento",
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        }
    }
}