// port-lint: source toml/src/ser/document/array.rs
package io.github.kotlinmania.toml.ser.document

import io.github.kotlinmania.toml.Value
import io.github.kotlinmania.toml.ser.Style

/**
 * Serializer for document arrays.
 */
internal class SerializeDocumentTupleVariant(
    private val buf: Buffer,
    private val table: Table,
    private val style: Style = Style.COMPACT,
) {
    public fun serialize(values: List<Value>) {
        val serializer = SerializeDocumentTable(buf, table, style)
        val entries = values.mapIndexed { idx, v -> idx.toString() to v }.toMap()
        serializer.serializeTable(entries)
    }
}
