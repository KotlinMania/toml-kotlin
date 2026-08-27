// port-lint: source ser/value/array.rs
package io.github.kotlinmania.toml.ser.value

import io.github.kotlinmania.toml.Value
import io.github.kotlinmania.toml.ser.Style

/**
 * Serializes TOML array values.
 */
public object ArrayValueSerializer {
    public fun serializeArray(elements: List<Value>, style: Style = Style.COMPACT): String =
        buildString {
            if (elements.isEmpty()) {
                append("[]")
                return@buildString
            }

            if (style == Style.PRETTY && elements.size > 3) {
                append("[\n")
                for (elem in elements) {
                    append("    ")
                    append(ValueSerializer.serialize(elem, style))
                    append(",\n")
                }
                append("]")
            } else {
                append("[")
                for ((i, elem) in elements.withIndex()) {
                    if (i > 0) append(", ")
                    append(ValueSerializer.serialize(elem, style))
                }
                append("]")
            }
        }
}
