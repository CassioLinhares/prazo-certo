package br.com.fiap.prazocerto.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.prazocerto.screen.HomeScreen
import br.com.fiap.prazocerto.screen.LoginScreen
import br.com.fiap.prazocerto.screen.SignupScreen

@Composable
fun NavigationRoutes() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destination.LoginScreen.route
    ) {
        composable(Destination.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(Destination.SignupScreen.route) {
            SignupScreen(navController = navController)
        }
        composable(Destination.HomeScreen.route) {
            HomeScreen(navController = navController)
        }

    }
}