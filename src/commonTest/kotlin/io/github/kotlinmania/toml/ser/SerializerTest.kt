// port-lint: tests ser/mod.rs
package io.github.kotlinmania.toml.ser

import io.github.kotlinmania.toml.Table
import io.github.kotlinmania.toml.Value
import io.github.kotlinmania.toml.de.fromStr
import io.github.kotlinmania.toml.tableOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SerializerTest {

    @Test
    fun testSerializeSimpleTable() {
        val table = tableOf(
            "title" to Value.Str("TOML Example"),
            "count" to Value.Integer(42L),
            "rate" to Value.Float(3.14),
            "enabled" to Value.Boolean(true),
        )

        val output = toString(table)
        assertTrue(output.contains("title = \"TOML Example\""))
        assertTrue(output.contains("count = 42"))
        assertTrue(output.contains("rate = 3.14"))
        assertTrue(output.contains("enabled = true"))

        val parsed = fromStr(output)
        assertEquals(table["title"], parsed["title"])
        assertEquals(table["count"], parsed["count"])
        assertEquals(table["rate"], parsed["rate"])
        assertEquals(table["enabled"], parsed["enabled"])
    }

    @Test
    fun testSerializeChildTable() {
        val table = tableOf(
            "name" to Value.Str("root"),
            "database" to Value.Table(
                tableOf(
                    "server" to Value.Str("192.168.1.1"),
                    "port" to Value.Integer(8000L),
                ),
            ),
        )

        val output = toString(table)
        assertTrue(output.contains("[database]"))
        assertTrue(output.contains("server = \"192.168.1.1\""))
        assertTrue(output.contains("port = 8000"))

        val parsed = fromStr(output)
        assertEquals(table["name"], parsed["name"])
        assertEquals(table["database"]?.get("server"), parsed["database"]?.get("server"))
        assertEquals(table["database"]?.get("port"), parsed["database"]?.get("port"))
    }

    @Test
    fun testSerializeArrayOfTables() {
        val table = tableOf(
            "products" to Value.Array(
                listOf(
                    Value.Table(tableOf("name" to Value.Str("Hammer"), "sku" to Value.Integer(1L))),
                    Value.Table(tableOf("name" to Value.Str("Nail"), "sku" to Value.Integer(2L))),
                ),
            ),
        )

        val output = toString(table)
        assertTrue(output.contains("[[products]]"))
        assertTrue(output.contains("name = \"Hammer\""))
        assertTrue(output.contains("name = \"Nail\""))

        val parsed = fromStr(output)
        val products = parsed["products"]
        assertTrue(products is Value.Array)
        assertEquals(2, products.value.size)
        assertEquals(Value.Str("Hammer"), products.value[0]["name"])
        assertEquals(Value.Str("Nail"), products.value[1]["name"])
    }
}
