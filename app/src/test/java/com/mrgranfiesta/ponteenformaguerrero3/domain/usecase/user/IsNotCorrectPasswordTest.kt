package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.UserRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class IsNotCorrectPasswordTest {
    @RelaxedMockK
    private lateinit var repo: UserRepository

    private lateinit var isNotCorrectPasswordUseCase : IsNotCorrectPasswordUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        mockkObject(CurrentUser)
        isNotCorrectPasswordUseCase = IsNotCorrectPasswordUseCase(
            repo = repo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test isNotCorrectPassword password correcta`() {
        coEvery { CurrentUser.user?.idUser } returns 1
        coEvery { repo.isNotCorrectPassword(any(), any()) } returns false
        val result = isNotCorrectPasswordUseCase("password")

        coVerify { repo.isNotCorrectPassword(any(), any()) }
        assertEquals(false, result)
    }

    @Test
    fun `test isNotCorrectPassword password no correcta`() {
        coEvery { CurrentUser.user?.idUser } returns 1
        coEvery { repo.isNotCorrectPassword(any(), any()) } returns true
        val result = isNotCorrectPasswordUseCase("password")

        coVerify { repo.isNotCorrectPassword(any(), any()) }
        assertEquals(true, result)
    }

    @Test
    fun `test isNotCorrectPassword CurrentUser no logeado`() {
        coEvery { CurrentUser.user } returns null
        val result = isNotCorrectPasswordUseCase("password")

        coVerify(exactly = 0) { repo.isNotCorrectPassword(any(), any()) }
        assertEquals(true, result)
    }
}