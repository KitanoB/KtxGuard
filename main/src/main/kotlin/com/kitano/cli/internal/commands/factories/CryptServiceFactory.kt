package com.kitano.cli.internal.commands.factories

import com.kitano.crypto.CryptService
import com.kitano.crypto.internal.enums.AlgorithmType
import org.koin.java.KoinJavaComponent

/**
 * Factory for creating a [CryptService] instance
 * Created by KitanoB on 2023/11/01.
 */
class CryptServiceFactory {

    private val cryptService by KoinJavaComponent.inject<CryptService>(CryptService::class.java)
    fun create(algorithmType: AlgorithmType): CryptService {
        return when (algorithmType) {
            AlgorithmType.AES -> cryptService
            AlgorithmType.DES -> cryptService
            AlgorithmType.RSA -> cryptService
            else -> throw IllegalArgumentException("Unknown algorithm type")
        }
    }
}