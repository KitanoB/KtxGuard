package com.kitano.cli.internal.commands

import com.kitano.cli.internal.commands.factories.CryptServiceFactory
import com.kitano.crypto.internal.enums.AlgorithmType
import java.io.File
import java.security.PublicKey
import org.koin.java.KoinJavaComponent

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

    private val cryptServiceFactory by KoinJavaComponent.inject<CryptServiceFactory>(CryptServiceFactory::class.java)

    override fun execute() {
        val file = File(filePath)
        val fileContent = file.readText()
        val encryptedData = cryptServiceFactory.create(algorithmType)
            .encrypt(fileContent, password, algorithmType, publicKey)
        if (!newFilePath.isNullOrEmpty()) {
            File(newFilePath).writeText(encryptedData)
        } else file.writeText(encryptedData)
    }
}