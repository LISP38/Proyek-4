package com.example.pertemuan1.data.network

import com.example.pertemuan1.data.model.JabarResponse
import com.example.pertemuan1.data.model.JabarWrapper
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Body
import retrofit2.http.PUT

interface JabarApi {

    @GET("od_17046_rata_rata_lama_sekolah_berdasarkan_kabupatenkota/{id}")
    suspend fun getJabarById(@Path("id") id: Int): JabarResponse

    @GET("od_17046_rata_rata_lama_sekolah_berdasarkan_kabupatenkota")
    suspend fun getJabar(): JabarWrapper

    @PUT("od_17046_rata_rata_lama_sekolah_berdasarkan_kabupatenkota/{id}")
    suspend fun updateData(
        @Path("id") id: Int,                // ID untuk data yang ingin diperbarui
        @Body updatedData: JabarResponse    // Data yang akan diperbarui
    ): JabarResponse

}