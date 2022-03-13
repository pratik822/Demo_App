package com.example.demo_app

import okhttp3.OkHttpClient
import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object{
        val headerInterceptor=HeaderInterceptor();
        var okHttpClient= OkHttpClient.Builder().addInterceptor(headerInterceptor).build();
        fun getRetrofitInstance():Retrofit{
            return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("https://quotable.io/").build();
        }

        val service: NetworkModule = getRetrofitInstance().create(NetworkModule::class.java)
    }


}