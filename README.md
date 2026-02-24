# Quantity Measurement App
**Use Case 5: Unit-to-Unit Conversion**

## UC5 Overview
This use case extends UC4 by adding explicit **unit-to-unit conversion** functionality for length measurements.

The main goal is to allow the client to convert any length value from one unit to another (e.g., feet -> inches, yards -> centimeters) and get the result as a numeric value in the target unit — while reusing the existing conversion factors from the `LengthUnit` enum.

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
 ├── .gitignore
 |── pom.xml
 └── README.md

```


## Features Implemented in UC5
- Added static method: `QuantityLength.convert(double value, LengthUnit source, LengthUnit target)`  
  → Converts raw numeric value from source unit to target unit  
- Added instance method: `QuantityLength.convertTo(LengthUnit targetUnit)`  
  -> Returns new `QuantityLength` object in the target unit  
- Input validation: checks finite value, non-null units  
- Conversion formula: normalize to base unit (Feet) -> then to target unit  
- Floating-point safety: `EPSILON = 1e-6` tolerance in `equals()` (inherited & reused)  
- Updated `QuantityMeasurementApp` with `demonstrateConversion()` method showing multiple examples  
- No changes needed in `LengthUnit` enum — reuses existing `toFeet()` logic  
- Backward compatibility: all UC1–UC4 equality features and tests remain valid

## Preconditions
- `QuantityLength` class and `LengthUnit` enum from UC4 (with FEET, INCH, YARDS, CENTIMETERS)  
- Input value (double) + source unit + target unit are provided

## Main Flow
1. Call `convert(value, sourceUnit, targetUnit)` or `quantity.convertTo(targetUnit)`  
2. Validate: value is finite, source & target units are non-null  
3. Convert source value to base unit (Feet): `source.toFeet(value)`  
4. Convert base value to target unit: `valueInFeet / target.toFeet(1.0)`  
5. Return converted double value (static method) or new `QuantityLength` (instance method)

## Postconditions
- Accurate numeric value returned in the **target unit**  
- Mathematical equivalence preserved (within floating-point precision)  
- Invalid inputs (NaN, Infinity, null unit) throw `IllegalArgumentException`  
- Original objects remain unchanged (immutability maintained)

## Key Concepts Learned / Tested in UC5
1. **Centralized & Reusable Conversion Logic**  
   - All conversions use the same enum factors → no duplication  
2. **Static vs Instance Methods**  
   - Static `convert()` for quick raw conversions  
   - Instance `convertTo()` for object-oriented chaining  
3. **Floating-Point Precision Handling**  
   - `EPSILON` tolerance ensures reliable equality after conversion  
4. **Input Validation & Defensive Design**  
   - Rejects invalid values/units early with clear exceptions  
5. **API Flexibility**  
   - Two ways to convert -> suits different use cases  
6. **Immutability & Value Object Pattern**  
   - `convertTo()` returns new object — no side effects  
7. **Backward Compatibility**  
   - All previous equality checks (UC1–UC4) continue to work perfectly

## Key Concepts Tested
- Basic conversions (feet <-> inches, yards <-> feet)  
- Cross-unit conversions (yards <-> inches, cm <-> feet)  
- Round-trip accuracy (convert to target -> back -> ≈ original)  
- Zero, negative, large, small value conversions  
- Same-unit conversion returns unchanged value  
- Invalid inputs (null unit, NaN, Infinity) throw exceptions  
- Precision tolerance in comparisons after conversion  
- Mathematical consistency across all unit pairs
