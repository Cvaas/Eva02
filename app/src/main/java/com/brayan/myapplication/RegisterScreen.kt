// Archivo: RegisterScreen.kt
package com.brayan.myapplication // Asegúrate que el paquete sea el correcto

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * Esta es la función Composable que define tu pantalla de registro.
 * Recibe un parámetro: una función lambda que se llamará cuando
 * el registro sea exitoso, para poder navegar a otra pantalla.
 */
@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit // "Orden" para navegar a la siguiente pantalla
) {
    // --- Variables de Estado ---
    // Así es como Compose "recuerda" lo que el usuario escribe.
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // --- Instancias ---
    // Obtenemos la instancia de Firebase Auth
    val auth: FirebaseAuth = Firebase.auth
    // Obtenemos el contexto actual (necesario para mostrar Toasts)
    val context = LocalContext.current

    // --- UI Layout ---
    // Usamos una Columna para apilar elementos verticalmente
    Column(
        modifier = Modifier
            .fillMaxSize() // Ocupa toda la pantalla
            .padding(16.dp), // Añade un poco de espacio en los bordes
        verticalArrangement = Arrangement.Center, // Centra todo verticalmente
        horizontalAlignment = Alignment.CenterHorizontally // Centra todo horizontalmente
    ) {

        Text(text = "Crear una Cuenta", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        // Campo de texto para el Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de texto para la Contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(), // Oculta la contraseña
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de Registro
        Button(
            onClick = {
                // Validación simple
                if (email.isBlank() || password.isBlank()) {
                    Toast.makeText(context, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
                } else {
                    // --- LÓGICA DE FIREBASE ---
                    Log.d("FIREBASE_AUTH", "Iniciando registro con: $email")
                    auth.createUserWithEmailAndPassword(email.trim(), password.trim())
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Registro exitoso
                                Log.d("FIREBASE_AUTH", "createUserWithEmail:success")
                                Toast.makeText(context, "Registro exitoso.", Toast.LENGTH_SHORT).show()

                                // Ejecuta la "orden" para navegar a la siguiente pantalla
                                onRegisterSuccess()

                            } else {
                                // Si el registro falla, muestra el error
                                Log.w("FIREBASE_AUTH", "createUserWithEmail:failure", task.exception)
                                Toast.makeText(context, "Falló el registro: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                }
            }
        ) {
            Text("Registrarse")
        }
    }
}