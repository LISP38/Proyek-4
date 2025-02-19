package com.example.pertemuan1.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import com.example.pertemuan1.ui.DeleteScreen
import androidx.navigation.NavHostController
import androidx.compose.runtime.livedata.observeAsState
import com.example.pertemuan1.ui.theme.TertiaryColor
import com.example.pertemuan1.viewmodel.DataViewModel

@Composable
fun DataListScreen(navController: NavHostController, viewModel: DataViewModel) {
    val dataList by viewModel.dataList.observeAsState(emptyList())

    Box (
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        if (dataList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "No Data Available", style = MaterialTheme.typography.headlineMedium)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)

            ) {
                items(dataList) { item ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Provinsi: ${item.namaProvinsi} (${item.kodeProvinsi})",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Kabupaten/Kota: ${item.namaKabupatenKota} (${item.kodeKabupatenKota})",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Total: ${item.total} ${item.satuan}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Tahun: ${item.tahun}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(
                                    onClick = {
                                        navController.navigate("edit/${item.id}")
                                    },
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(text = "Edit")
                                }
                                Button(
                                    onClick = {
                                        navController.navigate("delete/${item.id}")
                                    },
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(text = "Delete")
                                }

                            }
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { navController.navigate("form") },
            modifier = Modifier
                .align(Alignment.BottomEnd) // ⬅️ FAB di pojok kanan bawah
                .padding(16.dp), // ⬅️ Memberikan jarak dari tepi
            containerColor = TertiaryColor
        ) {
            Icon(
                imageVector = Icons.Default.Add, // ⬅️ Icon tambah bawaan
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
