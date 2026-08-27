// port-lint: source ser/style.rs
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

/** Serialization style options. */
public data class Style(
    public val multilineArray: Boolean = false,
) {
    public companion object {
        public val DEFAULT: Style = Style()
        public val COMPACT: Style = Style(multilineArray = false)
        public val PRETTY: Style = Style(multilineArray = true)
    }
}
