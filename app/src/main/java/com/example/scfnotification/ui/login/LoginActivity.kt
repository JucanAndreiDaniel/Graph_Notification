package com.example.scfnotification.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.scfnotification.R
import com.example.scfnotification.data.sharedpreferences.IPreferenceHelper
import com.example.scfnotification.data.sharedpreferences.PreferenceManager
import com.example.scfnotification.network.NetworkOperations
import com.example.scfnotification.ui.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private var _usernameText: EditText? = null
    private var _passwordText: EditText? = null
    private var _loginButton: Button? = null
    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(applicationContext) }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        if (preferenceHelper.getApiKey() != "") {
            startActivity(Intent(this, MainActivity::class.java))
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_login)

        _loginButton = this.findViewById(R.id.login)
        _passwordText = this.findViewById(R.id.password)
        _usernameText = this.findViewById(R.id.username)
        _loginButton!!.setOnClickListener { login() }
    }

    private fun login() {
        _loginButton!!.isEnabled = false

        val username = _usernameText!!.text.toString()
        val password = _passwordText!!.text.toString()
        val token = NetworkOperations().login(username, password)

        if (token != null) {
            preferenceHelper.setApiKey(token)
            onLoginSuccess()
        } else onLoginFailed()
    }

    override fun onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true)
    }

    private fun onLoginSuccess() {
        _loginButton!!.isEnabled = true
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun onLoginFailed() {
        Toast.makeText(baseContext, "Login failed", Toast.LENGTH_LONG).show()
        _loginButton!!.isEnabled = true
    }

    companion object {
        const val TAG = "LoginActivity"
    }
}
