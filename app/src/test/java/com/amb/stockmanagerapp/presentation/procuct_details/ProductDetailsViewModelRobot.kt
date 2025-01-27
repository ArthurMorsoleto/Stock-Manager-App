package com.amb.stockmanagerapp.presentation.procuct_details

import androidx.lifecycle.SavedStateHandle
import com.amb.stockmanagerapp.domain.model.Product
import com.amb.stockmanagerapp.domain.model.Rating
import com.amb.stockmanagerapp.domain.usecase.GetProductDetailsUseCase
import com.amb.stockmanagerapp.utils.Response
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow

object ProductDetailsViewModelRobot {
    private lateinit var subject: ProductDetailsViewModel
    private val useCase = mockk<GetProductDetailsUseCase>()
    private val savedStateHandle = mockk<SavedStateHandle>()
    private val fakeProduct = Product(
        id = 2,
        name = "nome do produto 3",
        description = "descrição do produto 3",
        image = "image 2",
        price = 10.0,
        rating = Rating(3, 3.4)
    )

    infix fun arrange(f: Arrange.() -> Unit) = Arrange().apply(f)
    infix fun act(f: Act.() -> Unit) = Act().apply(f)
    infix fun assert(f: Assert.() -> Unit) = Assert().apply(f)

    class Arrange {
        fun mockGetProductsDetailsSuccess() {
            coEvery { useCase(any()) } returns flow { emit(Response.Success(fakeProduct)) }
        }

        fun mockGetProductsDetailsLoading() {
            coEvery { useCase(any()) } returns flow { emit(Response.Loading()) }
        }

        fun mockSavedState() {
            every { savedStateHandle.get<String>(any()) } returns "productId"
        }

        fun mockGetProductsDetailsError() {
            coEvery { useCase(any()) } returns flow { emit(Response.Error("error")) }
        }

        fun mockSavedStateError() {
            every { savedStateHandle.get<String>(any()) } returns null
        }
    }

    class Act {
        fun getProductDetails() {
            subject = ProductDetailsViewModel(useCase, savedStateHandle)
        }
    }

    class Assert {
        fun verifyLoadingViewState() {
            assertEquals(true, subject.viewState.value.isLoading)
        }

        fun verifySuccessViewState() {
            assertEquals("nome do produto 3", subject.viewState.value.data?.name)
        }

        fun verifyErrorViewState() {
            assertEquals("error", subject.viewState.value.error)
        }

        fun verifyErrorViewStateForSavedState() {
            assertEquals("error when loading product details", subject.viewState.value.error)
        }
    }
}