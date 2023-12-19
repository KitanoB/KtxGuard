package com.kitano.cli.internal.utils

import com.kitano.cli.internal.exceptions.InvalidCommandException
import com.kitano.crypto.internal.enums.AlgorithmType
import java.nio.file.Files
import java.nio.file.Paths
import java.security.KeyPair
import java.security.KeyPairGenerator
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options

/**
 * Class that contains utility functions for the CLI.
 * Created by KitanoB on 2023/10/10.
 */
class UtilsCLI {

    /**
     * Generates the CLI options.
     * @return The CLI options.
     */
    fun generateCliOptions(): Options {
        val options = Options()
        options.addOption(
            Option.builder().option("a").longOpt("algorithm").hasArg().argName("ALGORITHM")
                .desc("The algorithm to use for encryption/decryption.").required().build()
        )
        options.addOption(
            Option.builder().option("d").longOpt("decrypt").hasArg().argName("FILE").desc("Decrypts a string.").build()
        )
        options.addOption(
            Option.builder().option("df").longOpt("decryptFile").hasArg().argName("FILE").desc("Decrypts a file.")
                .build()
        )
        options.addOption(
            Option.builder().option("e").longOpt("encrypt").hasArg().argName("FILE").desc("Encrypts a string.").build()
        )
        options.addOption(
            Option.builder().option("ef").longOpt("encryptFile").hasArg().argName("FILE").desc("Encrypts a file.")
                .build()
        )
        options.addOption("h", "help", false, "Prints this help message.")
        options.addOption("l", "list", false, "Lists all available algorithms.")
        options.addOption(
            Option.builder().option("o").longOpt("output").hasArg().argName("FILE").desc("The output file to write to.")
                .build()
        )
        options.addOption(
            Option.builder().option("p").longOpt("password").hasArg().argName("PASSWORD")
                .desc("The password to use for encryption/decryption.").required().build()
        )
        options.addOption("r", "random", false, "Generates a random password.")
        options.addOption("qr", "qrcode", false, "Generates a QR code from the public key.")
        return options
    }

    fun generateRsaKeyPair(): KeyPair {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(2048)
        return keyPairGenerator.generateKeyPair()
    }

    fun isValidFilePath(filepath: String): Boolean {
        val path = Paths.get(filepath)

        return Files.exists(path) &&
                Files.isReadable(path) &&
                Files.isRegularFile(path)
    }

    fun hasValidExtension(filepath: String, validExtensions: List<String> = listOf("txt", "md")): Boolean {
        val extension = filepath.substringAfterLast('.', "")
        return extension in validExtensions
    }

    fun validate(cmd: CommandLine) {

        if (cmd.hasOption("a") || cmd.hasOption("algorithm")) {
            val algorithm = cmd.getOptionValue("a").uppercase()
            if (!algorithm.isNullOrEmpty() && !algorithm.isNullOrBlank()) {
                val algorithmAvaivable = AlgorithmType.values().any { it.name.uppercase() == algorithm }
                if (!algorithmAvaivable) {
                    throw InvalidCommandException("The algorithm is invalid.")
                }
            } else if (!cmd.args.isNullOrEmpty())  {
                throw InvalidCommandException("The algorithm is invalid.")
            }
        }

        if (cmd.hasOption("e") && cmd.hasOption("d")) {
            throw InvalidCommandException("Cannot encrypt and decrypt at the same time.")
        } else if (cmd.hasOption("e") && cmd.hasOption("ef")) {
            throw InvalidCommandException("Cannot encrypt a string and encrypt a file at the same time.")
        } else if (cmd.hasOption("d") && cmd.hasOption("df")) {
            throw InvalidCommandException("Cannot decrypt a string and decrypt a file at the same time.")
        } else if (cmd.hasOption("e") && cmd.hasOption("d")) {
            throw InvalidCommandException("Cannot encrypt a string and decrypt a string at the same time.")
        } else if (cmd.hasOption("ef") && cmd.hasOption("df")) {
            throw InvalidCommandException("Cannot encrypt a file and decrypt a file at the same time.")
        }

        if (cmd.hasOption("ef") || cmd.hasOption("df")) {
            val filePath = cmd.getOptionValue("ef") ?: cmd.getOptionValue("df")
            if (!isValidFilePath(filePath) || !hasValidExtension(filePath)) {
                throw InvalidCommandException("The file path is invalid.")
            }
        }

        if (cmd.hasOption("o") || cmd.hasOption("output")) {
            val filePath = cmd.getOptionValue("o") ?: cmd.getOptionValue("output")
            if (!isValidFilePath(filePath) || !hasValidExtension(filePath)) {
                throw InvalidCommandException("The file path is invalid.")
            }
        }

        if (cmd.hasOption("e") || cmd.hasOption("encrypt")) {
            if (cmd.getOptionValue("e") == null && cmd.getOptionValue("encrypt") == null) {
                throw InvalidCommandException("No string to encrypt provided.")
            }
        }

        if (cmd.hasOption("ef") || cmd.hasOption("encryptFile")) {
            if (cmd.getOptionValue("ef") == null && cmd.getOptionValue("encryptFile") == null) {
                throw InvalidCommandException("No file to encrypt provided.")
            }
        }

        if (cmd.hasOption("d") || cmd.hasOption("decrypt")) {
            if (cmd.getOptionValue("d") == null && cmd.getOptionValue("decrypt") == null) {
                throw InvalidCommandException("No string to decrypt provided.")
            }
        }

        if (cmd.hasOption("df") || cmd.hasOption("decryptFile")) {
            if (cmd.getOptionValue("df") == null && cmd.getOptionValue("decryptFile") == null) {
                throw InvalidCommandException("No file to decrypt provided.")
            }
        }

        if (cmd.hasOption("p") || cmd.hasOption("password")) {
            if (cmd.getOptionValue("p") == null && cmd.getOptionValue("password") == null) {
                throw InvalidCommandException("No password provided.")
            }
        }


        // check if there is multiple time the same option
        val options = cmd.options
        val availableOptions = generateCliOptions().options.stream().map { it.opt.uppercase() }.toList()
        val optionsList = mutableListOf<String>()
        for (option in options) {
            if (optionsList.contains(option.opt) || !availableOptions.contains(option.opt.uppercase())) {
                throw InvalidCommandException("The option ${option.opt} is provided multiple times.")
            } else {
                optionsList.add(option.opt)
            }
        }

    }


}