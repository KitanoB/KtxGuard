package com.kitano.cli

import com.kitano.cli.exceptions.InvalidCommandException
import com.kitano.cli.internal.ArgumentParser
import com.kitano.cli.utils.Utils
import com.kitano.cli.utils.UtilsCLI
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.mockito.Mockito.mock

class ArgumentParserImplTest {


    private var parserImpl: ArgumentParser? = null
    @BeforeTest
    fun setup() {
        parserImpl = ArgumentParserImpl(DefaultValueStrategy())
        startKoin {
            modules(listOf(module { single { UtilsCLI() } }, module { single { Utils() }}))
        }
    }
    @Test
    fun `test parse with valid options command`() {
        val mockArgumentParser = mock(ArgumentParserImpl::class.java)
        val args = arrayOf("-e", "sampleString", "-p", "password123", "-a", "aes")
        mockArgumentParser.parse(args)
    }
    @Test(expected = InvalidCommandException::class)
    fun `test parse with invalid options command`(){
        val args = arrayOf("-xyz", "sampleString", "-p", "password123", "-a", "aes")
        parserImpl!!.parse(args)
    }

    @Test(expected = InvalidCommandException::class)
    fun `test parse with encrypt string and decrypt string at the same time options command`(){
        val args = arrayOf("-e", "sampleString", "-d", "password123", "-a", "aes")
        parserImpl!!.parse(args)
    }

    @Test(expected = InvalidCommandException::class)
    fun `test parse with encrypt string and encrypt file at the same time options command`(){
        val args = arrayOf("-e", "sampleString", "-ef", "password123", "-a", "aes")
        parserImpl!!.parse(args)
    }

    @Test(expected = InvalidCommandException::class)
    fun `test parse with decrypt string and decrypt file at the same time options command`(){
        val args = arrayOf("-d", "sampleString", "-df", "password123", "-a", "aes")
        parserImpl!!.parse(args)
    }

    @Test(expected = InvalidCommandException::class)
    fun `test parse with encrypt string and decrypt file at the same time`(){
        val args = arrayOf("-e", "sampleString", "-df", "password123", "-a", "aes")
        parserImpl!!.parse(args)
    }

    @Test(expected = InvalidCommandException::class)
    fun `test parse with encrypt file and invalid path`(){
        val args = arrayOf("-ef", "sampleString", "-p", "password123", "-a", "aes")
        parserImpl!!.parse(args)
    }

    @Test(expected = InvalidCommandException::class)
    fun `test parse with decrypt file and invalid path`(){
        val args = arrayOf("-df", "sampleString", "-p", "password123", "-a", "aes")
        parserImpl!!.parse(args)
    }

    @Test(expected = InvalidCommandException::class)
    fun `test parse with invalid file extension`(){
        val args = arrayOf("-ef", "sampleString.zip", "-p", "password123", "-a", "aes")
        parserImpl!!.parse(args) }

    @Test(expected = InvalidCommandException::class)
    fun `test parse with invalidAlgorithm`() {
        val args = arrayOf("-e", "sampleString", "-p", "password123", "-a", "invalidAlgorithm")
        parserImpl!!.parse(args)
    }

    @Test
    fun `test parse with validAlgorithm UPPERCASE`() {
        val args = arrayOf("-e", "sampleString", "-p", "password123", "-a", "AES")
        parserImpl!!.parse(args)
    }

    @Test
    fun `test parse with validAlgorithm lowercase`() {
        val args = arrayOf("-e", "sampleString", "-p", "password123", "-a", "aes")
        parserImpl!!.parse(args)
    }

    @Test
    fun `test parse with validAlgorithm mixedcase`() {
        val args = arrayOf("-e", "sampleString", "-p", "password123", "-a", "aEs")
        parserImpl!!.parse(args)
    }

    @Test(expected = InvalidCommandException::class)
    fun `test parse with validAlgorithm mixedcase and 2 same options and invalid one`() {
        val args = arrayOf("-e", "sampleString", "-p", "password123", "-a", "aEs", "-a", "invalidAlgorithm")
        parserImpl!!.parse(args)
    }

    @Test(expected = InvalidCommandException::class)
    fun `test parse with validAlgorithm mixedcase and 2 same options`() {
        val args = arrayOf("-e", "sampleString", "-p", "password123", "-a", "aEs", "-a", "aes")
        parserImpl!!.parse(args)
    }


    @Test(expected = InvalidCommandException::class)
    fun `test parse with validAlgorithm but no value passed as output`() {
        val args = arrayOf("-e", "sampleString", "-p", "password123", "-a", "aEs", "-o")
        parserImpl!!.parse(args)
    }

    @Test(expected = InvalidCommandException::class)
    fun `test parse with validAlgorithm but no value passed as output 2`() {
        val args = arrayOf("-e", "sampleString", "-p", "password123", "-a", "aEs", "-o", "-o")
        parserImpl!!.parse(args)
    }

    @Test(expected = InvalidCommandException::class)
    fun `test parse with validAlgorithm but invalid value as output`() {
        val args = arrayOf("-e", "sampleString", "-p", "password123", "-a", "aEs", "-o", "invalidPath")
        parserImpl!!.parse(args)
    }
    @AfterTest
    fun stop() = stopKoin()

}