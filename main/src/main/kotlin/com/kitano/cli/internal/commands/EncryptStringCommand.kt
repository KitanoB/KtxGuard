package com.kitano.cli.internal.commands

import com.kitano.cli.internal.commands.factories.CryptServiceFactory
import com.kitano.crypto.internal.enums.AlgorithmType
import java.security.PublicKey
import org.koin.java.KoinJavaComponent

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

    private val cryptServiceFactory by KoinJavaComponent.inject<CryptServiceFactory>(CryptServiceFactory::class.java)

    override fun execute() {
        val encryptedData = cryptServiceFactory.create(algorithmType)
            .encrypt(input, password, algorithmType, publicKey)
        println(encryptedData)
    }
}