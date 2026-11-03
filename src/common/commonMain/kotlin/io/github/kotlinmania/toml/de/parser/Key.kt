// port-lint: source de/parser/key.rs
package io.github.kotlinmania.toml.de.parser

/**
 * Parses and processes TOML keys including simple and dotted keys.
 */
public object KeyParser {
    /**
     * Splits a raw key string into its dotted parts.
     */
    public fun parseKeyPath(raw: String): List<String> {
        val keys = mutableListOf<String>()
        var idx = 0
        val len = raw.length

        while (idx < len) {
            // Skip whitespace
            while (idx < len && (raw[idx] == ' ' || raw[idx] == '\t')) {
                idx++
            }
            if (idx >= len) break

            val ch = raw[idx]
            if (ch == '"') {
                val start = idx + 1
                var end = start
                var escaped = false
                while (end < len) {
                    if (escaped) {
                        escaped = false
                    } else if (raw[end] == '\\') {
                        escaped = true
                    } else if (raw[end] == '"') {
                        break
                    }
                    end++
                }
                val content = if (end <= len) raw.substring(start, end) else raw.substring(start)
                keys.add(decodeBasicString(content))
                idx = end + 1
            } else if (ch == '\'') {
                val start = idx + 1
                var end = start
                while (end < len && raw[end] != '\'') {
                    end++
                }
                val content = if (end <= len) raw.substring(start, end) else raw.substring(start)
                keys.add(content)
                idx = end + 1
            } else {
                val start = idx
                while (idx < len && raw[idx] != '.' && raw[idx] != ' ' && raw[idx] != '\t' && raw[idx] != '=') {
                    idx++
                }
                val key = raw.substring(start, idx).trim()
                if (key.isNotEmpty()) {
                    keys.add(key)
                }
            }

            // Skip whitespace up to next dot
            while (idx < len && (raw[idx] == ' ' || raw[idx] == '\t')) {
                idx++
            }
            if (idx < len && raw[idx] == '.') {
                idx++
            }
        }

        return keys
    }

    public fun decodeBasicString(s: String): String {
        val sb = StringBuilder()
        var i = 0
        val len = s.length
        while (i < len) {
            val c = s[i]
            if (c == '\\' && i + 1 < len) {
                i++
                when (val esc = s[i]) {
                    'b' -> sb.append('\b')
                    't' -> sb.append('\t')
                    'n' -> sb.append('\n')
                    'f' -> sb.append('\u000C')
                    'r' -> sb.append('\r')
                    '"' -> sb.append('"')
                    '\\' -> sb.append('\\')
                    'u' -> {
                        if (i + 4 < len) {
                            val hex = s.substring(i + 1, i + 5)
                            hex.toIntOrNull(16)?.let { sb.append(it.toChar()) } ?: sb.append("\\u").append(hex)
                            i += 4
                        } else {
                            sb.append("\\u")
                        }
                    }
                    'U' -> {
                        if (i + 8 < len) {
                            val hex = s.substring(i + 1, i + 9)
                            hex.toIntOrNull(16)?.let {
                                if (it in 0..0x10FFFF) {
                                    sb.append(it.toChar())
                                } else {
                                    sb.append("\\U").append(hex)
                                }
                            } ?: sb.append("\\U").append(hex)
                            i += 8
                        } else {
                            sb.append("\\U")
                        }
                    }
                    else -> sb.append('\\').append(esc)
                }
            } else {
                sb.append(c)
            }
            i++
        }
        return sb.toString()
    }
}
