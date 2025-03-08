package com.example.pertemuan1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pertemuan1.data.AppDatabase
import com.example.pertemuan1.data.DataEntity
import com.example.pertemuan1.data.model.JabarResponse
import com.example.pertemuan1.data.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).dataDao()

    val dataList: LiveData<List<DataEntity>> = dao.getAll()

    // Define MutableLiveData for loading and error states
    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    fun insertData(
        kodeProvinsi: String,
        namaProvinsi: String,
        kodeKabupatenKota: String,
        namaKabupatenKota: String,
        total: String,
        satuan: String,
        tahun: String
    ) {
        viewModelScope.launch {
            val totalValue = total.toFloatOrNull() ?: 0.0f  // Convert to Float
            val tahunValue = tahun.toIntOrNull() ?: 0       // Convert to Int
            val kodeProvinsiValue = kodeProvinsi.toIntOrNull() ?: 0  // Convert to Int
            val kodeKabupatenKotaValue = kodeKabupatenKota.toIntOrNull() ?: 0  // Convert to Int

            dao.insert(
                DataEntity(
                    kodeProvinsi = kodeProvinsiValue,
                    namaProvinsi = namaProvinsi,
                    kodeKabupatenKota = kodeKabupatenKotaValue,
                    namaKabupatenKota = namaKabupatenKota,
                    total = totalValue,
                    satuan = satuan,
                    tahun = tahunValue
                )
            )
        }
    }

    suspend fun getDataById(id: Int): DataEntity? {
        return withContext(Dispatchers.IO) {
            dao.getById(id)
        }
    }

    fun deleteDataById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }

    fun updateData(updatedData: DataEntity) {
        viewModelScope.launch {
            try {
                // Menyimpan data baru ke dalam database lokal
                dao.update(updatedData) // Pastikan DAO Anda punya fungsi update
                _loading.value = false
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            }
        }
    }

    fun updateDataInApi(updatedData: DataEntity) {
        viewModelScope.launch {
            try {
                // Panggil API untuk memperbarui data
                RetrofitClient.api.updateData(updatedData.id, JabarResponse(
                    id = updatedData.id,
                    kode_provinsi = updatedData.kodeProvinsi,
                    nama_provinsi = updatedData.namaProvinsi,
                    kode_kabupaten_kota = updatedData.kodeKabupatenKota,
                    nama_kabupaten_kota = updatedData.namaKabupatenKota,
                    rata_rata_lama_sekolah = updatedData.total,
                    satuan = updatedData.satuan,
                    tahun = updatedData.tahun
                )
                )
                _loading.value = false
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            }
        }
    }

    fun refreshData() {}

}

