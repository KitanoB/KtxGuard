package com.kitano.di

import com.kitano.cli.ConsoleInputProvider
import com.kitano.cli.internal.UserInputProvider
import com.kitano.cli.utils.Utils
import com.kitano.cli.utils.UtilsCLI
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin() {
    val utilsModule = module {
        single { Utils() }
    }
    val utilsCLI = module {
        single { UtilsCLI() }
    }

    val userInput = module {
        single<UserInputProvider> { ConsoleInputProvider() }
    }

    startKoin {
        modules(listOf(utilsModule, utilsCLI, userInput))
    }
}