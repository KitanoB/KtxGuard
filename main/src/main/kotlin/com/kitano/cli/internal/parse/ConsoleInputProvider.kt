package com.kitano.cli.internal.parse

import java.util.Scanner

class ConsoleInputProvider : UserInputProvider {
    override fun getUserInput(prompt: String): String {
        return Scanner(System.`in`).nextLine().trim()
    }
}