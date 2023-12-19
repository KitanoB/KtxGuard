package com.kitano.cli.internal.parse

import org.jline.terminal.Terminal
import org.jline.terminal.TerminalBuilder

class TerminalFactory {
    companion object {
        fun build(dumbTerminal: Boolean): Terminal {
            return if (!dumbTerminal) TerminalBuilder.builder().system(true).build()
            else TerminalBuilder.builder().dumb(true).build()
        }
    }
}