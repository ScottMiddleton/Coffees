@file:OptIn(ExperimentalMaterialApi::class)

package com.middleton.hotcoffees.coffee_review.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.middleton.hotcoffees.R
import com.middleton.hotcoffees.coffee_options.presentation.shared.LoadingScreen
import com.middleton.hotcoffees.ui.theme.LocalSpacing

@Composable
fun CoffeeReviewScreen(
    viewModel: CoffeeReviewViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState,
    onNavigateUp: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val spacing = LocalSpacing.current

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is CoffeeReviewEvent.SnackBarEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
                CoffeeReviewEvent.SubmissionSuccess -> {
                    onNavigateUp()
                }
            }
        }
    }

    if (state.isLoading) {
        LoadingScreen()
    } else {
        Column {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.review)) },
                navigationIcon = {
                    IconButton(modifier = Modifier.padding(end = 8.dp), onClick = onNavigateUp) {
                        Icon(Icons.Filled.ArrowBack, null, tint = Color.White)
                    }
                })

            Column(modifier = Modifier.padding(spacing.spaceMedium)) {

                OutlinedTextField(
                    value = state.review.name ?: "",
                    onValueChange = { viewModel.emitAction(CoffeeReviewAction.OnNameUpdated(it)) },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    )
                )

                Spacer(modifier = Modifier.height(LocalSpacing.current.spaceExtraSmall))

                OutlinedTextField(
                    value = state.review.body ?: "",
                    onValueChange = { viewModel.emitAction(CoffeeReviewAction.OnBodyUpdated(it)) },
                    label = { Text("Review") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                Spacer(modifier = Modifier.height(LocalSpacing.current.spaceExtraSmall))

                RatingDropdown(
                    suggestions = state.ratingSuggestions,
                    currentSelection = state.review.rating?.toString(),
                    onRatingUpdated = { rating ->
                        viewModel.emitAction(CoffeeReviewAction.OnRatingUpdated(rating))
                    })

                Spacer(modifier = Modifier.height(LocalSpacing.current.spaceMedium))

                Button(
                    onClick = {
                        viewModel.emitAction(CoffeeReviewAction.OnReviewSubmitted)
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally),
                    enabled = state.isValidReview
                ) {
                    Text(stringResource(R.string.submit_reveiw))
                }
            }
        }
    }
}

@Composable
private fun RatingDropdown(
    modifier: Modifier = Modifier,
    suggestions: List<Int>,
    currentSelection: String?,
    onRatingUpdated: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = currentSelection ?: "",
            onValueChange = {},
            label = { Text(text = stringResource(R.string.rating)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            readOnly = true
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            suggestions.forEach { rating ->
                DropdownMenuItem(
                    onClick = {
                        onRatingUpdated(rating)
                        expanded = false
                    }
                ) {
                    Text(text = rating.toString())
                }
            }
        }
    }
}