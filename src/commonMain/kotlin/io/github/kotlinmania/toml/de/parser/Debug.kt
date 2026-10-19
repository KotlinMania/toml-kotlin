// port-lint: source de/parser/debug.rs
package io.github.kotlinmania.toml.de.parser

import kotlin.concurrent.atomics.AtomicInt
import kotlin.concurrent.atomics.ExperimentalAtomicApi

@OptIn(ExperimentalAtomicApi::class)
public class TraceScope(
    private val text: String,
    private var guard: DebugDepthGuard = DEBUG_DEPTH.scoped(),
) {
    init {
        trace("> $text")
    }

    public fun close() {
        val g = guard.take()
        g.close()
        trace("< $text")
    }

    public fun drop() {
        close()
    }

    public companion object {
        public fun new(text: CharSequence): TraceScope = TraceScope(text.toString())

        public fun trace(text: String) {
            val depth = DEBUG_DEPTH.depth()
            if (DEBUG_ENABLED) {
                val indent = " ".repeat(depth)
                println("$indent$text")
            }
        }

        private const val DEBUG_ENABLED: Boolean = false
    }
}

public fun trace(text: String) {
    TraceScope.trace(text)
}

@OptIn(ExperimentalAtomicApi::class)
public class DebugDepth(
    private val atomic: AtomicInt = AtomicInt(0),
) {
    public fun scoped(): DebugDepthGuard = DebugDepthGuard.new()

    public fun enterUnchecked(): Int = atomic.fetchAndAdd(1)

    public fun exitUnchecked() {
        atomic.fetchAndAdd(-1)
    }

    public fun depth(): Int = atomic.load()
}

@OptIn(ExperimentalAtomicApi::class)
private val DEBUG_DEPTH: DebugDepth = DebugDepth()

@OptIn(ExperimentalAtomicApi::class)
public class DebugDepthGuard(
    public val depth: Int,
    private var inc: Boolean = true,
) {
    public fun take(): DebugDepthGuard {
        val currentDepth = depth
        val currentInc = inc
        inc = false
        return DebugDepthGuard(currentDepth, currentInc)
    }

    public fun drop() {
        close()
    }

    public fun close() {
        if (inc) {
            DEBUG_DEPTH.exitUnchecked()
            inc = false
        }
    }

    public fun asRef(): Int = depth

    public fun deref(): Int = depth

    public companion object {
        public fun new(): DebugDepthGuard {
            val depth = DEBUG_DEPTH.enterUnchecked()
            return DebugDepthGuard(depth, inc = true)
        }
    }
}
