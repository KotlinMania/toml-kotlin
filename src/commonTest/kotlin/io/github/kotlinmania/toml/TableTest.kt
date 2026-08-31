// port-lint: tests toml/src/table.rs
package io.github.kotlinmania.toml

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TableTest {
    @Test
    fun testTableCreation() {
        val table =
            tomlTableOf(
                "title" to Value.Str("TOML Example"),
                "owner" to
                    Value.Table(
                        tomlTableOf(
                            "name" to Value.Str("Tom Preston-Werner"),
                        ),
                    ),
            )

        assertEquals(2, table.size)
        assertEquals(Value.Str("TOML Example"), table["title"])
        assertTrue(table["owner"]?.isTable == true)
        assertEquals(Value.Str("Tom Preston-Werner"), table["owner"]?.get("name"))
    }
}
