package com.acronym

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.acronym.model.ResponseItem
import com.acronym.repository.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NetworkCallViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
    @Mock
    private lateinit var repository: Repository

    @Test
    fun testNetworkCallResponseSuccess() {
        testCoroutineRule.runBlockingTest {
            doReturn(flowOf(emptyList<ResponseItem>())).
            `when`(repository)
                .getLongForm("")
            verify(repository).getLongForm("")
        }
    }

    @Test
    fun testNetworkCallResponseError() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "error occurred"
            doReturn(flow<List<ResponseItem>>{
                throw IOException(errorMessage)
            })
            .`when`(repository)
            .getLongForm("HHM")
            verify(repository).getLongForm("HHM")
        }
    }
}