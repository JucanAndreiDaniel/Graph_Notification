package com.example.scfnotification.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.CountDownLatch

class LoginViewModel: ViewModel() {
    fun login(username:String,password:String): Boolean {
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
            var result=false
            val client = OkHttpClient().newBuilder().build()
            val request = Request.Builder().post(requestBody).url(url).build()
            val responseCall = client.newCall(request)
            val countDownLatch = CountDownLatch(1)

            responseCall.enqueue(
                object : Callback {

                    override fun onFailure(call: Call, e: IOException) {
                        val d = Log.d("Login", "Big Fail")
                        e.printStackTrace()
                        countDownLatch.countDown()
                    }

                    override fun onResponse(call: Call, response: Response) {
                        try {
                            val responseBody: ResponseBody? = response.body()
                            if (response.isSuccessful && response.code() == 200) {
                                result= true
                                Log.d(LoginActivity.TAG, result.toString())

                            }
                            else {
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
            return false
        }

    }
}