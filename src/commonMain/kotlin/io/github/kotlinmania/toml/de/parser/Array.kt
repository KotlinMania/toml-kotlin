// port-lint: source toml/src/de/parser/array.rs
package io.github.kotlinmania.toml.de.parser

/**
 * Parsers for TOML arrays.
 */
public object ArrayParser {
    public fun parseArray(raw: String): DeArray {
        val array = DeArray()
        var s = raw.trim()
        if (s.startsWith("[") && s.endsWith("]")) {
            s = s.substring(1, s.length - 1).trim()
        }

        var idx = 0
        val len = s.length

        while (idx < len) {
            // Skip whitespace, newlines, comments
            idx = skipWhitespaceAndComments(s, idx)
            if (idx >= len) break

            val elemStart = idx
            val (elemEnd, nextIdx) = extractNextElement(s, idx)
            val elemRaw = s.substring(elemStart, elemEnd).trim()
            if (elemRaw.isNotEmpty()) {
                val value = parseAnyValue(elemRaw)
                array.push(Spanned(value, elemStart..elemEnd))
            }
            idx = nextIdx
        }

        return array
    }

    internal fun skipWhitespaceAndComments(s: String, startIndex: Int): Int {
        var idx = startIndex
        val len = s.length
        while (idx < len) {
            val c = s[idx]
            if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
                idx++
            } else if (c == '#') {
                while (idx < len && s[idx] != '\n') {
                    idx++
                }
            } else {
                break
            }
        }
        return idx
    }

    private fun extractNextElement(s: String, startIndex: Int): Pair<Int, Int> {
        var idx = startIndex
        val len = s.length
        var inQuotes = false
        var quoteChar = ' '
        var inTripleQuotes = false
        var bracketDepth = 0
        var braceDepth = 0

        while (idx < len) {
            val c = s[idx]
            if (inTripleQuotes) {
                if (idx + 2 < len && s[idx] == quoteChar && s[idx + 1] == quoteChar && s[idx + 2] == quoteChar) {
                    inTripleQuotes = false
                    idx += 3
                    continue
                }
            } else if (inQuotes) {
                if (c == '\\' && quoteChar == '"' && idx + 1 < len) {
                    idx += 2
                    continue
                }
                if (c == quoteChar) {
                    inQuotes = false
                }
            } else {
                if (idx + 2 < len && (s.substring(idx, idx + 3) == "\"\"\"" || s.substring(idx, idx + 3) == "'''")) {
                    inTripleQuotes = true
                    quoteChar = s[idx]
                    idx += 3
                    continue
                } else if (c == '"' || c == '\'') {
                    inQuotes = true
                    quoteChar = c
                } else if (c == '[') {
                    bracketDepth++
                } else if (c == ']') {
                    if (bracketDepth > 0) bracketDepth--
                } else if (c == '{') {
                    braceDepth++
                } else if (c == '}') {
                    if (braceDepth > 0) braceDepth--
                } else if (c == ',' && bracketDepth == 0 && braceDepth == 0) {
                    return idx to idx + 1
                }
            }
            idx++
        }

        return idx to idx
    }

    internal fun parseAnyValue(s: String): DeValue {
        val trimmed = s.trim()
        if (trimmed.startsWith("[") && trimmed.endsWith("]")) {
            return DeValue.Array(parseArray(trimmed))
        }
        if (trimmed.startsWith("{") && trimmed.endsWith("}")) {
            return DeValue.Table(InlineTableParser.parseInlineTable(trimmed))
        }
        return ValueParser.parseScalar(trimmed)
    }
}
