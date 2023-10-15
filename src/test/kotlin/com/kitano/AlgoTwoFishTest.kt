package com.kitano

import com.kitano.core.AlgorithmType
import com.kitano.core.CrypterFactory
import com.kitano.core.exceptions.IncorrectKeyException

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


/**
 * Test class for the TwoFish algorithm
 * Created by KitanoB on 2018/11/11.
 */
class AlgoTwoFishTest {

    @Test
    fun when_encrypt_with_two_fish_same_string_is_retrieved_after_decrypt() {
        val crypter = CrypterFactory.createCrypter(AlgorithmType.TWOFISH)
        val password = "123456"
        val plainText = "This is a test"
        val encryptedText = crypter.encrypt(plainText, password, null)
        val decryptedText = crypter.decrypt(encryptedText, password, null)
        assertEquals(plainText, decryptedText)
    }

    @Test(expected = IncorrectKeyException::class)
    fun when_encrypt_with_two_fish_same_string_is_not_retrieved_after_decrypt_with_wrong_password() {
        val crypter = CrypterFactory.createCrypter(AlgorithmType.TWOFISH)
        val password = "123456"
        val plainText = "This is a test"
        val encryptedText = crypter.encrypt(plainText, password, null)
        val decryptedText = crypter.decrypt(encryptedText, "test", null)
        assertNotEquals(encryptedText, decryptedText)
    }

}