// port-lint: source toml/src/ser/value/map.rs
package io.github.kotlinmania.toml.ser.value

import io.github.kotlinmania.toml.Value
import io.github.kotlinmania.toml.ser.Style

/**
 * Serializes TOML inline tables and maps.
 */
public object MapValueSerializer {

    public fun serializeInlineTable(entries: Map<String, Value>, style: Style = Style.COMPACT): String =
        buildString {
            if (entries.isEmpty()) {
                append("{}")
                return@buildString
            }
            append("{ ")
            var first = true
            for ((k, v) in entries) {
                if (!first) append(", ")
                first = false
                append(KeySerializer.formatKey(k))
                append(" = ")
                append(ValueSerializer.serialize(v, style))
            }
            append(" }")
        }
}
