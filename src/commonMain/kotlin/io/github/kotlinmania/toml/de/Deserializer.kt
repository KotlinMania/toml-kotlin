// port-lint: source toml/src/de/deserializer/mod.rs
package io.github.kotlinmania.toml.de

import io.github.kotlinmania.toml.Table
import io.github.kotlinmania.toml.Value
import io.github.kotlinmania.toml.de.parser.DeTable
import io.github.kotlinmania.toml.de.parser.DeValue
import io.github.kotlinmania.toml.de.parser.DocumentParser
import io.github.kotlinmania.toml.de.parser.ValueParser

/**
 * Deserialization for TOML documents.
 */
public class Deserializer(
    public val raw: String?,
    public val root: DeTable,
) {
    public fun toTable(): Table = root.toTable()

    public companion object {
        public fun parse(raw: String): Deserializer {
            val root = DocumentParser.parseDocument(raw)
            return Deserializer(raw, root)
        }

        public fun new(raw: String): Deserializer = parse(raw)
    }
}

/**
 * Deserialization implementation for TOML values.
 */
public class ValueDeserializer(
    public val input: DeValue,
) {
    public fun toValue(): Value = input.toValue()

    public companion object {
        public fun parse(raw: String): ValueDeserializer {
            val input = ValueParser.parseScalar(raw)
            return ValueDeserializer(input)
        }

        public fun new(raw: String): ValueDeserializer = parse(raw)
    }
}

/**
 * Deserializes a string into a TOML Table.
 */
public fun fromStr(s: String): Table {
    val deserializer = Deserializer.parse(s)
    return deserializer.toTable()
}

/**
 * Deserializes bytes into a TOML Table.
 */
public fun fromSlice(s: ByteArray): Table {
    val str = s.decodeToString()
    return fromStr(str)
}
