package com.brayan.myapplication.ProbandoKotlin.Screens

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brayan.myapplication.ProbandoKotlin.ThemeColors

@Composable
fun TerceraScreen(
    onLoginSuccess: () -> Unit,
    themeColors: ThemeColors
) {
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
                text = "ACCESSO",
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

            // Botón de inicio de sesión
            Button(
                onClick = onLoginSuccess,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontalPadding)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = themeColors.primary
                ),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    text = "Iniciar sesión",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = themeColors.onPrimary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de Google
            OutlinedButton(
                onClick = { /* Implementar login con Google */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontalPadding)
                    .height(56.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = themeColors.cardBackground,
                    contentColor = themeColors.textPrimary
                ),
                shape = RoundedCornerShape(28.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Aquí iría el ícono de Google
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