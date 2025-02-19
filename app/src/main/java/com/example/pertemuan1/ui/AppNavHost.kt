package com.example.pertemuan1.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pertemuan1.viewmodel.DataViewModel

@Composable
fun AppNavHost(viewModel: DataViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "form") {
        composable("form") {
            DataEntryScreen(navController = navController, viewModel = viewModel)
        }
        composable("list") {
            DataListScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            route = "edit/{id}" +
                    "",
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
