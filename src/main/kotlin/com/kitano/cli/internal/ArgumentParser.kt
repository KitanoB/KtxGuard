package com.kitano.cli.internal

/**
 * Interface for parsing command line arguments
 * @see ArgumentParserImpl
 * Created by KitanoB on 2023/10/10.
 */
interface ArgumentParser {
    fun parse(args: Array<out String?>)
}