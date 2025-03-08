package com.example.pertemuan1.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserDataEntity(
    @PrimaryKey(autoGenerate = true)
    var id_user: Int = 0,

    @ColumnInfo(name = "nama")
    val nama: String,

    @ColumnInfo(name = "nim")
    val nim: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "profile_img", typeAffinity = ColumnInfo.BLOB)
    val profileImg: ByteArray
)