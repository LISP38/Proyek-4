package com.example.pertemuan1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan1.ui.AppNavHost
import com.example.pertemuan1.ui.theme.HanyarunrunTheme
import com.example.pertemuan1.viewmodel.DataViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HanyarunrunTheme {
                // Inisialisasi ViewModel
                val dataViewModel: DataViewModel = viewModel()
                // Menampilkan Navigation Host
                AppNavHost(viewModel = dataViewModel)
            }
        }
    }
}
