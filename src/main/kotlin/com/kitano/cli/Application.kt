package com.kitano.cli

import com.kitano.cli.internal.ArgumentParser
import com.kitano.cli.internal.MissingArgumentStrategy
import com.kitano.cli.internal.UserInputProvider
import com.kitano.cli.utils.Utils
import com.kitano.di.initKoin
import io.ktor.server.application.Application
import org.koin.java.KoinJavaComponent.inject

fun main(vararg args: String?) {
    initKoin()

    val utils: Utils by inject(Utils::class.java)
    utils.showStartupAsciiBanner()

    val strategy = selectStrategy(args)
    val argumentParser: ArgumentParser = ArgumentParserImpl(strategy)
    argumentParser.parse(args)
}

fun selectStrategy(args: Array<out String?>): MissingArgumentStrategy {
    val optionsRequiringValue =
        listOf("p", "password", "a", "algorithm", "ef", "encrypt file", "df", "decrypt file", "o", "output")

    val userInputProvider: UserInputProvider by inject(UserInputProvider::class.java)

    if (args.isEmpty()) {
        return UserPromptStrategy(userInputProvider)
    }

    for (option in optionsRequiringValue) {
        if (args.contains(option) && args[args.indexOf(option) + 1]?.isBlank() == true) {
            return UserPromptStrategy(userInputProvider)
        }
    }

    return DefaultValueStrategy()
}

fun Application.module() {}
