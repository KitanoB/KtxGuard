package com.kitano.cli.internal.parse

import com.kitano.cli.internal.parse.UserPromptStrategy.CLIContext.expectingFilePath
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options
import org.jline.reader.LineReader

class UserPromptStrategy(private val lineReader: LineReader) : MissingArgumentStrategy {

    object CLIContext {
        var expectingFilePath = false
    }


    private val optionPrompts = mapOf(
        "p" to "Please provide a password: ",
        "a" to "Please provide an algorithm (AES, DES, RSA): "
    )

    override fun handleMissingArguments(cmd: CommandLine): CommandLine {
        val newOptions = Options()
        val actionOption = determineAction(newOptions)
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
        return lineReader.readLine(prompt).trim()
    }

    private fun getActionPrompt(action: String): String {
        return when (action.trim()) {
            "e", "d" -> "Please provide a string to ${if (action == "e") "encrypt" else "decrypt"}: "
            "ef", "df" -> "Please provide a file (Drag&Drop available) to ${if (action == "ef") "encrypt" else "decrypt"}: "
            else -> throw IllegalArgumentException("Invalid action")
        }
    }

    private fun determineAction(newOptions: Options): String {
        val action = getUserInput("Please provide an action (encrypt, decrypt): ")
        val forFile = isActionForFile()
        val actionOption = determineActionOption(action, forFile)

        val newOption = Option.builder(actionOption).hasArg().argName(actionOption).build()
        newOptions.addOption(newOption)

        return actionOption
    }


    private fun isActionForFile(): Boolean {
        val value = getUserInput("Is it for a file? (y/n): ")
        expectingFilePath = value.equals("y", ignoreCase = true) || value.equals("yes", ignoreCase = true)
        return expectingFilePath
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
