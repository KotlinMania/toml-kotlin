// port-lint: source toml/src/ser/mod.rs
package io.github.kotlinmania.toml.ser

import io.github.kotlinmania.toml.Table
import io.github.kotlinmania.toml.ser.document.Buffer
import io.github.kotlinmania.toml.ser.document.Serializer

public typealias DocumentSerializer = Serializer

/**
 * Serialize the given TOML Table as a String of TOML.
 */
public fun toString(table: Table): String {
    val buf = Buffer.new()
    val serializer = Serializer.new(buf)
    serializer.serialize(table)
    return buf.toString()
}

/**
 * Serialize the given TOML Table as a pretty String of TOML.
 */
public fun toStringPretty(table: Table): String {
    val buf = Buffer.new()
    val serializer = Serializer.pretty(buf)
    serializer.serialize(table)
    return buf.toString()
}
