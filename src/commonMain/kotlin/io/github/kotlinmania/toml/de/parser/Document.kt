// port-lint: source de/parser/document.rs
package io.github.kotlinmania.toml.de.parser

/**
 * Top-level document parser for TOML documents.
 */
public object DocumentParser {

    public fun parseDocument(input: String): DeTable {
        val root = DeTable()
        var currentTable = root
        var currentPath: List<String> = emptyList()
        var inArrayOfTables = false

        val lines = input.lines()
        var lineIdx = 0
        val totalLines = lines.size

        while (lineIdx < totalLines) {
            val rawLine = lines[lineIdx].trim()
            lineIdx++

            if (rawLine.isEmpty() || rawLine.startsWith("#")) {
                continue
            }

            // Array of tables [[header]]
            if (rawLine.startsWith("[[") && rawLine.endsWith("]]")) {
                val headerContent = rawLine.substring(2, rawLine.length - 2).trim()
                val path = KeyParser.parseKeyPath(headerContent)
                currentPath = path
                inArrayOfTables = true
                currentTable = navigateArrayOfTables(root, path)
                continue
            }

            // Standard table [header]
            if (rawLine.startsWith("[") && rawLine.endsWith("]")) {
                val headerContent = rawLine.substring(1, rawLine.length - 1).trim()
                val path = KeyParser.parseKeyPath(headerContent)
                currentPath = path
                inArrayOfTables = false
                currentTable = navigateStandardTable(root, path)
                continue
            }

            // Key-value pair
            val eqIdx = rawLine.indexOf('=')
            if (eqIdx != -1) {
                val keyPart = rawLine.substring(0, eqIdx).trim()
                val keys = KeyParser.parseKeyPath(keyPart)
                if (keys.isEmpty()) continue

                // Accumulate value possibly spanning multiple lines (e.g. multiline string, multiline array)
                var valPart = rawLine.substring(eqIdx + 1).trim()
                while (isMultilineIncomplete(valPart) && lineIdx < totalLines) {
                    valPart += "\n" + lines[lineIdx]
                    lineIdx++
                }

                // Strip trailing comment if not in quotes
                val cleanVal = stripTrailingComment(valPart)
                val value = ArrayParser.parseAnyValue(cleanVal)

                insertDottedKey(currentTable, keys, value)
            }
        }

        return root
    }

    private fun isMultilineIncomplete(s: String): Boolean {
        // Count unescaped triple quotes
        val tripleDouble = countOccurrences(s, "\"\"\"")
        if (tripleDouble % 2 != 0) return true

        val tripleSingle = countOccurrences(s, "'''")
        if (tripleSingle % 2 != 0) return true

        // Count brackets / braces outside strings
        var brackets = 0
        var braces = 0
        var inQuotes = false
        var quoteChar = ' '
        var i = 0
        while (i < s.length) {
            val c = s[i]
            if (inQuotes) {
                if (c == '\\' && quoteChar == '"' && i + 1 < s.length) {
                    i += 2
                    continue
                }
                if (c == quoteChar) inQuotes = false
            } else {
                if (c == '"' || c == '\'') {
                    inQuotes = true
                    quoteChar = c
                } else if (c == '[') brackets++
                else if (c == ']') { if (brackets > 0) brackets-- }
                else if (c == '{') braces++
                else if (c == '}') { if (braces > 0) braces-- }
            }
            i++
        }
        return brackets > 0 || braces > 0
    }

    private fun countOccurrences(s: String, sub: String): Int {
        var count = 0
        var idx = 0
        while (true) {
            val found = s.indexOf(sub, idx)
            if (found == -1) break
            count++
            idx = found + sub.length
        }
        return count
    }

    private fun stripTrailingComment(s: String): String {
        var inQuotes = false
        var quoteChar = ' '
        var inTriple = false
        var i = 0
        while (i < s.length) {
            val c = s[i]
            if (inTriple) {
                if (i + 2 < s.length && s[i] == quoteChar && s[i + 1] == quoteChar && s[i + 2] == quoteChar) {
                    inTriple = false
                    i += 3
                    continue
                }
            } else if (inQuotes) {
                if (c == '\\' && quoteChar == '"' && i + 1 < s.length) {
                    i += 2
                    continue
                }
                if (c == quoteChar) inQuotes = false
            } else {
                if (i + 2 < s.length && (s.substring(i, i + 3) == "\"\"\"" || s.substring(i, i + 3) == "'''")) {
                    inTriple = true
                    quoteChar = s[i]
                    i += 3
                    continue
                } else if (c == '"' || c == '\'') {
                    inQuotes = true
                    quoteChar = c
                } else if (c == '#') {
                    return s.substring(0, i).trim()
                }
            }
            i++
        }
        return s.trim()
    }

    private fun navigateStandardTable(root: DeTable, path: List<String>): DeTable {
        var current = root
        for (k in path) {
            val existing = current.entries.firstOrNull { it.key.value == k }?.value?.value
            val nextTable = when (existing) {
                is DeValue.Table -> existing.value
                else -> {
                    val newTable = DeTable()
                    current[Spanned(k)] = Spanned(DeValue.Table(newTable))
                    newTable
                }
            }
            current = nextTable
        }
        return current
    }

    private fun navigateArrayOfTables(root: DeTable, path: List<String>): DeTable {
        var current = root
        for (i in 0 until path.size - 1) {
            val k = path[i]
            val existing = current.entries.firstOrNull { it.key.value == k }?.value?.value
            val nextTable = when (existing) {
                is DeValue.Table -> existing.value
                is DeValue.Array -> {
                    val last = existing.value.lastOrNull()?.value
                    if (last is DeValue.Table) last.value else {
                        val t = DeTable()
                        existing.value.push(Spanned(DeValue.Table(t)))
                        t
                    }
                }
                else -> {
                    val newTable = DeTable()
                    current[Spanned(k)] = Spanned(DeValue.Table(newTable))
                    newTable
                }
            }
            current = nextTable
        }

        val lastKey = path.last()
        val existingArray = current.entries.firstOrNull { it.key.value == lastKey }?.value?.value
        val array = when (existingArray) {
            is DeValue.Array -> existingArray.value
            else -> {
                val newArray = DeArray()
                newArray.setArrayOfTables(true)
                current[Spanned(lastKey)] = Spanned(DeValue.Array(newArray))
                newArray
            }
        }
        val table = DeTable()
        array.push(Spanned(DeValue.Table(table)))
        return table
    }

    private fun insertDottedKey(target: DeTable, keys: List<String>, value: DeValue) {
        var current = target
        for (i in 0 until keys.size - 1) {
            val k = keys[i]
            val existing = current.entries.firstOrNull { it.key.value == k }?.value?.value
            val nextTable = when (existing) {
                is DeValue.Table -> existing.value
                else -> {
                    val newTable = DeTable(isDotted = true)
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
