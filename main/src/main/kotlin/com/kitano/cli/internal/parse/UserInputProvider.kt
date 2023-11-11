package com.kitano.cli.internal.parse

interface UserInputProvider {
    fun getUserInput(prompt: String): String
}