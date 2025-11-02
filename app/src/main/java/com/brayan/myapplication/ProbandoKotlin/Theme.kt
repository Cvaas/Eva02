package com.brayan.myapplication.ProbandoKotlin

import androidx.compose.ui.graphics.Color

object AppColors {
    // Tema Claro
    object Light {
        val background = Color(0xFFF5F5F5)
        val surface = Color.White
        val primary = Color(0xFF2196F3)
        val onPrimary = Color.White
        val textPrimary = Color(0xFF37474F)
        val textSecondary = Color(0xFF78909C)
        val iconTint = Color(0xFF546E7A)
        val iconBackground = Color(0xFFECEFF1)
        val divider = Color(0xFFB0BEC5)
        val cardBackground = Color.White
        val navigationBackground = Color.White
        val topAppBarBackground = Color.White
    }

    // Tema Oscuro
    object Dark {
        val background = Color(0xFF121212)
        val surface = Color(0xFF1E1E1E)
        val primary = Color(0xFF2196F3)
        val onPrimary = Color.White
        val textPrimary = Color(0xFFE0E0E0)
        val textSecondary = Color(0xFFB0B0B0)
        val iconTint = Color(0xFFB0BEC5)
        val iconBackground = Color(0xFF2C2C2C)
        val divider = Color(0xFF424242)
        val cardBackground = Color(0xFF252525)
        val navigationBackground = Color(0xFF1E1E1E)
        val topAppBarBackground = Color(0xFF1E1E1E)
    }
}

data class ThemeColors(
    val background: Color,
    val surface: Color,
    val primary: Color,
    val onPrimary: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val iconTint: Color,
    val iconBackground: Color,
    val divider: Color,
    val cardBackground: Color,
    val navigationBackground: Color,
    val topAppBarBackground: Color
)

fun getThemeColors(isDarkTheme: Boolean): ThemeColors {
    return if (isDarkTheme) {
        ThemeColors(
            background = AppColors.Dark.background,
            surface = AppColors.Dark.surface,
            primary = AppColors.Dark.primary,
            onPrimary = AppColors.Dark.onPrimary,
            textPrimary = AppColors.Dark.textPrimary,
            textSecondary = AppColors.Dark.textSecondary,
            iconTint = AppColors.Dark.iconTint,
            iconBackground = AppColors.Dark.iconBackground,
            divider = AppColors.Dark.divider,
            cardBackground = AppColors.Dark.cardBackground,
            navigationBackground = AppColors.Dark.navigationBackground,
            topAppBarBackground = AppColors.Dark.topAppBarBackground
        )
    } else {
        ThemeColors(
            background = AppColors.Light.background,
            surface = AppColors.Light.surface,
            primary = AppColors.Light.primary,
            onPrimary = AppColors.Light.onPrimary,
            textPrimary = AppColors.Light.textPrimary,
            textSecondary = AppColors.Light.textSecondary,
            iconTint = AppColors.Light.iconTint,
            iconBackground = AppColors.Light.iconBackground,
            divider = AppColors.Light.divider,
            cardBackground = AppColors.Light.cardBackground,
            navigationBackground = AppColors.Light.navigationBackground,
            topAppBarBackground = AppColors.Light.topAppBarBackground
        )
    }
}