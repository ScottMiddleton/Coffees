package com.middleton.hotcoffees.feature.coffee_options.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.middleton.hotcoffees.COFFEE_ID_KEY
import com.middleton.hotcoffees.feature.coffee_options.domain.model.Coffee
import com.middleton.hotcoffees.feature.coffee_options.domain.usecases.GetCoffeeByIdUseCase
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

    private val _state: MutableStateFlow<CoffeeDetailsState> = MutableStateFlow(CoffeeDetailsState())
    val state: StateFlow<CoffeeDetailsState>
        get() = _state

    private val uiActions = MutableSharedFlow<CoffeeDetailsAction>()

    init {
        viewModelScope.launch {
            uiActions.collect {
                handleAction(it)
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            _state.value = state.value.copy(
                coffee = getCoffeeByIdUseCase.invoke(coffeeId)
            )
        }
    }

    fun emitAction(action: CoffeeDetailsAction) {
        viewModelScope.launch {
            uiActions.emit(action)
        }
    }

    private fun handleAction(action: CoffeeDetailsAction) {
        when (action) {
            is CoffeeDetailsAction.OnLikedChanged -> {
                viewModelScope.launch(Dispatchers.IO) {
                    updateCoffeeLikedStatusUseCase.invoke(coffeeId, action.isLiked)

                    _state.value = state.value.copy(
                        coffee = getCoffeeByIdUseCase.invoke(coffeeId)
                    )
                }
            }
            is CoffeeDetailsAction.OnReviewClicked -> {

            }
        }
    }
}


data class CoffeeDetailsState(val coffee: Coffee? = null)
sealed class CoffeeDetailsAction {
    data class OnLikedChanged(val isLiked: Boolean) : CoffeeDetailsAction()
    data class OnReviewClicked(val isLiked: Boolean) : CoffeeDetailsAction()
}
