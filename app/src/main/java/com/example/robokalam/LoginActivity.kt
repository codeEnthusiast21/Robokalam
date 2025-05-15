package com.example.robokalam

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.robokalam.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (validateInput(email, password)) {
                // Save login status
                val sharedPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putBoolean("is_logged_in", true)
                editor.putString("user_email", email)
                editor.apply()

                // Navigate to MainActivity
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        binding.tvRegister.setOnClickListener {
            binding.layoutRegister.visibility = View.VISIBLE
            binding.layoutLogin.visibility = View.GONE
        }

        binding.tvBackToLogin.setOnClickListener {
            binding.layoutLogin.visibility = View.VISIBLE
            binding.layoutRegister.visibility = View.GONE
        }

        binding.btnRegister.setOnClickListener {
            val name = binding.etRegisterName.text.toString()
            val email = binding.etRegisterEmail.text.toString()
            val password = binding.etRegisterPassword.text.toString()

            if (validateRegistration(name, email, password)) {
                // Save registration data
                val sharedPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putBoolean("is_logged_in", true)
                editor.putString("user_email", email)
                editor.putString("user_name", name)
                editor.apply()

                // Navigate to MainActivity
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Enter valid email"
            return false
        }
        if (password.isEmpty() || password.length < 6) {
            binding.etPassword.error = "Password must be at least 6 characters"
            return false
        }
        return true
    }

    private fun validateRegistration(name: String, email: String, password: String): Boolean {
        if (name.isEmpty()) {
            binding.etRegisterName.error = "Name is required"
            return false
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etRegisterEmail.error = "Enter valid email"
            return false
        }
        if (password.isEmpty() || password.length < 6) {
            binding.etRegisterPassword.error = "Password must be at least 6 characters"
            return false
        }
        return true
    }
}