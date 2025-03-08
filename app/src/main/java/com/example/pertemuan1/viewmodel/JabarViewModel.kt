//package com.example.pertemuan1.viewmodel
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.pertemuan1.data.model.JabarResponse
//import com.example.pertemuan1.data.network.RetrofitClient
//import kotlinx.coroutines.launch
//import com.example.pertemuan1.data.model.JabarWrapper
//
//class JabarViewModel : ViewModel() {
//    private val _dataSekolah = MutableLiveData<List<JabarResponse>>()
//    val dataSekolah: LiveData<List<JabarResponse>> = _dataSekolah
//
//    private val _loading = MutableLiveData<Boolean>()
//    val loading: LiveData<Boolean> = _loading
//
//    private val _error = MutableLiveData<String>()
//    val error: LiveData<String> = _error
//
//    fun fetchDataSekolah() {
//        viewModelScope.launch {
//            try {
//                _loading.value = true
//                val result = RetrofitClient.api.getJabar()
//                _dataSekolah.value = result.data // Langsung pakai result karena sudah berupa List
//                _loading.value = false
//            } catch (e: Exception) {
//                _error.value = "Error: ${e.message}"
//                _loading.value = false
//            }
//        }
//    }
//
//    fun fetchDataSekolahById(id: Int) {
//        viewModelScope.launch {
//            try {
//                _loading.value = true
//                val result = RetrofitClient.api.getJabarById(id)
//                _dataSekolah.value = listOf(result) // Dibungkus dalam list agar tetap konsisten
//                _loading.value = false
//            } catch (e: Exception) {
//                _error.value = "Error: ${e.message}"
//                _loading.value = false
//            }
//        }
//    }
//}


package com.example.pertemuan1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pertemuan1.data.AppDatabase
import com.example.pertemuan1.data.DataDao
import com.example.pertemuan1.data.DataEntity  // Import DataEntity dari package data
import com.example.pertemuan1.data.model.JabarResponse
import com.example.pertemuan1.data.network.RetrofitClient
import kotlinx.coroutines.launch

class JabarViewModel(application: Application) : AndroidViewModel(application) {

    private val _dataSekolah = MutableLiveData<List<JabarResponse>>()
    val dataSekolah: LiveData<List<JabarResponse>> = _dataSekolah

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val dao: DataDao = AppDatabase.getDatabase(application).dataDao()  // DAO untuk database lokal

    // Fetch data dari API
    fun fetchDataSekolah() {
        viewModelScope.launch {
            try {
                _loading.value = true
                val result = RetrofitClient.api.getJabar()
                _dataSekolah.value = result.data  // Menampilkan data dari API
                _loading.value = false
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
                _loading.value = false
            }
        }
    }

    // Fungsi untuk menambahkan data ke dalam database lokal dan memperbarui data di UI
    fun addDataSekolah(newData: JabarResponse) {
        viewModelScope.launch {
            try {
                // Menyimpan data baru ke dalam database lokal
                dao.insert(DataEntity(  // Gunakan DataEntity untuk menyimpan data ke database lokal
                    id = newData.id,
                    kodeProvinsi = newData.kode_provinsi,  // Sesuaikan dengan data di JabarResponse
                    namaProvinsi = newData.nama_provinsi,  // Sesuaikan dengan data di JabarResponse
                    kodeKabupatenKota = newData.kode_kabupaten_kota,
                    namaKabupatenKota = newData.nama_kabupaten_kota,
                    total = newData.rata_rata_lama_sekolah,  // Sesuaikan properti
                    satuan = newData.satuan,
                    tahun = newData.tahun
                ))

                // Memperbarui LiveData dengan menambahkan data baru yang disimpan
                fetchDataSekolah()  // Memanggil ulang fetch data untuk mengambil data terbaru (termasuk yang baru dimasukkan)
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            }
        }
    }

    fun getAllDataSekolah() {
        viewModelScope.launch {
            try {
                _loading.value = true
                val result = dao.getAll()  // Ambil data LiveData<List<DataEntity>>

                result.observeForever { dataList ->
                    // Mengonversi DataEntity ke JabarResponse
                    _dataSekolah.value = dataList.map { dataEntity ->
                        JabarResponse(
                            id = dataEntity.id,
                            kode_provinsi = dataEntity.kodeProvinsi,
                            nama_provinsi = dataEntity.namaProvinsi,
                            kode_kabupaten_kota = dataEntity.kodeKabupatenKota,
                            nama_kabupaten_kota = dataEntity.namaKabupatenKota,
                            rata_rata_lama_sekolah = dataEntity.total,
                            satuan = dataEntity.satuan,
                            tahun = dataEntity.tahun
                        )
                    }
                }
                _loading.value = false
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
                _loading.value = false
            }
        }
    }

    // Fungsi untuk mengambil data berdasarkan ID tertentu
    fun fetchDataSekolahById(id: Int) {
        viewModelScope.launch {
            try {
                _loading.value = true
                val result = RetrofitClient.api.getJabarById(id)
                _dataSekolah.value = listOf(result)  // Dibungkus dalam list agar tetap konsisten
                _loading.value = false
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
                _loading.value = false
            }
        }
    }
}
