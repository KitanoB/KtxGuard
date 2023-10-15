package com.kitano.cli

import com.kitano.cli.internal.ArgumentParser
import com.kitano.cli.utils.UtilsCLI
import kotlin.system.exitProcess
import org.apache.commons.cli.CommandLineParser
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Options
import org.koin.java.KoinJavaComponent.inject

/**
 * Argument parser implementation
 * @see ArgumentParser
 * Created by KitanoB on 2019/10/10.
 */
class ArgumentParserImpl : ArgumentParser {

    private val utilsCLI: UtilsCLI by inject(UtilsCLI::class.java)
    override fun parse(args: Array<out String?>) {

        val parser: CommandLineParser = DefaultParser()
        val options = utilsCLI.generateCliOptions()

        if (args.isEmpty()) {
            println("No arguments provided. Please provide a command-line argument.")
            printHelp("KtxGuard", options)
            exitProcess(0)
        }

        val cmd = parser.parse(options, args)

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

        val command = CommandFactory.createCommand(cmd)
        command?.execute()
    }

    private fun printHelp(cmdLineSyntax: String, options: Options) {
        val formatter = HelpFormatter()
        val header = "Do something useful with an input file\n\n"
        val footer = "\nPlease report issues at http://kita.no/issues"
        formatter.printHelp(cmdLineSyntax, header, options, footer, true)
    }

    private fun exitWithError(error: String) {
        println(error)
        exitProcess(1)
    }

}