package com.kitano.core

import com.kitano.core.crypters.symetric.AESCrypter
import com.kitano.core.crypters.symetric.DESCrypter
import com.kitano.core.crypters.asymetric.RSACrypter
import com.kitano.core.crypters.symetric.TwoFishCrypter
import com.kitano.core.internal.ICipher
import java.security.Security


/**
 * Factory class for the encryption algorithms
 * @see BaseCrypter
 * Created by KitanoB on 2023/10/10.
 */
object CrypterFactory {
    /**
     * Creates a new instance of the given algorithm
     * @param type The algorithm to create
     * @return A new instance of the given algorithm
     */
    fun createCrypter(algorithmType: AlgorithmType): ICipher {
        Security.addProvider(org.bouncycastle.jce.provider.BouncyCastleProvider())
        return when (algorithmType) {
            AlgorithmType.AES -> AESCrypter()
            AlgorithmType.TWOFISH -> TwoFishCrypter()
            AlgorithmType.DES -> DESCrypter()
            AlgorithmType.RSA -> RSACrypter()
        }
    }
}
