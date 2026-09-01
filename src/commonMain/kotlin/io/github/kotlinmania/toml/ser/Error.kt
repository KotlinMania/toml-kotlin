// port-lint: source ser/error.rs
package io.github.kotlinmania.toml.ser

/*
 * Copyright (c) 2014 Alex Crichton
 * Copyright (c) 2025 Sydney Renee, The Solace Project
 *
 * This source code is dual-licensed under either the MIT license found in the
 * LICENSE-MIT file in the root directory of this source tree or the Apache
 * License, Version 2.0 found in the LICENSE-APACHE file in the root directory
 * of this source tree. You may select, at your option, one of the
 * above-listed licenses.
 */

/**
 * Errors that can occur when serializing a type.
 */
public class Error internal constructor(
    internal val inner: ErrorInner,
) : Exception(inner.toString()) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Error) return false
        return inner == other.inner
    }

    override fun hashCode(): Int = inner.hashCode()

    override fun toString(): String = inner.toString()

    public companion object {
        public fun custom(inner: CharSequence): Error = Error(ErrorInner.Custom(inner.toString()))

        public fun unsupportedType(type: String? = null): Error = Error(ErrorInner.UnsupportedType(type))

        public fun unsupportedNone(): Error = Error(ErrorInner.UnsupportedNone)

        public fun keyNotString(): Error = Error(ErrorInner.KeyNotString)

        public fun dateInvalid(): Error = Error(ErrorInner.DateInvalid)
    }
}

internal sealed class ErrorInner {
    data class UnsupportedType(
        val type: String?,
    ) : ErrorInner() {
        override fun toString(): String =
            if (type != null) "unsupported $type type" else "unsupported type"
    }

    data object UnsupportedNone : ErrorInner() {
        override fun toString(): String = "unsupported None value"
    }

    data object KeyNotString : ErrorInner() {
        override fun toString(): String = "map key was not a string"
    }

    data object DateInvalid : ErrorInner() {
        override fun toString(): String = "a serialized date was invalid"
    }

    data class Custom(
        val message: String,
    ) : ErrorInner() {
        override fun toString(): String = message
    }
}
