package com.mrgranfiesta.ponteenformaguerrero3.domain.factory

import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.presentation.navigations.AppScreem
import org.junit.Test

class ListManagerTest {

    @Test
    fun `test usuario con rol premium contiene la pantalla de usuario actual`(){
        val optionsList = ListManager.getDrawerOpt(Rol.PREMIUN_USER)
        assert(optionsList.contains(AppScreem.CurrentUserInfo))
    }

    @Test
    fun `test usuario con rol estandar contiene la pantalla de usuario actual`(){
        val optionsList = ListManager.getDrawerOpt(Rol.STANDAR_USER)
        assert(optionsList.contains(AppScreem.CurrentUserInfo))
    }

    @Test
    fun `test usuario con rol invitado no contiene la pantalla de usuario actual`(){
        val optionsList = ListManager.getDrawerOpt(Rol.GUEST)
        assert(!optionsList.contains(AppScreem.CurrentUserInfo))
    }

    @Test
    fun `test usuario con inicial no contiene la pantalla de usuario actual`(){
        val optionsList = ListManager.getDrawerOpt(Rol.INIT_DATA_USER)
        assert(!optionsList.contains(AppScreem.CurrentUserInfo))
    }
}