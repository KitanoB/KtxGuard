package com.kitano.cli

import com.kitano.cli.internal.Command
import com.kitano.core.AlgorithmType
import com.kitano.core.CrypterContext
import com.kitano.core.CrypterFactory
import java.security.PublicKey

/**
 * Decrypts a string using the given password
 *
 * @param input The string to decrypt
 * @param password The password to use for decryption
 * @see Command
 * Created by KitanoB on 2023/10/10.
 */
class EncryptStringCommand(
    val input: String,
    val password: String?,
    val publicKey: PublicKey?,
    val algorithmType: AlgorithmType,
) : Command {
    override fun execute() {
        val crypterContext = CrypterContext(CrypterFactory.createCrypter(algorithmType))
        val encryptedData = crypterContext.encrypt(input, password, publicKey)
        println(encryptedData)
    }
}