# Quantity Measurement App
**Use Case 6: Addition of Two Length Units (Same Category)**

## UC6 Overview
This use case extends UC5 by adding support for **addition of two length measurements** (possibly in different units, but same category: length).

The result is returned as a new `QuantityLength` object in the **specified target unit** (often the unit of the first operand), with internal conversion to/from the base unit (Feet) to ensure accurate arithmetic.

The key goal is to demonstrate domain-specific arithmetic operations on immutable value objects while reusing the conversion infrastructure from previous use cases.

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
 │                        └── AdditionTest.java  
 ├── .gitignore
 |── pom.xml
 └── README.md

```

## Features Implemented in UC6
- Added instance method: `QuantityLength.add(QuantityLength other, LengthUnit resultUnit)`  
  → Adds two quantities and returns new `QuantityLength` in `resultUnit`  
- Internal logic: convert both to base unit (Feet) → add → convert sum to target unit  
- Validation: non-null operands, non-null result unit, finite values  
- Immutability preserved: original objects unchanged  
- Commutative behavior supported: `a.add(b, u)` ≈ `b.add(a, u)`  
- Updated `QuantityMeasurementApp` with `demonstrateAddition()` method showing examples  
- Floating-point safety reused via `EPSILON` (from UC5)  
- Backward compatibility: all UC1–UC5 features (equality & conversion) remain fully functional

## Preconditions
- `QuantityLength` class and `LengthUnit` enum from UC5  
- Two valid `QuantityLength` objects (same dimension: length)  
- Desired result unit (any supported `LengthUnit`)

## Main Flow
1. Call `firstQuantity.add(secondQuantity, resultUnit)`  
2. Validate: second quantity ≠ null, result unit ≠ null, values finite  
3. Convert both quantities to base unit (Feet): `this.toBaseUnit() + other.toBaseUnit()`  
4. Perform addition in base unit  
5. Convert sum back to result unit: `sumInFeet / resultUnit.toFeet(1.0)`  
6. Return new `QuantityLength(sumValue, resultUnit)`

## Postconditions
- New `QuantityLength` object with correct sum in specified unit  
- Original quantities remain unchanged  
- Result is mathematically accurate (within floating-point limits)  
- Addition is commutative (order of operands does not affect sum)  
- Invalid inputs throw appropriate exceptions

## Key Concepts Learned / Tested in UC6
1. **Arithmetic Operations on Value Objects**  
   - Domain logic (addition) encapsulated in immutable class  
2. **Reusability of Conversion Infrastructure**  
   - Leverages UC5 base-unit conversion logic → no duplication  
3. **Normalization for Arithmetic**  
   - Base unit (Feet) simplifies addition of heterogeneous units  
4. **Immutability & Factory Pattern**  
   - `add()` acts as factory → always returns new object  
5. **Commutativity Property**  
   - Mathematical law preserved: a + b = b + a  
6. **Zero & Negative Value Handling**  
   - Zero acts as identity element  
   - Negative measurements supported correctly  
7. **Type Safety & Defensive Programming**  
   - Null & finite checks prevent runtime surprises  
8. **Backward Compatibility**  
   - All previous equality and conversion features continue to work

## Key Concepts Tested
- Same-unit addition (feet + feet, inch + inch)  
- Cross-unit addition (feet + inch → feet or inch result)  
- Commutativity (order independence)  
- Zero value addition (identity element)  
- Negative value addition  
- Large/small magnitude addition  
- Null operand validation  
- Result always expressed in requested unit  
- Floating-point accuracy after addition & conversion
