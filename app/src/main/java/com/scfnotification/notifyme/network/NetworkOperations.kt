package com.scfnotification.notifyme.network

import android.os.Build
import android.util.Log
import com.scfnotification.notifyme.data.entities.Notification
import okhttp3.* // ktlint-disable no-wildcard-imports
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.CountDownLatch

class NetworkOperations {

    fun getCoins(APIKEY: String): List<CryptoCoinValueItem> {

        val countDownLatch = CountDownLatch(1)
        val apiInterface = ApiInterface.create().getCoins("Token $APIKEY")
        var responseBody: List<CryptoCoinValueItem> = listOf()
        apiInterface.enqueue(object : Callback<List<CryptoCoinValueItem>> {
            override fun onResponse(
                call: Call<List<CryptoCoinValueItem>>?,
                response: Response<List<CryptoCoinValueItem>>?
            ) {
                if (response!!.isSuccessful) {
                    responseBody = response.body()!!
                }
                countDownLatch.countDown()
            }

            override fun onFailure(call: Call<List<CryptoCoinValueItem>>?, t: Throwable?) {
                countDownLatch.countDown()
            }
        })
        countDownLatch.await()
        return responseBody
    }

    fun changeEnabled(APIKEY: String, coin_id: String, state: Boolean) {
        val apiInterface = ApiInterface.create().enableNotification("Token $APIKEY", coin_id, state)
        apiInterface.enqueue(object : Callback<Boolean> {
            override fun onResponse(
                call: Call<Boolean>?,
                response: Response<Boolean>?
            ) {
            }

            override fun onFailure(call: Call<Boolean>?, t: Throwable?) {
            }
        })
    }

    fun deleteNotification(APIKEY: String, coin_id: String) {
        val apiInterface = ApiInterface.create().deleteNotification("Token $APIKEY", coin_id)
        apiInterface.enqueue(object : Callback<Boolean> {
            override fun onResponse(
                call: Call<Boolean>?,
                response: Response<Boolean>?
            ) {
            }

            override fun onFailure(call: Call<Boolean>?, t: Throwable?) {
            }
        })
    }

    fun setNotification(
        APIKEY: String,
        coin_id: String,
        value_type: String,
        final_value: Double,
        via_mail: Boolean
    ) {
        val apiInterface = ApiInterface.create().addNotification(
            "Token $APIKEY",
            coin_id,
            value_type,
            final_value,
            via_mail,
        )
        apiInterface.enqueue(object : Callback<Boolean> {
            override fun onResponse(
                call: Call<Boolean>?,
                response: Response<Boolean>?
            ) {
            }

            override fun onFailure(call: Call<Boolean>?, t: Throwable?) {
            }
        })
    }

    fun getNotifications(APIKEY: String): List<Notification> {
        val countDownLatch = CountDownLatch(1)
        val apiInterface = ApiInterface.create().getNotifications("Token $APIKEY")
        var responseBody: List<Notification> = listOf()
        apiInterface.enqueue(object : Callback<List<Notification>> {
            override fun onResponse(
                call: Call<List<Notification>>?,
                response: Response<List<Notification>>?
            ) {
                if (response!!.isSuccessful) {
                    responseBody = response.body()!!
                }
                countDownLatch.countDown()
            }

            override fun onFailure(call: Call<List<Notification>>?, t: Throwable?) {
                countDownLatch.countDown()
            }
        })
        countDownLatch.await()
        return responseBody
    }

    fun sendFCM(APIKEY: String, FCMKEY: String) {
        val apiInterface = ApiInterface.create()
            .sendFirebase("Token $APIKEY", FCMKEY, Build.MODEL, true, "android")
        apiInterface.enqueue(object : Callback<Boolean> {
            override fun onResponse(
                call: Call<Boolean>?,
                response: Response<Boolean>?
            ) {
            }

            override fun onFailure(call: Call<Boolean>?, t: Throwable?) {
            }
        })
    }

    fun getFavorites(APIKEY: String): List<CryptoCoinValueItem> {
        val countDownLatch = CountDownLatch(1)
        val apiInterface = ApiInterface.create().getFavorites("Token $APIKEY")
        var responseBody: List<CryptoCoinValueItem> = listOf()
        apiInterface.enqueue(object : Callback<List<CryptoCoinValueItem>> {
            override fun onResponse(
                call: Call<List<CryptoCoinValueItem>>?,
                response: Response<List<CryptoCoinValueItem>>?
            ) {
                if (response!!.isSuccessful) {
                    responseBody = response.body()!!
                }
                countDownLatch.countDown()
            }

            override fun onFailure(call: Call<List<CryptoCoinValueItem>>?, t: Throwable?) {
                countDownLatch.countDown()
            }
        })
        countDownLatch.await()
        return responseBody
    }

    fun removeFavorite(coinID: String, APIKEY: String) {
        val apiInterface = ApiInterface.create().deleteFavorite("Token $APIKEY", coinID)
        apiInterface.enqueue(object : Callback<Boolean> {
            override fun onResponse(
                call: Call<Boolean>?,
                response: Response<Boolean>?
            ) {
            }

            override fun onFailure(call: Call<Boolean>?, t: Throwable?) {
            }
        })
    }

    fun addFavorite(coinID: String, APIKEY: String) {
        val apiInterface = ApiInterface.create().addFavorite("Token $APIKEY", coinID)
        apiInterface.enqueue(object : Callback<Boolean> {
            override fun onResponse(
                call: Call<Boolean>?,
                response: Response<Boolean>?
            ) {
            }

            override fun onFailure(call: Call<Boolean>?, t: Throwable?) {
            }
        })
    }

    fun login(username: String, password: String): String? {
        Log.d("TAG", "Login")

        val url = "https://notifyme.setrofex.tk/api/login/"

        val jsonObject = JSONObject()

        try {
            jsonObject.put("username", username)
            jsonObject.put("password", password)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val jsonString = jsonObject.toString()

        val json = MediaType.parse("application/json; charset=utf-8")
        val requestBody = RequestBody.create(json, jsonString)

        try {
            var result: String? = null
            val client = OkHttpClient().newBuilder().build()
            val request = Request.Builder().post(requestBody).url(url).build()
            val responseCall = client.newCall(request)
            val countDownLatch = CountDownLatch(1)

            responseCall.enqueue(
                object : okhttp3.Callback {

                    override fun onFailure(call: okhttp3.Call, e: IOException) {
                        Log.d("Login", "Big Fail")
                        e.printStackTrace()
                        countDownLatch.countDown()
                    }

                    override fun onResponse(
                        call: okhttp3.Call,
                        response: okhttp3.Response
                    ) {
                        try {
                            val responseBody: ResponseBody? = response.body()
                            if (response.isSuccessful && response.code() == 200) {
                                val results = responseBody!!.string()
                                val jsonObj = JSONObject(
                                    results.substring(
                                        results.indexOf("{"),
                                        results.lastIndexOf("}") + 1
                                    )
                                )
                                result = jsonObj.getString("access_token")
                                Log.d("token:", result!!)
                            } else {
                                throw IOException("Response not successful: $response")
                            }
                            countDownLatch.countDown()
                        } catch (e: IOException) {
                            Log.d("Login", "onResponse: failed!$e")
                            countDownLatch.countDown()
                        }
                    }
                })
            countDownLatch.await()
            return result
        } catch (error: IOException) {
            return null
        }
    }
}
