package com.kitano.cli

import com.kitano.cli.internal.Command
import com.kitano.core.AlgorithmType
import com.kitano.core.CrypterContext
import com.kitano.core.CrypterFactory
import java.io.File
import java.security.PublicKey

/**
 * Encrypts a file using the given password
 *
 * @param filePath The path to the file to encrypt
 * @param password The password to use for encryption
 * @see Command
 * Created by KitanoB on 2023/10/10.
 */
class EncryptFileCommand(
    private val filePath: String,
    val password: String?,
    private val publicKey: PublicKey?,
    private val algorithmType: AlgorithmType,
    private val newFilePath: String?,
) : Command {
    override fun execute() {
        val file = File(filePath)
        val fileContent = file.readText()
        val crypterContext = CrypterContext(CrypterFactory.createCrypter(algorithmType))
        val encryptedData = crypterContext.encrypt(fileContent, password, publicKey)
        if (!newFilePath.isNullOrEmpty()) {
            File(newFilePath).writeText(encryptedData)
        } else file.writeText(encryptedData)
    }
}