// port-lint: source de/parser/devalue.rs
package io.github.kotlinmania.toml.de.parser

/**
 * Spanned wrapper attaching source position information.
 */
public data class Spanned<T>(
    public val value: T,
    public val span: IntRange = 0..0,
) {
    override fun toString(): String = value.toString()
}

/**
 * Represents a TOML integer during deserialization.
 */
public data class DeInteger(
    public val inner: String,
    public val radix: UInt = 10u,
) {
    public fun toU64(): ULong? =
        try {
            inner.toULong(radix.toInt())
        } catch (_: Exception) {
            null
        }

    public fun toI64(): Long? =
        try {
            inner.toLong(radix.toInt())
        } catch (_: Exception) {
            null
        }

    public fun toU128(): String? = toU64()?.toString()

    public fun toI128(): String? = toI64()?.toString()

    public fun asStr(): String = inner

    override fun toString(): String =
        when (radix) {
            2u -> "0b$inner"
            8u -> "0o$inner"
            10u -> inner
            16u -> "0x$inner"
            else -> inner
        }

    public companion object {
        public fun default(): DeInteger = DeInteger("0", 10u)
    }
}

/**
 * Represents a TOML float during deserialization.
 */
public data class DeFloat(
    public val inner: String,
) {
    public fun toF64(): Double? {
        val f = inner.toDoubleOrNull() ?: return null
        if (f.isInfinite() && !inner.contains("inf")) {
            return null
        }
        return f
    }

    public fun asStr(): String = inner

    override fun toString(): String = inner

    public companion object {
        public fun default(): DeFloat = DeFloat("0.0")
    }
}

/**
 * Representation of a TOML value for deserialization.
 */
public sealed class DeValue {
    public data class Str(public val value: kotlin.String) : DeValue()
    public data class Integer(public val value: DeInteger) : DeValue()
    public data class Float(public val value: DeFloat) : DeValue()
    public data class Boolean(public val value: kotlin.Boolean) : DeValue()
    public data class Datetime(public val value: kotlin.String) : DeValue()
    public data class Array(public val value: DeArray) : DeValue()
    public data class Table(public val value: DeTable) : DeValue()

    public fun makeOwned() {
        when (this) {
            is Array -> {
                for (e in value) {
                    e.value.makeOwned()
                }
            }
            is Table -> value.makeOwned()
            else -> {}
        }
    }

    public fun get(index: Index): Spanned<DeValue>? = index.index(this)

    public fun asInteger(): DeInteger? = (this as? Integer)?.value

    public fun isInteger(): kotlin.Boolean = this is Integer

    public val isIntegerVal: kotlin.Boolean get() = this is Integer

    public fun asFloat(): DeFloat? = (this as? Float)?.value

    public fun isFloat(): kotlin.Boolean = this is Float

    public val isFloatVal: kotlin.Boolean get() = this is Float

    public fun asBool(): kotlin.Boolean? = (this as? Boolean)?.value

    public fun isBool(): kotlin.Boolean = this is Boolean

    public val isBoolVal: kotlin.Boolean get() = this is Boolean

    public fun asStr(): kotlin.String? = (this as? Str)?.value

    public fun isStr(): kotlin.Boolean = this is Str

    public val isStrVal: kotlin.Boolean get() = this is Str

    public fun asDatetime(): kotlin.String? = (this as? Datetime)?.value

    public fun isDatetime(): kotlin.Boolean = this is Datetime

    public val isDatetimeVal: kotlin.Boolean get() = this is Datetime

    public fun asArray(): DeArray? = (this as? Array)?.value

    public fun asArrayMut(): DeArray? = (this as? Array)?.value

    public fun isArray(): kotlin.Boolean = this is Array

    public val isArrayVal: kotlin.Boolean get() = this is Array

    public fun asTable(): DeTable? = (this as? Table)?.value

    public fun asTableMut(): DeTable? = (this as? Table)?.value

    public fun isTable(): kotlin.Boolean = this is Table

    public val isTableVal: kotlin.Boolean get() = this is Table

    public fun sameType(other: DeValue): kotlin.Boolean = this::class == other::class

    public fun typeStr(): kotlin.String =
        when (this) {
            is Str -> "string"
            is Integer -> "integer"
            is Float -> "float"
            is Boolean -> "boolean"
            is Datetime -> "datetime"
            is Array -> "array"
            is Table -> "table"
        }

    public fun toValue(): io.github.kotlinmania.toml.Value =
        when (this) {
            is Str -> io.github.kotlinmania.toml.Value.Str(value)
            is Integer -> io.github.kotlinmania.toml.Value.Integer(value.toI64() ?: 0L)
            is Float -> io.github.kotlinmania.toml.Value.Float(value.toF64() ?: 0.0)
            is Boolean -> io.github.kotlinmania.toml.Value.Boolean(value)
            is Datetime -> io.github.kotlinmania.toml.Value.Datetime(value)
            is Array -> io.github.kotlinmania.toml.Value.Array(value.toArray())
            is Table -> io.github.kotlinmania.toml.Value.Table(value.toTable())
        }
}

/**
 * Types that can be used to index a [DeValue].
 */
public interface Index {
    public fun index(value: DeValue): Spanned<DeValue>?
}

public class IntIndex(public val index: Int) : Index {
    override fun index(value: DeValue): Spanned<DeValue>? =
        when (value) {
            is DeValue.Array -> value.value.getOrNull(index)
            else -> null
        }
}

public class StringIndex(public val key: String) : Index {
    override fun index(value: DeValue): Spanned<DeValue>? =
        when (value) {
            is DeValue.Table -> {
                value.value.entries.firstOrNull { it.key.value == key }?.value
            }
            else -> null
        }
}
