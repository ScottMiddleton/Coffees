package com.middleton.hotcoffees.coffee_options.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.middleton.hotcoffees.R
import com.middleton.hotcoffees.ui.theme.LocalSpacing
import com.middleton.hotcoffees.ui.theme.MediumGray65
import com.middleton.hotcoffees.ui.theme.TextWhite

@Composable
fun CoffeeDetailsScreen(viewModel: CoffeeDetailsViewModel = hiltViewModel()) {
    when (val state = viewModel.state.collectAsStateWithLifecycle().value) {
        is CoffeeDetailsState.Success -> CoffeeDetailsContent(state) {
            viewModel.emitAction(
                CoffeeDetailsAction.OnLikedChanged(it)
            )
        }
        CoffeeDetailsState.Loading -> LoadingScreen()
    }
}

@Composable
fun CoffeeDetailsContent(
    state: CoffeeDetailsState.Success,
    onLikeCheckedChange: (Boolean) -> Unit
) {
    val isLiked = remember { mutableStateOf(state.coffee.liked) }
    val spacing = LocalSpacing.current
    Column {
        Card(modifier = Modifier.heightIn(max = 400.dp)) {
            Box(Modifier) {
                val placeholder = painterResource(R.drawable.placeholder)
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = state.coffee.imageUrl,
                    contentDescription = null,
                    error = placeholder,
                    contentScale = Crop
                )

                Row(
                    modifier = Modifier
                        .background(MediumGray65)
                        .align(Alignment.TopCenter),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = state.coffee.title,
                        style = MaterialTheme.typography.h2,
                        color = TextWhite,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f).padding(start = spacing.spaceSmall)
                    )
                    IconToggleButton(
                        onCheckedChange = {
                            isLiked.value = it
                            onLikeCheckedChange(it)
                        },
                        checked = isLiked.value
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = null,
                            tint = if (isLiked.value) Color.Red else Color.White
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(spacing.spaceMedium))

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.spaceMedium)) {
            Text(text = state.coffee.description, style = MaterialTheme.typography.body1)

            Spacer(modifier = Modifier.height(spacing.spaceSmall))

            Text(
                text = stringResource(R.string.ingredients) + state.coffee.ingredients.joinToString(),
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            TextButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { /* handle review */ }) {
                Icon(
                    Icons.Filled.Create,
                    modifier = Modifier.padding(end = spacing.spaceSmall),
                    contentDescription = null
                )
                Text(text = stringResource(R.string.review), style = MaterialTheme.typography.h6)
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
            Modifier.size(48.dp),
            color = Color.Black
        )
    }
}