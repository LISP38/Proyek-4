package com.example.pertemuan1.ui

import android.util.Log
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
import com.example.pertemuan1.viewmodel.ProfileViewModel
import com.example.pertemuan1.ui.theme.BottomBar
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pertemuan1.SplashScreen

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
            if (currentRoute != null && currentRoute != "splash") {
                BottomBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "splash",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("splash") {
                SplashScreen (navController)
            }

            composable("form") {
                DataEntryScreen(navController = navController, viewModel = viewModel)
            }

            composable("list") {
                DataListScreen(navController = navController, viewModel = viewModel)
            }

            composable("jabar"){
                JabarScreen()
            }


            composable("profile") {
                ProfileScreen(viewModel = profileViewModel)
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
