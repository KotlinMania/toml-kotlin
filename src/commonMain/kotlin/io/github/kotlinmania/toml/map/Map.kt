// port-lint: source toml/src/map.rs
package io.github.kotlinmania.toml.map

/**
 * Represents a TOML key/value map type.
 */
public open class TomlMap<K, V>(
    public var isDotted: Boolean = false,
    public var isImplicit: Boolean = false,
    public var isInline: Boolean = false,
) : Map<K, V> {
    private val underlying: LinkedHashMap<K, V> = LinkedHashMap()

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

    /**
     * Returns the number of elements in the map.
     */
    public fun len(): Int = underlying.size

    override fun containsKey(key: K): Boolean = underlying.containsKey(key)

    override fun containsValue(value: V): Boolean = underlying.containsValue(value)

    override fun get(key: K): V? = underlying[key]

    public fun getMut(key: K): V? = underlying[key]

    public fun getKeyValue(key: K): Pair<K, V>? {
        for ((k, v) in underlying) {
            if (k == key) {
                return k to v
            }
        }
        return null
    }

    override fun isEmpty(): Boolean = underlying.isEmpty()

    public fun insert(key: K, value: V): V? = underlying.put(key, value)

    public fun put(key: K, value: V): V? = underlying.put(key, value)

    public operator fun set(key: K, value: V) {
        underlying[key] = value
    }

    public fun remove(key: K): V? = underlying.remove(key)

    public fun removeEntry(key: K): Pair<K, V>? {
        val v = underlying.remove(key) ?: return null
        return key to v
    }

    public fun retain(predicate: (K, V) -> Boolean) {
        val iterator = underlying.entries.iterator()
        while (iterator.hasNext()) {
            val entry = iterator.next()
            if (!predicate(entry.key, entry.value)) {
                iterator.remove()
            }
        }
    }

    public fun mutEntries(op: (K, V) -> Pair<K, V>) {
        val pairs = underlying.entries.map { it.key to it.value }
        underlying.clear()
        for ((k, v) in pairs) {
            val (newK, newV) = op(k, v)
            underlying[newK] = newV
        }
    }

    public fun entry(key: K): Entry<K, V> {
        return if (containsKey(key)) {
            Entry.Occupied(OccupiedEntry(this, key))
        } else {
            Entry.Vacant(VacantEntry(this, key))
        }
    }

    public fun clear() {
        underlying.clear()
    }

    public fun putAll(from: Map<out K, V>) {
        underlying.putAll(from)
    }

    public fun extend(iter: Iterable<Pair<K, V>>) {
        for ((k, v) in iter) {
            underlying[k] = v
        }
    }

    public fun iter(): Iterator<Map.Entry<K, V>> = underlying.entries.iterator()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TomlMap<*, *>) return false
        return underlying == other.underlying
    }

    override fun hashCode(): Int = underlying.hashCode()

    override fun toString(): String = underlying.toString()

    public companion object {
        public fun <K, V> new(): TomlMap<K, V> = TomlMap()

        public fun <K, V> withCapacity(@Suppress("UNUSED_PARAMETER") capacity: Int): TomlMap<K, V> = TomlMap()

        public fun <K, V> from(pairs: Iterable<Pair<K, V>>): TomlMap<K, V> {
            val map = TomlMap<K, V>()
            map.extend(pairs)
            return map
        }
    }
}

/**
 * A view into a single entry in a map, which may either be vacant or occupied.
 */
public sealed class Entry<K, V> {
    public abstract val key: K

    public fun orInsert(default: V): V =
        when (this) {
            is Vacant -> insert(default)
            is Occupied -> intoMut()
        }

    public fun orInsertWith(default: () -> V): V =
        when (this) {
            is Vacant -> insert(default())
            is Occupied -> intoMut()
        }

    public class Vacant<K, V>(public val entry: VacantEntry<K, V>) : Entry<K, V>() {
        override val key: K get() = entry.key()

        public fun insert(value: V): V = entry.insert(value)
    }

    public class Occupied<K, V>(public val entry: OccupiedEntry<K, V>) : Entry<K, V>() {
        override val key: K get() = entry.key()

        public fun get(): V = entry.get()

        public fun getMut(): V = entry.getMut()

        public fun intoMut(): V = entry.intoMut()

        public fun insert(value: V): V = entry.insert(value)

        public fun remove(): V = entry.remove()
    }
}

/**
 * A vacant Entry.
 */
public class VacantEntry<K, V>(
    private val map: TomlMap<K, V>,
    private val keyVal: K,
) {
    public fun key(): K = keyVal

    public fun insert(value: V): V {
        map[keyVal] = value
        return value
    }
}

/**
 * An occupied Entry.
 */
public class OccupiedEntry<K, V>(
    private val map: TomlMap<K, V>,
    private val keyVal: K,
) {
    public fun key(): K = keyVal

    public fun get(): V = map[keyVal] ?: error("Occupied entry missing key")

    public fun getMut(): V = map[keyVal] ?: error("Occupied entry missing key")

    public fun intoMut(): V = map[keyVal] ?: error("Occupied entry missing key")

    public fun insert(value: V): V {
        val old = map.put(keyVal, value)
        return old ?: value
    }

    public fun remove(): V {
        return map.remove(keyVal) ?: error("Occupied entry missing key")
    }
}
