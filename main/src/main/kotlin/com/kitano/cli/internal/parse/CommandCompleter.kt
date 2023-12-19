package com.kitano.cli.internal.parse

import org.jline.builtins.Completers
import org.jline.reader.Completer
import org.jline.reader.LineReader


class CommandCompleter : Completer {
    private val fileNameCompleter = Completers.FileNameCompleter()
    override fun complete(
        reader: LineReader,
        line: org.jline.reader.ParsedLine,
        candidates: MutableList<org.jline.reader.Candidate>,
    ) {
        val buffer = line.line().lowercase().trim()

        when {
            buffer.startsWith("e") -> {
                candidates.addAll(listOf("encrypt").map { org.jline.reader.Candidate(it) })
            }
            buffer.startsWith("d") -> {
                candidates.addAll(listOf( "decrypt").map { org.jline.reader.Candidate(it) })
            }
            buffer.matches(".*encrypt file.*|.*decrypt file.*".toRegex()) -> {
                fileNameCompleter.complete(reader, line, candidates)
            }
            buffer.matches(".*encrypt.*|.*decrypt.*".toRegex()) -> {
                val words = arrayOf("encrypt", "decrypt")
                words.filter { it.startsWith(buffer.split(" ").last()) }.forEach { candidates.add(org.jline.reader.Candidate(it)) }
            }
            buffer.startsWith("y") -> {
                candidates.addAll(listOf( "yes").map { org.jline.reader.Candidate(it) })
            }
            buffer.startsWith("n") -> {
                candidates.addAll(listOf( "n").map { org.jline.reader.Candidate(it) })
            }
        }
    }

}