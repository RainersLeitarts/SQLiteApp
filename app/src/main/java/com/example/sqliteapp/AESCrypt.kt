package com.example.sqliteapp

import android.annotation.SuppressLint
import android.util.Base64
import java.nio.charset.Charset
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class AESCrypt {
    private val ALGORITHM = "AES"
    private val KEY = "xKKqMWbfUreRXTkb"

    @SuppressLint("GetInstance")
    fun encrypt(password: String): String {
        val key: Key = this.generateKey()
        val cipher: Cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encryptedByteValue: ByteArray =
            cipher.doFinal(password.toByteArray(Charset.forName("utf-8")))
        return Base64.encodeToString(encryptedByteValue, Base64.DEFAULT)
    }

    @SuppressLint("GetInstance")
    fun decrypt(password: String): String {
        val key: Key = this.generateKey()
        val cipher: Cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decryptedValue64: ByteArray = Base64.decode(password, Base64.DEFAULT)
        val decryptedByteValue: ByteArray = cipher.doFinal(decryptedValue64)
        //Maybe remove charset
        return String(decryptedByteValue, Charset.forName("utf-8"))
    }


    private fun generateKey(): Key {
        return SecretKeySpec(KEY.toByteArray(), ALGORITHM)
    }
}