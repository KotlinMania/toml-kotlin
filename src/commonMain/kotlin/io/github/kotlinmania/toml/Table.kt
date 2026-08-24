// port-lint: source table.rs
package io.github.kotlinmania.toml

import io.github.kotlinmania.toml.map.TomlMap

/**
 * Type representing a TOML table, payload of the `Value::Table` variant.
 */
public typealias Table = TomlMap<String, Value>

/**
 * Creates an empty TOML table.
 */
public fun tomlTableOf(vararg pairs: Pair<String, Value>): Table =
    TomlMap<String, Value>().apply {
        for ((k, v) in pairs) {
            put(k, v)
        }
    }
