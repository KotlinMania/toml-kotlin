// port-lint: source toml/src/lib.rs
package io.github.kotlinmania.toml

/**
 * A TOML-parsing and manipulation library.
 */
public object Toml {
    public const val VERSION: String = "0.8.20"

    /**
     * Parses a string into a TOML Table.
     */
    public fun fromStr(s: String): Table =
        io.github.kotlinmania.toml.de
            .fromStr(s)

    /**
     * Parses bytes into a TOML Table.
     */
    public fun fromSlice(s: ByteArray): Table =
        io.github.kotlinmania.toml.de
            .fromSlice(s)

    /**
     * Serializes a TOML Table to a String.
     */
    public fun toString(table: Table): String =
        io.github.kotlinmania.toml.ser
            .toString(table)

    /**
     * Serializes a TOML Table to a pretty String.
     */
    public fun toStringPretty(table: Table): String =
        io.github.kotlinmania.toml.ser
            .toStringPretty(table)
}

/**
 * Deserializes a string into a TOML Table.
 */
public fun fromStr(s: String): Table =
    io.github.kotlinmania.toml.de
        .fromStr(s)

/**
 * Deserializes bytes into a TOML Table.
 */
public fun fromSlice(s: ByteArray): Table =
    io.github.kotlinmania.toml.de
        .fromSlice(s)

/**
 * Serializes a TOML Table to a String.
 */
public fun toTomlString(table: Table): String =
    io.github.kotlinmania.toml.ser
        .toString(table)

/**
 * Serializes a TOML Table to a pretty String.
 */
public fun toTomlStringPretty(table: Table): String =
    io.github.kotlinmania.toml.ser
        .toStringPretty(table)
