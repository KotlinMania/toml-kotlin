// port-lint: source de/deserializer/key.rs
package io.github.kotlinmania.toml.de.deserializer

import io.github.kotlinmania.toml.de.TomlSpan

/**
 * Deserialization for TOML table keys.
 */
public class KeyDeserializer(
    public val key: String,
    public val span: TomlSpan? = null,
) {
    public fun getKeyString(): String = key

    public companion object {
        public fun new(key: String, span: TomlSpan? = null): KeyDeserializer =
            KeyDeserializer(key, span)
    }
}
