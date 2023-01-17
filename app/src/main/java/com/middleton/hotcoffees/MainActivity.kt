package com.middleton.hotcoffees

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.middleton.hotcoffees.domain.model.Coffee
import com.middleton.hotcoffees.navigation.Route
import com.middleton.hotcoffees.presentation.CoffeeOptionsScreen
import com.middleton.hotcoffees.ui.theme.HotCoffeesTheme
import dagger.hilt.android.AndroidEntryPoint

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
                ) { offsets ->
                    NavHost(
                        navController = navController,
                        startDestination = Route.COFFEE_OPTIONS
                    ) {
                        composable(Route.COFFEE_OPTIONS) {
                            CoffeeOptionsScreen(navigateToDetail = { coffeeId ->
                                navController.navigate(Route.COFFEE_DETAIL + "/$coffeeId")
                            })
                        }

                        composable(
                            Route.COFFEE_DETAIL + "/{$COFFEE_ID_KEY}",
                            arguments = listOf(navArgument(COFFEE_ID_KEY) { type = NavType.IntType })
                        ) { backStackEntry ->
                            val bse = backStackEntry.arguments?.getInt(COFFEE_ID_KEY)
                        }
                    }

                }
            }
        }
    }
}

const val COFFEE_ID_KEY = "COFFEE_ID_KEY"

