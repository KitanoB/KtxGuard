package com.kitano.cli

enum class CommandType(val shortOpt: String, val longOpt: String) {
    ENCRYPT_STRING("e", "encrypt"),
    ENCRYPT_FILE("ef", "encryptFile"),
    DECRYPT_STRING("d", "decrypt"),
    DECRYPT_FILE("df", "decryptFile")
}