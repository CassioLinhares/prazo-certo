package br.com.fiap.prazocerto.navigation

sealed class Destination(val route: String) {
    object LoginScreen : Destination("login")
    object SignupScreen : Destination("signup")
    object HomeScreen : Destination("home")
}