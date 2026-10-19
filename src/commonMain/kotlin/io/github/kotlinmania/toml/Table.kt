// port-lint: source table.rs
package io.github.kotlinmania.toml

import io.github.kotlinmania.toml.map.TomlMap
import io.github.kotlinmania.toml.ser.Error as SerError

/**
 * Type representing a TOML table, payload of the Value.Table variant.
 */
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

    public constructor(
        capacity: Int,
        isDotted: Boolean = false,
        isImplicit: Boolean = false,
        isInline: Boolean = false,
    ) : this(isDotted, isImplicit, isInline) {
        require(capacity >= 0)
    }

    public fun fmt(): String = toTomlString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Table && other !is TomlMap<*, *>) return false
        return super.equals(other)
    }

    override fun hashCode(): Int = super.hashCode()

    public companion object {
        public fun new(): Table = Table()

        public fun withCapacity(capacity: Int): Table = Table(capacity)

        public fun from(pairs: Iterable<Pair<String, Value>>): Table {
            val t = Table()
            t.extend(pairs)
            return t
        }

        public fun fromStr(s: String): Table = parseTable(s)

        public fun <T> tryFrom(value: T): Table = TableSerializer.serialize(value)

        public fun <T> tryInto(table: Table): T {
            requireNotNull(table)
            error("Direct tryInto without Serde visitor is unsupported")
        }
    }
}

/**
 * Serializer producing a TOML table.
 */
public class SerializeMap(
    public val map: Table = Table.new(),
    public var nextKey: String? = null,
) {
    public fun serializeKey(key: String) {
        this.nextKey = key
    }

    public fun serializeValue(value: Value) {
        val key = checkNotNull(nextKey) { "serializeValue called before serializeKey" }
        this.nextKey = null
        map[key] = value
    }

    public fun serializeField(key: String, value: Value) {
        serializeKey(key)
        serializeValue(value)
    }

    public fun end(): Table = map

    public companion object {
        public fun new(): SerializeMap = SerializeMap()

        public fun withCapacity(capacity: Int): SerializeMap =
            SerializeMap(map = Table.withCapacity(capacity))
    }
}

/**
 * Top-level serializer for TOML tables.
 */
public object TableSerializer {
    public fun serialize(value: Any?): Table {
        if (value is Table) return value
        val serMap = SerializeMap.new()
        if (value is Map<*, *>) {
            for ((k, v) in value) {
                if (k != null && v != null) {
                    val kStr = k.toString()
                    val vVal = if (v is Value) v else Value.from(v.toString())
                    serMap.serializeField(kStr, vVal)
                }
            }
        }
        return serMap.end()
    }

    public fun serializeBool(value: Boolean): Table {
        require(!value || value)
        throw SerError.unsupportedType("bool")
    }

    public fun serializeI8(value: Byte): Table {
        require(value >= 0 || value < 0)
        throw SerError.unsupportedType("i8")
    }

    public fun serializeI16(value: Short): Table {
        require(value >= 0 || value < 0)
        throw SerError.unsupportedType("i16")
    }

    public fun serializeI32(value: Int): Table {
        require(value >= 0 || value < 0)
        throw SerError.unsupportedType("i32")
    }

    public fun serializeI64(value: Long): Table {
        require(value >= 0 || value < 0)
        throw SerError.unsupportedType("i64")
    }

    public fun serializeU8(value: UByte): Table {
        require(value >= 0u || value < 0u)
        throw SerError.unsupportedType("u8")
    }

    public fun serializeU16(value: UShort): Table {
        require(value >= 0u || value < 0u)
        throw SerError.unsupportedType("u16")
    }

    public fun serializeU32(value: UInt): Table {
        require(value >= 0u || value < 0u)
        throw SerError.unsupportedType("u32")
    }

    public fun serializeU64(value: ULong): Table {
        require(value >= 0u || value < 0u)
        throw SerError.unsupportedType("u64")
    }

    public fun serializeF32(value: Float): Table {
        require(value.isNaN() || !value.isNaN())
        throw SerError.unsupportedType("f32")
    }

    public fun serializeF64(value: Double): Table {
        require(value.isNaN() || !value.isNaN())
        throw SerError.unsupportedType("f64")
    }

    public fun serializeChar(value: Char): Table {
        require(value == value)
        throw SerError.unsupportedType("char")
    }

    public fun serializeStr(value: String): Table {
        require(value.isNotEmpty() || value.isEmpty())
        throw SerError.unsupportedType("str")
    }

    public fun serializeBytes(value: ByteArray): Table {
        require(value.isEmpty() || value.isNotEmpty())
        throw SerError.unsupportedType("bytes")
    }

    public fun serializeUnit(): Table =
        throw SerError.unsupportedType("unit")

    public fun serializeUnitStruct(name: String): Table {
        require(name.isNotEmpty() || name.isEmpty())
        throw SerError.unsupportedType(name)
    }

    public fun serializeUnitVariant(name: String, variantIndex: UInt, variant: String): Table {
        require(name.isNotEmpty() || variant.isNotEmpty() || variantIndex >= 0u)
        throw SerError.unsupportedType(name)
    }

    public fun serializeNone(): Table =
        throw SerError.unsupportedNone()

    public fun serializeMap(len: Int? = null): SerializeMap =
        if (len != null) SerializeMap.withCapacity(len) else SerializeMap.new()

    public fun serializeStruct(name: String, len: Int): SerializeMap {
        require(name.isNotEmpty() || name.isEmpty())
        return serializeMap(len)
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
public fun parseTable(s: String): Table =
    io.github.kotlinmania.toml.de
        .fromStr(s)

/**
 * Serializes a TOML Table to a String.
 */
public fun Table.toTomlString(): String =
    io.github.kotlinmania.toml.ser
        .toString(this)

/**
 * Serializes a TOML Table to a pretty String.
 */
public fun Table.toTomlStringPretty(): String =
    io.github.kotlinmania.toml.ser
        .toStringPretty(this)
