//package com.example.pertemuan1.data.network
//
//import com.example.pertemuan1.data.network.JabarApi
//import okhttp3.Interceptor
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//object RetrofitClient {
//    private const val BASE_URL = "https://data.jabarprov.go.id/api-backend/bigdata/bps/"
//
//    private val loggingInterceptor = HttpLoggingInterceptor().apply {
//        level = HttpLoggingInterceptor.Level.BODY
//    }
//
//    private val authInterceptor = Interceptor { chain ->
//        val request = chain.request().newBuilder()
//            .addHeader("Authorization", "Bearer YOUR_API_KEY_HERE")
//            .build()
//        chain.proceed(request)
//    }
//
//    private val client = OkHttpClient.Builder()
//        .addInterceptor(loggingInterceptor)
//        .addInterceptor(authInterceptor)
//        .build()
//
//    private val retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .client(client)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    val api: JabarApi = retrofit.create(JabarApi::class.java)
//}

package com.example.pertemuan1.data.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://data.jabarprov.go.id/api-backend/bigdata/bps/"

    // Interceptor untuk logging
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Interceptor untuk menambahkan API Key di header
    private val authInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer YOUR_API_KEY_HERE") // Ganti dengan API Key yang valid
            .build()
        chain.proceed(request)
    }

    // Setup OkHttpClient untuk menambahkan interceptor
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .build()

    // Membuat Retrofit instance
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)  // Menambahkan OkHttpClient ke Retrofit
        .addConverterFactory(GsonConverterFactory.create())  // Konverter JSON ke objek
        .build()

    // API instance yang akan digunakan untuk request
    val api: JabarApi = retrofit.create(JabarApi::class.java)
}
