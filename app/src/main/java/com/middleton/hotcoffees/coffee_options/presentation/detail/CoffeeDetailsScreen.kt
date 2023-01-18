package com.middleton.hotcoffees.coffee_options.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.middleton.hotcoffees.coffee_options.domain.model.Coffee
import com.middleton.hotcoffees.ui.theme.LocalSpacing
import com.middleton.hotcoffees.R

@Composable
fun CoffeeDetailsScreen(viewModel: CoffeeDetailsViewModel = hiltViewModel()) {
    when (val state = viewModel.state.collectAsStateWithLifecycle().value) {
        is CoffeeDetailsState.CoffeeDetails -> CoffeeDetailsContent(state.coffee)
        CoffeeDetailsState.Loading -> LoadingScreen()
    }
}

@Composable
fun CoffeeDetailsContent(coffee: Coffee) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier.padding(spacing.spaceMedium)
    ) {
        Card {
            Column(modifier = Modifier.padding(spacing.spaceMedium)) {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = coffee.title, style = MaterialTheme.typography.h2,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = { /* handle like */ }) {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                }

                val placeholder = painterResource(R.drawable.placeholder)
                AsyncImage(
                    model = coffee.imageUrl,
                    contentDescription = null,
                    placeholder = placeholder,
                    error = placeholder
                )
            }
        }

        Spacer(modifier = Modifier.height(spacing.spaceMedium))

        Column {
            Text(text = coffee.description, style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            Text(text = "Ingredients: ${coffee.ingredients.joinToString()}")
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            TextButton(onClick = { /* handle review */ }) {
                Icon(Icons.Filled.Create, contentDescription = null)
                Text(text = "Review", style = MaterialTheme.typography.h6)
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator(
            Modifier.size(48.dp)
        )
    }
}