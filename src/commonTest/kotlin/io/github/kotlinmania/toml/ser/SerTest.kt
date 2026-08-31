// port-lint: tests toml/src/ser/error.rs
package io.github.kotlinmania.toml.ser

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SerTest {
    @Test
    fun testSerializationErrors() {
        val errCustom = Error.custom("custom message")
        assertEquals("custom message", errCustom.message)
        assertEquals("custom message", errCustom.toString())

        val errUnsupported = Error.unsupportedType("i128")
        assertEquals("unsupported i128 type", errUnsupported.message)

        val errUnsupportedGeneric = Error.unsupportedType()
        assertEquals("unsupported type", errUnsupportedGeneric.message)

        val errNone = Error.unsupportedNone()
        assertEquals("unsupported None value", errNone.message)

        val errKey = Error.keyNotString()
        assertEquals("map key was not a string", errKey.message)

        val errDate = Error.dateInvalid()
        assertEquals("a serialized date was invalid", errDate.message)

        val errCustom2 = Error.custom("custom message")
        assertEquals(errCustom, errCustom2)
        assertEquals(errCustom.hashCode(), errCustom2.hashCode())
    }

    @Test
    fun testStyle() {
        val defaultStyle = Style.DEFAULT
        assertFalse(defaultStyle.multilineArray)

        val multilineStyle = Style(multilineArray = true)
        assertTrue(multilineStyle.multilineArray)
    }
}
