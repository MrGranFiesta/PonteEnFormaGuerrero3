package com.mrgranfiesta.ponteenformaguerrero3.presentation.screem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ActionModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.IconTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ERROR_GENERIC
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_FAQ_LIST_TITLE
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.PARAMS_ID
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.FloatingButtonVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.TopBarVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.FloatingButtonVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TopBarVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.FAQResponseVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle1
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle10
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle11
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle12
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle13
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle14
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle15
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle16
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle17
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle18
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle19
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle2
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle20
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle21
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle22
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle23
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle24
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle25
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle26
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle27
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle28
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle29
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle3
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle30
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle31
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle32
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle33
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle34
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle4
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle5
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle6
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle7
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle8
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.faqArticle.FaqArticle9

@Composable
fun FAQResponse(navBackStackEntry: NavBackStackEntry) {
    val params: String? = navBackStackEntry.arguments?.getString(PARAMS_ID)
    if (params != null) {
        val idFaq = params.toInt()
        FAQResponsePrepareMenu()
        FAQResponseBody(idFaq)
    }

}

@Composable
fun FAQResponsePrepareMenu(
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    floatingButtonVM: FloatingButtonVM = FloatingButtonVMSingle.getFloatingButtonVM()
) {
    LaunchedEffect(true) {
        topBarVM.config(
            title = LABEL_FAQ_LIST_TITLE,
            icon = IconTopBar.ARRROW,
            mode = ModeTopBar.TITLE,
            isGeasture = true,
            action = ActionModeTopBar.POP_BACK_STACK,
        )
        floatingButtonVM.resetFloatingButton()
    }
    topBarVM.clearActionList()
}

@Composable
fun FAQResponseBody(
    idFaq: Int,
    faqResponseVM: FAQResponseVM = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            text = "$idFaq. - ${faqResponseVM.getTitle(idFaq)}"
        )
        FAQResponseArticle(idFaq)
    }
}

@Composable
fun FAQResponseArticle(idFaq: Int) {
    mapOf<Int, @Composable () -> Unit>(
        1 to { FaqArticle1() },
        2 to { FaqArticle2() },
        3 to { FaqArticle3() },
        4 to { FaqArticle4() },
        5 to { FaqArticle5() },
        6 to { FaqArticle6() },
        7 to { FaqArticle7() },
        8 to { FaqArticle8() },
        9 to { FaqArticle9() },
        10 to { FaqArticle10() },
        11 to { FaqArticle11() },
        12 to { FaqArticle12() },
        13 to { FaqArticle13() },
        14 to { FaqArticle14() },
        15 to { FaqArticle15() },
        16 to { FaqArticle16() },
        17 to { FaqArticle17() },
        18 to { FaqArticle18() },
        19 to { FaqArticle19() },
        20 to { FaqArticle20() },
        21 to { FaqArticle21() },
        22 to { FaqArticle22() },
        23 to { FaqArticle23() },
        24 to { FaqArticle24() },
        25 to { FaqArticle25() },
        26 to { FaqArticle26() },
        27 to { FaqArticle27() },
        28 to { FaqArticle28() },
        29 to { FaqArticle29() },
        30 to { FaqArticle30() },
        31 to { FaqArticle31() },
        32 to { FaqArticle32() },
        33 to { FaqArticle33() },
        34 to { FaqArticle34() }
    )[idFaq]?.invoke() ?: Text(LABEL_ERROR_GENERIC)
}