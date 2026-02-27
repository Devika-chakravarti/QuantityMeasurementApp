# Quantity Measurement App
**Use Case 11: Volume Measurement Equality, Conversion, and Addition (Litre, Millilitre, Gallon)**

## UC11 Overview
This use case extends the application to support a **third measurement category: volume**, operating independently from length and weight through the generic `Quantity<U extends IMeasurable>` class and `IMeasurable` interface established in UC10.

It introduces three volume units (LITRE as base, MILLILITRE, GALLON) and supports the full feature set:
- Equality comparison (cross-unit)
- Unit-to-unit conversion
- Addition (implicit & explicit target unit)

UC11 validates that the refactored generic architecture from UC10 truly scales — adding volume requires **only a new enum** (`VolumeUnit`) implementing `IMeasurable`. No changes to `Quantity<U>`, `QuantityMeasurementApp`, or existing tests are needed.

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
 │                      └── IMeasurable.java
 │                      └── VolumeUnit.java
 │                      └── Quantity.java
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
 │                        └── QuantityTest.java
 │                        └── VolumeTest.java
 ├── .gitignore
 |── pom.xml
 └── README.md

```

## Features Implemented in UC11
- Created standalone `VolumeUnit` enum implementing `IMeasurable`  
  - Base unit: LITRE (1.0)  
  - MILLILITRE: 0.001 (1 mL = 0.001 L)  
  - GALLON: 3.78541 (1 gal ≈ 3.78541 L)  
- No changes to `Quantity<U>` — generic class handles volume seamlessly  
- No changes to `QuantityMeasurementApp` — existing generic demo methods (`demonstrateEquality`, `demonstrateConversion`, `demonstrateAddition`) work with `VolumeUnit`  
- Added volume-specific demonstration in `main()` using generic methods  
- New test class `VolumeTest` covering equality, conversion, addition, validation, edge cases  
- Category type safety: `Quantity<VolumeUnit>` cannot equal `Quantity<LengthUnit>` or `Quantity<WeightUnit>`  
- Backward compatibility: length & weight features (UC1–UC10) unaffected

## Preconditions
- `Quantity<U extends IMeasurable>` class and `IMeasurable` interface from UC10  
- `LengthUnit` & `WeightUnit` enums implementing `IMeasurable`  
- Two or more `Quantity<VolumeUnit>` objects or raw values with volume units

## Main Flow
1. Create `Quantity<VolumeUnit>` objects with value + unit  
2. For equality: call `.equals()` → compares via `toBaseUnit()` (litre) + EPSILON  
3. For conversion: call `.convertTo(targetUnit)` → delegates to `unit.convertToBaseUnit()` + `target.convertFromBaseUnit()`  
4. For addition: call `.add(other)` or `.add(other, targetUnit)` → sum in litre → convert to target unit  
5. Return new immutable `Quantity<VolumeUnit>` object

## Postconditions
- Correct equality, conversion, addition results for volume  
- Cross-unit operations accurate (L ↔ mL ↔ gal)  
- Volume incomparable with length or weight (type-safe rejection)  
- Immutability preserved  
- No regressions in length & weight features (UC1–UC10)  
- Scalable pattern confirmed: new category added with **only enum creation**

## Key Concepts Learned / Tested in UC11
1. **Scalability of Generic Design**  
   - Third category added with **zero changes** to core classes  
2. **Pattern Replication**  
   - `VolumeUnit` mirrors `LengthUnit` & `WeightUnit` structure  
3. **Base Unit Normalization**  
   - Litre as base → simplifies all volume operations  
4. **Category Type Safety**  
   - Volume ≠ length/weight (class check in equals)  
5. **Conversion Factor Precision**  
   - Accurate factors (0.001 mL, 3.78541 gal)  
6. **Immutability Across Categories**  
   - New objects returned for convert/add  
7. **Equals & HashCode Consistency**  
   - Proper override for collections  
8. **Method Overloading**  
   - add() supports implicit/explicit target unit  
9. **Floating-Point Consistency**  
   - EPSILON tolerance for cross-unit precision  
10. **Architectural Validation**  
    - UC10 pattern scales linearly with categories

## Key Concepts Tested
- Litre-to-litre same/different value equality  
- Millilitre-to-millilitre equality  
- Gallon-to-gallon equality  
- Cross-unit equality (L ↔ mL, L ↔ gal, mL ↔ gal)  
- Volume vs. length/weight incompatibility  
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
