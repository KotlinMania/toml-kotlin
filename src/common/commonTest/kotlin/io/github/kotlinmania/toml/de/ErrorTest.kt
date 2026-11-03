// port-lint: tests de/error.rs
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

import kotlin.test.Test
import kotlin.test.assertEquals

class ErrorTest {
    @Test
    fun empty() {
        val input = ""
        val index = 0
        val position = Error.translatePosition(input, index)
        assertEquals(Pair(0, 0), position)
    }

    @Test
    fun start() {
        val input = "Hello"
        val index = 0
        val position = Error.translatePosition(input, index)
        assertEquals(Pair(0, 0), position)
    }

    @Test
    fun end() {
        val input = "Hello"
        val index = input.length - 1
        val position = Error.translatePosition(input, index)
        assertEquals(Pair(0, input.length - 1), position)
    }

    @Test
    fun after() {
        val input = "Hello"
        val index = input.length
        val position = Error.translatePosition(input, index)
        assertEquals(Pair(0, input.length), position)
    }

    @Test
    fun firstLine() {
        val input = "Hello\nWorld\n"
        val index = 2
        val position = Error.translatePosition(input, index)
        assertEquals(Pair(0, 2), position)
    }

    @Test
    fun endOfLine() {
        val input = "Hello\nWorld\n"
        val index = 5
        val position = Error.translatePosition(input, index)
        assertEquals(Pair(0, 5), position)
    }

    @Test
    fun startOfSecondLine() {
        val input = "Hello\nWorld\n"
        val index = 6
        val position = Error.translatePosition(input, index)
        assertEquals(Pair(1, 0), position)
    }

    @Test
    fun secondLine() {
        val input = "Hello\nWorld\n"
        val index = 8
        val position = Error.translatePosition(input, index)
        assertEquals(Pair(1, 2), position)
    }

    @Test
    fun errorMessageAndNew() {
        val err = Error.new("expected integer", "foo = bar", TomlSpan(0, 3))
        assertEquals("expected integer", err.message())
        assertEquals(TomlSpan(0, 3), err.span())
        assertEquals(err.toString(), err.fmt())
    }

    @Test
    fun renderLiteralTest() {
        assertEquals("newline", Error.renderLiteral("\n"))
        assertEquals("'`'", Error.renderLiteral("`"))
        assertEquals("`abc`", Error.renderLiteral("abc"))
    }

    @Test
    fun tomlSinkOption() {
        val sink = TomlSink.ofOption("foo = bar")
        assertEquals(null, sink.intoInner())
        val err = Error.new("err1")
        sink.reportError(err)
        assertEquals(err, sink.intoInner())
        val err2 = Error.new("err2")
        sink.reportError(err2)
        assertEquals(err, sink.intoInner())
    }

    @Test
    fun tomlSinkList() {
        val sink = TomlSink.ofList("foo = bar")
        assertEquals(0, sink.intoInner().size)
        val err1 = Error.new("err1")
        val err2 = Error.new("err2")
        sink.reportError(err1)
        sink.reportError(err2)
        assertEquals(listOf(err1, err2), sink.intoInner())
    }
}
