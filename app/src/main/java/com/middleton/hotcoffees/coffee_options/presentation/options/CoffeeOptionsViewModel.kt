package com.middleton.hotcoffees.coffee_options.presentation.options

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.middleton.hotcoffees.coffee_options.domain.model.Coffee
import com.middleton.hotcoffees.coffee_options.domain.usecases.GetCoffeesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoffeeOptionsViewModel @Inject constructor(
    getCoffeesUseCase: GetCoffeesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CoffeeOptionsState())

    val state: StateFlow<CoffeeOptionsState>
        get() = _state

    init {
        viewModelScope.launch {
            getCoffeesUseCase.invoke().onSuccess {
                _state.value = CoffeeOptionsState(it)
            }.onFailure {
                // TODO Handle error
            }
        }
    }
}

data class CoffeeOptionsState(val coffees: List<Coffee> = emptyList())