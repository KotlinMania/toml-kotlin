// port-lint: source toml/src/ser/value/key.rs
package io.github.kotlinmania.toml.ser.value

/**
 * Serializes keys into valid TOML bare or quoted key strings.
 */
public object KeySerializer {
    public fun isBareKey(key: String): Boolean {
        if (key.isEmpty()) return false
        for (c in key) {
            if (!(c in 'a'..'z' || c in 'A'..'Z' || c in '0'..'9' || c == '-' || c == '_')) {
                return false
            }
        }
        return true
    }

    public fun formatKey(key: String): String {
        if (isBareKey(key)) {
            return key
        }
        return quoteKey(key)
    }

    public fun quoteKey(key: String): String {
        val sb = StringBuilder("\"")
        for (c in key) {
            when (c) {
                '\"' -> sb.append("\\\"")
                '\\' -> sb.append("\\\\")
                '\b' -> sb.append("\\b")
                '\t' -> sb.append("\\t")
                '\n' -> sb.append("\\n")
                '\u000C' -> sb.append("\\f")
                '\r' -> sb.append("\\r")
                else -> {
                    if (c.code < 0x20 || c.code == 0x7F) {
                        val hex = c.code.toString(16).padStart(4, '0')
                        sb.append("\\u").append(hex)
                    } else {
                        sb.append(c)
                    }
                }
            }
        }
        sb.append("\"")
        return sb.toString()
    }
}
