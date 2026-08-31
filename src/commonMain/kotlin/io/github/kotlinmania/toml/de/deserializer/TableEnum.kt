// port-lint: source toml/src/de/deserializer/table_enum.rs
package io.github.kotlinmania.toml.de.deserializer

import io.github.kotlinmania.toml.Value
import io.github.kotlinmania.toml.de.TomlSpan
import io.github.kotlinmania.toml.de.parser.DeValue

/**
 * Deserializes table values into enum variants.
 */
public class TableEnumDeserializer(
    public val value: DeValue,
    public val span: TomlSpan? = null,
) {
    public fun toValue(): Value = value.toValue()

    public companion object {
        public fun new(value: DeValue, span: TomlSpan? = null): TableEnumDeserializer =
            TableEnumDeserializer(value, span)
    }
}
