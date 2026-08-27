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

    @Test
    fun testEntryAndMutations() {
        val map = TomlMap.new<String, Int>()
        assertEquals(0, map.len())

        val entryA = map.entry("a")
        assertTrue(entryA is Entry.Vacant)
        assertEquals("a", entryA.key)
        val valA = entryA.orInsert(10)
        assertEquals(10, valA)
        assertEquals(10, map["a"])
        assertEquals(1, map.len())

        val entryA2 = map.entry("a")
        assertTrue(entryA2 is Entry.Occupied)
        assertEquals(10, entryA2.get())
        assertEquals(10, entryA2.getMut())
        val valA2 = entryA2.orInsertWith { 20 }
        assertEquals(10, valA2)

        val entryB = map.entry("b")
        val valB = entryB.orInsertWith { 30 }
        assertEquals(30, valB)
        assertEquals(2, map.len())

        val kv = map.getKeyValue("a")
        assertEquals("a" to 10, kv)

        val removedEntry = map.removeEntry("b")
        assertEquals("b" to 30, removedEntry)
        assertEquals(1, map.len())

        map.extend(listOf("c" to 40, "d" to 50))
        assertEquals(3, map.len())

        map.retain { k, v -> k != "c" && v > 10 }
        assertEquals(1, map.len())
        assertEquals(50, map["d"])

        map.mutEntries { k, v -> "$k!" to v * 2 }
        assertEquals(100, map["d!"])

        map.isDotted = true
        map.isImplicit = true
        map.isInline = true
        assertTrue(map.isDotted)
        assertTrue(map.isImplicit)
        assertTrue(map.isInline)

        val fromMap = TomlMap.from(listOf("x" to 1, "y" to 2))
        assertEquals(2, fromMap.len())
        val capMap = TomlMap.withCapacity<String, Int>(10)
        assertEquals(0, capMap.len())
    }
}
