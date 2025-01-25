package com.amb.stockmanagerapp.presentation

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
class StockViewModelTest {

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when get products use case is called then should update view state with loading`() =
        runTest {
            StockViewModelTestRobot.run {
                arrange { mockGetProductsLoading() }
                act {
                    callGetProducts()
                    advanceUntilIdle()
                }
                assert { verifyLoadingViewState() }
            }
        }

    @Test
    fun `when get products use case is called then should update view state with success`() =
        runTest {
            StockViewModelTestRobot.run {
                arrange { mockGetProductsSuccess() }
                act {
                    callGetProducts()
                    advanceUntilIdle()
                }
                assert { verifySuccessViewState() }
            }
        }

    @Test
    fun `when get products use case is called then should update view state with error`() =
        runTest {
            StockViewModelTestRobot.run {
                arrange { mockGetProductsError() }
                act {
                    callGetProducts()
                    advanceUntilIdle()
                }
                assert { verifyErrorViewState() }
            }
        }
}