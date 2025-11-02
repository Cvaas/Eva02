package com.brayan.myapplication.ProbandoKotlin.Composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.brayan.myapplication.ProbandoKotlin.ThemeColors

@Composable
fun BottomNavigationBar(
    selectedItem: Int,
    onHomeClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onProfileClick: () -> Unit,
    themeColors: ThemeColors
) {
    NavigationBar(
        containerColor = themeColors.navigationBackground,
        contentColor = themeColors.iconTint
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home"
                )
            },
            selected = selectedItem == 0,
            onClick = onHomeClick,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = themeColors.iconTint,
                unselectedIconColor = themeColors.divider,
                indicatorColor = themeColors.iconBackground
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.History,
                    contentDescription = "History"
                )
            },
            selected = selectedItem == 1,
            onClick = onHistoryClick,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = themeColors.iconTint,
                unselectedIconColor = themeColors.divider,
                indicatorColor = themeColors.iconBackground
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile"
                )
            },
            selected = selectedItem == 2,
            onClick = onProfileClick,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = themeColors.iconTint,
                unselectedIconColor = themeColors.divider,
                indicatorColor = themeColors.iconBackground
            )
        )
    }
}