// port-lint: source src/value.rs
package io.github.kotlinmania.toml

import io.github.kotlinmania.toml.map.TomlMap

/**
 * Representation of a TOML value.
 */
public sealed class Value {
    /** Represents a TOML string */
    public data class String(
        public val value: kotlin.String,
    ) : Value() {
        override fun toString(): kotlin.String = "\"$value\""
    }

    /** Represents a TOML integer */
    public data class Integer(
        public val value: Long,
    ) : Value() {
        override fun toString(): kotlin.String = value.toString()
    }

    /** Represents a TOML float */
    public data class Float(
        public val value: Double,
    ) : Value() {
        override fun toString(): kotlin.String = value.toString()
    }

    /** Represents a TOML boolean */
    public data class Boolean(
        public val value: kotlin.Boolean,
    ) : Value() {
        override fun toString(): kotlin.String = value.toString()
    }

    /** Represents a TOML datetime */
    public data class Datetime(
        public val value: kotlin.String,
    ) : Value() {
        override fun toString(): kotlin.String = value
    }

    /** Represents a TOML array */
    public data class Array(
        public val value: List<Value>,
    ) : Value() {
        public constructor(vararg elements: Value) : this(elements.toList())

        override fun toString(): kotlin.String = value.toString()
    }

    /** Represents a TOML table */
    public data class Table(
        public val value: TomlMap<kotlin.String, Value>,
    ) : Value() {
        public constructor(map: Map<kotlin.String, Value> = emptyMap()) : this(TomlMap(map))

        override fun toString(): kotlin.String = value.toString()
    }

    /** Extracts the integer value if it is an integer. */
    public fun asInteger(): Long? = (this as? Integer)?.value

    /** Tests whether this value is an integer. */
    public val isInteger: kotlin.Boolean get() = this is Integer

    /** Extracts the float value if it is a float. */
    public fun asFloat(): Double? = (this as? Float)?.value

    /** Tests whether this value is a float. */
    public val isFloat: kotlin.Boolean get() = this is Float

    /** Extracts the boolean value if it is a boolean. */
    public fun asBoolean(): kotlin.Boolean? = (this as? Boolean)?.value

    /** Tests whether this value is a boolean. */
    public val isBoolean: kotlin.Boolean get() = this is Boolean

    /** Extracts the string of this value if it is a string. */
    public fun asString(): kotlin.String? = (this as? String)?.value

    /** Tests if this value is a string. */
    public val isString: kotlin.Boolean get() = this is String

    /** Extracts the datetime value if it is a datetime. */
    public fun asDatetime(): kotlin.String? = (this as? Datetime)?.value

    /** Tests whether this value is a datetime. */
    public val isDatetime: kotlin.Boolean get() = this is Datetime

    /** Extracts the array value if it is an array. */
    public fun asArray(): List<Value>? = (this as? Array)?.value

    /** Tests whether this value is an array. */
    public val isArray: kotlin.Boolean get() = this is Array

    /** Extracts the table value if it is a table. */
    public fun asTable(): TomlMap<kotlin.String, Value>? = (this as? Table)?.value

    /** Tests whether this value is a table. */
    public val isTable: kotlin.Boolean get() = this is Table

    /** Tests whether this and another value have the same type. */
    public fun sameType(other: Value): kotlin.Boolean = this::class == other::class

    /** Returns a human-readable representation of the type of this value. */
    public fun typeStr(): kotlin.String =
        when (this) {
            is String -> "string"
            is Integer -> "integer"
            is Float -> "float"
            is Boolean -> "boolean"
            is Datetime -> "datetime"
            is Array -> "array"
            is Table -> "table"
        }

    /** Index into a TOML table by key. */
    public operator fun get(key: kotlin.String): Value? = (this as? Table)?.value?.get(key)

    /** Index into a TOML array by index. */
    public operator fun get(index: Int): Value? = (this as? Array)?.value?.getOrNull(index)

    public companion object {
        public fun from(value: kotlin.String): Value = String(value)

        public fun from(value: Long): Value = Integer(value)

        public fun from(value: Int): Value = Integer(value.toLong())

        public fun from(value: Double): Value = Float(value)

        public fun from(value: kotlin.Boolean): Value = Boolean(value)

        public fun from(value: List<Value>): Value = Array(value)

        public fun from(value: Map<kotlin.String, Value>): Value = Table(TomlMap(value))
    }
}
