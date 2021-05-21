package com.scfnotification.notifyme.network

import android.os.Build
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterface {

    @GET("coins/")
    fun getCoins(@Header("Authorization") token: String): Call<List<CryptoCoinValueItem>>

    @GET("favorites/")
    fun getFavorites(@Header("Authorization") token: String): Call<List<CryptoCoinValueItem>>

    @FormUrlEncoded
    @POST("addFav/")
    fun addFavorite(
        @Header("Authorization") token: String,
        @Field("crypto_id") crypto_id: String
    ): Call<Boolean>

    @FormUrlEncoded
    @POST("v1/devices/")
    fun sendFirebase(
        @Header("Authorization") token: String,
        @Field("token") fcm_token: String,
        @Field("name") name: String,
        @Field("active") active: Boolean,
        @Field("type") type: String
    ): Call<Boolean>

    @GET("coin/{id}")
    fun getCoin(
        @Header("Authorization") token: String,
        @Path("name") name: String
    ): Call<CryptoCoinValueItem>

    companion object {

        var BASE_URL = "https://stockcrypto.ddns.net/"

        fun create(): ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}
