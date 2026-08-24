// port-lint: tests map.rs
package io.github.kotlinmania.toml.map

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TomlMapTest {
    @Test
    fun testEmptyMap() {
        val map = TomlMap<String, String>()
        assertTrue(map.isEmpty())
        assertEquals(0, map.size)
        assertFalse(map.isDotted)
        assertFalse(map.isImplicit)
        assertFalse(map.isInline)
    }

    @Test
    fun testBasicOperations() {
        val map = TomlMap<String, Int>()
        map["a"] = 1
        map["b"] = 2

        assertEquals(2, map.size)
        assertEquals(1, map["a"])
        assertEquals(2, map["b"])
        assertTrue(map.containsKey("a"))
        assertFalse(map.containsKey("c"))

        map.isDotted = true
        map.isImplicit = true
        map.isInline = true

        assertTrue(map.isDotted)
        assertTrue(map.isImplicit)
        assertTrue(map.isInline)

        val removed = map.remove("a")
        assertEquals(1, removed)
        assertEquals(1, map.size)
        assertFalse(map.containsKey("a"))
    }

    @Test
    fun testCopyConstructorAndEquality() {
        val original = mapOf("foo" to 42, "bar" to 99)
        val tomlMap1 = TomlMap(original)
        val tomlMap2 = TomlMap(original)

        assertEquals(tomlMap1, tomlMap2)
        assertEquals(tomlMap1.hashCode(), tomlMap2.hashCode())
        assertEquals(2, tomlMap1.size)
        assertEquals(42, tomlMap1["foo"])
    }
}
