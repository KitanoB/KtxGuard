package com.kitano.di

import com.kitano.cli.ArgumentParserImpl
import com.kitano.cli.utils.UtilsCLI
import com.kitano.cli.internal.ArgumentParser
import com.kitano.cli.utils.Utils
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin() {
    val argParserModule = module {
        single<ArgumentParser> { ArgumentParserImpl() }
    }
    val utilsModule = module {
        single { Utils() }
    }
    val utilsCLI = module {
        single { UtilsCLI() }
    }

    startKoin {
        modules(listOf(argParserModule, utilsModule, utilsCLI))
    }
}