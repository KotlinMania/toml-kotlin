// port-lint: tests de/mod.rs
package io.github.kotlinmania.toml.de

import io.github.kotlinmania.toml.Table
import io.github.kotlinmania.toml.Value
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class DeserializerTest {
    @Test
    fun testParseSimpleDocument() {
        val toml =
            """
            title = "TOML Example"
            count = 42
            rate = 3.14
            enabled = true
            
            [owner]
            name = "Tom"
            dob = 1979-05-27T07:32:00Z
            """.trimIndent()

        val table = fromStr(toml)
        assertEquals(Value.Str("TOML Example"), table["title"])
        assertEquals(Value.Integer(42L), table["count"])
        assertEquals(Value.Float(3.14), table["rate"])
        assertEquals(Value.Boolean(true), table["enabled"])

        val owner = table["owner"]
        assertNotNull(owner)
        assertTrue(owner is Value.Table)
        assertEquals(Value.Str("Tom"), owner["name"])
        assertEquals(Value.Datetime("1979-05-27T07:32:00Z"), owner["dob"])
    }

    @Test
    fun testParseDottedKeys() {
        val toml =
            """
            fruit.apple.color = "red"
            fruit.apple.taste.sweet = true
            """.trimIndent()

        val table = fromStr(toml)
        val fruit = table["fruit"]
        assertNotNull(fruit)
        val apple = fruit["apple"]
        assertNotNull(apple)
        assertEquals(Value.Str("red"), apple["color"])
        val taste = apple["taste"]
        assertNotNull(taste)
        assertEquals(Value.Boolean(true), taste["sweet"])
    }

    @Test
    fun testParseArrayOfTables() {
        val toml =
            """
            [[products]]
            name = "Hammer"
            sku = 738594937
            
            [[products]]
            name = "Nail"
            sku = 284758393
            color = "gray"
            """.trimIndent()

        val table = fromStr(toml)
        val products = table["products"]
        assertNotNull(products)
        assertTrue(products is Value.Array)
        assertEquals(2, products.value.size)

        val p1 = products.value[0]
        assertEquals(Value.Str("Hammer"), p1["name"])
        assertEquals(Value.Integer(738594937L), p1["sku"])

        val p2 = products.value[1]
        assertEquals(Value.Str("Nail"), p2["name"])
        assertEquals(Value.Str("gray"), p2["color"])
    }

    @Test
    fun testParseInlineTableAndArray() {
        val toml =
            """
            point = { x = 1, y = 2 }
            numbers = [ 1, 2, 3 ]
            """.trimIndent()

        val table = fromStr(toml)
        val point = table["point"]
        assertNotNull(point)
        assertEquals(Value.Integer(1L), point["x"])
        assertEquals(Value.Integer(2L), point["y"])

        val numbers = table["numbers"]
        assertNotNull(numbers)
        assertTrue(numbers is Value.Array)
        assertEquals(3, numbers.value.size)
        assertEquals(Value.Integer(1L), numbers[0])
    }

    @Test
    fun testFromSlice() {
        val toml = "hello = \"world\""
        val bytes = toml.encodeToByteArray()
        val table = fromSlice(bytes)
        assertEquals(Value.Str("world"), table["hello"])
    }
}
