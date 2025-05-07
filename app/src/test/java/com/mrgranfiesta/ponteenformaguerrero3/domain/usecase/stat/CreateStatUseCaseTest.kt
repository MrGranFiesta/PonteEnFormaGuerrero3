package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.stat

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.StatRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.StatBean
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.time.LocalDateTime


@RunWith(RobolectricTestRunner::class)
class CreateStatUseCaseTest {
    @RelaxedMockK
    private lateinit var repo: StatRepository

    private lateinit var createStatUseCase: CreateStatUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        createStatUseCase = CreateStatUseCase(
            repo = repo
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test createStatUseCase`() {
        val statBean = StatBean(
            idStatHistory= 1,
            idRutinaSnapshot= 1,
            idRutina= 1,
            idUser= 1,
            dateInit = LocalDateTime.of(2023, 1, 1, 10, 0, 0),
            dateEnd = LocalDateTime.of(2023, 1, 1, 10, 0, 0),
            isCalentamientoDone= true,
            isMovArticularDone= true,
            isEstiramientoDone= true,
            isCompleted= true
        )
        coEvery { repo.insert(any()) } returns 1
        val result = createStatUseCase(statBean)
        coVerify { repo.insert(any()) }
        assertEquals(1, result)
    }
}