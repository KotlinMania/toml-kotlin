import Testing
import Toml

// Smoke test for the Kotlin → Swift Export → SPM → swift test pipeline.
@Suite("Toml Swift Export Tests")
struct TomlExportTests {
    @Test("Parse TOML document into Table and serialize back")
    func parseAndSerializeTable() {
        let parsed = parseTable(s: "[package]\nname = \"toml\"\nversion = \"0.2.0\"\n")
        #expect(!parsed.isEmpty())
        let serialized = ser.toString(table: parsed)
        #expect(serialized.contains("[package]"))
        #expect(serialized.contains("name = \"toml\""))
    }
}
