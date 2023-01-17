package com.middleton.hotcoffees.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.middleton.hotcoffees.domain.model.Coffee

@Composable
fun CoffeeOption(coffee: Coffee, onClick: () -> Unit) {
    Card(modifier = Modifier.padding(8.dp).clickable { onClick() }) {
            Text(coffee.title, style = MaterialTheme.typography.h6)
//            Text(coffee.description, style = MaterialTheme.typography.body2)
//            Text("Ingredients: ${coffee.ingredients.joinToString()}")
//            Row {
//                IconButton(onClick = { /* handle like */ }) {
//                    Icon(Icons.Filled.Favorite, contentDescription = null)
//                }
//                IconButton(onClick = { /* handle review */ }) {
//                    Icon(Icons.Filled.Create, contentDescription = null)
//                }
//            }
        }
    }

