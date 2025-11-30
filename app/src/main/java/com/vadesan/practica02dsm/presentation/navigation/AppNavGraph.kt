package com.vadesan.practica02dsm.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vadesan.practica02dsm.presentation.deckofcardsapi.GameScreen
import com.vadesan.practica02dsm.presentation.home.HomeScreen

@Composable
fun AppNavGraph(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home")
    {
        composable(route = "home") {
            DrawerScaffold(navController) {
                HomeScreen(navController)
            }
        }
        composable(route = "game") {
            DrawerScaffold(navController) {
                GameScreen()
            }
        }
    }
}