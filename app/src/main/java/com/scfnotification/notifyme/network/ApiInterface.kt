package com.scfnotification.notifyme.network

import com.scfnotification.notifyme.data.entities.Notification
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterface {

    @GET("coins/")
    fun getCoins(
        @Header("Authorization") token: String
    ): Call<List<CryptoCoinValueItem>>

    @GET("notifications/")
    fun getNotifications(
        @Header("Authorization") token: String
    ): Call<List<Notification>>

    @GET("favorites/")
    fun getFavorites(
        @Header("Authorization") token: String
    ): Call<List<CryptoCoinValueItem>>

    @FormUrlEncoded
    @POST("favorites/add")
    fun addFavorite(
        @Header("Authorization") token: String,
        @Field("crypto_id") crypto_id: String
    ): Call<Boolean>

    @FormUrlEncoded
    @POST("favorites/delete")
    fun deleteFavorite(
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

    @GET("coins/{id}")
    fun getCoin(
        @Header("Authorization") token: String,
        @Path("name") name: String
    ): Call<CryptoCoinValueItem>

    @FormUrlEncoded
    @POST("notifications/create")
    fun addNotification(
        @Header("Authorization") token: String,
        @Field("crypto_id") coin_id: String,
        @Field("option") value_type: String,
        @Field("value") final_value: Double,
        @Field("viamail") via_mail: Boolean,
    ): Call<Boolean>

    @FormUrlEncoded
    @POST("notifications/delete")
    fun deleteNotification(
        @Header("Authorization") token: String,
        @Field("crypto_id") crypto_id: String
    ): Call<Boolean>

    @FormUrlEncoded
    @POST("notifications/change")
    fun enableNotification(
        @Header("Authorization") token: String,
        @Field("crypto_id") crypto_id: String,
        @Field("state") state: Boolean
    ): Call<Boolean>

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
