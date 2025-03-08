package com.example.pertemuan1.data.model

data class JabarResponse(
    val id: Int,
    val kode_kabupaten_kota: Int,
    val kode_provinsi: Int,
    val nama_kabupaten_kota: String,
    val nama_provinsi: String,
    val rata_rata_lama_sekolah: Float,
    val satuan: String,
    val tahun: Int
)

data class JabarWrapper(
    val data: List<JabarResponse> // Menyesuaikan struktur JSON
)