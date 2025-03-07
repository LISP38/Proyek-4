package com.example.pertemuan1.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pertemuan1.data.DataEntity
import com.example.pertemuan1.viewmodel.DataViewModel

@Composable
fun DeleteScreen(
    navController: NavHostController,
    viewModel: DataViewModel,
    dataId: Int
) {
    val context = LocalContext.current
    var data by remember { mutableStateOf<DataEntity?>(null) }
    var showDialog by remember { mutableStateOf(false) } // 🛠 Perbaikan di sini!

    // Ambil data berdasarkan ID
    LaunchedEffect(dataId) {
        data = viewModel.getDataById(dataId)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Hapus Data",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            data?.let {
                Text("Apakah Anda yakin ingin menghapus data berikut?", fontWeight = FontWeight.Medium)
                Text("• Kode Provinsi: ${it.kodeProvinsi}")
                Text("• Nama Provinsi: ${it.namaProvinsi}")
                Text("• Kode Kabupaten/Kota: ${it.kodeKabupatenKota}")
                Text("• Nama Kabupaten/Kota: ${it.namaKabupatenKota}")
                Text("• Total: ${it.total}")
                Text("• Satuan: ${it.satuan}")
                Text("• Tahun: ${it.tahun}")

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        showDialog = true // ✅ Sekarang showDialog dikenali
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Hapus Data", color = MaterialTheme.colorScheme.onError)
                }

                Button(
                    onClick = { navController.popBackStack() },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Batal")
                }
            } ?: Text("Data tidak ditemukan!", fontWeight = FontWeight.Medium)
        }
    }

    // Dialog konfirmasi penghapusan
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Konfirmasi Hapus") },
            text = { Text("Apakah Anda yakin ingin menghapus data ini? Tindakan ini tidak dapat dibatalkan.") },
            confirmButton = {
                Button(
                    onClick = {
                        data?.let {
                            viewModel.deleteDataById(dataId)
                            Toast.makeText(context, "Data berhasil dihapus!", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Hapus", color = MaterialTheme.colorScheme.onError)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }
}
