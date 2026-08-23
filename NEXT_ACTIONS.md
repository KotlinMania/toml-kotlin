# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 8/36 (22.2%)
- **Function parity:** 38/572 matched (target 86) — 6.6%
- **Class/type parity:** 9/167 matched (target 27) — 5.4%
- **Combined symbol parity:** 47/739 matched (target 113) — 6.4%
- **Average inline-code cosine:** 0.36 (function body across 7 matched files)
- **Average documentation cosine:** 0.57 (doc text across 7 matched files)
- **Cheat-zeroed Files:** 1
- **Critical Issues:** 6 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

### 1. de.error
- **Similarity:** 0.55 (needs 30% improvement)
- **Dependencies:** 17
- **Priority Score:** 17062204.0
- **Functions:** 15/20 matched (target 18)
- **Missing functions:** `new`, `render_literal`, `fmt`, `into_inner`, `report_error`
- **Types:** 1/2 matched
- **Missing types:** `TomlSink`
- **Symbol Deficit:** 6 (functions: 5, types: 1)
- **Action:** Deep review - likely missing major functionality

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

1. **parser.devalue** (12 deps)
   - Path: `de/parser/devalue.rs`
   - Essential for 12 other files

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. de.error

- **Target:** `de.Error`
- **Similarity:** 0.55
- **Dependents:** 17
- **Priority Score:** 17062204.0
- **Functions:** 15/20 matched (target 18)
- **Missing functions:** `new`, `render_literal`, `fmt`, `into_inner`, `report_error`
- **Types:** 1/2 matched
- **Missing types:** `TomlSink`
- **Tests:** 8/8 matched

### 2. ser.style

- **Target:** `ser.Style`
- **Similarity:** 1.00
- **Dependents:** 7
- **Priority Score:** 7000100.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_

### 3. table

- **Target:** `toml.Table [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 6
- **Priority Score:** 6626310.0
- **Functions:** 0/48 matched (target 2)
- **Missing functions:** `try_from`, `try_into`, `fmt`, `from_str`, `serialize`, `deserialize`, `expecting`, `visit_unit`, `visit_map`, `deserialize_any`, `deserialize_enum`, `deserialize_option`, `deserialize_newtype_struct`, `into_deserializer`, `serialize_bool`, `serialize_i8`, `serialize_i16`, `serialize_i32`, `serialize_i64`, `serialize_u8`, `serialize_u16`, `serialize_u32`, `serialize_u64`, `serialize_f32`, `serialize_f64`, `serialize_char`, `serialize_str`, `serialize_bytes`, `serialize_unit`, `serialize_unit_struct`, `serialize_unit_variant`, `serialize_newtype_struct`, `serialize_newtype_variant`, `serialize_none`, `serialize_some`, `serialize_seq`, `serialize_tuple`, `serialize_tuple_struct`, `serialize_tuple_variant`, `serialize_map`, `serialize_struct`, `serialize_struct_variant`, `new`, `with_capacity`, `serialize_key`, `serialize_value`, `end`, `serialize_field`
- **Types:** 1/15 matched (target 2)
- **Missing types:** `Err`, `Visitor`, `Value`, `Error`, `Deserializer`, `TableSerializer`, `Ok`, `SerializeSeq`, `SerializeTuple`, `SerializeTupleStruct`, `SerializeTupleVariant`, `SerializeMap`, `SerializeStruct`, `SerializeStructVariant`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/table.rs` vs expected `table.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:src/table.rs` vs expected `table.rs`
- **Proposed provenance header:** `// port-lint: source table.rs` (current: `// port-lint: source src/table.rs`)
- **Proposed provenance header:** `// port-lint: tests table.rs` (current: `// port-lint: tests src/table.rs`)
- **Lint issues:** 2

### 4. value

- **Target:** `toml.Value [PROVENANCE-FALLBACK]`
- **Similarity:** 0.04
- **Dependents:** 4
- **Priority Score:** 5081809.5
- **Functions:** 9/91 matched (target 34)
- **Missing functions:** `try_from`, `try_into`, `get_mut`, `is_integer`, `is_float`, `as_bool`, `is_bool`, `as_str`, `is_str`, `is_datetime`, `as_array_mut`, `is_array`, `as_table_mut`, `is_table`, `index`, `index_mut`, `fmt`, `from_str`, `serialize`, `deserialize`, `expecting`, `visit_bool`, `visit_i64`, `visit_u64`, `visit_u32`, `visit_i32`, `visit_f64`, `visit_str`, `visit_string`, `visit_some`, `visit_seq`, `visit_map`, `deserialize_any`, `deserialize_enum`, `deserialize_option`, `deserialize_newtype_struct`, `new`, `next_element_seed`, `size_hint`, `next_key_seed`, `next_value_seed`, `variant_seed`, `unit_variant`, `newtype_variant_seed`, `tuple_variant`, `struct_variant`, `into_deserializer`, `serialize_bool`, `serialize_i8`, `serialize_i16`, `serialize_i32`, `serialize_i64`, `serialize_u8`, `serialize_u16`, `serialize_u32`, `serialize_u64`, `serialize_f32`, `serialize_f64`, `serialize_char`, `serialize_str`, `serialize_bytes`, `serialize_unit`, `serialize_unit_struct`, `serialize_unit_variant`, `serialize_newtype_struct`, `serialize_newtype_variant`, `serialize_none`, `serialize_some`, `serialize_seq`, `serialize_tuple`, `serialize_tuple_struct`, `serialize_tuple_variant`, `serialize_map`, `serialize_struct`, `serialize_struct_variant`, `serialize_element`, `end`, `serialize_field`, `serialize_key`, `serialize_value`, `tuple`, `struct_`
- **Types:** 2/27 matched (target 9)
- **Missing types:** `Output`, `Index`, `Sealed`, `Err`, `ValueVisitor`, `Error`, `SeqDeserializer`, `MapDeserializer`, `Variant`, `MapEnumDeserializer`, `Deserializer`, `ValueSerializer`, `Ok`, `SerializeSeq`, `SerializeTuple`, `SerializeTupleStruct`, `SerializeTupleVariant`, `SerializeMap`, `SerializeStruct`, `SerializeStructVariant`, `ValueSerializeVec`, `ValueSerializeMap`, `ValueSerializeTupleVariant`, `ValueSerializeStructVariant`, `ValueSerializeVariant`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/value.rs` vs expected `value.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:src/value.rs` vs expected `value.rs`
- **Proposed provenance header:** `// port-lint: source value.rs` (current: `// port-lint: source src/value.rs`)
- **Proposed provenance header:** `// port-lint: tests value.rs` (current: `// port-lint: tests src/value.rs`)
- **Lint issues:** 2

### 5. document.buffer

- **Target:** `document.Buffer`
- **Similarity:** 0.63
- **Dependents:** 5
- **Priority Score:** 5021303.5
- **Functions:** 9/11 matched (target 12)
- **Missing functions:** `fmt`, `has_children`
- **Types:** 2/2 matched
- **Missing types:** _none_

### 6. map

- **Target:** `map.Map [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 2
- **Priority Score:** 2585810.0
- **Functions:** 0/38 matched (target 6)
- **Missing functions:** `new`, `with_capacity`, `clear`, `get`, `contains_key`, `get_mut`, `get_key_value`, `insert`, `remove`, `remove_entry`, `retain`, `entry`, `len`, `is_empty`, `iter`, `iter_mut`, `keys`, `values`, `mut_entries`, `is_dotted`, `is_implicit`, `is_inline`, `set_implicit`, `set_dotted`, `set_inline`, `default`, `clone`, `eq`, `index`, `index_mut`, `fmt`, `from_iter`, `extend`, `key`, `or_insert`, `or_insert_with`, `into_mut`, `into_iter`
- **Types:** 0/20 matched (target 2)
- **Missing types:** `Map`, `MapImpl`, `RandomState`, `Output`, `Entry`, `VacantEntry`, `OccupiedEntry`, `VacantEntryImpl`, `OccupiedEntryImpl`, `Item`, `IntoIter`, `Iter`, `IterImpl`, `IterMut`, `IterMutImpl`, `IntoIterImpl`, `Keys`, `KeysImpl`, `Values`, `ValuesImpl`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/map.rs` vs expected `map.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:src/map.rs` vs expected `map.rs`
- **Proposed provenance header:** `// port-lint: source map.rs` (current: `// port-lint: source src/map.rs`)
- **Proposed provenance header:** `// port-lint: tests map.rs` (current: `// port-lint: tests src/map.rs`)
- **Lint issues:** 2

### 7. ser.error

- **Target:** `ser.Error`
- **Similarity:** 0.27
- **Dependents:** 0
- **Priority Score:** 31007.3
- **Functions:** 5/8 matched (target 13)
- **Missing functions:** `new`, `from`, `fmt`
- **Types:** 2/2 matched (target 7)
- **Missing types:** _none_

### 8. lib

- **Target:** `toml.Lib [STUB] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10110.0
- **Functions:** 0/0 matched (target 1)
- **Missing functions:** _none_
- **Types:** 0/1 matched (target 2)
- **Missing types:** `ReadmeDoctests`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/lib.rs` vs expected `lib.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:src/lib.rs` vs expected `lib.rs`
- **Proposed provenance header:** `// port-lint: source lib.rs` (current: `// port-lint: source src/lib.rs`)
- **Proposed provenance header:** `// port-lint: tests lib.rs` (current: `// port-lint: tests src/lib.rs`)
- **Lint issues:** 2

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

## Reexport / Wiring Modules

These files match `reexport_modules` patterns in `.ast_distance_config.json`. They are filtered out of
normal priority and missing-file ladders because they are wiring
modules, not direct logic ports. Consult them for call-site routing;
do not treat them as the next implementation target by default.

### Missing

| Source | Expected target | Deps | Source path | Expected path |
|--------|-----------------|------|-------------|---------------|
| `deserializer.mod` | `de.deserializer.Mod` | 0 | `de/deserializer/mod.rs` | `de/deserializer/Mod.kt` |
| `de.mod` | `de.Mod` | 0 | `de/mod.rs` | `de/Mod.kt` |
| `parser.mod` | `de.parser.Mod` | 0 | `de/parser/mod.rs` | `de/parser/Mod.kt` |
| `document.mod` | `ser.document.Mod` | 0 | `ser/document/mod.rs` | `ser/document/Mod.kt` |
| `ser.mod` | `ser.Mod` | 0 | `ser/mod.rs` | `ser/Mod.kt` |
| `value.mod` | `ser.value.Mod` | 0 | `ser/value/mod.rs` | `ser/value/Mod.kt` |

