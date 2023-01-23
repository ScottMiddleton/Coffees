package com.middleton.hotcoffees

import HotCoffeesTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.middleton.hotcoffees.coffee_options.presentation.detail.CoffeeDetailsScreen
import com.middleton.hotcoffees.coffee_options.presentation.options.CoffeeOptionsScreen
import com.middleton.hotcoffees.coffee_review.presentation.CoffeeReviewScreen
import com.middleton.hotcoffees.navigation.Route
import dagger.hilt.android.AndroidEntryPoint

const val COFFEE_ID_KEY = "COFFEE_ID_KEY"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HotCoffeesTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) { padding ->
                    NavHost(
                        modifier = Modifier.padding(padding),
                        navController = navController,
                        startDestination = Route.COFFEE_OPTIONS
                    ) {
                        composable(Route.COFFEE_OPTIONS) {
                            CoffeeOptionsScreen(navigateToDetail = { coffeeId ->
                                navController.navigate(Route.COFFEE_DETAIL + "/$coffeeId")
                            }, scaffoldState = scaffoldState)
                        }

                        composable(
                            Route.COFFEE_DETAIL + "/{$COFFEE_ID_KEY}",
                            arguments = listOf(navArgument(COFFEE_ID_KEY) {
                                type = NavType.IntType
                            })
                        ) {
                            CoffeeDetailsScreen(onNavigateUp = {
                                navController.navigateUp()
                            }, onReviewClicked = { coffeeId ->
                                navController.navigate(Route.COFFEE_REVIEW + "/$coffeeId")
                            })
                        }

                        composable(
                            Route.COFFEE_REVIEW + "/{$COFFEE_ID_KEY}",
                            arguments = listOf(navArgument(COFFEE_ID_KEY) {
                                type = NavType.IntType
                            })
                        ) {
                            CoffeeReviewScreen(scaffoldState = scaffoldState, onNavigateUp = {
                                navController.navigateUp()
                            })
                        }
                    }
                }
            }
        }
    }
}



