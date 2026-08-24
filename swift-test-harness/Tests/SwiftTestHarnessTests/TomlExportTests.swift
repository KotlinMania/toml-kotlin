import Testing
import Toml

// Smoke test for the Kotlin → Swift Export → SPM → swift test pipeline.
@Suite("Toml Swift Export Smoke Tests")
struct TomlExportTests {
    @Test("Toml swift module imported cleanly")
    func testSwiftModuleLoads() {
        #expect(Bool(true))
    }
}
