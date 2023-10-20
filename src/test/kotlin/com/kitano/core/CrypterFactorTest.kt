package com.kitano.core

import com.kitano.core.crypters.asymetric.RSACrypter
import com.kitano.core.crypters.symetric.AESCrypter
import com.kitano.core.crypters.symetric.DESCrypter
import kotlin.test.Test
import kotlin.test.assertTrue

class CrypterFactorTest {

    private val classUnderTest = CrypterFactory

    @Test
    fun `createCrypter with algorithm type AES returns a AESCrypter instance`() {
        val crypter = classUnderTest.createCrypter(AlgorithmType.AES)
        assertTrue(crypter is AESCrypter)
    }

    @Test
    fun `createCrypter with algorithm type DES returns a DESCrypter instance`() {
        val crypter = classUnderTest.createCrypter(AlgorithmType.DES)
        assertTrue(crypter is DESCrypter)
    }

    @Test
    fun `createCrypter with algorithm type RSA returns a RSACrypter instance`() {
        val crypter = classUnderTest.createCrypter(AlgorithmType.RSA)
        assertTrue(crypter is RSACrypter)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `createCrypter with algorithm type UNKNOWN throws IllegalArgumentException`() {
        classUnderTest.createCrypter(AlgorithmType.UNKNOWN)
    }
}