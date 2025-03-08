package com.example.pertemuan1.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan1.viewmodel.JabarViewModel

@Composable
fun JabarScreen(
    viewModel: JabarViewModel = viewModel()
) {
    // Ambil data sekolah dari ViewModel
    val dataSekolah by viewModel.dataSekolah.observeAsState(emptyList())
    val isLoading by viewModel.loading.observeAsState(false)
    val error by viewModel.error.observeAsState("")

    // Ambil data sekolah ketika screen pertama kali diluncurkan
    LaunchedEffect(Unit) {
        viewModel.fetchDataSekolah()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Rata-Rata Lama Sekolah Berdasarkan Kabupaten/Kota",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Menampilkan indikator loading jika data sedang diambil
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else if (error.isNotEmpty()) {
            // Menampilkan error jika terjadi kegagalan saat memuat data
            Text(
                text = error,
                color = Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            // Menampilkan daftar data sekolah
            LazyColumn {
                items(dataSekolah) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "${item.nama_kabupaten_kota}, ${item.nama_provinsi}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Tahun: ${item.tahun}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Rata-rata Lama Sekolah: ${item.rata_rata_lama_sekolah} ${item.satuan}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Kode Kabupaten/Kota: ${item.kode_kabupaten_kota}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}
