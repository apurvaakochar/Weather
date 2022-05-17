package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivitySignUpBinding
import javax.crypto.KeyGenerator

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel = SignUpViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signupButton.setOnClickListener {
            val email = binding.editTextTextEmailAddress.editableText.toString()
            val password = binding.editTextTextPassword.editableText.toString()
            viewModel.generateKeyWithEmailPassword(email, password)
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
        }
        binding.loginButton.setOnClickListener{
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }
    }
}