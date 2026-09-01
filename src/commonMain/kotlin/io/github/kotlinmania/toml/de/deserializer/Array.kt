// port-lint: source de/deserializer/array.rs
package io.github.kotlinmania.toml.de.deserializer

import io.github.kotlinmania.toml.Value
import io.github.kotlinmania.toml.de.TomlSpan
import io.github.kotlinmania.toml.de.parser.DeArray

/**
 * Deserialization for TOML arrays.
 */
public class ArrayDeserializer(
    public val input: DeArray,
    public val span: TomlSpan? = null,
) {
    public fun toList(): List<Value> = input.toArray()

    public companion object {
        public fun new(input: DeArray, span: TomlSpan? = null): ArrayDeserializer =
            ArrayDeserializer(input, span)
    }
}
