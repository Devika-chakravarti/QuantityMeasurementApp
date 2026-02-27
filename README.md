# Quantity Measurement App
**Use Case 7: Addition with Target Unit Specification**

## UC7 Overview
This use case extends UC6 by providing greater flexibility in addition operations:  
the caller can now **explicitly specify any supported unit** as the target unit for the result,  
instead of being limited to the unit of the first operand or implicit defaults.

This allows the result to be expressed in a completely different unit (e.g., add feet + inches → result in yards),  
making the API more versatile while maintaining immutability, commutativity, and mathematical accuracy.

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
 │                        └── TargetUnitSpecificationTest.java 
 ├── .gitignore
 |── pom.xml
 └── README.md

```

## Features Implemented in UC7
- Overloaded instance method: `QuantityLength.add(QuantityLength other, LengthUnit targetUnit)`  
  → Adds two quantities and returns new `QuantityLength` in the **explicitly specified** `targetUnit`  
- Backward-compatible overload: `add(QuantityLength other)` → defaults to unit of `this` (UC6 behavior)  
- Private utility method `addInBaseAndConvert()` → avoids code duplication (DRY principle)  
- Validation: non-null operands, non-null target unit, finite values  
- Immutability preserved: original objects unchanged  
- Commutative behavior supported: `a.add(b, u)` ≈ `b.add(a, u)` for any target unit  
- Updated `QuantityMeasurementApp` with `demonstrateTargetUnitAddition()` method showing flexible examples  
- Floating-point safety reused via `EPSILON` (from UC5)  
- Backward compatibility: all UC1–UC6 features (equality, conversion, previous addition) remain fully functional

## Preconditions
- `QuantityLength` class and `LengthUnit` enum from UC6  
- Two valid `QuantityLength` objects (same dimension: length)  
- Explicit target unit (any supported `LengthUnit`, can be same or different from operands)

## Main Flow
1. Call `firstQuantity.add(secondQuantity, targetUnit)`  
2. Validate: second quantity ≠ null, target unit ≠ null, values finite  
3. Convert both quantities to base unit (Feet): `this.toBaseUnit() + other.toBaseUnit()`  
4. Perform addition in base unit  
5. Convert sum back to target unit: `sumInFeet / targetUnit.toFeet(1.0)`  
6. Return new `QuantityLength(sumValue, targetUnit)`

## Postconditions
- New `QuantityLength` object with correct sum in the **explicitly specified target unit**  
- Original quantities remain unchanged  
- Result unit is always the caller-specified unit (not inferred from operands)  
- Addition is mathematically accurate (within floating-point limits)  
- Addition remains commutative regardless of target unit  
- Invalid inputs throw `IllegalArgumentException`

## Key Concepts Learned / Tested in UC7
1. **Method Overloading**  
   - Two `add()` signatures: implicit (UC6) and explicit target unit  
   - Maintains backward compatibility while adding flexibility  
2. **Private Utility Method (DRY Principle)**  
   - `addInBaseAndConvert()` centralizes conversion + addition logic  
   - Avoids code duplication across overloads  
3. **API Consistency & Caller Control**  
   - Explicit target unit gives full control → reduces ambiguity  
   - Supports diverse use cases (result in any unit)  
4. **Unit Independence in Arithmetic**  
   - Arithmetic performed in base unit → independent of operand units  
5. **Precision Across Scales**  
   - Conversion to very different units (e.g., feet → yards) handled correctly  
6. **Functional & Immutable Design**  
   - Pure method: same inputs → same output  
   - Always returns new object  
7. **Backward Compatibility**  
   - UC6-style addition still works unchanged  
8. **Defensive Programming**  
   - Strict null/finite checks on all parameters

## Key Concepts Tested
- Explicit target unit same as first operand  
- Explicit target unit same as second operand  
- Explicit target unit different from both operands  
- Target unit consistency (always matches specified unit)  
- Cross-scale target units (large → small, small → large)  
- Commutativity with explicit target unit  
- Zero value addition with target unit  
- Negative value addition with target unit  
- Null target unit validation  
- Large/small magnitude addition across units  
- Mathematical correctness for all unit combinations  
- Floating-point precision tolerance after addition & conversion
