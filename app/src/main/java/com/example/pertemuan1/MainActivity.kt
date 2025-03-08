package com.example.pertemuan1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan1.ui.AppNavHost
import com.example.pertemuan1.ui.theme.Pertemuan1Theme
import com.example.pertemuan1.viewmodel.DataViewModel
import androidx.navigation.compose.rememberNavController
import com.example.pertemuan1.viewmodel.ProfileViewModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import com.example.pertemuan1.ui.JabarScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pertemuan1Theme {
                // Inisialisasi NavController
                val navController = rememberNavController()
                // Inisialisasi ViewModel
                val dataViewModel: DataViewModel = viewModel()
                val profileViewModel: ProfileViewModel = viewModel()

                JabarScreen()

                // Menampilkan Navigation Host dengan parameter yang benar
                AppNavHost(
                    navController = navController,
                    viewModel = dataViewModel,
                    profileViewModel = profileViewModel
                )
            }
        }
    }
}