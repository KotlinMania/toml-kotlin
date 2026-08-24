// port-lint: source de/error.rs
package io.github.kotlinmania.toml.de

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
 * Errors that can occur when deserializing a type.
 */
public class Error(
    override val message: String,
    private var input: String? = null,
    private var span: IntRange? = null,
) : Exception(message) {
    private val keys: MutableList<String> = mutableListOf()

    public fun addKey(key: String) {
        keys.add(0, key)
    }

    /** The start/end index into the original document where the error occurred */
    public fun span(): IntRange? = span

    public fun setSpan(span: IntRange?) {
        this.span = span
    }

    /** Provide the encoded TOML the error applies to */
    public fun setInput(input: String?) {
        this.input = input
    }

    override fun toString(): String =
        buildString {
            var context = false
            val currentInput = input
            val currentSpan = span
            if (currentInput != null && currentSpan != null) {
                context = true
                val (line, column) = translatePosition(currentInput, currentSpan.first)
                val lineNum = line + 1
                val colNum = column + 1
                val gutter = lineNum.toString().length
                val lines = currentInput.split('\n')
                val content = lines.getOrNull(line) ?: ""
                val highlightLen =
                    (currentSpan.last - currentSpan.first + 1)
                        .coerceAtMost((content.length - column).coerceAtLeast(0))

                appendLine("TOML parse error at line $lineNum, column $colNum")
                for (i in 0..gutter) {
                    append(" ")
                }
                appendLine("|")
                append("$lineNum | ")
                appendLine(content)

                for (i in 0..gutter) {
                    append(" ")
                }
                append("|")
                for (i in 0..column) {
                    append(" ")
                }
                append("^")
                for (i in 1 until highlightLen) {
                    append("^")
                }
                appendLine()
            }
            append(message)
            if (!context && keys.isNotEmpty()) {
                append("\nin `")
                append(keys.joinToString("."))
                append("`")
            }
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Error) return false
        return message == other.message &&
            input == other.input &&
            keys == other.keys &&
            span == other.span
    }

    override fun hashCode(): Int {
        var result = message.hashCode()
        result = 31 * result + (input?.hashCode() ?: 0)
        result = 31 * result + keys.hashCode()
        result = 31 * result + (span?.hashCode() ?: 0)
        return result
    }

    public companion object {
        public fun custom(
            msg: CharSequence,
            span: IntRange? = null,
        ): Error =
            Error(
                message = msg.toString(),
                input = null,
                span = span,
            )

        internal fun translatePosition(
            input: String,
            index: Int,
        ): Pair<Int, Int> {
            if (input.isEmpty()) {
                return Pair(0, index)
            }
            val safeIndex = index.coerceAtMost(input.length - 1)
            val columnOffset = index - safeIndex
            val clamped = safeIndex

            var lastNl = -1
            for (i in (clamped - 1) downTo 0) {
                if (input[i] == '\n') {
                    lastNl = i
                    break
                }
            }
            val lineStart = if (lastNl >= 0) lastNl + 1 else 0
            var line = 0
            for (i in 0 until lineStart) {
                if (input[i] == '\n') {
                    line++
                }
            }
            val column =
                (
                    if (lineStart <= clamped) {
                        clamped - lineStart
                    } else {
                        0
                    }
                ) + columnOffset
            return Pair(line, column)
        }
    }
}
