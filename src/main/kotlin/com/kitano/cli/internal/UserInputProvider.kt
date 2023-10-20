package com.kitano.cli.internal

interface UserInputProvider {
    fun getUserInput(prompt: String): String
}