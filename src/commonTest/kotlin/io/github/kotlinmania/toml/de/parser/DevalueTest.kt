// port-lint: tests de/parser/devalue.rs
package io.github.kotlinmania.toml.de.parser

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DevalueTest {
    @Test
    fun testDeInteger() {
        val intDec = DeInteger("42", 10u)
        assertEquals(42L, intDec.toI64())
        assertEquals(42uL, intDec.toU64())
        assertEquals("42", intDec.asStr())
        assertEquals(10u, intDec.radix)
        assertEquals("42", intDec.toString())

        val intHex = DeInteger("2a", 16u)
        assertEquals(42L, intHex.toI64())
        assertEquals("0x2a", intHex.toString())

        val intBin = DeInteger("101", 2u)
        assertEquals(5L, intBin.toI64())
        assertEquals("0b101", intBin.toString())

        val intOct = DeInteger("77", 8u)
        assertEquals(63L, intOct.toI64())
        assertEquals("0o77", intOct.toString())
    }

    @Test
    fun testDeFloat() {
        val f = DeFloat("3.14159")
        assertEquals(3.14159, f.toF64())
        assertEquals("3.14159", f.asStr())
        assertEquals("3.14159", f.toString())
    }

    @Test
    fun testDeValueVariants() {
        val strVal = DeValue.Str("hello")
        assertTrue(strVal.isStr())
        assertEquals("hello", strVal.asStr())
        assertEquals("string", strVal.typeStr())

        val intVal = DeValue.Integer(DeInteger("123", 10u))
        assertTrue(intVal.isInteger())
        assertEquals(123L, intVal.asInteger()?.toI64())
        assertEquals("integer", intVal.typeStr())

        val boolVal = DeValue.Boolean(true)
        assertTrue(boolVal.isBool())
        assertEquals(true, boolVal.asBool())
        assertEquals("boolean", boolVal.typeStr())

        val dtVal = DeValue.Datetime("1979-05-27T07:32:00Z")
        assertTrue(dtVal.isDatetime())
        assertEquals("1979-05-27T07:32:00Z", dtVal.asDatetime())
        assertEquals("datetime", dtVal.typeStr())

        val arr = DeArray()
        arr.push(Spanned(strVal))
        arr.push(Spanned(intVal))
        val arrVal = DeValue.Array(arr)
        assertTrue(arrVal.isArray())
        assertEquals(2, arrVal.asArray()?.size)
        assertEquals("array", arrVal.typeStr())

        val item0 = arrVal.get(IntIndex(0))
        assertNotNull(item0)
        assertTrue(item0.value.isStr())

        val table = DeTable()
        table[Spanned("key1")] = Spanned(strVal)
        val tblVal = DeValue.Table(table)
        assertTrue(tblVal.isTable())
        assertEquals("table", tblVal.typeStr())

        val retrieved = tblVal.get(StringIndex("key1"))
        assertNotNull(retrieved)
        assertTrue(retrieved.value.isStr())
    }

    @Test
    fun testSameType() {
        val s1 = DeValue.Str("a")
        val s2 = DeValue.Str("b")
        val i1 = DeValue.Integer(DeInteger("1", 10u))
        assertTrue(s1.sameType(s2))
        assertFalse(s1.sameType(i1))
    }
}
