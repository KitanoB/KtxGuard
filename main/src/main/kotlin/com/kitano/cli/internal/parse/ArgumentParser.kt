package com.kitano.cli.internal.parse

import com.kitano.cli.internal.exceptions.InvalidCommandException

/**
 * Interface for parsing command line arguments
 * @see ArgumentParserImpl
 * Created by KitanoB on 2023/10/10.
 */
interface ArgumentParser {
    @Throws(InvalidCommandException::class)
    fun parse(args: Array<out String?>)
}