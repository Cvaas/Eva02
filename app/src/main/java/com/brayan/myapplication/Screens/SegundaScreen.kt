package com.brayan.myapplication.ProbandoKotlin.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

data class LockEvent(
    val status: String,
    val time: String,
    val isLocked: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SegundaScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToProfile: () -> Unit,
    themeColors: ThemeColors
) {
    var events by remember {
        mutableStateOf(
            listOf(
                LockEvent("Cerrada", "", true),
                LockEvent("Abierta", "9:35", false),
                LockEvent("Ayer", "8:15", false),
                LockEvent("20 de abril", "20 de", false)
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Historial",
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
                selectedItem = 1,
                onHomeClick = onNavigateToHome,
                onHistoryClick = { },
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
            val itemPadding = if (maxWidth < 600.dp) 12.dp else 16.dp

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(itemPadding),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(events) { event ->
                    LockEventItem(event, themeColors)
                }
            }
        }
    }
}

@Composable
fun LockEventItem(event: LockEvent, themeColors: ThemeColors) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = themeColors.cardBackground
        ),
        elevation = CardDefaults.cardElevation(2.dp)
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
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Ícono de estado
                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = CircleShape,
                    color = themeColors.iconBackground
                ) {
                    Icon(
                        imageVector = when {
                            event.status == "Cerrada" -> Icons.Default.Lock
                            event.status.contains("20 de") -> Icons.Default.Check
                            else -> Icons.Default.LockOpen
                        },
                        contentDescription = "Lock Status",
                        modifier = Modifier.padding(12.dp),
                        tint = themeColors.iconTint
                    )
                }

                // Información del evento
                Column {
                    Text(
                        text = "CERRADURA 1",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = themeColors.textPrimary
                    )
                    Text(
                        text = event.status,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = if (event.status == "Cerrada") themeColors.textPrimary else themeColors.primary
                    )
                }
            }

            // Hora
            if (event.time.isNotEmpty()) {
                Text(
                    text = event.time,
                    fontSize = 14.sp,
                    color = themeColors.textSecondary
                )
            }
        }
    }
}