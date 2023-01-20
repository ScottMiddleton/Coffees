package com.middleton.hotcoffees.coffee_options.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.middleton.hotcoffees.COFFEE_ID_KEY
import com.middleton.hotcoffees.coffee_options.domain.model.Coffee
import com.middleton.hotcoffees.coffee_options.domain.usecases.GetCoffeeByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoffeeDetailsViewModel @Inject constructor(
    private val getCoffeeByIdUseCase: GetCoffeeByIdUseCase,
    private val updateCoffeeLikedStatusUseCase: UpdateCoffeeLikedStatusUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val coffeeId: Int = checkNotNull(savedStateHandle[COFFEE_ID_KEY])

    private val _state: MutableStateFlow<CoffeeDetailsState> =
        MutableStateFlow(CoffeeDetailsState.Loading)
    val state: StateFlow<CoffeeDetailsState>
        get() = _state

    private val viewActions = MutableSharedFlow<CoffeeDetailsAction>()

    init {
        viewModelScope.launch {
            viewActions.collect {
                handleAction(it)
            }
        }


        viewModelScope.launch(Dispatchers.IO) {
            _state.value = CoffeeDetailsState.Success(
                coffee = getCoffeeByIdUseCase.invoke(coffeeId)
            )
        }
    }

    fun emitAction(action: CoffeeDetailsAction) {
        viewModelScope.launch {
            viewActions.emit(action)
        }
    }

    private fun handleAction(action: CoffeeDetailsAction) {
        when (action) {
            is CoffeeDetailsAction.OnLikedChanged -> {
                viewModelScope.launch(Dispatchers.IO) {
                    updateCoffeeLikedStatusUseCase.invoke(coffeeId, action.isLiked)
                }
            }
            is CoffeeDetailsAction.OnReviewClicked -> {

            }
        }
    }
}

sealed class CoffeeDetailsState {
    data class Success(val coffee: Coffee) : CoffeeDetailsState()
    object Loading : CoffeeDetailsState()
}

sealed class CoffeeDetailsAction {
    data class OnLikedChanged(val isLiked: Boolean) : CoffeeDetailsAction()
    data class OnReviewClicked(val isLiked: Boolean) : CoffeeDetailsAction()
}
