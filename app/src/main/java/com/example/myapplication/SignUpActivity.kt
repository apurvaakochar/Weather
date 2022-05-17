package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.example.myapplication.databinding.ActivitySignUpBinding
import java.security.KeyStore
import javax.crypto.KeyGenerator

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setContentView(R.layout.activity_sign_up)
        binding.signupButton.setOnClickListener {
//            Log.i("signup", "Do Something") // TODO

            val email = binding.editTextTextEmailAddress.editableText.toString()
            val password = binding.editTextTextPassword.editableText.toString()
            val keyGenerator =
                KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                email+password,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()

            keyGenerator.init(keyGenParameterSpec)
            keyGenerator.generateKey()

            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)

//            print(getKey(binding.editTextTextPassword.text.toString()))
//        val pair = encryptData("Test this encryption")
//
//        val decryptedData = decryptData(pair.first, pair.second)
//
//        val encrypted = pair.second.toString(Charsets.UTF_8)
//        println("Encrypted data: $encrypted")
//        println("Decrypted data: $decryptedData")
        }
        binding.loginButton.setOnClickListener{
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }
    }

    fun hasAccessToKeyStore(key: String = "MyKeyAlias"): Boolean {
        val keystore = KeyStore.getInstance("AndroidKeyStore")
        keystore.load(null)
        return keystore.getEntry(key, null) as? KeyStore.SecretKeyEntry != null
    }

//    fun encryptData(data: String): Pair<ByteArray, ByteArray> {
//        val cipher = Cipher.getInstance("AES/CBC/NoPadding")
//
//        var temp = data
//        while (temp.toByteArray().size % 16 != 0)
//            temp += "\u0020"
//
//        cipher.init(Cipher.ENCRYPT_MODE, getKey())
//
//        val ivBytes = cipher.iv
//        val encryptedBytes = cipher.doFinal(temp.toByteArray(Charsets.UTF_8))
//
//        return Pair(ivBytes, encryptedBytes)
//    }
//
//    fun decryptData(ivBytes: ByteArray, data: ByteArray): String{
//        val cipher = Cipher.getInstance("AES/CBC/NoPadding")
//        val spec = IvParameterSpec(ivBytes)
//
//        cipher.init(Cipher.DECRYPT_MODE, getKey(), spec)
//        return cipher.doFinal(data).toString(Charsets.UTF_8).trim()
//    }
}