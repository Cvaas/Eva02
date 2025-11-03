package com.brayan.myapplication.ProbandoKotlin.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brayan.myapplication.ProbandoKotlin.ThemeColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfiguracionScreen(
    onNavigateBack: () -> Unit,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    themeColors: ThemeColors
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Configuración",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = themeColors.textPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = themeColors.topAppBarBackground,
                    titleContentColor = themeColors.textPrimary
                )
            )
        }
    ) { paddingValues ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(themeColors.background)
        ) {
            val horizontalPadding = if (maxWidth < 600.dp) 16.dp else 24.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = horizontalPadding)
                    .padding(top = 24.dp)
            ) {
                Text(
                    text = "Apariencia",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = themeColors.textPrimary
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Selector de tema
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = themeColors.cardBackground
                    ),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Tema",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = themeColors.textPrimary
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            ThemeOption(
                                text = "Claro",
                                isSelected = !isDarkTheme,
                                onClick = { onThemeChange(false) },
                                themeColors = themeColors,
                                modifier = Modifier.weight(1f)
                            )
                            ThemeOption(
                                text = "Oscuro",
                                isSelected = isDarkTheme,
                                onClick = { onThemeChange(true) },
                                themeColors = themeColors,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ThemeOption(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    themeColors: ThemeColors,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
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