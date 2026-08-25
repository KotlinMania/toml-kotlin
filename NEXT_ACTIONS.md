# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 11/36 (30.6%)
- **Function parity:** 75/563 matched (target 144) — 13.3%
- **Class/type parity:** 15/167 matched (target 47) — 9.0%
- **Combined symbol parity:** 90/730 matched (target 191) — 12.3%
- **Average inline-code cosine:** 0.29 (function body across 11 matched files)
- **Average documentation cosine:** 0.52 (doc text across 11 matched files)
- **Cheat-zeroed Files:** 1
- **Critical Issues:** 9 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

### 1. de.error
- **Similarity:** 0.50 (needs 35% improvement)
- **Dependencies:** 17
- **Priority Score:** 17072204.0
- **Functions:** 14/20 matched (target 17)
- **Missing functions:** `new`, `message`, `render_literal`, `fmt`, `into_inner`, `report_error`
- **Types:** 1/2 matched
- **Missing types:** `TomlSink`
- **Symbol Deficit:** 7 (functions: 6, types: 1)
- **Action:** Deep review - likely missing major functionality

### 2. parser.devalue
- **Similarity:** 0.30 (needs 55% improvement)
- **Dependencies:** 12
- **Priority Score:** 12073807.0
- **Functions:** 27/31 matched (target 38)
- **Missing functions:** `radix`, `fmt`, `parse`, `parse_recoverable`
- **Types:** 4/7 matched (target 15)
- **Missing types:** `DeString`, `Output`, `Sealed`
- **Symbol Deficit:** 7 (functions: 4, types: 3)
- **Action:** Deep review - likely missing major functionality

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. de.error

- **Target:** `de.Error`
- **Similarity:** 0.50
- **Dependents:** 17
- **Priority Score:** 17072204.0
- **Functions:** 14/20 matched (target 17)
- **Missing functions:** `new`, `message`, `render_literal`, `fmt`, `into_inner`, `report_error`
- **Types:** 1/2 matched
- **Missing types:** `TomlSink`
- **Tests:** 8/8 matched

### 2. parser.devalue

- **Target:** `parser.Devalue`
- **Similarity:** 0.30
- **Dependents:** 12
- **Priority Score:** 12073807.0
- **Functions:** 27/31 matched (target 38)
- **Missing functions:** `radix`, `fmt`, `parse`, `parse_recoverable`
- **Types:** 4/7 matched (target 15)
- **Missing types:** `DeString`, `Output`, `Sealed`

### 3. parser.detable

- **Target:** `parser.Detable`
- **Similarity:** 0.09
- **Dependents:** 7
- **Priority Score:** 7020409.0
- **Functions:** 1/3 matched (target 2)
- **Missing functions:** `parse`, `parse_recoverable`
- **Types:** 1/1 matched (target 2)
- **Missing types:** _none_

### 4. ser.style

- **Target:** `ser.Style`
- **Similarity:** 1.00
- **Dependents:** 7
- **Priority Score:** 7000100.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_

### 5. table

- **Target:** `toml.Table`
- **Similarity:** 0.00
- **Dependents:** 6
- **Priority Score:** 6626310.0
- **Functions:** 0/48 matched (target 2)
- **Missing functions:** `try_from`, `try_into`, `fmt`, `from_str`, `serialize`, `deserialize`, `expecting`, `visit_unit`, `visit_map`, `deserialize_any`, `deserialize_enum`, `deserialize_option`, `deserialize_newtype_struct`, `into_deserializer`, `serialize_bool`, `serialize_i8`, `serialize_i16`, `serialize_i32`, `serialize_i64`, `serialize_u8`, `serialize_u16`, `serialize_u32`, `serialize_u64`, `serialize_f32`, `serialize_f64`, `serialize_char`, `serialize_str`, `serialize_bytes`, `serialize_unit`, `serialize_unit_struct`, `serialize_unit_variant`, `serialize_newtype_struct`, `serialize_newtype_variant`, `serialize_none`, `serialize_some`, `serialize_seq`, `serialize_tuple`, `serialize_tuple_struct`, `serialize_tuple_variant`, `serialize_map`, `serialize_struct`, `serialize_struct_variant`, `new`, `with_capacity`, `serialize_key`, `serialize_value`, `end`, `serialize_field`
- **Types:** 1/15 matched (target 2)
- **Missing types:** `Err`, `Visitor`, `Value`, `Error`, `Deserializer`, `TableSerializer`, `Ok`, `SerializeSeq`, `SerializeTuple`, `SerializeTupleStruct`, `SerializeTupleVariant`, `SerializeMap`, `SerializeStruct`, `SerializeStructVariant`

### 6. parser.dearray

- **Target:** `parser.Dearray`
- **Similarity:** 0.19
- **Dependents:** 5
- **Priority Score:** 5152008.0
- **Functions:** 4/15 matched (target 8)
- **Missing functions:** `deref`, `deref_mut`, `as_ref`, `as_mut`, `borrow`, `borrow_mut`, `index`, `into_iter`, `from_iter`, `default`, `fmt`
- **Types:** 1/5 matched (target 2)
- **Missing types:** `Target`, `Output`, `Item`, `IntoIter`

### 7. value

- **Target:** `toml.Value`
- **Similarity:** 0.04
- **Dependents:** 4
- **Priority Score:** 5081809.5
- **Functions:** 9/91 matched (target 34)
- **Missing functions:** `try_from`, `try_into`, `get_mut`, `is_integer`, `is_float`, `as_bool`, `is_bool`, `as_str`, `is_str`, `is_datetime`, `as_array_mut`, `is_array`, `as_table_mut`, `is_table`, `index`, `index_mut`, `fmt`, `from_str`, `serialize`, `deserialize`, `expecting`, `visit_bool`, `visit_i64`, `visit_u64`, `visit_u32`, `visit_i32`, `visit_f64`, `visit_str`, `visit_string`, `visit_some`, `visit_seq`, `visit_map`, `deserialize_any`, `deserialize_enum`, `deserialize_option`, `deserialize_newtype_struct`, `new`, `next_element_seed`, `size_hint`, `next_key_seed`, `next_value_seed`, `variant_seed`, `unit_variant`, `newtype_variant_seed`, `tuple_variant`, `struct_variant`, `into_deserializer`, `serialize_bool`, `serialize_i8`, `serialize_i16`, `serialize_i32`, `serialize_i64`, `serialize_u8`, `serialize_u16`, `serialize_u32`, `serialize_u64`, `serialize_f32`, `serialize_f64`, `serialize_char`, `serialize_str`, `serialize_bytes`, `serialize_unit`, `serialize_unit_struct`, `serialize_unit_variant`, `serialize_newtype_struct`, `serialize_newtype_variant`, `serialize_none`, `serialize_some`, `serialize_seq`, `serialize_tuple`, `serialize_tuple_struct`, `serialize_tuple_variant`, `serialize_map`, `serialize_struct`, `serialize_struct_variant`, `serialize_element`, `end`, `serialize_field`, `serialize_key`, `serialize_value`, `tuple`, `struct_`
- **Types:** 2/27 matched (target 9)
- **Missing types:** `Output`, `Index`, `Sealed`, `Err`, `ValueVisitor`, `Error`, `SeqDeserializer`, `MapDeserializer`, `Variant`, `MapEnumDeserializer`, `Deserializer`, `ValueSerializer`, `Ok`, `SerializeSeq`, `SerializeTuple`, `SerializeTupleStruct`, `SerializeTupleVariant`, `SerializeMap`, `SerializeStruct`, `SerializeStructVariant`, `ValueSerializeVec`, `ValueSerializeMap`, `ValueSerializeTupleVariant`, `ValueSerializeStructVariant`, `ValueSerializeVariant`

### 8. document.buffer

- **Target:** `document.Buffer`
- **Similarity:** 0.70
- **Dependents:** 5
- **Priority Score:** 5011303.0
- **Functions:** 10/11 matched (target 12)
- **Missing functions:** `fmt`
- **Types:** 2/2 matched
- **Missing types:** _none_

### 9. map

- **Target:** `map.Map`
- **Similarity:** 0.06
- **Dependents:** 2
- **Priority Score:** 2535809.2
- **Functions:** 5/38 matched (target 15)
- **Missing functions:** `new`, `with_capacity`, `get_mut`, `get_key_value`, `insert`, `remove_entry`, `retain`, `entry`, `len`, `iter`, `iter_mut`, `keys`, `values`, `mut_entries`, `is_dotted`, `is_implicit`, `is_inline`, `set_implicit`, `set_dotted`, `set_inline`, `default`, `clone`, `eq`, `index`, `index_mut`, `fmt`, `from_iter`, `extend`, `key`, `or_insert`, `or_insert_with`, `into_mut`, `into_iter`
- **Types:** 0/20 matched (target 2)
- **Missing types:** `Map`, `MapImpl`, `RandomState`, `Output`, `Entry`, `VacantEntry`, `OccupiedEntry`, `VacantEntryImpl`, `OccupiedEntryImpl`, `Item`, `IntoIter`, `Iter`, `IterImpl`, `IterMut`, `IterMutImpl`, `IntoIterImpl`, `Keys`, `KeysImpl`, `Values`, `ValuesImpl`

### 10. ser.error

- **Target:** `ser.Error`
- **Similarity:** 0.27
- **Dependents:** 0
- **Priority Score:** 31007.3
- **Functions:** 5/8 matched (target 15)
- **Missing functions:** `new`, `from`, `fmt`
- **Types:** 2/2 matched (target 8)
- **Missing types:** _none_

### 11. lib

- **Target:** `toml.Lib [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10110.0
- **Functions:** 0/0 matched (target 1)
- **Missing functions:** _none_
- **Types:** 0/1 matched (target 2)
- **Missing types:** `ReadmeDoctests`

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

