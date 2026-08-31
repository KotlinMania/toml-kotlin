# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 34/39 (87.2%)
- **Function parity:** 107/527 matched (target 241) — 20.3%
- **Class/type parity:** 30/172 matched (target 69) — 17.4%
- **Combined symbol parity:** 137/699 matched (target 310) — 19.6%
- **Average inline-code cosine:** 0.13 (function body across 29 matched files)
- **Average documentation cosine:** 0.30 (doc text across 29 matched files)
- **Cheat-zeroed Files:** 3
- **Critical Issues:** 32 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

### 1. de.error
- **Similarity:** 0.53 (needs 32% improvement)
- **Dependencies:** 17
- **Priority Score:** 17072204.0
- **Functions:** 14/20 matched (target 22)
- **Missing functions:** `new`, `message`, `render_literal`, `fmt`, `into_inner`, `report_error`
- **Types:** 1/2 matched (target 3)
- **Missing types:** `TomlSink`
- **Symbol Deficit:** 7 (functions: 6, types: 1)
- **Action:** Deep review - likely missing major functionality

### 2. parser.devalue
- **Similarity:** 0.30 (needs 55% improvement)
- **Dependencies:** 12
- **Priority Score:** 12073807.0
- **Functions:** 27/31 matched (target 39)
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
- **Similarity:** 0.53
- **Dependents:** 17
- **Priority Score:** 17072204.0
- **Functions:** 14/20 matched (target 22)
- **Missing functions:** `new`, `message`, `render_literal`, `fmt`, `into_inner`, `report_error`
- **Types:** 1/2 matched (target 3)
- **Missing types:** `TomlSink`
- **Tests:** 8/8 matched

### 2. parser.devalue

- **Target:** `parser.Devalue`
- **Similarity:** 0.30
- **Dependents:** 12
- **Priority Score:** 12073807.0
- **Functions:** 27/31 matched (target 39)
- **Missing functions:** `radix`, `fmt`, `parse`, `parse_recoverable`
- **Types:** 4/7 matched (target 15)
- **Missing types:** `DeString`, `Output`, `Sealed`

### 3. parser.detable

- **Target:** `parser.Detable`
- **Similarity:** 0.18
- **Dependents:** 7
- **Priority Score:** 7010408.0
- **Functions:** 2/3 matched (target 5)
- **Missing functions:** `parse_recoverable`
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

### 5. toml.table

- **Target:** `toml.Table`
- **Similarity:** 0.01
- **Dependents:** 6
- **Priority Score:** 6616310.0
- **Functions:** 1/48 matched (target 10)
- **Missing functions:** `try_from`, `try_into`, `fmt`, `from_str`, `serialize`, `deserialize`, `expecting`, `visit_unit`, `visit_map`, `deserialize_any`, `deserialize_enum`, `deserialize_option`, `deserialize_newtype_struct`, `into_deserializer`, `serialize_bool`, `serialize_i8`, `serialize_i16`, `serialize_i32`, `serialize_i64`, `serialize_u8`, `serialize_u16`, `serialize_u32`, `serialize_u64`, `serialize_f32`, `serialize_f64`, `serialize_char`, `serialize_str`, `serialize_bytes`, `serialize_unit`, `serialize_unit_struct`, `serialize_unit_variant`, `serialize_newtype_struct`, `serialize_newtype_variant`, `serialize_none`, `serialize_some`, `serialize_seq`, `serialize_tuple`, `serialize_tuple_struct`, `serialize_tuple_variant`, `serialize_map`, `serialize_struct`, `serialize_struct_variant`, `with_capacity`, `serialize_key`, `serialize_value`, `end`, `serialize_field`
- **Types:** 1/15 matched (target 2)
- **Missing types:** `Err`, `Visitor`, `Value`, `Error`, `Deserializer`, `TableSerializer`, `Ok`, `SerializeSeq`, `SerializeTuple`, `SerializeTupleStruct`, `SerializeTupleVariant`, `SerializeMap`, `SerializeStruct`, `SerializeStructVariant`

### 6. parser.dearray

- **Target:** `parser.Dearray`
- **Similarity:** 0.19
- **Dependents:** 5
- **Priority Score:** 5152008.0
- **Functions:** 4/15 matched (target 9)
- **Missing functions:** `deref`, `deref_mut`, `as_ref`, `as_mut`, `borrow`, `borrow_mut`, `index`, `into_iter`, `from_iter`, `default`, `fmt`
- **Types:** 1/5 matched (target 2)
- **Missing types:** `Target`, `Output`, `Item`, `IntoIter`

### 7. toml.value

- **Target:** `toml.Value`
- **Similarity:** 0.06
- **Dependents:** 4
- **Priority Score:** 5031809.5
- **Functions:** 14/91 matched (target 40)
- **Missing functions:** `try_from`, `try_into`, `is_integer`, `is_float`, `is_bool`, `is_str`, `is_datetime`, `as_array_mut`, `is_array`, `is_table`, `index`, `index_mut`, `fmt`, `serialize`, `deserialize`, `expecting`, `visit_bool`, `visit_i64`, `visit_u64`, `visit_u32`, `visit_i32`, `visit_f64`, `visit_str`, `visit_string`, `visit_some`, `visit_seq`, `visit_map`, `deserialize_any`, `deserialize_enum`, `deserialize_option`, `deserialize_newtype_struct`, `new`, `next_element_seed`, `size_hint`, `next_key_seed`, `next_value_seed`, `variant_seed`, `unit_variant`, `newtype_variant_seed`, `tuple_variant`, `struct_variant`, `into_deserializer`, `serialize_bool`, `serialize_i8`, `serialize_i16`, `serialize_i32`, `serialize_i64`, `serialize_u8`, `serialize_u16`, `serialize_u32`, `serialize_u64`, `serialize_f32`, `serialize_f64`, `serialize_char`, `serialize_str`, `serialize_bytes`, `serialize_unit`, `serialize_unit_struct`, `serialize_unit_variant`, `serialize_newtype_struct`, `serialize_newtype_variant`, `serialize_none`, `serialize_some`, `serialize_seq`, `serialize_tuple`, `serialize_tuple_struct`, `serialize_tuple_variant`, `serialize_map`, `serialize_struct`, `serialize_struct_variant`, `serialize_element`, `end`, `serialize_field`, `serialize_key`, `serialize_value`, `tuple`, `struct_`
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

### 9. toml.map

- **Target:** `map.Map [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 2
- **Priority Score:** 2345810.0
- **Functions:** 21/38 matched (target 45)
- **Missing functions:** `iter_mut`, `keys`, `values`, `is_dotted`, `is_implicit`, `is_inline`, `set_implicit`, `set_dotted`, `set_inline`, `default`, `clone`, `eq`, `index`, `index_mut`, `fmt`, `from_iter`, `into_iter`
- **Types:** 3/20 matched (target 7)
- **Missing types:** `Map`, `MapImpl`, `RandomState`, `Output`, `VacantEntryImpl`, `OccupiedEntryImpl`, `Item`, `IntoIter`, `Iter`, `IterImpl`, `IterMut`, `IterMutImpl`, `IntoIterImpl`, `Keys`, `KeysImpl`, `Values`, `ValuesImpl`

### 10. document.strategy

- **Target:** `document.Strategy`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 515210.0
- **Functions:** 0/39 matched (target 1)
- **Missing functions:** `from`, `custom`, `fmt`, `serialize_bool`, `serialize_i8`, `serialize_i16`, `serialize_i32`, `serialize_i64`, `serialize_i128`, `serialize_u8`, `serialize_u16`, `serialize_u32`, `serialize_u64`, `serialize_u128`, `serialize_f32`, `serialize_f64`, `serialize_char`, `serialize_str`, `serialize_bytes`, `serialize_none`, `serialize_some`, `serialize_unit`, `serialize_unit_struct`, `serialize_unit_variant`, `serialize_newtype_struct`, `serialize_newtype_variant`, `serialize_seq`, `serialize_tuple`, `serialize_tuple_struct`, `serialize_tuple_variant`, `serialize_map`, `serialize_struct`, `serialize_struct_variant`, `new`, `serialize_element`, `end`, `serialize_field`, `serialize_key`, `serialize_value`
- **Types:** 1/13 matched (target 1)
- **Missing types:** `WalkValue`, `Ok`, `Error`, `SerializeSeq`, `SerializeTuple`, `SerializeTupleStruct`, `SerializeTupleVariant`, `SerializeMap`, `SerializeStruct`, `SerializeStructVariant`, `ArrayWalkValue`, `StructWalkValue`

### 11. value.map

- **Target:** `value.Map`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 474810.0
- **Functions:** 0/36 matched (target 1)
- **Missing functions:** `map`, `struct_`, `serialize_key`, `serialize_value`, `end`, `serialize_field`, `new`, `dt_err`, `serialize_bool`, `serialize_i8`, `serialize_i16`, `serialize_i32`, `serialize_i64`, `serialize_u8`, `serialize_u16`, `serialize_u32`, `serialize_u64`, `serialize_f32`, `serialize_f64`, `serialize_char`, `serialize_str`, `serialize_bytes`, `serialize_none`, `serialize_some`, `serialize_unit`, `serialize_unit_struct`, `serialize_unit_variant`, `serialize_newtype_struct`, `serialize_newtype_variant`, `serialize_seq`, `serialize_tuple`, `serialize_tuple_struct`, `serialize_tuple_variant`, `serialize_map`, `serialize_struct`, `serialize_struct_variant`
- **Types:** 1/12 matched (target 1)
- **Missing types:** `SerializeMap`, `Ok`, `Error`, `SerializeDatetime`, `SerializeTable`, `SerializeSeq`, `SerializeTuple`, `SerializeTupleStruct`, `SerializeTupleVariant`, `SerializeStruct`, `SerializeStructVariant`

### 12. document.array_of_tables

- **Target:** `document.ArrayOfTables`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 434410.0
- **Functions:** 0/33 matched (target 1)
- **Missing functions:** `new`, `serialize_bool`, `serialize_i8`, `serialize_i16`, `serialize_i32`, `serialize_i64`, `serialize_u8`, `serialize_u16`, `serialize_u32`, `serialize_u64`, `serialize_f32`, `serialize_f64`, `serialize_char`, `serialize_str`, `serialize_bytes`, `serialize_none`, `serialize_some`, `serialize_unit`, `serialize_unit_struct`, `serialize_unit_variant`, `serialize_newtype_struct`, `serialize_newtype_variant`, `serialize_seq`, `serialize_tuple`, `serialize_tuple_struct`, `serialize_tuple_variant`, `serialize_map`, `serialize_struct`, `serialize_struct_variant`, `seq`, `end`, `serialize_element`, `serialize_field`
- **Types:** 1/11 matched (target 1)
- **Missing types:** `Ok`, `Error`, `SerializeSeq`, `SerializeTuple`, `SerializeTupleStruct`, `SerializeTupleVariant`, `SerializeMap`, `SerializeStruct`, `SerializeStructVariant`, `SerializeArrayOfTablesSerializer`

### 13. value.key

- **Target:** `value.Key`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 394010.0
- **Functions:** 0/30 matched (target 3)
- **Missing functions:** `serialize_bool`, `serialize_i8`, `serialize_i16`, `serialize_i32`, `serialize_i64`, `serialize_i128`, `serialize_u8`, `serialize_u16`, `serialize_u32`, `serialize_u64`, `serialize_u128`, `serialize_f32`, `serialize_f64`, `serialize_char`, `serialize_str`, `serialize_bytes`, `serialize_none`, `serialize_some`, `serialize_unit`, `serialize_unit_struct`, `serialize_unit_variant`, `serialize_newtype_struct`, `serialize_newtype_variant`, `serialize_seq`, `serialize_tuple`, `serialize_tuple_struct`, `serialize_tuple_variant`, `serialize_map`, `serialize_struct`, `serialize_struct_variant`
- **Types:** 1/10 matched (target 1)
- **Missing types:** `Ok`, `Error`, `SerializeSeq`, `SerializeTuple`, `SerializeTupleStruct`, `SerializeTupleVariant`, `SerializeMap`, `SerializeStruct`, `SerializeStructVariant`

### 14. deserializer.key

- **Target:** `deserializer.Key`
- **Similarity:** 0.03
- **Dependents:** 0
- **Priority Score:** 272909.7
- **Functions:** 1/24 matched (target 2)
- **Missing functions:** `into_deserializer`, `deserialize_any`, `deserialize_bool`, `deserialize_i8`, `deserialize_i16`, `deserialize_i32`, `deserialize_i64`, `deserialize_i128`, `deserialize_u8`, `deserialize_u16`, `deserialize_u32`, `deserialize_u64`, `deserialize_u128`, `deserialize_char`, `deserialize_enum`, `deserialize_struct`, `deserialize_newtype_struct`, `variant_seed`, `unit_only`, `unit_variant`, `newtype_variant_seed`, `tuple_variant`, `struct_variant`
- **Types:** 1/5 matched (target 1)
- **Missing types:** `Deserializer`, `Error`, `Variant`, `UnitOnly`

### 15. deserializer.value

- **Target:** `deserializer.Value`
- **Similarity:** 0.15
- **Dependents:** 0
- **Priority Score:** 131708.5
- **Functions:** 3/14 matched (target 4)
- **Missing functions:** `with_struct_key_validation`, `from`, `deserialize_any`, `deserialize_u128`, `deserialize_i128`, `deserialize_option`, `deserialize_newtype_struct`, `deserialize_struct`, `deserialize_enum`, `into_deserializer`, `validate_struct_keys`
- **Types:** 1/3 matched (target 1)
- **Missing types:** `Error`, `Deserializer`

### 16. deserializer.table

- **Target:** `deserializer.Table`
- **Similarity:** 0.07
- **Dependents:** 0
- **Priority Score:** 131509.3
- **Functions:** 1/10 matched (target 2)
- **Missing functions:** `deserialize_any`, `deserialize_option`, `deserialize_newtype_struct`, `deserialize_struct`, `deserialize_enum`, `into_deserializer`, `next_key_seed`, `next_value_seed`, `variant_seed`
- **Types:** 1/5 matched (target 1)
- **Missing types:** `Error`, `Deserializer`, `TableMapAccess`, `Variant`

### 17. parser.debug

- **Target:** `parser.Debug [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 111410.0
- **Functions:** 2/10 matched (target 3)
- **Missing functions:** `drop`, `scoped`, `enter_unchecked`, `exit_unchecked`, `depth`, `take`, `as_ref`, `deref`
- **Types:** 1/4 matched (target 1)
- **Missing types:** `DebugDepth`, `DebugDepthGuard`, `Target`

### 18. parser.document

- **Target:** `parser.Document`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 101010.0
- **Functions:** 0/8 matched (target 7)
- **Missing functions:** `document`, `on_table`, `capture_trailing`, `capture_key_value`, `finish_table`, `start_table`, `descend_path`, `get_key_span`
- **Types:** 0/2 matched (target 1)
- **Missing types:** `TableHeader`, `State`

### 19. parser.inline_table

- **Target:** `parser.InlineTable`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 101010.0
- **Functions:** 0/9 matched (target 4)
- **Missing functions:** `on_inline_table`, `whitespace`, `capture_key`, `finish_key`, `capture_value`, `finish_value`, `close`, `descend_path`, `get_key_span`
- **Types:** 0/1 matched
- **Missing types:** `State`

### 20. value.array

- **Target:** `value.Array`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 101010.0
- **Functions:** 0/6 matched (target 1)
- **Missing functions:** `seq`, `end`, `multiline_array`, `serialize_element`, `serialize_field`, `tuple`
- **Types:** 0/4 matched (target 1)
- **Missing types:** `SerializeValueArray`, `Ok`, `Error`, `SerializeTupleVariant`

### 21. parser.array

- **Target:** `parser.Array`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 80810.0
- **Functions:** 0/7 matched (target 4)
- **Missing functions:** `on_array`, `open`, `whitespace`, `capture_value`, `finish_value`, `sep_value`, `close`
- **Types:** 0/1 matched
- **Missing types:** `State`

### 22. deserializer.array

- **Target:** `deserializer.Array`
- **Similarity:** 0.12
- **Dependents:** 0
- **Priority Score:** 70908.8
- **Functions:** 1/5 matched (target 2)
- **Missing functions:** `deserialize_any`, `deserialize_struct`, `into_deserializer`, `next_element_seed`
- **Types:** 1/4 matched (target 1)
- **Missing types:** `Error`, `Deserializer`, `ArraySeqAccess`

### 23. document.map

- **Target:** `document.Map`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 70810.0
- **Functions:** 0/5 matched (target 1)
- **Missing functions:** `map`, `end`, `serialize_key`, `serialize_value`, `serialize_field`
- **Types:** 1/3 matched (target 1)
- **Missing types:** `Ok`, `Error`

### 24. parser.key

- **Target:** `parser.Key`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 60610.0
- **Functions:** 0/5 matched (target 2)
- **Missing functions:** `on_key`, `more_key`, `new`, `whitespace`, `close_key`
- **Types:** 0/1 matched
- **Missing types:** `State`

### 25. deserializer.table_enum

- **Target:** `deserializer.TableEnum`
- **Similarity:** 0.16
- **Dependents:** 0
- **Priority Score:** 50708.4
- **Functions:** 1/5 matched (target 2)
- **Missing functions:** `unit_variant`, `newtype_variant_seed`, `tuple_variant`, `struct_variant`
- **Types:** 1/2 matched (target 1)
- **Missing types:** `Error`

### 26. document.array

- **Target:** `document.Array`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 50610.0
- **Functions:** 0/3 matched (target 1)
- **Missing functions:** `tuple`, `serialize_field`, `end`
- **Types:** 1/3 matched (target 1)
- **Missing types:** `Ok`, `Error`

### 27. ser.error

- **Target:** `ser.Error`
- **Similarity:** 0.27
- **Dependents:** 0
- **Priority Score:** 31007.3
- **Functions:** 5/8 matched (target 15)
- **Missing functions:** `new`, `from`, `fmt`
- **Types:** 2/2 matched (target 8)
- **Missing types:** _none_

### 28. toml.macros

- **Target:** `toml.Macros`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 30310.0
- **Functions:** 0/3 matched (target 1)
- **Missing functions:** `insert_toml`, `push_toml`, `traverse`
- **Types:** 0/0 matched
- **Missing types:** _none_

### 29. parser.value

- **Target:** `parser.Value [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 20210.0
- **Functions:** 0/2 matched
- **Missing functions:** `value`, `on_scalar`
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

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

### Matched

| Source | Target | Path |
|--------|--------|------|
| `value.mod` | `value.ValueSerializer` | `toml/src/ser/value/mod` |
| `document.mod` | `document.Serializer` | `toml/src/ser/document/mod` |
| `deserializer.mod` | `de.Deserializer` | `toml/src/de/deserializer/mod` |
| `toml.lib` | `toml.Lib` | `toml/src/lib` |
| `ser.mod` | `ser.Serializer` | `toml/src/ser/mod` |

