package com.mrgranfiesta.ponteenformaguerrero3.data.db.datasource

import android.net.Uri
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.ConfMaterialBean

class InitialConfMaterialDB {
    companion object {
        fun getListDataDefaulConfMaterial(): List<ConfMaterialBean> {
            return listOf(
                ConfMaterialBean(
                    idConfMaterial = 1_000_000,
                    idMaterial = 999_997,
                    idStep = 9_999_980,
                    confValue = 2.5,
                    nombre = "",
                    isMaterialWeight = true,
                    photoUri = Uri.EMPTY
                ),
                ConfMaterialBean(
                    idConfMaterial = 999_999,
                    idMaterial = 999_997,
                    idStep = 9_999_976,
                    confValue = 4.5,
                    nombre = "",
                    isMaterialWeight = true,
                    photoUri = Uri.EMPTY
                ),
                ConfMaterialBean(
                    idConfMaterial = 999_998,
                    idMaterial = 999_997,
                    idStep = 9_999_970,
                    confValue = 2.5,
                    nombre = "",
                    isMaterialWeight = true,
                    photoUri = Uri.EMPTY
                ),
                ConfMaterialBean(
                    idConfMaterial = 999_997,
                    idMaterial = 999_997,
                    idStep = 9_999_968,
                    confValue = 2.5,
                    nombre = "",
                    isMaterialWeight = true,
                    photoUri = Uri.EMPTY
                ),
                ConfMaterialBean(
                    idConfMaterial = 999_996,
                    idMaterial = 999_997,
                    idStep = 9_999_965,
                    confValue = 4.5,
                    nombre = "",
                    isMaterialWeight = true,
                    photoUri = Uri.EMPTY
                ),
                ConfMaterialBean(
                    idConfMaterial = 999_995,
                    idMaterial = 999_997,
                    idStep = 9_999_963,
                    confValue = 4.5,
                    nombre = "",
                    isMaterialWeight = true,
                    photoUri = Uri.EMPTY
                )
            )
        }
    }
}