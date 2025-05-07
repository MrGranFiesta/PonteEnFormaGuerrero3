package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_FILTER_TOOLSTIP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ModeTopBar.SEARCH_FILTER
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.TopBarVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TopBarVM

@Composable
fun SearchAppBar(
    modeBar: ModeTopBar,
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM()
) {
    val searchText by topBarVM.searchText.collectAsState()
    TextField(
        value = searchText,
        onValueChange = {
            topBarVM.setSearchText(it)
        },
        textStyle = MaterialTheme.typography.titleLarge,
        maxLines = 1,
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "searchIcon"
            )
        },
        trailingIcon = {
            if (modeBar == SEARCH_FILTER) {
                IconButtonWithTooltip(
                    tooltipText = LABEL_FILTER_TOOLSTIP,
                    onClick = {
                        topBarVM.setFiltersDialog(true)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.FilterList,
                        contentDescription = "Filter Icon",
                    )
                }
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}