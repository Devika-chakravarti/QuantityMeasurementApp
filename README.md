# Quantity Measurement App
**Use Case 9: Weight Measurement Equality, Conversion, and Addition (Kilogram, Gram, Pound)**

## UC9 Overview
This use case extends the application to support a **new measurement category: weight**, operating independently from length.

It introduces three weight units (KILOGRAM as base, GRAM, POUND) and mirrors the full feature set of length:
- Equality comparison (cross-unit)
- Unit-to-unit conversion
- Addition (implicit & explicit target unit)

The design proves that the architectural patterns from UC1–UC8 (standalone unit enum, delegated conversion, immutable quantity class) scale seamlessly to multiple categories without coupling or duplication.

## Project Structure:
```
src  
 ├── main  
 │    └── java/
 │         └── com/
 │              └── quantitymeasurement
 │                      └── Feet.java
 │                      └── Inches.java
 │                      └── LengthUnit.java
 │                      └── QuantityLength.java
 │                      └── WeightUnit.java
 │                      └── QuantityWeight.java
 │                      └── QuantityMeasurementApp.java
 │  
 └── test  
 │    └── java/
 │         └── com/
 │              └── quantitymeasurement
 │                        └── FeetEqualityTest.java
 │                        └── InchesEqualityTest.java
 │                        └── QuantityLengthTest.java
 │                        └── YardEqualityTest.java
 │                        └── ConversionTest.java
 │                        └── AdditionTest.java
 │                        └── TargetUnitSpecificationTest.java
 │                        └── LengthUnitRefactoredTest.java
 │                        └── WeightEqualityTest.java
 ├── .gitignore
 |── pom.xml
 └── README.md

```

## Features Implemented in UC9
- Created standalone `WeightUnit` enum (KILOGRAM, GRAM, POUND) with conversion responsibility  
  - Base unit: KILOGRAM (1.0)  
  - GRAM: 0.001 (1 g = 0.001 kg)  
  - POUND: 0.453592 (1 lb ≈ 0.453592 kg)  
- Created `QuantityWeight` class mirroring `QuantityLength`  
  - Immutable (private final fields)  
  - Constructor validation (finite value, non-null unit)  
  - `equals()`: compares via base unit (kg) + EPSILON tolerance  
  - `convertTo(WeightUnit target)`: delegates to unit methods  
  - `add(QuantityWeight other)`: implicit target = this.unit (UC6 style)  
  - `add(QuantityWeight other, WeightUnit targetUnit)`: explicit target (UC7 style)  
- Category type safety: `QuantityWeight` cannot equal `QuantityLength`  
- Added `demonstrateWeightFeatures()` in `QuantityMeasurementApp` showing equality, conversion, addition  
- New test class `WeightEqualityTest` covering equality, conversion, addition, validation, edge cases  
- Backward compatibility: length features (UC1–UC8) unaffected

## Preconditions
- `QuantityLength` / `LengthUnit` from UC8  
- Two or more `QuantityWeight` objects or raw values with weight units  
- All units are from `WeightUnit` enum (independent from length)

## Main Flow
1. Create `QuantityWeight` objects with value + unit  
2. For equality: call `.equals()` → compares via `toBaseUnit()` (kg)  
3. For conversion: call `.convertTo(targetUnit)` → delegates to `unit.convertToBaseUnit()` + `target.convertFromBaseUnit()`  
4. For addition: call `.add(other)` or `.add(other, targetUnit)` → sum in kg → convert to target unit  
5. Return new immutable `QuantityWeight` object

## Postconditions
- Correct equality, conversion, addition results for weight  
- Cross-unit operations accurate (kg ↔ g ↔ lb)  
- Length and weight are incomparable (type-safe rejection)  
- Immutability preserved  
- No regressions in length features (UC1–UC8)  
- Scalable pattern ready for new categories (temperature, volume, etc.)

## Key Concepts Learned / Tested in UC9
1. **Multiple Measurement Categories**  
   - Independent weight category mirrors length design  
2. **Scalable Generic Pattern**  
   - `WeightUnit` & `QuantityWeight` reuse UC8 pattern  
3. **Category Type Safety**  
   - Weight ≠ Length (class check in equals)  
4. **Base Unit Normalization**  
   - Kilogram as base → simplifies all weight operations  
5. **Conversion Factor Precision**  
   - Accurate factors (0.001 g, 0.453592 lb)  
6. **Immutability Across Categories**  
   - New objects returned for convert/add  
7. **Equals & HashCode Consistency**  
   - Proper override for collections  
8. **Method Overloading**  
   - add() supports implicit/explicit target unit  
9. **Floating-Point Consistency**  
   - EPSILON tolerance for cross-unit precision  
10. **Architectural Validation**  
    - Pattern scales to new categories without changes to length

## Key Concepts Tested
- Kilogram-to-kilogram same/different value equality  
- Gram-to-gram equality  
- Pound-to-pound equality  
- Cross-unit equality (kg ↔ g, kg ↔ lb, g ↔ lb)  
- Weight vs. length incompatibility  
- Conversion accuracy (all unit pairs)  
- Round-trip conversion preservation  
- Same-unit addition  
- Cross-unit addition (implicit & explicit target)  
- Commutativity with explicit target  
- Zero & negative value handling  
- Null/invalid input validation  
- Large/small magnitude operations  
- HashCode consistency with equals  
- All unit combination coverage  
- Precision tolerance after operations
