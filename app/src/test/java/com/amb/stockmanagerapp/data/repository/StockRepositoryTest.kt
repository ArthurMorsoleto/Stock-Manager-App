@file:OptIn(ExperimentalCoroutinesApi::class)

package com.amb.stockmanagerapp.data.repository

import com.amb.stockmanagerapp.data.dto.LocalProductResponse
import com.amb.stockmanagerapp.data.dto.ProductResponse
import com.amb.stockmanagerapp.data.dto.RatingResponse
import com.amb.stockmanagerapp.data.local.ProductsDao
import com.amb.stockmanagerapp.data.remote.ProductsApi
import com.amb.stockmanagerapp.domain.repository.StockRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class StockRepositoryTest {

    private lateinit var subject: StockRepository

    private val localDataSource = mockk<ProductsDao>()
    private val remoteDataSource = mockk<ProductsApi>()

    private val fakeLocalResponse = listOf(
        LocalProductResponse(
            id = 1,
            title = "title",
            price = 50.0,
            description = "description",
            image = "image",
            category = "category",
            rateCount = 3,
            rate = 4.0,
        )
    )
    private val fakeRemoteResponse = listOf(
        ProductResponse(
            id = 1,
            title = "title",
            price = 50.0,
            description = "description",
            image = "image",
            category = "category",
            rating = RatingResponse(3, 3.0)
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.IO)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testGetProductsFromLocalDataSource() = runTest {
        coEvery { localDataSource.getAll() } returns fakeLocalResponse

        subject = StockRepositoryImpl(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource,
            shouldFetchDataFromRemoteDataSource = false,
            dispatcher = Dispatchers.IO
        )
        val response = subject.getStockProducts()

        assertEquals(1, response.size)
        coVerify(exactly = 1) { localDataSource.getAll() }
        coVerify(exactly = 0) { remoteDataSource.getProducts() }
    }

    @Test
    fun testGetProductsFromRemoteDataSource() = runTest {
        coEvery { localDataSource.getAll() } returns emptyList()
        coEvery { remoteDataSource.getProducts() } returns fakeRemoteResponse
        coEvery { localDataSource.upsertAll(any()) } just Runs

        subject = StockRepositoryImpl(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource,
            shouldFetchDataFromRemoteDataSource = true,
            dispatcher = Dispatchers.IO
        )
        val response = subject.getStockProducts()

        assertEquals(1, response.size)
        coVerify(exactly = 1) { localDataSource.upsertAll(any()) }
        coVerify(exactly = 1) { remoteDataSource.getProducts() }
    }

    @Test
    fun testGetProductDetails() = runTest {
        coEvery { localDataSource.getById(any()) } returns fakeLocalResponse.first()

        subject = StockRepositoryImpl(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource,
            shouldFetchDataFromRemoteDataSource = true,
            dispatcher = Dispatchers.IO
        )
        val response = subject.getProductDetails(1)
        assertEquals("title", response?.title)
    }
}