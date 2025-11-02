package com.brayan.myapplication.ProbandoKotlin.Screens

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brayan.myapplication.ProbandoKotlin.Composables.BottomNavigationBar
import com.brayan.myapplication.ProbandoKotlin.ThemeColors

@Composable
fun PrincipalScreen(
    onNavigateToHistory: () -> Unit,
    onNavigateToProfile: () -> Unit,
    themeColors: ThemeColors
) {
    var lockStatus by remember { mutableStateOf("Abierta") }
    var isLocked by remember { mutableStateOf(false) }

    // Función para cambiar el estado de la cerradura
    fun toggleLock() {
        isLocked = !isLocked
        lockStatus = if (isLocked) "Cerrada" else "Abierta"
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedItem = 0,
                onHomeClick = { },
                onHistoryClick = onNavigateToHistory,
                onProfileClick = onNavigateToProfile,
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
            val cardPadding = if (maxWidth < 600.dp) 16.dp else 32.dp
            val iconSize = if (maxWidth < 600.dp) 60.dp else 80.dp

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Card con el estado de la cerradura
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = cardPadding, vertical = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = themeColors.cardBackground
                    ),
                    elevation = CardDefaults.cardElevation(4.dp),
                    onClick = { toggleLock() }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(cardPadding),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Ícono de candado
                        Surface(
                            modifier = Modifier.size(iconSize),
                            shape = CircleShape,
                            color = themeColors.iconBackground
                        ) {
                            Icon(
                                imageVector = if (isLocked) Icons.Default.Lock else Icons.Default.LockOpen,
                                contentDescription = "Lock Status",
                                modifier = Modifier
                                    .padding(20.dp)
                                    .size(40.dp),
                                tint = themeColors.iconTint
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Título
                        Text(
                            text = "CERRADURA 1",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = themeColors.textPrimary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Estado
                        Text(
                            text = lockStatus,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Normal,
                            color = if (isLocked) themeColors.textPrimary else themeColors.primary
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Ubicación
                        Text(
                            text = "Ubicación: Entrada principal",
                            fontSize = 14.sp,
                            color = themeColors.textSecondary
                        )
                    }
                }
            }
        }
    }
}