package com.kitano

import com.kitano.core.AlgorithmType
import com.kitano.core.CrypterFactory
import java.security.InvalidKeyException
import java.security.KeyPair
import java.security.KeyPairGenerator
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
/**
 * Test class for the RSA algorithm
 * Created by KitanoB on 2018/11/11.
 */
class AlgoRSATest {

    private var keyPair: KeyPair? = null

    @BeforeTest
    fun setup() {
        keyPair = generateRsaKeyPair()
    }


    @Test
    fun when_encrypt_with_rsa_same_string_is_not_retrieved_after_decrypt_with_wrong_password() {
        val crypter = CrypterFactory.createCrypter(AlgorithmType.RSA)
        val password = "123456"
        val plainText = "RSA encrypted string"
            val encryptedText = crypter.encrypt(plainText, password, keyPair?.public)
            val decryptedText = crypter.decrypt(encryptedText, "test" , keyPair?.private)
            assertEquals(encryptedText, encryptedText)
    }

    private fun generateRsaKeyPair(): KeyPair {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(2048)
        return keyPairGenerator.generateKeyPair()
    }

}