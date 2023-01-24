@file:OptIn(ExperimentalCoroutinesApi::class)

package com.middleton.hotcoffees.coffee_options.domain.usecases

import app.cash.turbine.test
import com.middleton.hotcoffees.coffee_options.presentation.options.FakeCoffeeOptionsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class GetCoffeesSortedUseCaseTest {

    private val fakeRepository = FakeCoffeeOptionsRepository()
    private val sut = GetCoffeesSortedUseCase(fakeRepository)

    @Test
    fun `Coffees are sorted by liked in descending order`() = runTest {
        fakeRepository.isUpdateSuccessful = true
        fakeRepository.provideCoffees()

        fakeRepository.getCoffeesFlow.value = fakeRepository.provideCoffees().toMutableList().let { list ->
            list[0] = list[0].copy(liked = true)
            list[1] = list[1].copy(liked = false)
            list[2] = list[2].copy(liked = false)
            list
        }

        sut.invoke().test {
            val list = awaitItem()
            assertEquals(1, list[0].id)
            assertEquals(2, list[1].id)
            assertEquals(3, list[2].id)
        }
    }

    @Test
    fun `When an empty list is returned from repo, empty list is returned from sut`() = runTest {
        fakeRepository.isUpdateSuccessful = true
        fakeRepository.getCoffeesFlow.value = emptyList()

        sut.invoke().test {
            val list = awaitItem()
            assertTrue(list.isEmpty())
        }
    }

    @Test
    fun `When no Coffees are liked, order returned from sut has not changed`() = runTest {
        fakeRepository.isUpdateSuccessful = true
        fakeRepository.getCoffeesFlow.value = fakeRepository.provideCoffees().toMutableList().let { list ->
            list[0] = list[0].copy(liked = false)
            list[1] = list[1].copy(liked = false)
            list[2] = list[2].copy(liked = false)
            list
        }

        sut.invoke().test {
            val list = awaitItem()
            assertEquals(1, list[0].id)
            assertEquals(2, list[1].id)
            assertEquals(3, list[2].id)
        }
    }
}