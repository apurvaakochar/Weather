package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityLoginBinding
import java.security.KeyStore

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signinButton.setOnClickListener {

            val email = binding.editTextTextEmailAddress.editableText.toString()
            val password = binding.editTextTextPassword.editableText.toString()

            if (hasAccessToKeyStore(email+password)){
                val intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)
            }
        }

        binding.signupButton.setOnClickListener{
            val intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(intent)
        }

    }

    fun hasAccessToKeyStore(key: String = "MyKeyAlias"): Boolean {
        val keystore = KeyStore.getInstance("AndroidKeyStore")
        keystore.load(null)
        return keystore.getEntry(key, null) as? KeyStore.SecretKeyEntry != null
    }
}