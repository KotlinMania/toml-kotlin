// port-lint: source toml/src/de/deserializer/value.rs
package io.github.kotlinmania.toml.de.deserializer

import io.github.kotlinmania.toml.Value
import io.github.kotlinmania.toml.de.TomlSpan
import io.github.kotlinmania.toml.de.parser.DeValue
import io.github.kotlinmania.toml.de.parser.ValueParser

/**
 * Deserialization implementation for TOML [Value]s.
 */
public class ValueDeserializer(
    public val input: DeValue,
    public val span: TomlSpan? = null,
    public val validateStructKeys: Boolean = false,
) {
    public fun toValue(): Value = input.toValue()

    public companion object {
        public fun parse(raw: String): ValueDeserializer {
            val input = ValueParser.parseScalar(raw)
            return ValueDeserializer(input)
        }

        public fun new(raw: String): ValueDeserializer = parse(raw)

        public fun withParts(
            input: DeValue,
            span: TomlSpan? = null,
            validateStructKeys: Boolean = false,
        ): ValueDeserializer = ValueDeserializer(input, span, validateStructKeys)
    }
}
