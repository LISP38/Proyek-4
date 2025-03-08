package com.example.pertemuan1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pertemuan1.data.AppDatabase
import com.example.pertemuan1.data.DataDao
import com.example.pertemuan1.data.DataEntity
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

    private val sharedPreferences: SharedPreferences = application.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

    // Fungsi untuk mengecek apakah aplikasi pertama kali dijalankan
    private fun isFirstLaunch(): Boolean {
        return sharedPreferences.getBoolean("isFirstLaunch", true)
    }

    // Fungsi untuk menandai bahwa aplikasi sudah pernah diluncurkan
    private fun setFirstLaunchCompleted() {
        sharedPreferences.edit().putBoolean("isFirstLaunch", false).apply()
    }

    // Fetch data dari API
    fun fetchDataSekolah() {
        viewModelScope.launch {
            try {
                _loading.value = true

                // Cek jika aplikasi pertama kali dijalankan
                if (isFirstLaunch()) {
                    // Jika pertama kali dijalankan, clear data di database
                    dao.clearAll()
                    // Tandai aplikasi sudah diluncurkan
                    setFirstLaunchCompleted()
                }

                val result = RetrofitClient.api.getJabar()
                _dataSekolah.value = result.data  // Menampilkan data dari API
                _loading.value = false

                // Menyimpan data dari API ke dalam database lokal setelah diambil
                result.data.forEach { item ->
                    dao.insert(DataEntity(
                        id = item.id,
                        kodeProvinsi = item.kode_provinsi,
                        namaProvinsi = item.nama_provinsi,
                        kodeKabupatenKota = item.kode_kabupaten_kota,
                        namaKabupatenKota = item.nama_kabupaten_kota,
                        total = item.rata_rata_lama_sekolah,
                        satuan = item.satuan,
                        tahun = item.tahun
                    ))
                }

                // Update LiveData dengan data dari API
                _dataSekolah.value = result.data
                _loading.value = false

            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
                _loading.value = false
            }
        }
    }

    fun addDataSekolah(newData: JabarResponse) {
        viewModelScope.launch {
            try {
                // Cek apakah data dengan id yang sama sudah ada di database
                val existingData = dao.getById(newData.id)
                if (existingData == null) {
                    // Jika data belum ada, simpan data baru
                    dao.insert(DataEntity(
                        id = newData.id,
                        kodeProvinsi = newData.kode_provinsi,
                        namaProvinsi = newData.nama_provinsi,
                        kodeKabupatenKota = newData.kode_kabupaten_kota,
                        namaKabupatenKota = newData.nama_kabupaten_kota,
                        total = newData.rata_rata_lama_sekolah,
                        satuan = newData.satuan,
                        tahun = newData.tahun
                    ))
                } else {
                    // Jika data sudah ada, update data yang ada
                    dao.update(DataEntity(
                        id = newData.id,
                        kodeProvinsi = newData.kode_provinsi,
                        namaProvinsi = newData.nama_provinsi,
                        kodeKabupatenKota = newData.kode_kabupaten_kota,
                        namaKabupatenKota = newData.nama_kabupaten_kota,
                        total = newData.rata_rata_lama_sekolah,
                        satuan = newData.satuan,
                        tahun = newData.tahun
                    ))
                }

                // Memperbarui LiveData dengan data yang terbaru
                fetchDataSekolah()
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            }
        }
    }


    // Fungsi untuk mengambil data dari database lokal
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

    fun clearAndInsertNewData(newData: List<JabarResponse>) {
        viewModelScope.launch {
            try {
                // Menghapus semua data yang ada
                dao.clearAll()

                // Menyimpan data baru
                newData.forEach {
                    dao.insert(DataEntity(
                        id = it.id,
                        kodeProvinsi = it.kode_provinsi,
                        namaProvinsi = it.nama_provinsi,
                        kodeKabupatenKota = it.kode_kabupaten_kota,
                        namaKabupatenKota = it.nama_kabupaten_kota,
                        total = it.rata_rata_lama_sekolah,
                        satuan = it.satuan,
                        tahun = it.tahun
                    ))
                }

                // Memperbarui LiveData
                fetchDataSekolah()
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            }
        }
    }

}
