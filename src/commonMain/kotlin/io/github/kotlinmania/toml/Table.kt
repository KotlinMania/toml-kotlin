// port-lint: source toml/src/table.rs
package io.github.kotlinmania.toml

import io.github.kotlinmania.toml.map.TomlMap

public class Table(
    isDotted: Boolean = false,
    isImplicit: Boolean = false,
    isInline: Boolean = false,
) : TomlMap<String, Value>(isDotted, isImplicit, isInline) {
    public constructor(
        other: Map<String, Value>,
        isDotted: Boolean = false,
        isImplicit: Boolean = false,
        isInline: Boolean = false,
    ) : this(isDotted, isImplicit, isInline) {
        putAll(other)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Table && other !is TomlMap<*, *>) return false
        return super.equals(other)
    }

    override fun hashCode(): Int = super.hashCode()

    public companion object {
        public fun new(): Table = Table()

        public fun from(pairs: Iterable<Pair<String, Value>>): Table {
            val t = Table()
            t.extend(pairs)
            return t
        }
    }
}

/**
 * Creates a TOML table containing the specified key-value pairs.
 */
public fun tableOf(vararg pairs: Pair<String, Value>): Table =
    Table().apply {
        for ((k, v) in pairs) {
            put(k, v)
        }
    }

/**
 * Creates a TOML table containing the specified key-value pairs.
 */
public fun tomlTableOf(vararg pairs: Pair<String, Value>): Table = tableOf(*pairs)

/**
 * Parses a string into a TOML Table.
 */
public fun parseTable(s: String): Table = io.github.kotlinmania.toml.de.fromStr(s)

/**
 * Serializes a TOML Table to a String.
 */
public fun Table.toTomlString(): String = io.github.kotlinmania.toml.ser.toString(this)

/**
 * Serializes a TOML Table to a pretty String.
 */
public fun Table.toTomlStringPretty(): String = io.github.kotlinmania.toml.ser.toStringPretty(this)
