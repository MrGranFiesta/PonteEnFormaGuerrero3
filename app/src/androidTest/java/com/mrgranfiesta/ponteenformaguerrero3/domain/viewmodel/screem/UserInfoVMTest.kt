package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import com.mrgranfiesta.ponteenformaguerrero3.data.datastore.UserDataStore
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user.DeleteUserByIdUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user.UpdateUserPhotoUriUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user.UpdateUserRolUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user.UpdateUserUsernameUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UserInfoVMTest {
    lateinit var viewModel: UserInfoVM

    @RelaxedMockK
    private lateinit var deleteUserByIdUseCase: DeleteUserByIdUseCase

    @RelaxedMockK
    private lateinit var updateUserPhotoUriUseCase: UpdateUserPhotoUriUseCase

    @RelaxedMockK
    private lateinit var updateUserUsernameUseCase: UpdateUserUsernameUseCase

    @RelaxedMockK
    private lateinit var updateUserRolUseCase: UpdateUserRolUseCase

    @RelaxedMockK
    private lateinit var userDataStore: UserDataStore

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = UserInfoVM(
            deleteUserByIdUseCase = deleteUserByIdUseCase,
            updateUserPhotoUriUseCase = updateUserPhotoUriUseCase,
            updateUserUsernameUseCase = updateUserUsernameUseCase,
            updateUserRolUseCase = updateUserRolUseCase,
            userDataStore = userDataStore
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun testInitDataCorrect() {
        val statusField = viewModel.javaClass.getDeclaredField("status")
        statusField.isAccessible = true

        assertTrue(statusField.getBoolean(viewModel))

        viewModel.initData()

        assertFalse(statusField.getBoolean(viewModel))
    }
}