package com.kitano.cli.internal.commands

import com.kitano.cli.internal.commands.factories.CryptServiceFactory
import com.kitano.crypto.internal.enums.AlgorithmType
import java.io.File
import java.security.PrivateKey
import org.koin.java.KoinJavaComponent.inject

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


    private val cryptServiceFactory by inject<CryptServiceFactory>(CryptServiceFactory::class.java)

    override fun execute() {
        val file = File(filePath)
        val fileContent = file.readText()
        val decryptedData = cryptServiceFactory.create(algorithmType)
            .decrypt(fileContent, password, algorithmType, privateKey)
        file.writeText(decryptedData)
    }
}