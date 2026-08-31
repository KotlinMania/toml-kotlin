// port-lint: tests toml/src/de/parser/detable.rs
package io.github.kotlinmania.toml.de.parser

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DetableTest {
    @Test
    fun testDeTableOperations() {
        val table = DeTable()
        table[Spanned("title")] = Spanned(DeValue.Str("TOML Example"))
        assertEquals(1, table.size)

        val entry = table.entries.first()
        assertEquals("title", entry.key.value)
        assertEquals("TOML Example", (entry.value.value as DeValue.Str).value)

        table.makeOwned()
        assertNotNull(table.get(Spanned("title")))
    }
}
