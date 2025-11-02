package com.brayan.myapplication.ProbandoKotlin.Screens

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brayan.myapplication.ProbandoKotlin.ThemeColors
import com.brayan.myapplication.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private const val TAG = "TerceraScreen"

@Composable
fun TerceraScreen(
    onLoginSuccess: () -> Unit,
    themeColors: ThemeColors
) {
    val context = LocalContext.current
    val auth = remember { Firebase.auth }
    var isLoading by remember { mutableStateOf(false) }
    var shouldNavigate by remember { mutableStateOf(false) }

    // Efecto para manejar la navegación cuando shouldNavigate sea true
    LaunchedEffect(shouldNavigate) {
        if (shouldNavigate) {
            Log.d(TAG, "LaunchedEffect: Navigating to main screen")
            onLoginSuccess()
            shouldNavigate = false
        }
    }

    // Configurar Google Sign-In
    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    val googleSignInClient = remember {
        GoogleSignIn.getClient(context, gso)
    }

    // Launcher para el intent de Google Sign-In
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Log.d(TAG, "Activity result received with code: ${result.resultCode}")

        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                Log.d(TAG, "Google Sign-In successful, email: ${account.email}")

                val credential = GoogleAuthProvider.getCredential(account.idToken, null)

                // Autenticar con Firebase
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { authTask ->
                        isLoading = false

                        if (authTask.isSuccessful) {
                            val user = auth.currentUser
                            Log.d(TAG, "Firebase authentication successful, user: ${user?.email}")

                            Toast.makeText(
                                context,
                                "¡Bienvenido ${user?.displayName ?: user?.email}!",
                                Toast.LENGTH_SHORT
                            ).show()

                            // Activar la navegación a través de LaunchedEffect
                            shouldNavigate = true
                        } else {
                            Log.e(TAG, "Firebase authentication failed", authTask.exception)
                            Toast.makeText(
                                context,
                                "Error al autenticar: ${authTask.exception?.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            } catch (e: ApiException) {
                Log.e(TAG, "Google Sign-In failed with code: ${e.statusCode}", e)
                isLoading = false

                val errorMessage = when (e.statusCode) {
                    10 -> "Configuración incorrecta. Verifica SHA-1 en Firebase"
                    12501 -> "Inicio de sesión cancelado"
                    else -> "Error ${e.statusCode}: ${e.message}"
                }

                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
        } else if (result.resultCode == Activity.RESULT_CANCELED) {
            Log.d(TAG, "Google Sign-In cancelled by user")
            isLoading = false
            Toast.makeText(context, "Inicio de sesión cancelado", Toast.LENGTH_SHORT).show()
        } else {
            Log.d(TAG, "Unexpected result code: ${result.resultCode}")
            isLoading = false
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(themeColors.background)
    ) {
        val iconSize = if (maxWidth < 600.dp) 100.dp else 120.dp
        val titleSize = if (maxWidth < 600.dp) 28.sp else 32.sp
        val horizontalPadding = if (maxWidth < 600.dp) 24.dp else 32.dp

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Ícono principal
            Surface(
                modifier = Modifier.size(iconSize),
                shape = CircleShape,
                color = themeColors.iconBackground
            ) {
                Icon(
                    imageVector = Icons.Default.Memory,
                    contentDescription = "Access Control",
                    modifier = Modifier
                        .padding(iconSize * 0.25f)
                        .size(iconSize * 0.5f),
                    tint = themeColors.iconTint
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Título
            Text(
                text = "CONTROL DE",
                fontSize = titleSize,
                fontWeight = FontWeight.Bold,
                color = themeColors.textPrimary
            )
            Text(
                text = "ACCESO",
                fontSize = titleSize,
                fontWeight = FontWeight.Bold,
                color = themeColors.textPrimary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Subtítulo
            Text(
                text = "Cerradura controlada",
                fontSize = 16.sp,
                color = themeColors.textSecondary
            )
            Text(
                text = "con Wemos",
                fontSize = 16.sp,
                color = themeColors.textSecondary
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Botón de inicio de sesión simple (sin autenticación)
            Button(
                onClick = {
                    Log.d(TAG, "Simple login button clicked")
                    onLoginSuccess()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontalPadding)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = themeColors.primary
                ),
                shape = RoundedCornerShape(28.dp),
                enabled = !isLoading
            ) {
                Text(
                    text = "Iniciar sesión",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = themeColors.onPrimary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de Google Sign-In (FUNCIONAL)
            OutlinedButton(
                onClick = {
                    Log.d(TAG, "Google Sign-In button clicked")
                    isLoading = true

                    // Cerrar sesión anterior para forzar selector de cuenta
                    googleSignInClient.signOut().addOnCompleteListener {
                        try {
                            val signInIntent = googleSignInClient.signInIntent
                            launcher.launch(signInIntent)
                        } catch (e: Exception) {
                            Log.e(TAG, "Error launching Google Sign-In", e)
                            isLoading = false
                            Toast.makeText(
                                context,
                                "Error: ${e.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontalPadding)
                    .height(56.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = themeColors.cardBackground,
                    contentColor = themeColors.textPrimary
                ),
                shape = RoundedCornerShape(28.dp),
                enabled = !isLoading
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = themeColors.primary
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Conectando...",
                            fontSize = 16.sp
                        )
                    } else {
                        Text(
                            text = "G",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 12.dp)
                        )
                        Text(
                            text = "Continuar con Google",
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}