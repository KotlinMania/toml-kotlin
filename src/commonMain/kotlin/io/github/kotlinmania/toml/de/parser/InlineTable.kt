// port-lint: source de/parser/inline_table.rs
package io.github.kotlinmania.toml.de.parser

/**
 * Parsers for TOML inline tables.
 */
public object InlineTableParser {

    public fun parseInlineTable(raw: String): DeTable {
        val table = DeTable(isInline = true)
        var s = raw.trim()
        if (s.startsWith("{") && s.endsWith("}")) {
            s = s.substring(1, s.length - 1).trim()
        }

        var idx = 0
        val len = s.length

        while (idx < len) {
            idx = ArrayParser.skipWhitespaceAndComments(s, idx)
            if (idx >= len) break

            val eqIdx = findEquals(s, idx)
            if (eqIdx == -1) break

            val keyPart = s.substring(idx, eqIdx).trim()
            val keys = KeyParser.parseKeyPath(keyPart)
            if (keys.isEmpty()) break

            val valStart = eqIdx + 1
            val (valEnd, nextIdx) = findValueEnd(s, valStart)
            val valPart = s.substring(valStart, valEnd).trim()
            val value = ArrayParser.parseAnyValue(valPart)

            insertPath(table, keys, value)
            idx = nextIdx
        }

        return table
    }

    private fun findEquals(s: String, startIndex: Int): Int {
        var idx = startIndex
        val len = s.length
        var inQuotes = false
        var quoteChar = ' '

        while (idx < len) {
            val c = s[idx]
            if (inQuotes) {
                if (c == '\\' && quoteChar == '"' && idx + 1 < len) {
                    idx += 2
                    continue
                }
                if (c == quoteChar) inQuotes = false
            } else {
                if (c == '"' || c == '\'') {
                    inQuotes = true
                    quoteChar = c
                } else if (c == '=') {
                    return idx
                }
            }
            idx++
        }
        return -1
    }

    private fun findValueEnd(s: String, startIndex: Int): Pair<Int, Int> {
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
                if (c == quoteChar) inQuotes = false
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

    private fun insertPath(root: DeTable, keys: List<String>, value: DeValue) {
        var current = root
        for (i in 0 until keys.size - 1) {
            val k = keys[i]
            val existing = current.entries.firstOrNull { it.key.value == k }?.value?.value
            val nextTable = when (existing) {
                is DeValue.Table -> existing.value
                else -> {
                    val newTable = DeTable(isDotted = true, isInline = true)
                    current[Spanned(k)] = Spanned(DeValue.Table(newTable))
                    newTable
                }
            }
            current = nextTable
        }
        val lastKey = keys.last()
        current[Spanned(lastKey)] = Spanned(value)
    }
}
