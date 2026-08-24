// port-lint: tests value.rs
package io.github.kotlinmania.toml

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ValueTest {
    @Test
    fun testStringValue() {
        val v = Value.String("hello")
        assertTrue(v.isString)
        assertFalse(v.isInteger)
        assertEquals("hello", v.asString())
        assertNull(v.asInteger())
        assertEquals("string", v.typeStr())
    }

    @Test
    fun testIntegerValue() {
        val v = Value.Integer(12345L)
        assertTrue(v.isInteger)
        assertFalse(v.isString)
        assertEquals(12345L, v.asInteger())
        assertEquals("integer", v.typeStr())
    }

    @Test
    fun testFloatValue() {
        val v = Value.Float(3.1415)
        assertTrue(v.isFloat)
        assertFalse(v.isInteger)
        assertEquals(3.1415, v.asFloat())
        assertEquals("float", v.typeStr())
    }

    @Test
    fun testBooleanValue() {
        val v = Value.Boolean(true)
        assertTrue(v.isBoolean)
        assertFalse(v.isString)
        assertEquals(true, v.asBoolean())
        assertEquals("boolean", v.typeStr())
    }

    @Test
    fun testDatetimeValue() {
        val v = Value.Datetime("1979-05-27T07:32:00Z")
        assertTrue(v.isDatetime)
        assertEquals("1979-05-27T07:32:00Z", v.asDatetime())
        assertEquals("datetime", v.typeStr())
    }

    @Test
    fun testArrayValue() {
        val elem1 = Value.Integer(1L)
        val elem2 = Value.Integer(2L)
        val v = Value.Array(listOf(elem1, elem2))

        assertTrue(v.isArray)
        assertEquals(2, v.asArray()?.size)
        assertEquals(elem1, v[0])
        assertEquals(elem2, v[1])
        assertNull(v[2])
        assertEquals("array", v.typeStr())
    }

    @Test
    fun testTableValue() {
        val table = tomlTableOf("key1" to Value.String("val1"), "key2" to Value.Integer(42L))
        val v = Value.Table(table)

        assertTrue(v.isTable)
        assertEquals(Value.String("val1"), v["key1"])
        assertEquals(Value.Integer(42L), v["key2"])
        assertNull(v["nonexistent"])
        assertEquals("table", v.typeStr())
    }

    @Test
    fun testSameType() {
        val v1 = Value.Integer(10L)
        val v2 = Value.Integer(20L)
        val v3 = Value.String("10")

        assertTrue(v1.sameType(v2))
        assertFalse(v1.sameType(v3))
    }

    @Test
    fun testFactoryMethods() {
        assertEquals(Value.String("test"), Value.from("test"))
        assertEquals(Value.Integer(42L), Value.from(42L))
        assertEquals(Value.Integer(42L), Value.from(42))
        assertEquals(Value.Float(1.5), Value.from(1.5))
        assertEquals(Value.Boolean(false), Value.from(false))
    }
}
