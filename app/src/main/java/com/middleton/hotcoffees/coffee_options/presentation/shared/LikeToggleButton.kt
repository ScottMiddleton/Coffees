package com.middleton.hotcoffees.coffee_options.presentation.shared

import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LikeToggleButton(modifier: Modifier = Modifier, liked: Boolean, onLikeCheckedChange: (Boolean) -> Unit) {
    val isLiked = remember { mutableStateOf(liked) }
    IconToggleButton(
        modifier = modifier,
        onCheckedChange = { checked ->
            isLiked.value = checked
            onLikeCheckedChange(checked)
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