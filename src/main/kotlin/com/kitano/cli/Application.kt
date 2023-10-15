package com.kitano.cli

import com.kitano.cli.internal.ArgumentParser
import com.kitano.di.initKoin
import com.kitano.cli.utils.Utils
import io.ktor.server.application.Application
import org.koin.java.KoinJavaComponent.inject

val utils: Utils by inject(Utils::class.java)
val argumentParser: ArgumentParser by inject(ArgumentParser::class.java)
fun main(vararg args: String?) {
    initKoin()
    utils.showStartupAsciiBanner()
    argumentParser.parse(args)
}

fun Application.module() {}
