package com.kitano.cli.internal.commands

import com.kitano.cli.internal.commands.factories.CryptServiceFactory
import com.kitano.crypto.internal.enums.AlgorithmType
import java.security.PrivateKey
import org.koin.java.KoinJavaComponent

/**
 * Decrypts a string using the given password
 *
 * @param input The string to decrypt
 * @param password The password to use for decryption
 * @see Command
 * Created by KitanoB on 2023/10/10.
 */
class DecryptStringCommand(
    val input: String,
    val password: String?,
    private val privateKey: PrivateKey?,
    private val algorithmType: AlgorithmType,
) : Command {

    private val cryptServiceFactory by KoinJavaComponent.inject<CryptServiceFactory>(CryptServiceFactory::class.java)

    override fun execute() {
        val decryptedData = cryptServiceFactory.create(algorithmType)
            .decrypt(input, password, algorithmType, privateKey)
        println(decryptedData)
    }
}
