package com.example.pertemuan1.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.example.pertemuan1.ui.themenavbar.Pertemuan1Theme

private val LightColors = lightColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor
)

private val DarkColors = darkColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor
)

@Composable
fun Pertemuan1Theme(
    darkTheme: Boolean = false, // Ubah sesuai preferensi atau logika sistem
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}
