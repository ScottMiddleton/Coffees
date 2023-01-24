package com.middleton.hotcoffees.feature.coffee_options.presentation.options

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.middleton.hotcoffees.R
import com.middleton.hotcoffees.feature.coffee_options.domain.model.Coffee
import com.middleton.hotcoffees.feature.coffee_options.domain.usecases.GetCoffeesSortedUseCase
import com.middleton.hotcoffees.feature.coffee_options.domain.usecases.UpdateCoffeesUseCase
import com.middleton.hotcoffees.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoffeeOptionsViewModel @Inject constructor(
    getCoffeesSortedUseCase: GetCoffeesSortedUseCase, private val updateCoffeesUseCase: UpdateCoffeesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CoffeeOptionsState())
    val state: StateFlow<CoffeeOptionsState>
        get() = _state

    private val _uiEvent = Channel<CoffeeOptionsSnackBarEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var updateCoffeesJob: Job? = null

    init {
        viewModelScope.launch {
            getCoffeesSortedUseCase.invoke().collect {
                _state.value = _state.value.copy(coffees = it)
            }
        }

        updateCoffees()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun updateCoffees() {
        viewModelScope.launch {
            if (updateCoffeesJob?.isActive == false || updateCoffeesJob == null) {
                updateCoffeesJob = updateCoffeesUseCase.invoke().onEach {
                    it.onFailure {
                        _uiEvent.send(CoffeeOptionsSnackBarEvent(UiText.StringResource(R.string.generic_error_loading)))
                    }
                    _state.value = _state.value.copy(isLoading = false, isRefreshing = false)
                }.launchIn(viewModelScope)
            }
        }
    }

    fun refreshAction() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isRefreshing = true)
            updateCoffees()
        }
    }
}

data class CoffeeOptionsState(
    val coffees: List<Coffee> = emptyList(),
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false
)
data class CoffeeOptionsSnackBarEvent(val message: UiText)