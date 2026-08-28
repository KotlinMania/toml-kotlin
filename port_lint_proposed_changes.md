# port-lint Proposed Changes

**Generated:** 2026-08-27
**Source:** tmp/toml/src
**Target:** src/commonMain/kotlin

These are review proposals only. They are emitted when a Rust -> Kotlin pair matches only after fallback normalization, so the existing `port-lint` header is not an exact provenance match.

| Target file | Current header | Proposed header | Source path | Reason |
|-------------|----------------|-----------------|-------------|--------|
| `src/commonTest/kotlin/io/github/kotlinmania/toml/de/DeserializerTest.kt` | `// port-lint: tests de/mod.rs` | `// port-lint: tests de/deserializer/mod.rs` | `de/deserializer/mod.rs` | `port-lint provenance header matched only by basename: 'tests:de/mod.rs' vs expected 'de/deserializer/mod.rs'` |
