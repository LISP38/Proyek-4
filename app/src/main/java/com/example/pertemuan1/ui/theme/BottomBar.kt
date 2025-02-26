package com.example.pertemuan1.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.Color
import com.example.pertemuan1.ui.components.Pertemuan1Button
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding


@Composable
fun BottomBar(navController: NavController) {
    NavigationBar {
        val currentRoute = navController.currentDestination?.route

        val items = listOf(
            BottomNavItem("list", Icons.Default.List, "List"),
            BottomNavItem("form", Icons.Default.Add, "Entry")
        )

        items.forEach { item ->
            val isSelected = currentRoute == item.route

            Pertemuan1Button(
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = false }
                    }
                },
                enabled = true,
                content = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (isSelected) Color.White else Color.Gray
                    )
                    if (isSelected) {
                        Text(
                            text = item.label,
                            modifier = Modifier.padding(start = 8.dp),
                            color = Color.White
                        )
                    }
                }
            )
        }
    }
}

data class BottomNavItem(val route: String, val icon: ImageVector, val label: String)
