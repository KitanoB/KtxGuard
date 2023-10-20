package com.kitano.cli

import com.kitano.cli.internal.UserInputProvider
import java.util.Scanner

class ConsoleInputProvider : UserInputProvider {
    override fun getUserInput(prompt: String): String {
        print(prompt)
        return Scanner(System.`in`).nextLine().trim()
    }
}