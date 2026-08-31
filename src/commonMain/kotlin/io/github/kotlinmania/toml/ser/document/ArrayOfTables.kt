// port-lint: source toml/src/ser/document/array_of_tables.rs
package io.github.kotlinmania.toml.ser.document

import io.github.kotlinmania.toml.Value
import io.github.kotlinmania.toml.ser.Style

/**
 * Serializer for arrays of tables into a Buffer.
 */
internal class ArrayOfTablesSerializer(
    private val buf: Buffer,
    private val parent: Table,
    private val key: String,
    private val style: Style = Style.COMPACT,
) {
    public fun serialize(tables: List<Value.Table>) {
        parent.hasChildren(true)
        for (item in tables) {
            val elemTable = buf.elementTable(parent, key)
            val serializer = SerializeDocumentTable(buf, elemTable, style)
            serializer.serializeTable(item.value)
        }
    }
}
