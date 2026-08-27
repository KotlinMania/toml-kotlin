// port-lint: source ser/document/map.rs
package io.github.kotlinmania.toml.ser.document

import io.github.kotlinmania.toml.Value
import io.github.kotlinmania.toml.ser.Style
import io.github.kotlinmania.toml.ser.value.KeySerializer
import io.github.kotlinmania.toml.ser.value.ValueSerializer

/**
 * Serializer for TOML document tables into a Buffer.
 */
internal class SerializeDocumentTable(
    private val buf: Buffer,
    private val table: Table,
    private val style: Style = Style.COMPACT,
) {
    public fun serializeTable(entries: Map<String, Value>) {
        // First pass: serialize all scalar values and inline tables/arrays
        for ((k, v) in entries) {
            val strategy = SerializationStrategy.of(v)
            if (strategy == SerializationStrategy.VALUE) {
                val formattedKey = KeySerializer.formatKey(k)
                val formattedVal = ValueSerializer.serialize(v, style)
                table.bodyMut().append(formattedKey).append(" = ").append(formattedVal).append("\n")
            }
        }
        buf.push(table)

        // Second pass: serialize child tables
        for ((k, v) in entries) {
            val strategy = SerializationStrategy.of(v)
            if (strategy == SerializationStrategy.TABLE && v is Value.Table) {
                val childTable = buf.childTable(table, k)
                val childSerializer = SerializeDocumentTable(buf, childTable, style)
                childSerializer.serializeTable(v.value)
            }
        }

        // Third pass: serialize arrays of tables
        for ((k, v) in entries) {
            val strategy = SerializationStrategy.of(v)
            if (strategy == SerializationStrategy.ARRAY_OF_TABLES && v is Value.Array) {
                for (item in v.value) {
                    if (item is Value.Table) {
                        val elemTable = buf.elementTable(table, k)
                        val elemSerializer = SerializeDocumentTable(buf, elemTable, style)
                        elemSerializer.serializeTable(item.value)
                    }
                }
            }
        }
    }
}
