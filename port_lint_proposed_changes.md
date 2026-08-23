# port-lint Proposed Changes

**Generated:** 2026-08-23
**Source:** tmp/toml/src
**Target:** src/commonMain/kotlin

These are review proposals only. They are emitted when a Rust -> Kotlin pair matches only after fallback normalization, so the existing `port-lint` header is not an exact provenance match.

| Target file | Current header | Proposed header | Source path | Reason |
|-------------|----------------|-----------------|-------------|--------|
| `src/commonMain/kotlin/io/github/kotlinmania/toml/Table.kt` | `// port-lint: source src/table.rs` | `// port-lint: source table.rs` | `table.rs` | `port-lint provenance header matched only after fallback normalization: 'src/table.rs' vs expected 'table.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/toml/TableTest.kt` | `// port-lint: tests src/table.rs` | `// port-lint: tests table.rs` | `table.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:src/table.rs' vs expected 'table.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/toml/Value.kt` | `// port-lint: source src/value.rs` | `// port-lint: source value.rs` | `value.rs` | `port-lint provenance header matched only after fallback normalization: 'src/value.rs' vs expected 'value.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/toml/ValueTest.kt` | `// port-lint: tests src/value.rs` | `// port-lint: tests value.rs` | `value.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:src/value.rs' vs expected 'value.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/toml/map/Map.kt` | `// port-lint: source src/map.rs` | `// port-lint: source map.rs` | `map.rs` | `port-lint provenance header matched only after fallback normalization: 'src/map.rs' vs expected 'map.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/toml/map/TomlMapTest.kt` | `// port-lint: tests src/map.rs` | `// port-lint: tests map.rs` | `map.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:src/map.rs' vs expected 'map.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/toml/Lib.kt` | `// port-lint: source src/lib.rs` | `// port-lint: source lib.rs` | `lib.rs` | `port-lint provenance header matched only after fallback normalization: 'src/lib.rs' vs expected 'lib.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/toml/LibTest.kt` | `// port-lint: tests src/lib.rs` | `// port-lint: tests lib.rs` | `lib.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:src/lib.rs' vs expected 'lib.rs'` |
