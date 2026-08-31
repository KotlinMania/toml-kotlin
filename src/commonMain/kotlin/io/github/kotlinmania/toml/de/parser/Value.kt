// port-lint: source toml/src/de/parser/value.rs
package io.github.kotlinmania.toml.de.parser

/**
 * Parsers for TOML values including scalar values, strings, numbers, booleans, and datetimes.
 */
public object ValueParser {
    public fun parseScalar(raw: String): DeValue {
        val s = raw.trim()
        if (s == "true") return DeValue.Boolean(true)
        if (s == "false") return DeValue.Boolean(false)

        // String
        if (s.startsWith("\"\"\"") && s.endsWith("\"\"\"") && s.length >= 6) {
            val content = s.substring(3, s.length - 3)
            val trimmed =
                if (content.startsWith("\n")) {
                    content.substring(1)
                } else if (content.startsWith("\r\n")) {
                    content.substring(2)
                } else {
                    content
                }
            return DeValue.Str(KeyParser.decodeBasicString(trimmed))
        }
        if (s.startsWith("'''") && s.endsWith("'''") && s.length >= 6) {
            val content = s.substring(3, s.length - 3)
            val trimmed =
                if (content.startsWith("\n")) {
                    content.substring(1)
                } else if (content.startsWith("\r\n")) {
                    content.substring(2)
                } else {
                    content
                }
            return DeValue.Str(trimmed)
        }
        if (s.startsWith("\"") && s.endsWith("\"") && s.length >= 2) {
            return DeValue.Str(KeyParser.decodeBasicString(s.substring(1, s.length - 1)))
        }
        if (s.startsWith("'") && s.endsWith("'") && s.length >= 2) {
            return DeValue.Str(s.substring(1, s.length - 1))
        }

        // Float special values
        if (s == "inf" || s == "+inf" || s == "-inf" || s == "nan" || s == "+nan" || s == "-nan") {
            return DeValue.Float(DeFloat(s))
        }

        // Hex, Octal, Binary Integer
        if (s.startsWith("0x") || s.startsWith("0X")) {
            val clean = s.substring(2).replace("_", "")
            return DeValue.Integer(DeInteger(clean, 16u))
        }
        if (s.startsWith("0o") || s.startsWith("0O")) {
            val clean = s.substring(2).replace("_", "")
            return DeValue.Integer(DeInteger(clean, 8u))
        }
        if (s.startsWith("0b") || s.startsWith("0B")) {
            val clean = s.substring(2).replace("_", "")
            return DeValue.Integer(DeInteger(clean, 2u))
        }

        // Datetime (RFC 3339 patterns)
        if (isDatetimePattern(s)) {
            return DeValue.Datetime(s)
        }

        // Float (contains '.' or 'e'/'E')
        if (s.contains('.') || s.contains('e') || s.contains('E')) {
            val clean = s.replace("_", "")
            if (clean.toDoubleOrNull() != null) {
                return DeValue.Float(DeFloat(clean))
            }
        }

        // Integer
        val cleanInt = s.replace("_", "")
        if (cleanInt.toLongOrNull() != null) {
            return DeValue.Integer(DeInteger(cleanInt, 10u))
        }

        // Fallback to string
        return DeValue.Str(s)
    }

    private fun isDatetimePattern(s: String): Boolean {
        if (s.length < 5) return false
        // Date: YYYY-MM-DD
        if (s.length >= 10 && s[4] == '-' && s[7] == '-') {
            return true
        }
        // Time: HH:MM:SS
        if (s.length >= 5 && s[2] == ':') {
            return true
        }
        return false
    }
}
