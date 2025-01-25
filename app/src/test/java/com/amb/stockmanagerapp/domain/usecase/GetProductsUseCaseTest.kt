package com.amb.stockmanagerapp.domain.usecase

import com.amb.stockmanagerapp.domain.repository.StockRepository
import com.amb.stockmanagerapp.utils.Response
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetProductsUseCaseTest {

    private lateinit var subject: GetProductsUseCase
    private val repository = mockk<StockRepository>()

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.IO)
        subject = GetProductsUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when use case is invoked then should return loading response`() = runTest {
        coEvery { repository.getStockProducts() } returns listOf()
        val response = subject.invoke().toList().first()
        assertTrue(response is Response.Loading)
    }

    @Test
    fun `when use case is invoked then should return success response`() = runTest {
        coEvery { repository.getStockProducts() } returns listOf()
        val response = subject.invoke().toList().last()
        assertTrue(response is Response.Success)
    }

    @Test
    fun `when use case is invoked then should return error response`() = runTest {
        coEvery { repository.getStockProducts() } throws Exception("error")
        val response = subject.invoke().toList().last()
        assertTrue(response is Response.Error)
    }
}