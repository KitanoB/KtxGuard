package com.kitano.core

import com.kitano.cli.DecryptFileCommand
import com.kitano.cli.EncryptFileCommand
import com.kitano.core.crypters.symetric.AESCrypter
import java.io.File
import java.nio.file.Files
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class EncryptDecryptFileCommandTest {

    private lateinit var tempDir: File
    private lateinit var testFile: File
    private val testContent = "Test Content"
    private val encryptedContent = "Encrypted Content" // Ceci est un mock, dans la réalité, il sera différent

    @Before
    fun setup() {
        tempDir = Files.createTempDirectory("testDir").toFile()
        testFile = File(tempDir, "testFile.txt")
        testFile.writeText(testContent)
    }

    @Test
    fun `it writes to the given file when no newFilePath provided`() {
        val mockCrypter = Mockito.mock(AESCrypter::class.java)
        Mockito.`when`(mockCrypter.encrypt(testContent, "test", null)).thenReturn(encryptedContent)

        val command = EncryptFileCommand(testFile.absolutePath, "test", null, AlgorithmType.AES, null)
        command.execute()

        val fileContentAfterExecution = testFile.readText()
        Assert.assertNotEquals(encryptedContent, fileContentAfterExecution)

        val cmd = DecryptFileCommand(testFile.absolutePath, "test", null, AlgorithmType.AES)
        cmd.execute()

        val decryptedFileContent = testFile.readText()
        Assert.assertEquals(testContent, decryptedFileContent)
    }

    @Test
    fun `it writes to a new file when newFilePath is provided`() {
        val newFile = File(tempDir, "newFile.txt")
        val mockCrypter = Mockito.mock(AESCrypter::class.java)
        Mockito.`when`(mockCrypter.encrypt(testContent, "test", null)).thenReturn(encryptedContent)

        val command = EncryptFileCommand(testFile.absolutePath, "test", null, AlgorithmType.AES, newFile.absolutePath)
        command.execute()

        val newFileContent = newFile.readText()

        Assert.assertNotEquals(encryptedContent, newFileContent)

        val cmd = DecryptFileCommand(newFile.absolutePath, "test", null, AlgorithmType.AES)
        cmd.execute()

        val decryptedFileContent = newFile.readText()
        val originalFileContent = testFile.readText()

        Assert.assertEquals(originalFileContent, decryptedFileContent)

    }

    @After
    fun tearDown() {
        tempDir.deleteRecursively()
    }
}
