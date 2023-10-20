package com.kitano.cli

import com.kitano.cli.exceptions.InvalidCommandException
import com.kitano.cli.utils.UtilsCLI
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.Option
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class UtilsCliTest {

    private var utilsCli: UtilsCLI? = null

    @Before
    fun setup() {
        utilsCli = UtilsCLI()
    }

    @Test
    fun `when generateOptions is asked a list of Options is returned`() {
        val options = utilsCli!!.generateCliOptions()
        assertTrue(options.options.isNotEmpty())
    }

    @Test
    fun `when generateOptions is asked a list of Options is returned with the correct options`() {
        val options = utilsCli!!.generateCliOptions()
        assertTrue(options.hasOption("e"))
        assertTrue(options.hasOption("d"))
        assertTrue(options.hasOption("ef"))
        assertTrue(options.hasOption("df"))
        assertTrue(options.hasOption("p"))
        assertTrue(options.hasOption("a"))
    }

    @Test
    fun `when generateOptions is asked a list of Options is returned with the correct options description`() {
        val options = utilsCli!!.generateCliOptions()
        assertTrue(options.getOption("e").description == "Encrypts a string.")
        assertTrue(options.getOption("d").description == "Decrypts a string.")
        assertTrue(options.getOption("ef").description == "Encrypts a file.")
        assertTrue(options.getOption("df").description == "Decrypts a file.")
        assertTrue(options.getOption("p").description == "The password to use for encryption/decryption.")
        assertTrue(options.getOption("a").description == "The algorithm to use for encryption/decryption.")
    }

    @Test
    fun `when generateOptions is asked a list of Options is returned with the correct options long description`() {
        val options = utilsCli!!.generateCliOptions()
        assertTrue(options.getOption("e").longOpt == "encrypt")
        assertTrue(options.getOption("d").longOpt == "decrypt")
        assertTrue(options.getOption("ef").longOpt == "encryptFile")
        assertTrue(options.getOption("df").longOpt == "decryptFile")
        assertTrue(options.getOption("p").longOpt == "password")
        assertTrue(options.getOption("a").longOpt == "algorithm")
    }

    @Test
    fun `when generateOptions is asked a list of Options is returned with the correct options hasArg`() {
        val options = utilsCli!!.generateCliOptions()
        assertTrue(options.getOption("e").hasArg())
        assertTrue(options.getOption("d").hasArg())
        assertTrue(options.getOption("ef").hasArg())
        assertTrue(options.getOption("df").hasArg())
        assertTrue(options.getOption("p").hasArg())
        assertTrue(options.getOption("a").hasArg())
    }

    @Test
    fun `when generateOptions is asked a list of Options is returned with the correct options argName`() {
        val options = utilsCli!!.generateCliOptions()
        assertTrue(options.getOption("e").argName == "FILE")
        assertTrue(options.getOption("d").argName == "FILE")
        assertTrue(options.getOption("ef").argName == "FILE")
        assertTrue(options.getOption("df").argName == "FILE")
        assertTrue(options.getOption("p").argName == "PASSWORD")
        assertTrue(options.getOption("a").argName == "ALGORITHM")
    }

    @Test
    fun `when generateOptions is asked a list of Options is returned with the correct options required`() {
        val options = utilsCli!!.generateCliOptions()
        assertTrue(options.getOption("p").isRequired)
        assertTrue(options.getOption("a").isRequired)
    }

    @Test
    fun `when generateOptions is asked a list of Options is returned with the correct options numberOfArgs`() {
        val options = utilsCli!!.generateCliOptions()
        assertFalse(options.getOption("e").hasArgs())
        assertFalse(options.getOption("d").hasArgs())
        assertFalse(options.getOption("ef").hasArgs())
        assertFalse(options.getOption("df").hasArgs())
        assertFalse(options.getOption("p").hasArgs())
        assertFalse(options.getOption("a").hasArgs())
    }

    @Test
    fun `test a valid path is valid`() {
        val path = "src/main/resources/test.txt"
        assertTrue(utilsCli!!.isValidFilePath(path))
    }

    @Test
    fun `test a valid path with a valid extension is valid`() {
        val path = "src/main/resources/test.txt"
        assertTrue(utilsCli!!.isValidFilePath(path))
    }

    @Test
    fun `test a valid path with a invalid extension`() {
        val path = "src/main/resources/test.zip"
        assertFalse(utilsCli!!.isValidFilePath(path))
    }

    @Test
    fun `test a valid extension is valid`() {
        val path = "src/main/resources/test.txt"
        assertTrue(utilsCli!!.hasValidExtension(path))
    }

    @Test
    fun `test an invalid extension is invalid`() {
        val path = "src/main/resources/test.zip"
        assertFalse(utilsCli!!.hasValidExtension(path))
    }

    @Test(expected = InvalidCommandException::class)
    fun `when an invalid option is passed to validate throws InvalidCommandException`() {
        val mockCmd = mock(CommandLine::class.java)
        `when`(mockCmd.options).thenReturn(arrayOf(Option.builder().option("x").longOpt("invalid").build()))
        utilsCli!!.validate(mockCmd)
    }


}