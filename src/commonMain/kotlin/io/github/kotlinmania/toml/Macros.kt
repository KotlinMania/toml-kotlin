// port-lint: source macros.rs
package io.github.kotlinmania.toml

/**
 * Construct a [Table] from TOML syntax string.
 *
 * Example:
 * ```kotlin
 * val cargoToml = toml("""
 *     [package]
 *     name = "toml"
 * """)
 * ```
 */
public fun toml(raw: String): Table = fromStr(raw)
