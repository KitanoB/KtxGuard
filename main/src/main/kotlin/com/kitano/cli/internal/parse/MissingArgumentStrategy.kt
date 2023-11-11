package com.kitano.cli.internal.parse

import org.apache.commons.cli.CommandLine

interface MissingArgumentStrategy {
    fun handleMissingArguments(cmd: CommandLine): CommandLine
}
