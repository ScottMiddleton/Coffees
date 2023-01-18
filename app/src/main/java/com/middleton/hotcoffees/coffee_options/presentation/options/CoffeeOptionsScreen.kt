package com.middleton.hotcoffees.coffee_options.presentation.options

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.middleton.hotcoffees.coffee_options.domain.model.Coffee

@Composable
fun CoffeeOptionsScreen(
    viewModel: CoffeeOptionsViewModel = hiltViewModel(),
    navigateToDetail: (Int) -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    CoffeeOptionsContent(coffees = state.coffees) { coffeeId ->
        navigateToDetail(coffeeId)
    }
}

@Composable
fun CoffeeOptionsContent(modifier: Modifier = Modifier, coffees: List<Coffee>, onCoffeeClick: (Int) -> Unit) {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(coffees) { coffee ->
                CoffeeOption(coffee = coffee, onClick = { onCoffeeClick(coffee.id) })
            }
        }
}

@Composable
fun CoffeeOption(modifier: Modifier = Modifier, coffee: Coffee, onClick: () -> Unit) {
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable { onClick() }) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = coffee.title,
            style = MaterialTheme.typography.h6
        )
    }
}

