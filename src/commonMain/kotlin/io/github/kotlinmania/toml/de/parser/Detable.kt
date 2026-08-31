// port-lint: source toml/src/de/parser/detable.rs
package io.github.kotlinmania.toml.de.parser

import io.github.kotlinmania.toml.map.TomlMap

/**
 * Type representing a TOML table, payload of the [DeValue.Table] variant.
 */
public class DeTable(
    isDotted: Boolean = false,
    isImplicit: Boolean = false,
    isInline: Boolean = false,
) : TomlMap<Spanned<String>, Spanned<DeValue>>(isDotted, isImplicit, isInline) {
    internal constructor(
        other: Map<Spanned<String>, Spanned<DeValue>>,
        isDotted: Boolean = false,
        isImplicit: Boolean = false,
        isInline: Boolean = false,
    ) : this(isDotted, isImplicit, isInline) {
        putAll(other)
    }

    /** Ensure no data is borrowed */
    public fun makeOwned() {
        for ((_, v) in entries) {
            v.value.makeOwned()
        }
    }

    public fun toTable(): io.github.kotlinmania.toml.Table {
        val table =
            io.github.kotlinmania.toml.Table(
                isDotted = isDotted,
                isImplicit = isImplicit,
                isInline = isInline,
            )
        for ((k, v) in entries) {
            table[k.value] = v.value.toValue()
        }
        return table
    }

    public companion object {
        public fun parse(raw: String): Spanned<DeTable> {
            val doc = DocumentParser.parseDocument(raw)
            return Spanned(doc, 0..raw.length)
        }

        public fun new(): DeTable = DeTable()
    }
}
