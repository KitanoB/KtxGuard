package com.kitano.cli

import com.kitano.cli.internal.MissingArgumentStrategy
import org.apache.commons.cli.CommandLine

class DefaultValueStrategy : MissingArgumentStrategy {
    override fun handleMissingArguments(cmd: CommandLine): CommandLine {
        return cmd
    }

}