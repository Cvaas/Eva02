package com.brayan.myapplication.ProbandoKotlin.Screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brayan.myapplication.ProbandoKotlin.Composables.BottomNavigationBar
import com.brayan.myapplication.ProbandoKotlin.ThemeColors
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private const val TAG = "InformacionScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformacionScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onLogout: () -> Unit,
    themeColors: ThemeColors
) {
    val context = LocalContext.current
    val auth = remember { Firebase.auth }
    val currentUser = auth.currentUser

    // Obtener el nombre del usuario desde Firebase
    val userName = currentUser?.displayName ?: currentUser?.email?.substringBefore("@") ?: "Usuario"
    val userEmail = currentUser?.email ?: ""

    var showLogoutDialog by remember { mutableStateOf(false) }

    // Diálogo de confirmación para cerrar sesión
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = {
                Text(
                    text = "Cerrar sesión",
                    fontWeight = FontWeight.Bold,
                    color = themeColors.textPrimary
                )
            },
            text = {
                Text(
                    text = "¿Estás seguro de que deseas cerrar sesión?",
                    color = themeColors.textSecondary
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog = false
                        Log.d(TAG, "User logging out: ${currentUser?.email}")

                        // Cerrar sesión de Firebase
                        auth.signOut()

                        Toast.makeText(
                            context,
                            "Sesión cerrada exitosamente",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Navegar a la pantalla de login
                        onLogout()
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = themeColors.primary
                    )
                ) {
                    Text("Cerrar sesión")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showLogoutDialog = false },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = themeColors.textSecondary
                    )
                ) {
                    Text("Cancelar")
                }
            },
            containerColor = themeColors.cardBackground
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Perfil",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = themeColors.topAppBarBackground,
                    titleContentColor = themeColors.textPrimary
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedItem = 2,
                onHomeClick = onNavigateToHome,
                onHistoryClick = onNavigateToHistory,
                onProfileClick = { },
                themeColors = themeColors
            )
        }
    ) { paddingValues ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(themeColors.background)
        ) {
            val avatarSize = if (maxWidth < 600.dp) 100.dp else 120.dp
            val horizontalPadding = if (maxWidth < 600.dp) 16.dp else 24.dp

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                // Avatar del usuario
                Surface(
                    modifier = Modifier.size(avatarSize),
                    shape = CircleShape,
                    color = themeColors.iconBackground
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "User Avatar",
                        modifier = Modifier
                            .padding(30.dp)
                            .size(avatarSize * 0.5f),
                        tint = themeColors.iconTint
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Nombre del usuario
                Text(
                    text = userName,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = themeColors.textPrimary
                )

                // Email del usuario
                if (userEmail.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = userEmail,
                        fontSize = 14.sp,
                        color = themeColors.textSecondary
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Opciones de la cuenta
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = horizontalPadding),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Botón de Configuración
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = themeColors.cardBackground
                        ),
                        elevation = CardDefaults.cardElevation(2.dp),
                        onClick = onNavigateToSettings
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    modifier = Modifier.size(48.dp),
                                    shape = CircleShape,
                                    color = themeColors.iconBackground
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = "Configuración",
                                        modifier = Modifier.padding(12.dp),
                                        tint = themeColors.iconTint
                                    )
                                }

                                Column {
                                    Text(
                                        text = "Configuración",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = themeColors.textPrimary
                                    )
                                    Text(
                                        text = "Apariencia y preferencias",
                                        fontSize = 14.sp,
                                        color = themeColors.textSecondary
                                    )
                                }
                            }

                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = "Ir",
                                tint = themeColors.textSecondary
                            )
                        }
                    }

                    // Botón de Cerrar Sesión
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = themeColors.cardBackground
                        ),
                        elevation = CardDefaults.cardElevation(2.dp),
                        onClick = { showLogoutDialog = true }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    modifier = Modifier.size(48.dp),
                                    shape = CircleShape,
                                    color = Color(0xFFFFEBEE)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ExitToApp,
                                        contentDescription = "Cerrar sesión",
                                        modifier = Modifier.padding(12.dp),
                                        tint = Color(0xFFD32F2F)
                                    )
                                }

                                Column {
                                    Text(
                                        text = "Cerrar sesión",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color(0xFFD32F2F)
                                    )
                                    Text(
                                        text = "Salir de tu cuenta",
                                        fontSize = 14.sp,
                                        color = themeColors.textSecondary
                                    )
                                }
                            }

                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = "Ir",
                                tint = themeColors.textSecondary
                            )
                        }
                    }
                }
            }
        }
    }
}