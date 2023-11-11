package com.kitano.di

import com.kitano.cli.internal.commands.factories.CryptServiceFactory
import com.kitano.cli.internal.parse.UserInputProvider
import com.kitano.cli.internal.parse.ConsoleInputProvider
import com.kitano.cli.internal.utils.Utils
import com.kitano.cli.internal.utils.UtilsCLI
import com.kitano.crypto.CryptService
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

    val cryptService = module {
        single { CryptService() }
    }

    val cryptServiceFactory = module {
        single { CryptServiceFactory() }
    }

    startKoin {
        modules(listOf(utilsModule, utilsCLI, userInput, cryptService, cryptServiceFactory))
    }
}