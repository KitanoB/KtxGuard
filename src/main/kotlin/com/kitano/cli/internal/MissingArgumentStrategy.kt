package com.kitano.cli.internal

import org.apache.commons.cli.CommandLine

interface MissingArgumentStrategy {
    fun handleMissingArguments(cmd: CommandLine): CommandLine
}
