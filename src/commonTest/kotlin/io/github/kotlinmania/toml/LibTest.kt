// port-lint: tests toml/src/lib.rs
package io.github.kotlinmania.toml

import kotlin.test.Test
import kotlin.test.assertEquals

class LibTest {
    @Test
    fun testVersion() {
        assertEquals("0.8.20", Toml.VERSION)
    }
}
