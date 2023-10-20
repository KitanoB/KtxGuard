package com.kitano.cli

import com.kitano.cli.exceptions.InvalidCommandException
import com.kitano.cli.internal.Command
import com.kitano.cli.utils.UtilsCLI
import com.kitano.core.AlgorithmType
import java.security.PrivateKey
import java.security.PublicKey
import java.security.SecureRandom
import java.util.Base64
import kotlin.system.exitProcess
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Options
import org.koin.java.KoinJavaComponent

object CommandFactory {

    private val utilsCLI: UtilsCLI by KoinJavaComponent.inject(UtilsCLI::class.java)

    fun createCommand(cmd: CommandLine): Command? {

        val options = utilsCLI.generateCliOptions()

        if (cmd.options == null) {
            printHelp("KtxGuard", options)
            throw InvalidCommandException("No argument provided. Please provide an argument.")
        }

        if (cmd.hasOption("h") || cmd.hasOption("help")) {
            printHelp("KtxGuard", options)
            exitProcess(0)
        }

        if (cmd.hasOption("kp") || cmd.hasOption("keypair")) {
            val key = utilsCLI.generateRsaKeyPair()
            println("Public key: ${key.public}")
            println("Private key: ${key.private}")
            exitProcess(0)
        }

        val commandType = CommandType.values().firstOrNull { cmd.hasOption(it.shortOpt) || cmd.hasOption(it.longOpt) }
        var password = cmd.getOptionValue("p") ?: cmd.getOptionValue("password")
        var key = cmd.getOptionValue("k") ?: cmd.getOptionValue("key")
        if (password.isNullOrEmpty()) {
            if (cmd.hasOption("r") || cmd.hasOption("random")) {
                password = generateRandomKeyBase64(32)
            } else {
                throw InvalidCommandException("No password provided. Please provide a password.")
            }
        }

        val algorithmType = determineAlgorithm(cmd)

        if (commandType == null) {
            printHelp("KtxGuard", options)
            throw InvalidCommandException("No valid command provided. Please provide a valid command.")
        }

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

        }
    }

    private fun determineAlgorithm(cmd: CommandLine): AlgorithmType {
        val algorithm = cmd.getOptionValue("a") ?: cmd.getOptionValue("algorithm")
        return when (true) {
            algorithm.startsWith("aes", true) -> AlgorithmType.AES
            algorithm.startsWith("des", true) -> AlgorithmType.DES
            algorithm.startsWith("rsa", true) -> AlgorithmType.RSA
            else -> AlgorithmType.AES
        }
    }

    private fun generateRandomKeyBase64(length: Int): String {
        val randomBytes = ByteArray(length)
        SecureRandom().nextBytes(randomBytes)
        return Base64.getEncoder().encodeToString(randomBytes)
    }

    private fun printHelp(cmdLineSyntax: String, options: Options) {
        val formatter = HelpFormatter()
        val header = "Do something useful with an input file\n\n"
        val footer = "\nPlease report issues at http://kita.no/issues"
        formatter.printHelp(cmdLineSyntax, header, options, footer, true)
    }


}
