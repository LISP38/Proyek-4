package com.example.pertemuan1.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pertemuan1.viewmodel.DataViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pertemuan1.ui.theme.BottomBar
import androidx.compose.ui.Modifier

@Composable
fun AppNavHost(viewModel: DataViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != null) {
                BottomBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "list",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("form") {
                DataEntryScreen(navController = navController, viewModel = viewModel)
            }
//            composable("profile") {
//                ProfileScreen(navController = navController)
//            }
            composable("list") {
                DataListScreen(navController = navController, viewModel = viewModel)
            }
            composable(
                route = "edit/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                EditScreen(navController = navController, viewModel = viewModel, dataId = id)
            }
            composable("delete/{dataId}") { backStackEntry ->
                val dataId = backStackEntry.arguments?.getString("dataId")?.toIntOrNull() ?: 0
                DeleteScreen(navController, viewModel, dataId)
            }
        }
    }
}
