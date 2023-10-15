package com.kitano

import com.kitano.core.AlgorithmType
import com.kitano.core.CrypterFactory
import com.kitano.core.exceptions.IncorrectKeyException
import javax.crypto.BadPaddingException
import javax.crypto.IllegalBlockSizeException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

/**
 * Test class for the AES algorithm
 * Created by KitanoB on 2018/11/11.
 */
class AlgoAESTest {

    @Test
    fun when_encrypt_with_aes_same_string_is_retrieved_after_decrypt() {
        val crypter = CrypterFactory.createCrypter(AlgorithmType.AES)
        val password = "1234567890123456"
        val plainText = "This is a test"
        val encryptedText = crypter.encrypt(plainText, password, null)
        val decryptedText = crypter.decrypt(encryptedText, password, null)
        assertEquals(plainText, decryptedText)

    }

    @Test(expected = IncorrectKeyException::class)
    fun when_encrypt_with_aes_same_string_is_not_retrieved_after_decrypt_with_wrong_password() {
        val crypter = CrypterFactory.createCrypter(AlgorithmType.AES)
        val password = "123456"
        val plainText = "This is a test"
        val encryptedText = crypter.encrypt(plainText, password, null)
        val decryptedText = crypter.decrypt(encryptedText, "test", null)
        assertNotEquals(encryptedText, decryptedText)
    }

    @Test(expected = IncorrectKeyException::class)
    fun when_decrypt_with_different_key_throws_exception() {
        val crypter = CrypterFactory.createCrypter(AlgorithmType.AES)
        val password1 = "1234567890123456"
        val password2 = "6543210987654321"
        val plainText = "This is a test"
        val encryptedText = crypter.encrypt(plainText, password1, null)
        crypter.decrypt(encryptedText, password2, null)
    }

    @Test(expected = IncorrectKeyException::class)
    fun when_encrypt_long_string_with_aes_does_throw() {
        val crypter = CrypterFactory.createCrypter(AlgorithmType.AES)
        val password = "1234567890123456"
        val longString = "a".repeat(10_000)
        val encrypted = crypter.encrypt(longString, password, null)
        val decrypted = crypter.decrypt(longString, password, null)
        assertEquals(encrypted, decrypted)
    }

    @Test
    fun when_encrypt_empty_string_with_aes_and_then_decrypt_returns_empty_string() {
        val crypter = CrypterFactory.createCrypter(AlgorithmType.AES)
        val password = "1234567890123456"
        val plainText = ""
        val encryptedText = crypter.encrypt(plainText, password, null)
        val decryptedText = crypter.decrypt(encryptedText, password, null)
        assertEquals(plainText, decryptedText)
    }

    @Test
    fun when_encrypt_special_characters_with_aes_same_string_is_retrieved_after_decrypt() {
        val crypter = CrypterFactory.createCrypter(AlgorithmType.AES)
        val password = "1234567890123456"
        val specialString = "这是一个测试"
        val encryptedText = crypter.encrypt(specialString, password, null)
        val decryptedText = crypter.decrypt(encryptedText, password, null)
        assertEquals(specialString, decryptedText)
    }

}