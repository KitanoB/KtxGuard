package com.kitano.cli

import com.kitano.cli.internal.ArgumentParser
import com.kitano.cli.utils.Utils
import com.kitano.cli.utils.UtilsCLI
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.mockito.Mock
import org.mockito.Mockito.anyString
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.atLeast
import org.mockito.kotlin.verify

class PromptUserTest {

    private var parserImpl: ArgumentParser? = null

    @Mock
    private val consoleInputProvider: ConsoleInputProvider? = null

    @BeforeTest
    fun setup() {
        MockitoAnnotations.openMocks(this)
        `when`(consoleInputProvider?.getUserInput("Please provide an action (encrypt, decrypt): "))
            .thenReturn("encrypt")
        `when`(consoleInputProvider?.getUserInput("Is it for a file? (y/n): ")).thenReturn("n")
        `when`(consoleInputProvider?.getUserInput("Please provide a string to encrypt: "))
            .thenReturn("test")
        `when`(consoleInputProvider?.getUserInput("Please provide a password: "))
            .thenReturn("password")
        `when`(consoleInputProvider?.getUserInput("Please provide an algorithm (AES, DES, RSA): "))
            .thenReturn("AES")

        parserImpl = ArgumentParserImpl(UserPromptStrategy(consoleInputProvider!!))
        startKoin {
            modules(listOf(module { single { UtilsCLI() } }, module { single { Utils() } }))
        }
    }
    @Test
    fun `when user does not provide arguments a prompt will be displayed`() {
        ArgumentParserImpl(UserPromptStrategy(consoleInputProvider!!)).parse(arrayOf())
        verify(consoleInputProvider, atLeast(1)).getUserInput(anyString())
    }

    @AfterTest
    fun tearDown() {
        parserImpl = null
        stopKoin()
    }

}