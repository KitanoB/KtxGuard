package com.kitano.cli

import com.kitano.cli.internal.Command
import com.kitano.core.AlgorithmType
import com.kitano.core.CrypterContext
import com.kitano.core.CrypterFactory
import java.io.File
import java.security.PrivateKey

/**
 * Decrypts a file using the given password
 *
 * @param filePath The path to the file to decrypt
 * @param password The password to use for decryption
 * Created by KitanoB on 2023/10/10.
 */
class DecryptFileCommand(
    private val filePath: String,
    val password: String?,
    private val privateKey: PrivateKey?,
    private val algorithmType: AlgorithmType,
) : Command {
    override fun execute() {
        val file = File(filePath)
        val fileContent = file.readText()
        val crypterContext = CrypterContext(CrypterFactory.createCrypter(algorithmType))
        val decryptedData = crypterContext.decrypt(fileContent, password, privateKey)
        file.writeText(decryptedData)
    }
}