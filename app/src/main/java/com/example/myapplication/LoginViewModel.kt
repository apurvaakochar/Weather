package com.example.myapplication

import java.security.KeyStore

internal class LoginViewModel(private val keyStore: KeyStore = KeyStore.getInstance("AndroidKeyStore")) {

    fun hasKeyToUnlock(key: String): Boolean {
        keyStore.load(null)
        return (keyStore.getEntry(key, null) as? KeyStore.SecretKeyEntry) != null
    }
}