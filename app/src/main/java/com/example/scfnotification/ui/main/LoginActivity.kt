package com.example.scfnotification.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.scfnotification.R
import org.json.JSONObject


class LoginActivity : AppCompatActivity() {

    private var _usernameText: EditText? = null
    private var _passwordText: EditText? = null
    private var _loginButton: Button? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_login)

        _loginButton = this.findViewById<Button>(R.id.login)
        _passwordText = this.findViewById<EditText>(R.id.password)
        _usernameText = this.findViewById<EditText>(R.id.username)
        _loginButton!!.setOnClickListener { login() }

    }

    private fun login() {
        Log.d(TAG, "Login")
        _loginButton!!.isEnabled = false

        val username = _usernameText!!.text.toString()
        val password = _passwordText!!.text.toString()

        val url = "https://stockcrypto.ddns.net/api-token-auth/"

        val jsonobj = JSONObject()

        jsonobj.put("username", username)
        jsonobj.put("password", password)
        try {
            val que = Volley.newRequestQueue(this)
            val request: JsonObjectRequest = object : JsonObjectRequest(Method.POST, url, jsonobj,
                    Response.Listener {
                        print("Success")
                    }, Response.ErrorListener { error ->
                Log.d(TAG,"Failure (" + error.networkResponse + ")")
                onLoginFailed()
            }) {
                override fun deliverResponse(response: JSONObject?) {
                    Log.d(TAG, response.toString())
                    onLoginSuccess()
                }
            }
            que.add(request)

        } catch (error: VolleyError) {
            onLoginFailed()
        }

    }


    override fun onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true)
    }

    fun onLoginSuccess() {
        startActivity(Intent(this, MainActivity::class.java))
        _loginButton!!.isEnabled = true
    }

    fun onLoginFailed() {
        Toast.makeText(baseContext, "Login failed", Toast.LENGTH_LONG).show()
        _loginButton!!.isEnabled = true
    }


    companion object {
        private const val TAG = "LoginActivity"
    }
}