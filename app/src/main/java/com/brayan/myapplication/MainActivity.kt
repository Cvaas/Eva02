package com.brayan.myapplication // Paquete principal correcto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
// --- SOLUCIÓN DE ERRORES: IMPORTS NECESARIOS ---
import com.brayan.myapplication.ProbandoKotlin.Screens.LoginScreen
import com.brayan.myapplication.ProbandoKotlin.Screens.RegisterScreen
import com.brayan.myapplication.ProbandoKotlin.Screens.TerceraScreen
import com.brayan.myapplication.ProbandoKotlin.Screens.PrincipalScreen
import com.brayan.myapplication.ProbandoKotlin.Screens.SegundaScreen
import com.brayan.myapplication.ProbandoKotlin.Screens.InformacionScreen
import com.brayan.myapplication.ThemeColors // Importa ThemeColors del paquete principal
import com.brayan.myapplication.ui.theme.MyApplicationTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
// --------------------------------------------------

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        auth = Firebase.auth

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    MainAppNavigation(navController = navController) // Se quita el auth para simplificar
                }
            }
        }
    }
}

@Composable
fun MainAppNavigation(navController: NavHostController) {

    // El objeto ThemeColors soluciona el error 'No value passed for parameter themeColors'
    val themeColors = ThemeColors()

    // Ahora siempre empezamos en la pantalla inicial (TerceraScreen)
    val startDestination = "tercera"

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        // --- 1. RUTA: SPLASH (TerceraScreen) ---
        composable(route = "tercera") {
            TerceraScreen(
                onLoginSuccess = { navController.navigate("login") },
                themeColors = themeColors
            )
        }

        // --- 2. RUTA: LOGIN (Tu pantalla) ---
        composable(route = "login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("principal") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }

        // --- 3. RUTA: REGISTRO (Tu pantalla) ---
        composable(route = "register") {
            RegisterScreen(
                onRegisterSuccess = { navController.popBackStack() }
            )
        }

        // --- 4. RUTA: HOME (PrincipalScreen) ---
        composable(route = "principal") {
            PrincipalScreen(
                onNavigateToHistory = { navController.navigate("historial") },
                onNavigateToProfile = { navController.navigate("perfil") },
                themeColors = themeColors
            )
        }

        // --- 5. RUTA: HISTORIAL (SegundaScreen) ---
        composable(route = "historial") {
            SegundaScreen(
                onNavigateToHome = { navController.navigate("principal") },
                onNavigateToProfile = { navController.navigate("perfil") },
                themeColors = themeColors
            )
        }

        // --- 6. RUTA: PERFIL (InformacionScreen) ---
        composable(route = "perfil") {
            InformacionScreen(
                onNavigateToHome = { navController.navigate("principal") },
                onNavigateToHistory = { navController.navigate("historial") },
                isDarkTheme = false,
                onThemeChange = { /* Lógica */ },
                themeColors = themeColors
            )
        }
    }
}