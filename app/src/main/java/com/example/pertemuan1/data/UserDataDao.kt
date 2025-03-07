package com.example.pertemuan1.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserDataEntity)

    @Query("SELECT * FROM user_table WHERE id_user = :id LIMIT 1")
    fun getUserById(id: Int): LiveData<UserDataEntity>

    @Query("SELECT * FROM user_table WHERE id_user = :id LIMIT 1")
    suspend fun getUserByIdSync(id: Int): UserDataEntity?
    @Update
    suspend fun updateUser(user: UserDataEntity)
}
