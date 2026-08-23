// port-lint: source src/map.rs
package io.github.kotlinmania.toml.map

/**
 * Represents a TOML key/value type.
 */
public class TomlMap<K : Comparable<K>, V>(
    private val underlying: MutableMap<K, V> = linkedMapOf(),
    public var isDotted: Boolean = false,
    public var isImplicit: Boolean = false,
    public var isInline: Boolean = false,
) : MutableMap<K, V> by underlying {
    public constructor(other: Map<K, V>) : this(linkedMapOf<K, V>().apply { putAll(other) })

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TomlMap<*, *>) return false
        return underlying == other.underlying
    }

    override fun hashCode(): Int = underlying.hashCode()

    override fun toString(): String = underlying.toString()
}
