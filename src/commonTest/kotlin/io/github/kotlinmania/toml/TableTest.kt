// port-lint: tests src/table.rs
package io.github.kotlinmania.toml

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TableTest {
    @Test
    fun testTableCreation() {
        val table =
            tomlTableOf(
                "title" to Value.String("TOML Example"),
                "owner" to
                    Value.Table(
                        tomlTableOf(
                            "name" to Value.String("Tom Preston-Werner"),
                        ),
                    ),
            )

        assertEquals(2, table.size)
        assertEquals(Value.String("TOML Example"), table["title"])
        assertTrue(table["owner"]?.isTable == true)
        assertEquals(Value.String("Tom Preston-Werner"), table["owner"]?.get("name"))
    }
}
