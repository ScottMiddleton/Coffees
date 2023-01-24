package com.middleton.hotcoffees.coffee_options.presentation.options

import app.cash.turbine.test
import com.middleton.hotcoffees.R
import com.middleton.hotcoffees.coffee_options.domain.model.Coffee
import com.middleton.hotcoffees.coffee_options.domain.usecases.GetCoffeesSortedUseCase
import com.middleton.hotcoffees.coffee_options.domain.usecases.UpdateCoffeesUseCase
import com.middleton.hotcoffees.util.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CoffeeOptionsViewModelTest {

    private var repository = FakeCoffeeOptionsRepository()
    private lateinit var getCoffeesSortedUseCase: GetCoffeesSortedUseCase
    private lateinit var updateCoffeesUseCase: UpdateCoffeesUseCase

    private lateinit var sut: CoffeeOptionsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Before
    fun setUp() {
        getCoffeesSortedUseCase = GetCoffeesSortedUseCase(repository)
        updateCoffeesUseCase = UpdateCoffeesUseCase(repository)
    }

    @Test
    fun `When ViewModel is initialised, Given db is empty, state is returned with empty coffee list`() = runTest {
        repository.isUpdateSuccessful = false
        sut = CoffeeOptionsViewModel(getCoffeesSortedUseCase, updateCoffeesUseCase)
        val expectedCoffees = emptyList<Coffee>()

        sut.state.test {
            assertEquals(expectedCoffees, awaitItem().coffees)
        }
    }

    @Test
    fun `When coffees are updated successfully, state is returned with coffee list`() = runTest {
        sut = CoffeeOptionsViewModel(getCoffeesSortedUseCase, updateCoffeesUseCase)
        val expectedCoffees = repository.provideCoffees()

        sut.state.test {
            assertEquals(expectedCoffees, awaitItem().coffees)
        }
    }

    @Test
    fun `Given db is empty, when coffee update failed, state is returned with coffee list empty and SnackBar event triggered`() = runTest {
        repository.isUpdateSuccessful = false
        sut = CoffeeOptionsViewModel(getCoffeesSortedUseCase, updateCoffeesUseCase)
        val expectedCoffees = emptyList<Coffee>()

        sut.state.test {
            assertEquals(expectedCoffees, awaitItem().coffees)
        }

        sut.uiEvent.test {
            assertEquals(CoffeeOptionsSnackBarEvent(UiText.StringResource(R.string.generic_error_loading)), awaitItem())
        }
    }

    @Test
    fun `Given db is NOT empty, when coffee update failed, state is returned with coffee list and SnackBar event triggered`() = runTest {
        sut = CoffeeOptionsViewModel(getCoffeesSortedUseCase, updateCoffeesUseCase)
        val expectedCoffees = repository.provideCoffees()

        sut.state.test {
            assertEquals(expectedCoffees, awaitItem().coffees)
        }

        repository.isUpdateSuccessful = false
        sut.updateCoffees()

        sut.state.test {
            assertEquals(expectedCoffees, awaitItem().coffees)
        }

        sut.uiEvent.test {
            assertEquals(CoffeeOptionsSnackBarEvent(UiText.StringResource(R.string.generic_error_loading)), awaitItem())
        }
    }
}