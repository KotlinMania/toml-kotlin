// port-lint: source toml/src/ser/document/buffer.rs
package io.github.kotlinmania.toml.ser.document

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

/** TOML Document serialization buffer */
public class Buffer {
    private val tables: MutableList<Table?> = mutableListOf()

    public fun clear() {
        tables.clear()
    }

    internal fun rootTable(): Table = newTable(null)

    internal fun childTable(
        parent: Table,
        key: String,
    ): Table {
        parent.hasChildren(true)
        val keyPath = (parent.key ?: emptyList()) + listOf(key)
        return newTable(keyPath)
    }

    internal fun elementTable(
        parent: Table,
        key: String,
    ): Table {
        val table = childTable(parent, key)
        table.array = true
        return table
    }

    internal fun newTable(key: List<String>?): Table {
        val pos = tables.size
        val table =
            Table(
                key = key,
                body = StringBuilder(),
                hasChildrenField = false,
                array = false,
                pos = pos,
            )
        tables.add(null)
        return table
    }

    internal fun push(table: Table) {
        val pos = table.pos
        tables[pos] = table
    }

    override fun toString(): String =
        buildString {
            val filtered =
                tables
                    .filterNotNull()
                    .filter { requiredTable(it) }
            for ((index, table) in filtered.withIndex()) {
                if (index > 0) {
                    append("\n")
                }
                append(table.toString())
            }
        }

    public companion object {
        public fun new(): Buffer = Buffer()

        private fun requiredTable(table: Table): Boolean =
            if (table.key == null) {
                table.body.isNotEmpty()
            } else {
                table.array || table.body.isNotEmpty() || !table.hasChildren
            }
    }
}

internal class Table(
    val key: List<String>?,
    val body: StringBuilder,
    private var hasChildrenField: Boolean,
    var array: Boolean,
    val pos: Int,
) {
    val hasChildren: Boolean
        get() = hasChildrenField

    fun bodyMut(): StringBuilder = body

    fun hasChildren(yes: Boolean) {
        hasChildrenField = yes
    }

    override fun toString(): String =
        buildString {
            val currentKey = key
            if (currentKey != null) {
                if (array) {
                    append("[[")
                } else {
                    append("[")
                }
                append(currentKey.joinToString("."))
                if (array) {
                    append("]]\n")
                } else {
                    append("]\n")
                }
            }
            append(body)
        }
}
