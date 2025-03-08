package com.example.pertemuan1.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_table")
data class DataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val kodeProvinsi: Int,
    val namaProvinsi: String,
    val kodeKabupatenKota: Int,
    val namaKabupatenKota: String,
    val total: Float,
    val satuan: String,
    val tahun: Int
)