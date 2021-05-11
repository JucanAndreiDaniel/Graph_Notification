package com.example.scfnotification.data.retrofit

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("jCoin/")
    fun getCoins(@Header("Authorization") token:String) : Call<List<Coin>>

    @GET("jFav/")
    fun getFavs(@Header("Authorization") token:String) : Call<List<Coin>>

    @GET("cryptoSpecific/{id}")
    fun getCoin(@Header("Authorization") token:String, @Path("name") name: String) : Call<Coin>

    companion object {

        var BASE_URL = "https://stockcrypto.ddns.net/"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}