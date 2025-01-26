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
                    initViewModel()
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
                    initViewModel()
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
                    initViewModel()
                    advanceUntilIdle()
                }
                assert { verifyErrorViewState() }
            }
        }

    @Test
    fun `when on price sort is called then should update sort option from products data`() {
        runTest {
            StockViewModelTestRobot.run {
                arrange { mockGetProductsSuccess() }
                act {
                    initViewModel()
                    advanceUntilIdle()
                    callOnPriceSortClick()
                }
                assert { verifyIfListIsSortedByPrice() }
                act { callOnPriceSortClick() }
                assert { verifyIfListIsSortedByDescendingPrice() }
            }
        }
    }

    @Test
    fun `when on name sort is called then should update sort option from products data`() {
        runTest {
            StockViewModelTestRobot.run {
                arrange { mockGetProductsSuccess() }
                act {
                    initViewModel()
                    advanceUntilIdle()
                    callOnNameSortClick()
                }
                assert { verifyIfListIsSortedByName() }
                act { callOnNameSortClick() }
                assert { verifyIfListIsSortedByDescendingName() }
            }
        }
    }

    @Test
    fun testFilter()= runTest{
        StockViewModelTestRobot.apply {
            arrange { mockGetProductsSuccess() }
            act {
                initViewModel()
                advanceUntilIdle()
                callFilter()
            }
            assert {
                checkFilteredItems()
            }
        }
    }
}