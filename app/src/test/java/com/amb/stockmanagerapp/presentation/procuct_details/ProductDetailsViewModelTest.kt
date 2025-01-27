package com.amb.stockmanagerapp.presentation.procuct_details

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailsViewModelTest {

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when view model is initialized then should update view state with loading`() =
        runTest {
            ProductDetailsViewModelRobot.apply {
                arrange {
                    mockSavedState()
                    mockGetProductsDetailsLoading()
                }
                act {
                    getProductDetails()
                    advanceUntilIdle()
                }
                assert {
                    verifyLoadingViewState()
                }
            }
        }

    @Test
    fun `when view model is initialized then should update view state with success`() {
        runTest {
            ProductDetailsViewModelRobot.apply {
                arrange {
                    mockSavedState()
                    mockGetProductsDetailsSuccess()
                }
                act {
                    getProductDetails()
                    advanceUntilIdle()
                }
                assert {
                    verifySuccessViewState()
                }
            }
        }
    }

    @Test
    fun `when view model is initialized then should update view state with error`() {
        runTest {
            ProductDetailsViewModelRobot.apply {
                arrange {
                    mockSavedState()
                    mockGetProductsDetailsError()
                }
                act {
                    getProductDetails()
                    advanceUntilIdle()
                }
                assert {
                    verifyErrorViewState()
                }
            }
        }
    }

    @Test
    fun `when view model is initialized without saved state then should update view state with error`() {
        runTest {
            ProductDetailsViewModelRobot.apply {
                arrange {
                    mockSavedStateError()
                }
                act {
                    getProductDetails()
                    advanceUntilIdle()
                }
                assert {
                    verifyErrorViewStateForSavedState()
                }
            }
        }
    }
}