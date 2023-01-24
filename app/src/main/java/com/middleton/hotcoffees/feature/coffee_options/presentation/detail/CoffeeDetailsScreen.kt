package com.middleton.hotcoffees.feature.coffee_options.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
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
import com.middleton.hotcoffees.feature.coffee_options.domain.model.Coffee
import com.middleton.hotcoffees.ui.theme.LocalSpacing
import com.middleton.hotcoffees.ui.theme.MediumGray65
import com.middleton.hotcoffees.ui.theme.TextWhite

@Composable
fun CoffeeDetailsScreen(
    viewModel: CoffeeDetailsViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onReviewClicked: (Int) -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    Box {
        state.coffee?.let { coffee ->
            CoffeeDetailsContent(coffee, onLikeCheckedChange = {
                viewModel.emitAction(
                    CoffeeDetailsAction.OnLikedChanged(it)
                )
            }, onNavigateUp = onNavigateUp, onReviewClicked = { onReviewClicked(coffee.id) })
        }
    }
}

@Composable
fun CoffeeDetailsContent(
    coffee: Coffee,
    onLikeCheckedChange: (Boolean) -> Unit,
    onNavigateUp: () -> Unit,
    onReviewClicked: () -> Unit
) {
    val spacing = LocalSpacing.current
    var isLoading by remember { mutableStateOf(true) }

    Column {
        Box(modifier = Modifier.heightIn(max = 400.dp)) {
            val placeholder = painterResource(R.drawable.placeholder)
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = coffee.imageUrl,
                contentDescription = null,
                onSuccess = {
                    isLoading = false
                },
                onError = {
                    isLoading = false
                },
                error = placeholder,
                contentScale = Crop
            )

            CoffeeTopAppBar(
                title = coffee.title,
                liked = coffee.liked,
                onNavigateUp = onNavigateUp,
                onLikeCheckedChange = {liked -> onLikeCheckedChange(liked)}
            )

            if (isLoading) {
                CircularProgressIndicator(
                    Modifier
                        .size(48.dp)
                        .align(Alignment.Center),
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(spacing.spaceSmall))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.spaceMedium)
        ) {
            Text(text = coffee.description, style = MaterialTheme.typography.body1)

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.padding(end = spacing.spaceExtraSmall),
                    text = stringResource(R.string.ingredients),
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold

                )

                Text(
                    text = coffee.ingredients.joinToString(),
                    style = MaterialTheme.typography.body1
                )
            }


            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            WriteReviewButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = onReviewClicked
            )
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
        IconButton(modifier = Modifier.padding(end = 10.dp, start = 5.dp), onClick = onNavigateUp) {
            Icon(Icons.Filled.ArrowBack, null, tint = Color.White)
        }
        Text(
            text = title,
            style = MaterialTheme.typography.h3,
            color = TextWhite,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
        LikeToggleButton(liked = liked, onLikeCheckedChange = { liked ->
            onLikeCheckedChange(liked)
        })
    }
}

@Composable
fun LikeToggleButton(
    modifier: Modifier = Modifier,
    liked: Boolean,
    onLikeCheckedChange: (Boolean) -> Unit
) {
    IconToggleButton(
        modifier = modifier,
        onCheckedChange = { checked ->
            onLikeCheckedChange(checked)
        },
        checked = liked
    ) {
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = null,
            tint = if (liked) Color.Red else Color.White
        )
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
        Text(stringResource(R.string.write_review), style = textStyle, color = textColor)
    }
}