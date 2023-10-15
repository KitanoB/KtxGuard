package com.kitano.core

/**
 * Constants for the encryption algorithms
 * Created by KitanoB on 2023/10/10.
 */
object CryptoConstants {
    const val AES_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding"
    const val DES_CBC_PKCS5PADDING = "DES/CBC/PKCS5Padding"
    const val RSA_ECB_PKCS1PADDING = "RSA/ECB/PKCS1Padding"
    const val TWOFISH_CBC_PKCS5PADDING = "TWOFISH/CBC/PKCS5Padding"

    const val AES_TRANSFORMATION = "AES/CBC/PKCS5Padding"
    const val AES_KEY_SIZE = 256
    const val AES_IV_SIZE = 16


    const val PBKDF2_HMAC_SHA256_ALGORITHM = "PBKDF2WithHmacSHA256"

    const val DES_TRANSFORMATION = "DES/CBC/PKCS5Padding"
    const val DES_KEY_SIZE = 64
    const val DES_IV_SIZE = 8


    const val TWOFISH_TRANSFORMATION = "Twofish/CBC/PKCS5Padding"
    const val TWOFISH_KEY_SIZE = 256
    const val TWOFISH_IV_SIZE = 16

    const val RSA_TRANSFORMATION = "RSA/ECB/PKCS1Padding"

}