package com.kitano.cli

import com.kitano.cli.internal.UserInputProvider
import com.kitano.cli.utils.Utils
import com.kitano.cli.utils.UtilsCLI
import junit.framework.TestCase.assertTrue
import kotlin.test.AfterTest
import kotlin.test.Test
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

class StrategyTest {

    @Before
    fun setup() {
        startKoin {
            modules(listOf(module { single<UserInputProvider> { ConsoleInputProvider() } }))
        }
    }
    @Test
    fun `test selectStrategy with empty args returns UserPromptStrategy`() {
        val strategy = selectStrategy(emptyArray())
        assertTrue(strategy is UserPromptStrategy)
    }

    @Test
    fun `test selectStrategy with option requiring value followed by empty value returns UserPromptStrategy`() {
        val argsWithEmptyValue = arrayOf("p", "")
        val strategy = selectStrategy(argsWithEmptyValue)
        assertTrue(strategy is UserPromptStrategy)
    }

    @Test
    fun `test selectStrategy with complete args returns DefaultValueStrategy`() {
        val completeArgs = arrayOf("p", "passwordValue", "a", "AES")
        val strategy = selectStrategy(completeArgs)
        assertTrue(strategy is DefaultValueStrategy)
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

}