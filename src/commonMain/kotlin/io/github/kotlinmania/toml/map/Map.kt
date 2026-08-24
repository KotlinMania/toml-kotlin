// port-lint: source map.rs
package io.github.kotlinmania.toml.map

/**
 * Represents a TOML key/value type.
 */
public class TomlMap<K, V>(
    public var isDotted: Boolean = false,
    public var isImplicit: Boolean = false,
    public var isInline: Boolean = false,
) : Map<K, V> {
    private val underlying: MutableMap<K, V> = linkedMapOf()

    public constructor(
        other: Map<K, V>,
        isDotted: Boolean = false,
        isImplicit: Boolean = false,
        isInline: Boolean = false,
    ) : this(isDotted, isImplicit, isInline) {
        underlying.putAll(other)
    }

    override val entries: Set<Map.Entry<K, V>> get() = underlying.entries
    override val keys: Set<K> get() = underlying.keys
    override val size: Int get() = underlying.size
    override val values: Collection<V> get() = underlying.values

    override fun containsKey(key: K): Boolean = underlying.containsKey(key)

    override fun containsValue(value: V): Boolean = underlying.containsValue(value)

    override fun get(key: K): V? = underlying[key]

    override fun isEmpty(): Boolean = underlying.isEmpty()

    public fun put(key: K, value: V): V? = underlying.put(key, value)

    public operator fun set(key: K, value: V) {
        underlying[key] = value
    }

    public fun remove(key: K): V? = underlying.remove(key)

    public fun clear() {
        underlying.clear()
    }

    public fun putAll(from: Map<out K, V>) {
        underlying.putAll(from)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TomlMap<*, *>) return false
        return underlying == other.underlying
    }

    override fun hashCode(): Int = underlying.hashCode()

    override fun toString(): String = underlying.toString()
}
