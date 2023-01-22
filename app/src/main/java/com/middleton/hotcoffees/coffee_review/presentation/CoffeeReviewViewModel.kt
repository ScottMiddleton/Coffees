package com.middleton.hotcoffees.coffee_review.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.middleton.hotcoffees.COFFEE_ID_KEY
import com.middleton.hotcoffees.coffee_review.domain.model.Review
import com.middleton.hotcoffees.coffee_review.domain.usecases.SubmitReviewUseCase
import com.middleton.hotcoffees.coffee_review.domain.usecases.ValidateReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoffeeReviewViewModel @Inject constructor(
    private val submitReviewUseCase: SubmitReviewUseCase,
    private val validateReviewUseCase: ValidateReviewUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val coffeeId: Int = checkNotNull(savedStateHandle[COFFEE_ID_KEY])

    private val _state: MutableStateFlow<CoffeeReviewState> =
        MutableStateFlow(CoffeeReviewState(review = Review(coffeeId = coffeeId), isLoading = false))
    val state: StateFlow<CoffeeReviewState>
        get() = _state

    private val uiActions = MutableSharedFlow<CoffeeReviewAction>()

    init {
        viewModelScope.launch {
            uiActions.collect {
                handleAction(it)
            }
        }
    }

    fun emitAction(action: CoffeeReviewAction) {
        viewModelScope.launch {
            uiActions.emit(action)
        }
    }

    private fun handleAction(action: CoffeeReviewAction) {
        when (action) {
            CoffeeReviewAction.OnReviewSubmitted -> {
                viewModelScope.launch(Dispatchers.IO) {
                    submitReviewUseCase.invoke(state.value.review)
                }
            }
            is CoffeeReviewAction.OnDateUpdated -> {
                val review = state.value.review.copy(date = action.date)
                _state.value = state.value.copy(review = review, isValidReview = validateReviewUseCase.isValid(review))
            }
            is CoffeeReviewAction.OnNameUpdated -> {
                val review = state.value.review.copy(name = action.name)
                _state.value = state.value.copy(review = review, isValidReview = validateReviewUseCase.isValid(review))
            }
            is CoffeeReviewAction.OnRatingUpdated -> {
                val review = state.value.review.copy(rating = action.rating)
                _state.value = state.value.copy(review = review, isValidReview = validateReviewUseCase.isValid(review))
            }
            is CoffeeReviewAction.OnBodyUpdated -> {
                val review = state.value.review.copy(body = action.body)
                _state.value = state.value.copy(review = review, isValidReview = validateReviewUseCase.isValid(review))
            }
        }
    }
}

data class CoffeeReviewState(val review: Review, val isLoading: Boolean = false, val isValidReview: Boolean = false)

sealed class CoffeeReviewAction {
    object OnReviewSubmitted : CoffeeReviewAction()
    data class OnNameUpdated(val name: String) : CoffeeReviewAction()
    data class OnBodyUpdated(val body: String) : CoffeeReviewAction()
    data class OnDateUpdated(val date: String) : CoffeeReviewAction()
    data class OnRatingUpdated(val rating: Int) : CoffeeReviewAction()
}