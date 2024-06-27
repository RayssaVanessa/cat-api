package com.example.catapi.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.catapi.domain.CatUseCase
import com.example.catapi.domain.model.CatListModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)

internal class CatViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testScheduler)
    private val testScope = TestScope(testDispatcher)
    private  lateinit var catApiViewModel: CatViewModel

    @MockK
    private val catUseCaseMock: CatUseCase = mockk()

    @Before
      fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(testDispatcher)
        catApiViewModel = CatViewModel(catUseCaseMock)
    }

    @Test
    fun `when call loadData should handle IOException`() = testScope.runTest {
        // Given
        val exception = IOException("Network Error")
        coEvery { catUseCaseMock.getCats() } throws exception //lança excessao toda vez  q chamar o usecase

        // When
        catApiViewModel.loadData()
        testScheduler.advanceUntilIdle()

        // Then
        coVerify { catUseCaseMock.getCats() } //verifica se foi chamado
        assert(catApiViewModel.state.value?.isLoading == false)
        assert(catApiViewModel.state.value?.isError == "Network Error")
    }

    @Test
    fun `when the load data call is successful`() =  testScope.runTest {
        // Given
        val catList = listOf(CatListModel("cat1", "url1"))
        coEvery { catUseCaseMock.getCats() }  returns flowOf(catList)

        // When
        catApiViewModel.loadData()
        testScheduler.advanceUntilIdle()

        // Then
        coVerify { catUseCaseMock.getCats() }
        assert(catApiViewModel.state.value?.isLoading == false)
        assert(catApiViewModel.state.value?.catList == catList)
    }
}
