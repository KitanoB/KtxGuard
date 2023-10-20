package com.kitano.cli

import com.kitano.cli.CommandFactory.createCommand
import com.kitano.cli.exceptions.InvalidCommandException
import com.kitano.cli.utils.UtilsCLI
import com.kitano.core.AlgorithmType
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.apache.commons.cli.CommandLine
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class CommandFactoryTest {

    @BeforeTest
    fun setup() {
        startKoin {
            modules(listOf(module { single { UtilsCLI() } }))
        }
    }
    @Test
    fun `test createCommand with encrypt string option and provided password`() {
        val mockCmd = mock(CommandLine::class.java)
        `when`(mockCmd.hasOption("e")).thenReturn(true)
        `when`(mockCmd.getOptionValue("e")).thenReturn("sampleString")
        `when`(mockCmd.getOptionValue("p")).thenReturn("password123")
        `when`(mockCmd.getOptionValue("a")).thenReturn("aes")
        `when`(mockCmd.options).thenReturn(arrayOf())

        val command = createCommand(mockCmd)

        assertTrue(command is EncryptStringCommand)
        assertEquals("sampleString", command.input)
        assertEquals("password123", command.password)
    }

    @Test
    fun `test createCommand with encrypt string option and no algorithm provided`() {
        val mockCmd = mock(CommandLine::class.java)
        `when`(mockCmd.hasOption("e")).thenReturn(true)
        `when`(mockCmd.getOptionValue("e")).thenReturn("sampleString")
        `when`(mockCmd.hasOption("r")).thenReturn(true)
        `when`(mockCmd.getOptionValue("a")).thenReturn("aes")
        `when`(mockCmd.options).thenReturn(arrayOf())

        val command = createCommand(mockCmd)

        assertTrue(command is EncryptStringCommand)
        assertEquals("sampleString", command.input)
        assertTrue(command.password != null)
        assertEquals(command.algorithmType, AlgorithmType.AES)
    }

    @Test(expected = InvalidCommandException::class)
    fun `when createCommand with encrypt string option and no password`(){
        val mockCmd = mock(CommandLine::class.java)
        `when`(mockCmd.hasOption("e")).thenReturn(true)
        `when`(mockCmd.getOptionValue("e")).thenReturn("sampleString")
        `when`(mockCmd.getOptionValue("a")).thenReturn("aes")
        `when`(mockCmd.options).thenReturn(arrayOf())

        val command = createCommand(mockCmd)
        assertTrue(command is EncryptStringCommand)
    }

    @Test(expected = InvalidCommandException::class)
    fun `when createCommand has no argument the function execute is not called`(){
        val mockCmd = mock(CommandLine::class.java)
        val command = createCommand(mockCmd)
        assertTrue(command == null)
    }

    @AfterTest
    fun stop() = stopKoin()

}