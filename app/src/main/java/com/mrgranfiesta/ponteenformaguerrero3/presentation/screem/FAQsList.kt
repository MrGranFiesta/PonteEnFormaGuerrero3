package com.mrgranfiesta.ponteenformaguerrero3.presentation.screem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ActionModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.IconTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_FAQ_LIST_TITLE
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_FAQ_RESPONSE_TOOLSTIP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.faqs.Faq
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.ListManager
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.FloatingButtonVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.TopBarVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.FloatingButtonVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TopBarVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.IconButtonWithTooltip
import com.mrgranfiesta.ponteenformaguerrero3.presentation.navigations.AppScreem


@Composable
fun FAQsList(
    navController: NavController
) {
    FAQsListPrepareMenu()
    LazyRowFAQsList(navController)
}

@Composable
fun FAQsListPrepareMenu(
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    floatingButtonVM: FloatingButtonVM = FloatingButtonVMSingle.getFloatingButtonVM()
) {
    LaunchedEffect(true) {
        topBarVM.config(
            title = LABEL_FAQ_LIST_TITLE,
            icon = IconTopBar.BURGER,
            mode = ModeTopBar.TITLE,
            isGeasture = true,
            action = ActionModeTopBar.OPEN_DRAWER,
        )
        floatingButtonVM.resetFloatingButton()
    }
    topBarVM.clearActionList()
}

@Composable
fun LazyRowFAQsList(
    navController: NavController,
    faqList: List<Faq> = ListManager.getFaqsFactory()
) {
    val groupedFaqs = faqList.groupBy { it.category }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                bottom = 8.dp,
                start = 12.dp,
                end = 12.dp
            )
    ) {
        groupedFaqs.forEach { (category, faqs) ->
            item {
                Text(
                    text = category.title.uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(8.dp)
                )
            }
            items(faqs) { faq ->
                Card(
                    elevation = cardElevation(8.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clickable {
                            navController.navigate(AppScreem.FAQResponse.createRoute(faq.id))
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "${faq.id}. - ${faq.question}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 16.dp, bottom = 4.dp),
                            overflow = TextOverflow.Ellipsis
                        )
                        IconButtonWithTooltip(
                            tooltipText = LABEL_FAQ_RESPONSE_TOOLSTIP,
                            onClick = {
                                navController.navigate(AppScreem.FAQResponse.createRoute(faq.id))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Ver respuesta"
                            )
                        }
                    }
                }
            }
        }
    }
}