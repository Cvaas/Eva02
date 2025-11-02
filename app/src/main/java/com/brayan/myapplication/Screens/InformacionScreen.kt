package com.brayan.myapplication.ProbandoKotlin.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformacionScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToHistory: () -> Unit,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    themeColors: ThemeColors
) {
    var userName by remember { mutableStateOf("Carlos Pérez") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Perfil",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
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

                Spacer(modifier = Modifier.height(40.dp))

                // Sección de Ajustes
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = horizontalPadding)
                ) {
                    Text(
                        text = "Ajustes",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = themeColors.textPrimary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Selector de tema
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Tema",
                            fontSize = 16.sp,
                            color = themeColors.textPrimary
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ThemeOption(
                                text = "Claro",
                                isSelected = !isDarkTheme,
                                onClick = { onThemeChange(false) },
                                themeColors = themeColors
                            )
                            ThemeOption(
                                text = "Oscuro",
                                isSelected = isDarkTheme,
                                onClick = { onThemeChange(true) },
                                themeColors = themeColors
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ThemeOption(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    themeColors: ThemeColors
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = themeColors.iconTint,
                unselectedColor = themeColors.divider
            )
        )
        Text(
            text = text,
            fontSize = 16.sp,
            color = themeColors.textPrimary
        )
    }
}