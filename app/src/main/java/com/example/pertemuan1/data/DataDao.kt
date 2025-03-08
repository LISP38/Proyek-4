package com.example.pertemuan1.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: DataEntity)

    @Update
    suspend fun update(data: DataEntity)

    // Fungsi untuk menghapus semua data
    @Query("DELETE FROM data_table")
    suspend fun clearAll()

    @Query("SELECT * FROM data_table")
    fun getAll(): LiveData<List<DataEntity>>  // Mengembalikan LiveData

    @Query("SELECT * FROM data_table WHERE id = :dataId")
    suspend fun getById(dataId: Int): DataEntity?

    @Query("DELETE FROM data_table WHERE id = :dataId")
    suspend fun deleteById(dataId: Int) //
}
