package com.example.coroutines.ui.retrofit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.coroutines.data.api.ApiHelper
import com.example.coroutines.data.model.ApiUser
import com.example.coroutines.utils.Resource
import com.example.coroutines.data.local.DatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SingleNetworkCallViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var apiHelper: ApiHelper

    @Mock
    private lateinit var databaseHelper: DatabaseHelper // Mock DatabaseHelper

    private lateinit var viewModel: SingleNetworkCallViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SingleNetworkCallViewModel(apiHelper, databaseHelper) // Inject mock databaseHelper
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchUsers should emit Loading then Success when api call is successful`() = runTest(testDispatcher) {
        val mockUsers = listOf(ApiUser(1, "Test User", "test@example.com", "avatar.url"))
        whenever(apiHelper.getUsers()).thenReturn(flowOf(mockUsers))

        viewModel.users.test {
            val initialLoading = awaitItem()
            assert(initialLoading is Resource.Loading)

            viewModel.fetchUsers()

            val successItem = awaitItem()
            assert(successItem is Resource.Success)
            assertEquals(mockUsers, (successItem as Resource.Success).data)

            cancelAndConsumeRemainingEvents()
        }

        verify(apiHelper).getUsers()
    }

    @Test
    fun `fetchUsers should emit Loading then Error when api call fails`() = runTest(testDispatcher) {
        val errorMessage = "Network Error"
        val exception = RuntimeException(errorMessage)
        whenever(apiHelper.getUsers()).thenReturn(flow { throw exception })

        viewModel.users.test {
            val initialLoading = awaitItem()
            assert(initialLoading is Resource.Loading)

            viewModel.fetchUsers()

            val errorItem = awaitItem()
            assert(errorItem is Resource.Error)
            assertEquals(errorMessage, (errorItem as Resource.Error).message)

            cancelAndConsumeRemainingEvents()
        }

        verify(apiHelper).getUsers()
    }

    @Test
    fun `fetchUsers should emit Loading then Success with empty list`() = runTest(testDispatcher) {
        val emptyList = emptyList<ApiUser>()
        whenever(apiHelper.getUsers()).thenReturn(flowOf(emptyList))

        viewModel.users.test {
            val initialLoading = awaitItem()
            assert(initialLoading is Resource.Loading)

            viewModel.fetchUsers()

            val successItem = awaitItem()
            assert(successItem is Resource.Success)
            assertEquals(emptyList, (successItem as Resource.Success).data)

            cancelAndConsumeRemainingEvents()
        }

        verify(apiHelper).getUsers()
    }
}
