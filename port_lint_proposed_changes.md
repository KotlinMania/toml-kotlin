# port-lint Proposed Changes

**Generated:** 2026-08-24
**Source:** tmp/toml
**Target:** src

These are review proposals only. They are emitted when a Rust -> Kotlin pair matches only after fallback normalization, so the existing `port-lint` header is not an exact provenance match.

| Target file | Current header | Proposed header | Source path | Reason |
|-------------|----------------|-----------------|-------------|--------|
| `commonMain/kotlin/io/github/kotlinmania/toml/de/Error.kt` | `// port-lint: source de/error.rs` | `// port-lint: source de/error.rs` | `de/error.rs` | `port-lint provenance header matched only after fallback normalization: 'de/error.rs' vs expected 'de/error.rs'` |
| `commonTest/kotlin/io/github/kotlinmania/toml/de/ErrorTest.kt` | `// port-lint: tests de/error.rs` | `// port-lint: tests de/error.rs` | `de/error.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:de/error.rs' vs expected 'de/error.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/toml/ser/Style.kt` | `// port-lint: source ser/style.rs` | `// port-lint: source ser/style.rs` | `ser/style.rs` | `port-lint provenance header matched only after fallback normalization: 'ser/style.rs' vs expected 'ser/style.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/toml/Table.kt` | `// port-lint: source table.rs` | `// port-lint: source table.rs` | `table.rs` | `port-lint provenance header matched only after fallback normalization: 'table.rs' vs expected 'table.rs'` |
| `commonTest/kotlin/io/github/kotlinmania/toml/TableTest.kt` | `// port-lint: tests table.rs` | `// port-lint: tests table.rs` | `table.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:table.rs' vs expected 'table.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/toml/Value.kt` | `// port-lint: source value.rs` | `// port-lint: source value.rs` | `value.rs` | `port-lint provenance header matched only after fallback normalization: 'value.rs' vs expected 'value.rs'` |
| `commonTest/kotlin/io/github/kotlinmania/toml/ValueTest.kt` | `// port-lint: tests value.rs` | `// port-lint: tests value.rs` | `value.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:value.rs' vs expected 'value.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/toml/ser/document/Buffer.kt` | `// port-lint: source ser/document/buffer.rs` | `// port-lint: source ser/document/buffer.rs` | `ser/document/buffer.rs` | `port-lint provenance header matched only after fallback normalization: 'ser/document/buffer.rs' vs expected 'ser/document/buffer.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/toml/map/Map.kt` | `// port-lint: source map.rs` | `// port-lint: source map.rs` | `map.rs` | `port-lint provenance header matched only after fallback normalization: 'map.rs' vs expected 'map.rs'` |
| `commonTest/kotlin/io/github/kotlinmania/toml/map/TomlMapTest.kt` | `// port-lint: tests map.rs` | `// port-lint: tests map.rs` | `map.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:map.rs' vs expected 'map.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/toml/ser/Error.kt` | `// port-lint: source ser/error.rs` | `// port-lint: source ser/error.rs` | `ser/error.rs` | `port-lint provenance header matched only after fallback normalization: 'ser/error.rs' vs expected 'ser/error.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/toml/Lib.kt` | `// port-lint: source lib.rs` | `// port-lint: source lib.rs` | `lib.rs` | `port-lint provenance header matched only after fallback normalization: 'lib.rs' vs expected 'lib.rs'` |
| `commonTest/kotlin/io/github/kotlinmania/toml/LibTest.kt` | `// port-lint: tests lib.rs` | `// port-lint: tests lib.rs` | `lib.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:lib.rs' vs expected 'lib.rs'` |
