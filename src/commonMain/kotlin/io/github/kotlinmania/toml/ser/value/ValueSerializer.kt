// port-lint: source ser/value/mod.rs
package io.github.kotlinmania.toml.ser.value

import io.github.kotlinmania.toml.Value
import io.github.kotlinmania.toml.ser.Style

/**
 * Serializer for individual TOML values.
 */
public object ValueSerializer {

    public fun serialize(value: Value, style: Style = Style.COMPACT): String =
        when (value) {
            is Value.Str -> serializeString(value.value, style)
            is Value.Integer -> value.value.toString()
            is Value.Float -> serializeFloat(value.value)
            is Value.Boolean -> value.value.toString()
            is Value.Datetime -> value.value
            is Value.Array -> ArrayValueSerializer.serializeArray(value.value, style)
            is Value.Table -> MapValueSerializer.serializeInlineTable(value.value, style)
        }

    public fun serializeString(s: String, @Suppress("UNUSED_PARAMETER") style: Style = Style.COMPACT): String {
        return KeySerializer.quoteKey(s)
    }

    public fun serializeFloat(f: Double): String =
        when {
            f.isNaN() -> "nan"
            f == Double.POSITIVE_INFINITY -> "inf"
            f == Double.NEGATIVE_INFINITY -> "-inf"
            else -> {
                val s = f.toString()
                if (!s.contains('.') && !s.contains('e') && !s.contains('E')) {
                    "$s.0"
                } else {
                    s
                }
            }
        }
}
