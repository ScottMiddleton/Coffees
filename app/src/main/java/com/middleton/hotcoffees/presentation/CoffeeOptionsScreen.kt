package com.middleton.hotcoffees.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.middleton.hotcoffees.domain.model.Coffee

@Composable
fun CoffeeOptionsScreen(viewModel: CoffeeOptionsViewModel = hiltViewModel(), navigateToDetail: (Int) -> Unit) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    CoffeeOptionsContent(state.coffees) { coffeeId ->
        navigateToDetail(coffeeId)
    }
}

@Composable
fun CoffeeOptionsContent(coffees: List<Coffee>, onCoffeeClick: (Int) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(coffees) { coffee ->
            CoffeeOption(coffee = coffee, onClick = { onCoffeeClick(coffee.id) })
        }
    }
}

