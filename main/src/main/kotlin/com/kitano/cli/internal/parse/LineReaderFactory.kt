package com.kitano.cli.internal.parse

import org.jline.reader.LineReader
import org.jline.reader.LineReaderBuilder

class LineReaderFactory {
    companion object {
        fun create(isDumbTerminal: Boolean): LineReader {
            return LineReaderBuilder
                .builder()
                .terminal(
                    TerminalFactory
                        .build(isDumbTerminal)
                )
                .completer(CommandCompleter())
                .build()
        }
    }
}