package com.middleton.hotcoffees.coffee_options.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.middleton.hotcoffees.COFFEE_ID_KEY
import com.middleton.hotcoffees.coffee_options.domain.model.Coffee
import com.middleton.hotcoffees.coffee_options.domain.usecases.GetCoffeeByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoffeeDetailsViewModel @Inject constructor(
    private val getCoffeeByIdUseCase: GetCoffeeByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val coffeeId: Int = checkNotNull(savedStateHandle[COFFEE_ID_KEY])

    private val _state: MutableStateFlow<CoffeeDetailsState> = MutableStateFlow(CoffeeDetailsState.Loading)
    val state: StateFlow<CoffeeDetailsState>
        get() = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = CoffeeDetailsState.CoffeeDetails(getCoffeeByIdUseCase.invoke(coffeeId))
        }
    }
}

sealed class CoffeeDetailsState {
    data class CoffeeDetails(val coffee: Coffee) : CoffeeDetailsState()
    object Loading : CoffeeDetailsState()
}
