// port-lint: tests de/parser/dearray.rs
package io.github.kotlinmania.toml.de.parser

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DearrayTest {
    @Test
    fun testDeArrayOperations() {
        val arr = DeArray.new()
        assertEquals(0, arr.size)
        assertFalse(arr.isArrayOfTables())

        arr.push(Spanned(DeValue.String("item1")))
        arr.push(Spanned(DeValue.String("item2")))
        assertEquals(2, arr.size)
        assertEquals("item1", (arr[0].value as DeValue.String).value)

        arr.setArrayOfTables(true)
        assertTrue(arr.isArrayOfTables())
    }
}
