package com.example.myapplication

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import javax.crypto.KeyGenerator

internal class SignUpViewModel(
    private val keyGenerator: KeyGenerator = KeyGenerator.getInstance(
        KeyProperties.KEY_ALGORITHM_AES,
        "AndroidKeyStore"
    )
) {

    fun generateKeyWithEmailPassword(
        email: String,
        password: String,
    ) {
        val keyGenParameterSpecification = KeyGenParameterSpec.Builder(
            email + password,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()

        keyGenerator.init(keyGenParameterSpecification)
        keyGenerator.generateKey()
    }
}