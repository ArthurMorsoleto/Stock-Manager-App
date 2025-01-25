package com.amb.stockmanagerapp.presentation

import com.amb.stockmanagerapp.domain.model.Product
import com.amb.stockmanagerapp.domain.usecase.GetProductsUseCase
import com.amb.stockmanagerapp.utils.Response
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow

object StockViewModelTestRobot {

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
        )
    )

    infix fun arrange(f: Arrange.() -> Unit) = Arrange().apply(f)
    infix fun act(f: Act.() -> Unit) = Act().apply(f)
    infix fun assert(f: Assert.() -> Unit) = Assert().apply(f)

    class Arrange {
        fun mockGetProductsSuccess() {
            coEvery { useCase() } returns flow { emit(Response.Success(fakeProducts)) }
        }

        fun mockGetProductsLoading() {
            coEvery { useCase() } returns flow { emit(Response.Loading()) }
        }

        fun mockGetProductsError() {
            coEvery { useCase() } returns flow { emit(Response.Error("error")) }
        }
    }

    class Act {
        fun callGetProducts() {
            subject = StockViewModel(useCase)
        }
    }

    class Assert {
        fun verifySuccessViewState() {
            assertEquals(2, subject.viewState.value.data.size)
        }

        fun verifyLoadingViewState() {
            assertEquals(true, subject.viewState.value.isLoading)
        }

        fun verifyErrorViewState() {
            assertEquals("error", subject.viewState.value.error)
        }
    }
}