package com.amb.stockmanagerapp.presentation

import com.amb.stockmanagerapp.domain.model.Product
import com.amb.stockmanagerapp.domain.model.Rating
import com.amb.stockmanagerapp.domain.usecase.GetProductsUseCase
import com.amb.stockmanagerapp.utils.Response
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
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
            rating = Rating(3, 3.4)
        ),
        Product(
            id = 2,
            name = "nome do produto 3",
            description = "descrição do produto 3",
            image = "image 2",
            price = 10.0,
            rating = Rating(3, 3.4)
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
        fun initViewModel() {
            subject = StockViewModel(useCase)
        }

        fun callOnPriceSortClick() {
            subject.onPriceSortClick()
        }

        fun callOnNameSortClick() {
            subject.onNameSortClick()
        }

        fun callFilter() {
            subject.onFilterUpdate("produto 3")
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

        fun verifyIfListIsSortedByPrice() {
            val firstPrice = subject.viewState.value.data.first().price
            val secondPrice = subject.viewState.value.data.last().price
            assertTrue(secondPrice > firstPrice)
        }

        fun verifyIfListIsSortedByDescendingPrice() {
            val firstPrice = subject.viewState.value.data.first().price
            val secondPrice = subject.viewState.value.data.last().price
            assertTrue(firstPrice > secondPrice)
        }

        fun verifyIfListIsSortedByName() {
            val firstName = subject.viewState.value.data.first().name
            val secondName = subject.viewState.value.data.last().name
            assertTrue(firstName < secondName)
        }

        fun verifyIfListIsSortedByDescendingName() {
            val firstName = subject.viewState.value.data.first().name
            val secondName = subject.viewState.value.data.last().name
            assertTrue(firstName > secondName)
        }

        fun checkFilteredItems() {
            assertEquals(1, subject.viewState.value.data.size)
        }
    }
}