package com.kitano.cli

import com.kitano.cli.internal.MissingArgumentStrategy
import com.kitano.cli.internal.UserInputProvider
import java.util.Scanner
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options

class UserPromptStrategy(private val inputProvider: UserInputProvider) : MissingArgumentStrategy {

    private val optionPrompts = mapOf(
        "p" to "Please provide a password: ",
        "a" to "Please provide an algorithm (AES, DES, RSA): "
    )

    override fun handleMissingArguments(cmd: CommandLine): CommandLine {
        val scanner = Scanner(System.`in`)


        val newOptions = Options()
        val actionOption = determineAction(scanner, newOptions)
        val actionPrompt = getActionPrompt(actionOption)
        val actionValue = getUserInput(actionPrompt)
        val newArgsList = mutableListOf<String>("-$actionOption", actionValue)

        cmd.options.forEach {
            newOptions.addOption(it)
            newArgsList.add("-${it.opt}")
            cmd.getOptionValues(it.opt)?.forEach { value -> newArgsList.add(value) }
        }

        for ((option, prompt) in optionPrompts) {
            if (!cmd.hasOption(option) || cmd.getOptionValue(option).isNullOrEmpty()) {
                val inputValue = getUserInput(prompt)
                val newOption = Option.builder(option).hasArg().argName(option).build()
                newOptions.addOption(newOption)

                newArgsList.add("-$option")
                newArgsList.add(inputValue)
            }
        }

        return DefaultParser().parse(newOptions, newArgsList.toTypedArray())
    }

    private fun getUserInput(prompt: String): String {
        return inputProvider.getUserInput(prompt)
    }

    private fun getActionPrompt(action: String): String {
        return when (action) {
            "e", "d" -> "Please provide a string to ${if (action == "e") "encrypt" else "decrypt"}: "
            "ef", "df" -> "Please provide a file to ${if (action == "ef") "encrypt" else "decrypt"}: "
            else -> throw IllegalArgumentException("Invalid action")
        }
    }

    private fun determineAction(scanner: Scanner, newOptions: Options): String {
        val action = getUserInput("Please provide an action (encrypt, decrypt): ")
        val forFile = isActionForFile(scanner)
        val actionOption = determineActionOption(action, forFile)

        val newOption = Option.builder(actionOption).hasArg().argName(actionOption).build()
        newOptions.addOption(newOption)

        return actionOption
    }


    private fun isActionForFile(scanner: Scanner): Boolean {
        val value = getUserInput("Is it for a file? (y/n): ")
        return value.equals("y", ignoreCase = true)
    }

    private fun determineActionOption(action: String, forFile: Boolean): String {
        return when {
            forFile && action.equals("encrypt", ignoreCase = true) -> "ef"
            forFile && action.equals("decrypt", ignoreCase = true) -> "df"
            !forFile && action.equals("encrypt", ignoreCase = true) -> "e"
            !forFile && action.equals("decrypt", ignoreCase = true) -> "d"
            else -> throw IllegalArgumentException("Invalid action or file option")
        }
    }
}
