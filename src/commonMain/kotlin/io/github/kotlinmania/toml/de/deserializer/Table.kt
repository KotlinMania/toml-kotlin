// port-lint: source toml/src/de/deserializer/table.rs
package io.github.kotlinmania.toml.de.deserializer

import io.github.kotlinmania.toml.Table
import io.github.kotlinmania.toml.de.TomlSpan
import io.github.kotlinmania.toml.de.parser.DeTable

/**
 * Deserializer for TOML tables.
 */
public class TableDeserializer(
    public val items: DeTable,
    public val span: TomlSpan? = null,
) {
    public fun toTable(): Table = items.toTable()

    public companion object {
        public fun new(items: DeTable, span: TomlSpan? = null): TableDeserializer =
            TableDeserializer(items, span)
    }
}
