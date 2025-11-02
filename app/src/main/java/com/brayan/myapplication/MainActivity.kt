package com.brayan.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.brayan.myapplication.ProbandoKotlin.Inicial
import com.brayan.myapplication.ui.theme.MyApplicationTheme

/**
 * MainActivity
 * Punto de entrada principal de la aplicación de Control de Acceso
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            // Aplicar el tema de la aplicación
            MyApplicationTheme {
                // Surface contenedor con el color de fondo del tema
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Lanzar la navegación principal
                    Inicial()
                }
            }
        }
    }
}