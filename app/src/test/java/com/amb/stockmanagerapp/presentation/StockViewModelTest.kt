package com.amb.stockmanagerapp.presentation

import com.amb.stockmanagerapp.domain.model.Product
import com.amb.stockmanagerapp.domain.usecase.GetProductsUseCase
import com.amb.stockmanagerapp.utils.Response
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class StockViewModelTest {

    private lateinit var subject: StockViewModel

    private val useCase = mockk<GetProductsUseCase>()

    private val fakeProducts = listOf(
        Product(
            id = 1,
            name = "nome do produto 1",
            description = "descrição do produto 1",
            image = "image 1",
            price = 5.0,
            quantity = 3
        ),
        Product(
            id = 2,
            name = "nome do produto 3",
            description = "descrição do produto 3",
            image = "image 2",
            price = 5.0,
            quantity = 3
        ),
        Product(
            id = 3,
            name = "nome do produto 3",
            description = "descrição do produto 3",
            image = "image 3",
            price = 5.0,
            quantity = 3
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testGetProductsWithSuccessResponse() = runTest {
        coEvery { useCase.invoke() } returns flow { emit(Response.Success(fakeProducts)) }
        subject = StockViewModel(useCase)

        advanceUntilIdle()

        val response = subject.viewState.value
        assertEquals(3, response.data.size)
    }
}