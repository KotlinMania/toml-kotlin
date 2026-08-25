// port-lint: source de/parser/detable.rs
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

    public constructor(
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
}
