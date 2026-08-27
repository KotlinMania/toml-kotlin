// port-lint: source ser/document/strategy.rs
package io.github.kotlinmania.toml.ser.document

import io.github.kotlinmania.toml.Value

/**
 * Strategy for determining how a TOML field should be serialized within a document.
 */
public enum class SerializationStrategy {
    VALUE,
    TABLE,
    ARRAY_OF_TABLES,
    SKIP,
    UNKNOWN;

    public companion object {
        public fun of(value: Value): SerializationStrategy =
            when (value) {
                is Value.Table -> TABLE
                is Value.Array -> {
                    if (value.value.isNotEmpty() && value.value.all { it is Value.Table }) {
                        ARRAY_OF_TABLES
                    } else {
                        VALUE
                    }
                }
                else -> VALUE
            }
    }
}
