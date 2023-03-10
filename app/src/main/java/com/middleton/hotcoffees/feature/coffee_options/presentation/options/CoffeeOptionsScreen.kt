package com.middleton.hotcoffees.feature.coffee_options.presentation.options

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.middleton.hotcoffees.R
import com.middleton.hotcoffees.feature.coffee_options.domain.model.Coffee
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CoffeeOptionsScreen(
    viewModel: CoffeeOptionsViewModel = hiltViewModel(),
    navigateToDetail: (Int) -> Unit,
    scaffoldState: ScaffoldState
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            scaffoldState.snackbarHostState.showSnackbar(
                message = event.message.asString(context)
            )
        }
    }

    Box {
        CoffeeOptionsList(
            modifier = Modifier.padding(top = 28.dp),
            coffees = state.coffees,
            onCoffeeClick = { coffeeId ->
                navigateToDetail(coffeeId)
            },
            onSwipeToRefresh = {
                viewModel.refreshAction()
            },
            state = state
        )

        TopAppBar(
            title = {
                Text(
                    stringResource(R.string.coffee_options_title),
                    style = MaterialTheme.typography.h3
                )
            }
        )

        if (state.isLoading) {
            CircularProgressIndicator(
                Modifier
                    .size(48.dp)
                    .align(Alignment.Center),
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CoffeeOptionsList(
    modifier: Modifier = Modifier,
    coffees: List<Coffee>,
    onCoffeeClick: (Int) -> Unit,
    onSwipeToRefresh: (CoroutineScope) -> Unit,
    state: CoffeeOptionsState
) {
    val refreshScope = rememberCoroutineScope()
    val refreshing = state.isRefreshing

    fun refresh() = refreshScope.launch {
        onSwipeToRefresh(this)
    }

    val prState = rememberPullRefreshState(refreshing, ::refresh)

    Box(modifier.pullRefresh(prState)) {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(coffees) { coffee ->
                CoffeeListItem(onClick = { onCoffeeClick(coffee.id) }, coffee = coffee)
            }
        }
        PullRefreshIndicator(refreshing, prState,
            Modifier
                .align(Alignment.TopCenter)
                .padding(top = 24.dp))
    }
}

@Composable
fun CoffeeListItem(modifier: Modifier = Modifier, coffee: Coffee, onClick: () -> Unit) {
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable { onClick() }) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f),
                text = coffee.title,
                style = MaterialTheme.typography.h6
            )
            if (coffee.liked) {
                Icon(
                    modifier = Modifier.padding(8.dp),
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = Color.Red
                )
            }
        }
    }
}

