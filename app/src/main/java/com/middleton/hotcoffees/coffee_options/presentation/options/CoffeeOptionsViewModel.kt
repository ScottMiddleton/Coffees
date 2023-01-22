package com.middleton.hotcoffees.coffee_options.presentation.options

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.middleton.hotcoffees.R
import com.middleton.hotcoffees.coffee_options.domain.model.Coffee
import com.middleton.hotcoffees.coffee_options.domain.usecases.GetCoffeesUseCase
import com.middleton.hotcoffees.coffee_options.domain.usecases.UpdateCoffeesUseCase
import com.middleton.hotcoffees.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoffeeOptionsViewModel @Inject constructor(
    getCoffeesUseCase: GetCoffeesUseCase, private val updateCoffeesUseCase: UpdateCoffeesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CoffeeOptionsState())
    val state: StateFlow<CoffeeOptionsState>
        get() = _state

    private val uiActions = MutableSharedFlow<RefreshCoffeesAction>()

    private val _uiEvent = Channel<CoffeeOptionsSnackBarEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            getCoffeesUseCase.invoke().collect {
                _state.value = CoffeeOptionsState(coffees = it, isLoading = false)
            }
        }

        viewModelScope.launch {
            uiActions.collect {
                updateCoffees()
            }
        }

        updateCoffees()
    }

    private fun updateCoffees() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            updateCoffeesUseCase.invoke().onFailure {
                if (state.value.coffees.isEmpty()) {
                    _uiEvent.send(CoffeeOptionsSnackBarEvent(UiText.StringResource(R.string.generic_error)))
                    _state.value = _state.value.copy(isLoading = false)
                }
            }.onSuccess {
                _state.value = _state.value.copy(isLoading = false)
            }

        }
    }

    fun emitAction(action: RefreshCoffeesAction) {
        viewModelScope.launch {
            uiActions.emit(action)
        }
    }
}

data class CoffeeOptionsState(
    val coffees: List<Coffee> = emptyList(), val isLoading: Boolean = true
)

object RefreshCoffeesAction
data class CoffeeOptionsSnackBarEvent(val message: UiText)