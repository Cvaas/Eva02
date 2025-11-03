package com.brayan.myapplication.ProbandoKotlin

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.brayan.myapplication.ProbandoKotlin.Screens.*

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Principal : Screen("principal")
    object Historia : Screen("historia")
    object Informacion : Screen("informacion")
    object Configuracion : Screen("configuracion")
}

@Composable
fun Inicial() {
    val navController = rememberNavController()

    // Estado para gestionar el tema (false = claro, true = oscuro)
    var isDarkTheme by remember { mutableStateOf(false) }
    val themeColors = getThemeColors(isDarkTheme)

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            TerceraScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Principal.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                themeColors = themeColors
            )
        }

        composable(Screen.Principal.route) {
            PrincipalScreen(
                onNavigateToHistory = {
                    navController.navigate(Screen.Historia.route)
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Informacion.route)
                },
                themeColors = themeColors
            )
        }

        composable(Screen.Historia.route) {
            SegundaScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Principal.route) {
                        popUpTo(Screen.Principal.route) { inclusive = true }
                    }
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Informacion.route)
                },
                themeColors = themeColors
            )
        }

        composable(Screen.Informacion.route) {
            InformacionScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Principal.route) {
                        popUpTo(Screen.Principal.route) { inclusive = true }
                    }
                },
                onNavigateToHistory = {
                    navController.navigate(Screen.Historia.route)
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Configuracion.route)
                },
                onLogout = {
                    // Limpiar todo el backstack y volver al login
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                themeColors = themeColors
            )
        }

        composable(Screen.Configuracion.route) {
            ConfiguracionScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                isDarkTheme = isDarkTheme,
                onThemeChange = { isDarkTheme = it },
                themeColors = themeColors
            )
        }
    }
}