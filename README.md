# Quantity Measurement App
**Use Case 12: Subtraction and Division Operations on Quantity Measurements**

## UC12 Overview
This use case extends the generic `Quantity<U extends IMeasurable>` class by adding two new arithmetic operations:  
- **Subtraction** — computes the difference between two quantities of the same category, returning a new `Quantity<U>` in implicit (first operand's unit) or explicit target unit  
- **Division** — computes the dimensionless ratio (scalar double) between two quantities of the same category

These operations follow the same design patterns as addition (UC6–UC7): cross-unit support, immutability, validation, explicit/implicit target unit, and category type safety.  
UC12 completes basic arithmetic support across all categories (length, weight, volume) while preserving full backward compatibility.

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
 │                        └── ArithmeticOperationTest.java
 ├── .gitignore
 |── pom.xml
 └── README.md

```

## Features Implemented in UC12
- Added `subtract(Quantity<U> other)` → implicit target = this.unit  
- Added `subtract(Quantity<U> other, U targetUnit)` → explicit target unit  
- Added `divide(Quantity<U> other)` → returns dimensionless double ratio  
- Internal logic: convert to base unit → perform operation → convert result to target unit (for subtract)  
- Validation: non-null operands, non-null target unit, finite values, same category, non-zero divisor  
- Immutability preserved: original objects unchanged  
- Non-commutative behavior respected (subtract & divide order matters)  
- Updated `QuantityMeasurementApp` with `demonstrateSubtraction()`, `demonstrateSubtractionWithTarget()`, `demonstrateDivision()` using generics  
- New test class `ArithmeticOperationTest` covering subtract & divide across all categories  
- Backward compatibility: all UC1–UC11 features (equality, conversion, addition) unaffected

## Preconditions
- `Quantity<U extends IMeasurable>` class and `IMeasurable` interface from UC10  
- `LengthUnit`, `WeightUnit`, `VolumeUnit` enums implementing `IMeasurable`  
- Two valid `Quantity<U>` objects of the same category  

## Main Flow (Subtraction)
1. Call `firstQuantity.subtract(secondQuantity)` or `subtract(secondQuantity, targetUnit)`  
2. Validate: second ≠ null, target ≠ null, values finite, same category  
3. Convert both to base unit: `this.toBaseUnit() - other.toBaseUnit()`  
4. Convert difference to target unit (implicit = this.unit or explicit)  
5. Return new `Quantity<U>(difference, targetUnit)`

## Main Flow (Division)
1. Call `firstQuantity.divide(secondQuantity)`  
2. Validate: second ≠ null, same category, second value ≠ 0  
3. Convert both to base unit: `this.toBaseUnit() / other.toBaseUnit()`  
4. Return dimensionless double ratio

## Postconditions
- Subtraction: new `Quantity<U>` with correct difference in specified unit  
- Division: double scalar ratio (dimensionless)  
- Original quantities unchanged  
- Non-commutative: order matters for both operations  
- Division by zero throws `ArithmeticException`  
- Cross-category operations prevented  
- All UC1–UC11 behavior preserved

## Key Concepts Learned / Tested in UC12
1. **Comprehensive Arithmetic Support**  
   - Full set: add, subtract, divide — all generic & cross-unit  
2. **Non-Commutative Operations**  
   - Subtract & divide depend on operand order  
3. **Division by Zero Handling**  
   - Explicit exception prevents invalid math  
4. **Target Unit Consistency**  
   - Same overloading pattern as addition  
5. **Immutability in Operations**  
   - Always new objects returned  
6. **Category Isolation**  
   - Type safety prevents invalid arithmetic  
7. **Precision & Rounding**  
   - Two-decimal rounding for subtract; raw double for divide  
8. **Backward Compatibility**  
   - Previous operations unchanged

## Key Concepts Tested
- Same-unit subtraction  
- Cross-unit subtraction (implicit & explicit target)  
- Subtraction negative/zero results  
- Subtraction non-commutativity  
- Division same/cross-unit  
- Division ratios (>1, <1, =1)  
- Division non-commutativity  
- Division by zero exception  
- Cross-category prevention  
- Null/invalid input validation  
- Large/small magnitude operations  
- Chained operations support  
- Precision tolerance after arithmetic  
- Integration with addition/equality/conversion  
- All categories (length, weight, volume)
