

package com.middleton.hotcoffees.coffee_options.data.repository

import app.cash.turbine.test
import com.middleton.hotcoffees.feature.coffee_options.data.local.CoffeeAndUserInteraction
import com.middleton.hotcoffees.feature.coffee_options.data.local.CoffeeDao
import com.middleton.hotcoffees.feature.coffee_options.data.local.CoffeeEntity
import com.middleton.hotcoffees.feature.coffee_options.data.remote.CoffeeApi
import com.middleton.hotcoffees.coffee_options.data.remote.validCoffeeResponse
import com.middleton.hotcoffees.feature.coffee_options.domain.model.Coffee
import com.middleton.hotcoffees.feature.coffee_options.data.repository.CoffeeOptionsOptionsRepositoryImpl
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class CoffeeOptionsOptionsRepositoryImplTest {
    private lateinit var repository: CoffeeOptionsOptionsRepositoryImpl
    private lateinit var mockWebServer: MockWebServer
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var api: CoffeeApi
    private lateinit var dao: CoffeeDao

    @Before
    fun setUp() {
        dao = mockk(relaxed = true)
        mockWebServer = MockWebServer()
        okHttpClient = OkHttpClient.Builder()
            .writeTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .connectTimeout(1, TimeUnit.SECONDS)
            .build()
        api = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create(CoffeeApi::class.java)
        repository = CoffeeOptionsOptionsRepositoryImpl(
            dao = dao,
            api = api
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `When updateCoffees() called with success, valid response is emitted`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setBody(validCoffeeResponse)
        )

        val result = repository.updateCoffees()

        result.test {
            val item = awaitItem()
            assertTrue(item.isSuccess)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `When updateCoffees() called with error, failure response is emitted`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(400)
        )

        val result = repository.updateCoffees()

        result.test {
            val item = awaitItem()
            assertTrue(item.isFailure)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `When getCoffees() called, mapped coffee list is emitted`() = runBlocking {
        val expected = listOf(
            Coffee(
                1,
                "Espresso",
                "A strong, concentrated coffee made by forcing hot water through finely ground coffee beans.",
                listOf("Coffee Beans"),
                "https://example.com/espresso.jpg",
                liked = false
            ),
            Coffee(
                2,
                "Latte",
                "A coffee drink made with espresso and steamed milk, topped with a layer of foam.",
                listOf("Espresso", "Milk", "Foam"),
                "https://example.com/latte.jpg",
                liked = false
            )
        )

        every { dao.getAllCoffees() } returns flow {
            emit(
                listOf(
                    CoffeeAndUserInteraction(
                        CoffeeEntity(
                            1,
                            "Espresso",
                            "A strong, concentrated coffee made by forcing hot water through finely ground coffee beans.",
                            listOf("Coffee Beans"),
                            "https://example.com/espresso.jpg"
                        ),
                        null
                    ),
                    CoffeeAndUserInteraction(
                        CoffeeEntity(
                            2,
                            "Latte",
                            "A coffee drink made with espresso and steamed milk, topped with a layer of foam.",
                            listOf("Espresso", "Milk", "Foam"),
                            "https://example.com/latte.jpg"
                        ), null
                    )
                )
            )
        }

        repository.getCoffees().test {
            val item = awaitItem()
            assertEquals(expected, item)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When getCoffeeById() called, mapped coffee is returned`() = runTest {
        val coffeeId = 1

        val expected = Coffee(
            coffeeId,
            "Espresso",
            "A strong, concentrated coffee made by forcing hot water through finely ground coffee beans.",
            listOf("Coffee Beans"),
            "https://example.com/espresso.jpg",
            liked = false
        )

        every { dao.getCoffeeById(coffeeId) } returns
                CoffeeAndUserInteraction(
                    CoffeeEntity(
                        1,
                        "Espresso",
                        "A strong, concentrated coffee made by forcing hot water through finely ground coffee beans.",
                        listOf("Coffee Beans"),
                        "https://example.com/espresso.jpg"
                    ),
                    null
                )

        assertEquals(expected, repository.getCoffeeById(coffeeId))
    }
}