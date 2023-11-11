package com.kitano.cli.internal.parse

import org.apache.commons.cli.CommandLine

class DefaultValueStrategy : MissingArgumentStrategy {
    override fun handleMissingArguments(cmd: CommandLine): CommandLine {
        return cmd
    }

}