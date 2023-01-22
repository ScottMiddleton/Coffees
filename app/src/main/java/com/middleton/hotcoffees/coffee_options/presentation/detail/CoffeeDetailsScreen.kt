package com.middleton.hotcoffees.coffee_options.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.middleton.hotcoffees.R
import com.middleton.hotcoffees.coffee_options.presentation.shared.LikeToggleButton
import com.middleton.hotcoffees.coffee_options.presentation.shared.LoadingScreen
import com.middleton.hotcoffees.ui.theme.LocalSpacing
import com.middleton.hotcoffees.ui.theme.MediumGray65
import com.middleton.hotcoffees.ui.theme.TextWhite

@Composable
fun CoffeeDetailsScreen(
    viewModel: CoffeeDetailsViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {
    when (val state = viewModel.state.collectAsStateWithLifecycle().value) {
        is CoffeeDetailsState.Success -> {

            CoffeeDetailsContent(state, onLikeCheckedChange = {
                viewModel.emitAction(
                    CoffeeDetailsAction.OnLikedChanged(it)
                )
            }, onNavigateUp = onNavigateUp)
        }
        CoffeeDetailsState.Loading -> LoadingScreen()
    }
}

@Composable
fun CoffeeDetailsContent(
    state: CoffeeDetailsState.Success,
    onLikeCheckedChange: (Boolean) -> Unit,
    onNavigateUp: () -> Unit
) {
    val spacing = LocalSpacing.current

    Column {
        Box(modifier = Modifier.heightIn(max = 400.dp)) {
            val placeholder = painterResource(R.drawable.placeholder)
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = state.coffee.imageUrl,
                contentDescription = null,
                placeholder = placeholder,
                error = placeholder,
                contentScale = Crop
            )

            CoffeeTopAppBar(
                title = state.coffee.title,
                liked = state.coffee.liked,
                onNavigateUp = onNavigateUp,
                onLikeCheckedChange = onLikeCheckedChange
            )
        }

        Spacer(modifier = Modifier.height(spacing.spaceSmall))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.spaceMedium)
        ) {
            Text(text = state.coffee.description, style = MaterialTheme.typography.body1)

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.padding(end = spacing.spaceExtraSmall),
                    text = stringResource(R.string.ingredients),
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold

                )

                Text(
                    text = state.coffee.ingredients.joinToString(),
                    style = MaterialTheme.typography.body1
                )
            }


            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            WriteReviewButton(modifier = Modifier.align(Alignment.CenterHorizontally)) {

            }
        }
    }
}

@Composable
private fun CoffeeTopAppBar(
    title: String,
    onNavigateUp: () -> Unit,
    onLikeCheckedChange: (Boolean) -> Unit,
    liked: Boolean
) {
    Row(
        modifier = Modifier
            .background(MediumGray65)
            .height(56.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(modifier = Modifier.padding(end = 8.dp), onClick = onNavigateUp) {
            Icon(Icons.Filled.ArrowBack, null, tint = Color.White)
        }
        Text(
            text = title,
            style = MaterialTheme.typography.h2,
            color = TextWhite,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
        LikeToggleButton(liked = liked, onLikeCheckedChange = onLikeCheckedChange)
    }
}

@Composable
fun WriteReviewButton(modifier: Modifier, onClick: () -> Unit) {
    val textStyle = MaterialTheme.typography.button
    val textColor = MaterialTheme.colors.onSecondary

    Button(
        onClick = onClick,
        modifier = modifier.padding(16.dp)
    ) {
        Text("Write Review", style = textStyle, color = textColor)
    }
}