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
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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

    private  lateinit var catApiViewModel: CatViewModel

    @MockK
    private val catUseCaseMock: CatUseCase = mockk()
    val dispatchers = Dispatchers.Unconfined

    @Before
      fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(UnconfinedTestDispatcher())
        catApiViewModel = CatViewModel(catUseCaseMock, dispatchers)
    }

    @Test
    fun `when call loadData should handle IOException`() {
        // Given
        val exception = IOException("Network Error")
        coEvery { catUseCaseMock.getCats() } throws exception

        // When
        catApiViewModel.loadData()

        // Then
        coVerify { catUseCaseMock.getCats() }
        assert(catApiViewModel.state.value?.isLoading == false)
        assert(catApiViewModel.state.value?.isError == "Network Error")
    }

    @Test
    fun `when the load data call is successful`() {
        // Given
        val catList = listOf(CatListModel("cat1", "url1"))
        coEvery { catUseCaseMock.getCats() }  returns flowOf(catList)

        // When
        catApiViewModel.loadData()

        // Then
        coVerify { catUseCaseMock.getCats() }
        assert(catApiViewModel.state.value?.isLoading == false)
        assert(catApiViewModel.state.value?.catList == catList)
    }
}
