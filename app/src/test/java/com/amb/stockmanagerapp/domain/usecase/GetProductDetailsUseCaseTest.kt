package com.amb.stockmanagerapp.domain.usecase

import com.amb.stockmanagerapp.data.dto.ProductResponse
import com.amb.stockmanagerapp.data.dto.RatingResponse
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
class GetProductDetailsUseCaseTest {

    private lateinit var subject: GetProductDetailsUseCase
    private val repository = mockk<StockRepository>()
    private val fakeProduct = ProductResponse(
        id = 2,
        title = "nome do produto 3",
        description = "descrição do produto 3",
        image = "image 2",
        price = 10.0,
        category = "category",
        rating = RatingResponse(3, 3.4)
    )

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.IO)
        subject = GetProductDetailsUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when use case is invoked then should return loading response`() = runTest {
        coEvery { repository.getProductDetails(any()) } returns fakeProduct
        val response = subject.invoke("id").toList().first()
        assertTrue(response is Response.Loading)
    }

    @Test
    fun `when use case is invoked then should return success response`() = runTest {
        coEvery { repository.getProductDetails(any()) } returns fakeProduct
        val response = subject.invoke("id").toList().last()
        assertTrue(response is Response.Success)
    }

    @Test
    fun `when use case is invoked then should return error response`() = runTest {
        coEvery { repository.getProductDetails(any()) } throws Exception("error")
        val response = subject.invoke("id").toList().last()
        assertTrue(response is Response.Error)
    }
}