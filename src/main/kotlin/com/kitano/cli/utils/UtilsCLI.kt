package com.kitano.cli.utils

import java.security.KeyPair
import java.security.KeyPairGenerator
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
                .desc("The algorithm to use for encryption/decryption.").build()
        )
        //options.addOption("b", "bits", true, "The number of bits to use for the key.")
        //options.addOption(Option.builder().option("c").longOpt("check").hasArg().argName("FILE").desc("Checks the integrity of a file.").build())
        options.addOption(
            Option.builder().option("d").longOpt("decrypt").hasArg().argName("FILE").desc("Decrypts a file.").build()
        )
        options.addOption(
            Option.builder().option("df").longOpt("decryptFile").hasArg().argName("FILE").desc("Decrypts a file.")
                .build()
        )
        options.addOption(
            Option.builder().option("e").longOpt("encrypt").hasArg().argName("FILE").desc("Encrypts a file.").build()
        )
        options.addOption(
            Option.builder().option("ef").longOpt("encryptFile").hasArg().argName("FILE").desc("Encrypts a file.")
                .build()
        )
        //options.addOption("f", "force", false, "Forces the program to overwrite the output file if it exists.")
        //options.addOption("g", "generate", false, "Generates a new keypair.")
        options.addOption("h", "help", false, "Prints this help message.")
        //options.addOption("j", "json", false, "Outputs the result in JSON format.")
        //options.addOption("k", "keysize", true, "The size of the key to generate.")
        options.addOption("kp", "keypair", false, "The keypair to use for encryption/decryption.")
        options.addOption("l", "list", false, "Lists all available algorithms.")
        //options.addOption("n", "no-verify", false, "Disables verification of the output file.")
        options.addOption(
            Option.builder().option("o").longOpt("output").hasArg().argName("FILE").desc("The output file to write to.")
                .build()
        )
        options.addOption("p", "password", true, "The password to use for encryption/decryption.")
        options.addOption("r", "random", false, "Generates a random password.")
        //options.addOption("s", "silent", false, "Silences all output from the program.")
        options.addOption("t", "type", true, "The type of key to generate.")
        options.addOption("u", "public", true, "The public key to use for encryption.")
        options.addOption("w", "private", true, "The private key to use for decryption.")
        options.addOption("x", "extract", false, "Extracts the public key from a keypair.")
        options.addOption("qr", "qrcode", false, "Generates a QR code from the public key.")
        //options.addOption("y", "sign", true, "Signs a file.")
        //options.addOption("z", "verify", true, "Verifies a file.")
        return options
    }

    fun generateRsaKeyPair(): KeyPair {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(2048)
        return keyPairGenerator.generateKeyPair()
    }
}