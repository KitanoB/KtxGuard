package com.kitano.cli.internal.parse

import com.kitano.cli.internal.commands.factories.CommandFactory
import com.kitano.cli.internal.utils.UtilsCLI
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.CommandLineParser
import org.apache.commons.cli.DefaultParser
import org.koin.java.KoinJavaComponent.inject


/**
 * Argument parser implementation
 * @see ArgumentParser
 * Created by KitanoB on 2019/10/10.
 */
class ArgumentParserImpl(private val missingArgumentStrategy: MissingArgumentStrategy) : ArgumentParser {

    private val utilsCLI: UtilsCLI by inject(UtilsCLI::class.java)

    override fun parse(args: Array<out String?>) {

        val optionsAvailable = utilsCLI.generateCliOptions()
        val parser: CommandLineParser = DefaultParser()

        var cmd: CommandLine? = null


        try {
            cmd = parser.parse(optionsAvailable, args)
        } catch (e: org.apache.commons.cli.ParseException) {
            // do nothing
        }

        if (cmd == null || cmd.options.isNullOrEmpty()) {
            cmd = missingArgumentStrategy.handleMissingArguments(CommandLine.Builder().build())
        }

        utilsCLI.validate(cmd)

        val command = CommandFactory.createCommand(cmd)
        command?.execute()
    }
}