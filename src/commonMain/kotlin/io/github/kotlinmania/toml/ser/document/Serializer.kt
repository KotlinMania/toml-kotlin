// port-lint: source ser/document/mod.rs
package io.github.kotlinmania.toml.ser.document

import io.github.kotlinmania.toml.Table
import io.github.kotlinmania.toml.ser.Style

/**
 * Serialization for TOML documents.
 */
public class Serializer(
    public val buf: Buffer,
    public val style: Style = Style.COMPACT,
) {
    public fun serialize(table: Table) {
        val root = buf.rootTable()
        val serializer = SerializeDocumentTable(buf, root, style)
        serializer.serializeTable(table)
    }

    public companion object {
        public fun new(buf: Buffer): Serializer = Serializer(buf, Style.COMPACT)

        public fun pretty(buf: Buffer): Serializer = Serializer(buf, Style.PRETTY)
    }
}
