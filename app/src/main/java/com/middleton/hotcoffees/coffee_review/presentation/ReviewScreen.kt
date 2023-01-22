package com.middleton.hotcoffees.coffee_review.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ReviewScreen(viewModel: CoffeeReviewViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = state.review.name ?: "",
            onValueChange = { viewModel.emitAction(CoffeeReviewAction.OnNameUpdated(it)) },
            label = { Text("Name") },
            modifier = Modifier.padding(8.dp)
        )

//        TextField(
//            value = date,
//            onValueChange = { date = it },
//            label = { Text("Date") },
//            modifier = Modifier.padding(8.dp)
//        )

        TextField(
            value = state.review.body ?: "",
            onValueChange = { viewModel.emitAction(CoffeeReviewAction.OnBodyUpdated(it)) },
            label = { Text("Review") },
            modifier = Modifier.padding(8.dp)
        )

//        DropdownMenu(
//            selectedOption = rating,
//            onSelectionChange = { rating = it },
//            options = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"),
//            modifier = Modifier.padding(8.dp)
//        )

        Button(
            onClick = {
                viewModel.emitAction(CoffeeReviewAction.OnReviewSubmitted)
            },
            modifier = Modifier.padding(8.dp),
            enabled = state.isValidReview
        ) {
            Text("Submit Review")
        }
    }
}