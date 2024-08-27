package com.example.openinappassesment.Network

import com.example.openinappassesment.Database.TokenRepository
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance(token1 : String){
    companion object{
        val BASE_URL_1 = "https://api.inopenapp.com"
        val interceptor = HttpLoggingInterceptor().apply {
            this.level= HttpLoggingInterceptor.Level.BODY
        }
        fun create(token1: String) : Retrofit {
            val headerInterceptor = Interceptor { chain ->
                val originalRequest = chain.request()
                val newRequest = originalRequest.newBuilder()
                    .addHeader("Authorization", token1)
                    .build()
                chain.proceed(newRequest)
            }
            val client = OkHttpClient.Builder().apply {
                this.addInterceptor(interceptor)
                    .addInterceptor(headerInterceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(25, TimeUnit.SECONDS)
            }.build()

            return Retrofit.Builder().baseUrl(BASE_URL_1)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
        }
    }
}