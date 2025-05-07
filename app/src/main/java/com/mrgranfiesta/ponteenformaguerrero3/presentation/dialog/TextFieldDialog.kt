package com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ACEPTAR_UPPER
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CANCELAR_UPPER
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CHANGE_USERNAME_DIALOG
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.dialog.TextFieldDialogVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.TextFieldSecured

@Composable
fun TextFieldDialog(
    dismissDialog: () -> Unit,
    posOptActions : (String) -> Unit,
    text : String,
    label : String,
    textFieldDialogVM : TextFieldDialogVM = hiltViewModel()
){
    textFieldDialogVM.initData(text)

    val textDialog by textFieldDialogVM.textDialog.collectAsState()

    Dialog(
        onDismissRequest = {
            dismissDialog()
            textFieldDialogVM.resetStatus()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = LABEL_CHANGE_USERNAME_DIALOG,
                modifier = Modifier
                    .padding(8.dp)
            )
            TextFieldSecured(
                txt = textDialog,
                onValueChange = { textFieldDialogVM.setTextDialog(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                label = label,
                maxLines = 3,
                maxCount = 20,
                isActiveCheckIsEmpty = true,
                textFieldSecuredVM = viewModel(key = "1")
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = LABEL_CANCELAR_UPPER,
                    modifier = Modifier
                        .clickable {
                            dismissDialog()
                            textFieldDialogVM.resetStatus()
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
                            dismissDialog()
                            textFieldDialogVM.resetStatus()
                            posOptActions(textDialog)
                        },
                    text = LABEL_ACEPTAR_UPPER,
                    //style = MaterialTheme.typography.caption
                )
            }
        }
    }
}