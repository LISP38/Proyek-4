package com.example.pertemuan1.ui

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pertemuan1.viewmodel.DataViewModel
import androidx.navigation.NavHostController
import com.example.pertemuan1.SplashScreen
import com.example.pertemuan1.viewmodel.ProfileViewModel
import com.example.pertemuan1.ui.theme.BottomBar
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController


@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: DataViewModel,
    profileViewModel: ProfileViewModel
) {
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

            composable("list") {
                DataListScreen(navController = navController, viewModel = viewModel)
            }

            composable("profile") {
                ProfileScreen(navController = navController, viewModel = profileViewModel)
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
