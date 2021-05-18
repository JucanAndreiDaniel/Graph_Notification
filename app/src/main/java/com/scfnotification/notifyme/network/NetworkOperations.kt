package com.scfnotification.notifyme.network

import android.util.Log
import okhttp3.* // ktlint-disable no-wildcard-imports
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.CountDownLatch

class NetworkOperations {

    fun getcoins(APIKEY: String): List<CryptoCoinValueItem> {

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

    fun addFavorite(coinID: String, APIKEY: String) {
        val countDownLatch = CountDownLatch(1)
        val apiInterface = ApiInterface.create().addFavorite(coinID,"Token $APIKEY")
    }

    fun login(username: String, password: String): String? {
        Log.d("TAG", "Login")

        val url = "https://stockcrypto.ddns.net/api-token-auth/"

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

                    override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
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
                                result = jsonObj.getString("token")
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
