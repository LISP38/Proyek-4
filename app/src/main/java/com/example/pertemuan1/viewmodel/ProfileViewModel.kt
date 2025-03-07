package com.example.pertemuan1.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.*
import com.example.pertemuan1.data.AppDatabase
import com.example.pertemuan1.data.Converters
import com.example.pertemuan1.data.UserDataEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//menguhubungkan logic datadao dengan ui
class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = AppDatabase.getDatabase(application).userDataDao()

    val user: LiveData<UserDataEntity> = userDao.getUserById(1).also {
        it.observeForever { userData ->
            Log.d("ProfileViewModel", "User data updated: $userData")
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            insertDefaultUserIfNeeded()
        }
    }

    private suspend fun insertDefaultUserIfNeeded() {
        val existingUser = userDao.getUserByIdSync(1)
        if (existingUser == null) {
            val defaultBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888) // Bitmap kosong
            val defaultByteArray = Converters().fromBitmap(defaultBitmap)

            val defaultUser = UserDataEntity(
                id_user = 1,
                nama = "Mahasiswa JTK",
                nim = "22222",
                email = "mahasiswa@jtk.polban.ac.id",
                profileImg = defaultByteArray
            )
            userDao.insertUser(defaultUser)
            Log.d("ProfileViewModel", "Default user inserted: $defaultUser")
        } else {
            Log.d("ProfileViewModel", "User already exists: $existingUser")
        }
    }

    fun updateUser(nama: String, nim: String, email: String, profileImg: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingUser = userDao.getUserByIdSync(1)
            if (existingUser != null) {
                val updatedUser = existingUser.copy(
                    nama = nama,
                    nim = nim,
                    email = email,
                    profileImg = Converters().fromBitmap(profileImg)
                )
                userDao.updateUser(updatedUser)
                Log.d("ProfileViewModel", "User updated: $updatedUser")
            } else {
                Log.e("ProfileViewModel", "Failed to update: No user found with ID 1")
            }
        }
    }
}
