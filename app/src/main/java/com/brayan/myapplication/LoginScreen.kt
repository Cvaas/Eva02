// Archivo: LoginScreen.kt
package com.brayan.myapplication // Asegúrate que el paquete sea el correcto

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.TextButton
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.brayan.myapplication.R

/**
 * Pantalla de Login con Email/Contraseña y Google.
 * @param onLoginSuccess Se llama cuando el login (cualquiera) es exitoso.
 * @param onNavigateToRegister Se llama cuando el usuario presiona "Crear cuenta".
 */
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    // --- Variables de Estado ---
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // --- Instancias ---
    val auth: FirebaseAuth = Firebase.auth
    val context = LocalContext.current
    val activity = LocalContext.current as Activity

    // --- Configuración de Google Sign-In ---
    // Necesitamos el cliente de Google para iniciar el popup de login
    val googleSignInClient = remember {
        getGoogleSignInClient(activity)
    }

    // --- Launcher para el resultado de Google ---
    // Esto maneja lo que pasa cuando el usuario elige su cuenta de Google
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                // Google Sign-In fue exitoso, ahora autentica con Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("FIREBASE_AUTH", "Firebase Auth con Google: ${account.id}")
                firebaseAuthWithGoogle(account.idToken!!, auth, onLoginSuccess) { exception ->
                    // Manejo de error específico de Firebase
                    Log.w("FIREBASE_AUTH", "Error en firebaseAuthWithGoogle", exception)
                    Toast.makeText(context, "Falló la autenticación con Firebase: ${exception.message}", Toast.LENGTH_LONG).show()
                }
            } catch (e: ApiException) {
                // Falló el Google Sign-In (ej. el usuario cerró el popup)
                Log.w("FIREBASE_AUTH", "Google sign in failed", e)
                Toast.makeText(context, "Falló el inicio de sesión con Google: ${e.message}", Toast.LENGTH_LONG).show()
            }
        } else {
            // El intent no fue exitoso (ej. el usuario canceló)
            Log.w("FIREBASE_AUTH", "Google Sign-In cancelado (resultCode: ${result.resultCode})")
            Toast.makeText(context, "Inicio de sesión con Google cancelado.", Toast.LENGTH_SHORT).show()
        }
    }

    // --- UI Layout ---
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Iniciar Sesión", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        // --- Login con Email y Contraseña ---
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    Toast.makeText(context, "Completa todos los campos.", Toast.LENGTH_SHORT).show()
                } else {
                    // --- LÓGICA DE FIREBASE (Email/Pass) ---
                    auth.signInWithEmailAndPassword(email.trim(), password.trim())
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("FIREBASE_AUTH", "signInWithEmail:success")
                                Toast.makeText(context, "Inicio de sesión exitoso.", Toast.LENGTH_SHORT).show()
                                onLoginSuccess()
                            } else {
                                Log.w("FIREBASE_AUTH", "signInWithEmail:failure", task.exception)
                                Toast.makeText(context, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                }
            }
        ) {
            Text("Entrar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Botón de Login con Google ---
        Button(
            onClick = {
                // Inicia el flujo de Google Sign-In
                val signInIntent = googleSignInClient.signInIntent
                googleSignInLauncher.launch(signInIntent)
            }
        ) {
            Text("Entrar con Google")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Botón para navegar a Registro ---
        TextButton(onClick = onNavigateToRegister) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}

// --- Funciones de Ayuda para Google (fuera del @Composable) ---

/**
 * Obtiene el cliente de Google Sign-In.
 */
private fun getGoogleSignInClient(activity: Activity): GoogleSignInClient {
    // Configura Google Sign-In para pedir el ID Token del usuario.
    // R.string.default_web_client_id es generado automáticamente por el plugin de google-services
    // a partir de tu archivo google-services.json.
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(activity.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    return GoogleSignIn.getClient(activity, gso)
}

/**
 * Autentica al usuario en Firebase usando el token de Google.
 */
private fun firebaseAuthWithGoogle(
    idToken: String,
    auth: FirebaseAuth,
    onSuccess: () -> Unit,
    onFailure: (Exception) -> Unit
) {
    val credential = GoogleAuthProvider.getCredential(idToken, null)
    auth.signInWithCredential(credential)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Login con Google exitoso
                Log.d("FIREBASE_AUTH", "signInWithCredential:success")
                onSuccess()
            } else {
                // Falló el login con Firebase
                Log.w("FIREBASE_AUTH", "signInWithCredential:failure", task.exception)
                onFailure(task.exception ?: Exception("Error desconocido en Firebase Auth"))
            }
        }
}
