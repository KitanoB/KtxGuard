package com.kitano.cli

import com.kitano.cli.internal.Command
import com.kitano.core.AlgorithmType
import java.security.PrivateKey
import java.security.PublicKey
import java.security.SecureRandom
import java.util.Base64
import org.apache.commons.cli.CommandLine

object CommandFactory {
    fun createCommand(cmd: CommandLine): Command? {

        val commandType = CommandType.values().firstOrNull { cmd.hasOption(it.shortOpt) || cmd.hasOption(it.longOpt) }
        var password = cmd.getOptionValue("p") ?: cmd.getOptionValue("password")
        var key = cmd.getOptionValue("k") ?: cmd.getOptionValue("key")
        if (password.isNullOrEmpty()) {
            if (cmd.hasOption("r") || cmd.hasOption("random")) {
                password = generateRandomKeyBase64(32)
            } else {
                    println("No password provided. Please provide a password.")
                    return null
            }
        }

        val algorithmType = determineAlgorithm(cmd)

        return when (commandType) {
            CommandType.ENCRYPT_STRING -> EncryptStringCommand(
                cmd.getOptionValue("e") ?: cmd.getOptionValue("encrypt"),
                password,
                key as PublicKey?,
                algorithmType
            )

            CommandType.ENCRYPT_FILE -> EncryptFileCommand(
                cmd.getOptionValue("ef") ?: cmd.getOptionValue("encryptFile"),
                password,
                key as PublicKey?,
                algorithmType,
                cmd.getOptionValue("o") ?: cmd.getOptionValue("output")
            )

            CommandType.DECRYPT_STRING -> DecryptStringCommand(
                cmd.getOptionValue("d") ?: cmd.getOptionValue("decrypt"),
                password,
                key as PrivateKey?,
                algorithmType
            )

            CommandType.DECRYPT_FILE -> DecryptFileCommand(
                cmd.getOptionValue("df") ?: cmd.getOptionValue("decryptFile"),
                password,
                key as PrivateKey?,
                algorithmType
            )

            else -> null
        }
    }

    private fun determineAlgorithm(cmd: CommandLine): AlgorithmType {
        val algorithm = cmd.getOptionValue("a") ?: cmd.getOptionValue("algorithm")
        return when (true) {
            algorithm.startsWith("aes", true) -> AlgorithmType.AES
            algorithm.startsWith("des", true) -> AlgorithmType.DES
            algorithm.startsWith("twofish", true) -> AlgorithmType.TWOFISH
            algorithm.startsWith("rsa", true) -> AlgorithmType.RSA
            else -> AlgorithmType.AES
        }
    }

    private fun generateRandomKeyBase64(length: Int): String {
        val randomBytes = ByteArray(length)
        SecureRandom().nextBytes(randomBytes)
        return Base64.getEncoder().encodeToString(randomBytes)
    }

}
