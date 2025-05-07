package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

class IsDuplicatedEmailUseCaseTest {
    @RelaxedMockK
    private lateinit var repo: UserRepository

    private lateinit var isDuplicatedEmailUseCase : IsDuplicatedEmailUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        isDuplicatedEmailUseCase = IsDuplicatedEmailUseCase(
            repo = repo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test isDuplicatedEmailUseCase`() {
        coEvery { repo.isEmailDuplicated(any()) } returns true
        val result = isDuplicatedEmailUseCase("guest@gmail.com")

        assertEquals(true, result)
    }
}