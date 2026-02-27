# Quantity Measurement App
**Use Case 10: Generic Quantity Class with Unit Interface for Multi-Category Support**

## UC10 Overview
This use case refactors the entire architecture from UC1–UC9 into a **single generic Quantity<U extends IMeasurable>** class that works uniformly across all measurement categories (length, weight, and future ones).

It eliminates code duplication between `QuantityLength` and `QuantityWeight`, consolidates unit enum patterns through a common `IMeasurable` interface, and simplifies `QuantityMeasurementApp` by replacing category-specific methods with generic ones — fully adhering to DRY and Single Responsibility Principle.

The refactoring preserves **all functionality** from UC1–UC9 with **zero changes to public API** or client code, while establishing a scalable, maintainable foundation for adding new categories (volume, temperature, etc.) with minimal effort.

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
 ├── .gitignore
 |── pom.xml
 └── README.md

```

## Features Implemented in UC10
- Created `IMeasurable` interface with standard unit methods:  
  - `getConversionFactor()`  
  - `convertToBaseUnit(double)`  
  - `convertFromBaseUnit(double)`  
  - `getUnitName()`  
- Refactored `LengthUnit` and `WeightUnit` enums to implement `IMeasurable`  
- Replaced `QuantityLength` and `QuantityWeight` with single generic `Quantity<U extends IMeasurable>`  
  - Immutable (private final value & unit)  
  - Constructor validation (finite value, non-null unit)  
  - `equals()`: compares via `toBaseUnit()` + EPSILON, rejects cross-category via `unit.getClass()`  
  - `convertTo(U targetUnit)`: delegates to unit methods  
  - `add(Quantity<U> other)`: implicit target = this.unit  
  - `add(Quantity<U> other, U targetUnit)`: explicit target  
  - `toString()`, `hashCode()` consistent  
- Simplified `QuantityMeasurementApp`:  
  - Replaced category-specific demos with generic `<U extends IMeasurable>` methods  
  - Single `demonstrateEquality()`, `demonstrateConversion()`, `demonstrateAddition()` handle all categories  
- Unified test class `QuantityTest` covers generic behavior for length & weight  
- Backward compatibility: all UC1–UC9 tests pass unchanged

## Preconditions
- All functionality from UC1–UC9 operational  
- `IMeasurable` interface defined  
- `LengthUnit` & `WeightUnit` implement `IMeasurable`  
- `Quantity<U>` replaces category-specific classes

## Main Flow
1. Define `IMeasurable` interface with conversion contract  
2. Update `LengthUnit` & `WeightUnit` to implement `IMeasurable`  
3. Create generic `Quantity<U extends IMeasurable>`  
   - Delegate conversion/arithmetic to `unit`  
   - Enforce category safety in `equals()`  
4. Refactor `QuantityMeasurementApp` to use generic methods  
5. Run all previous tests → confirm no regressions

## Postconditions
- Single `Quantity<U>` class handles all categories  
- No duplication between length & weight logic  
- `QuantityMeasurementApp` simplified (fewer methods)  
- DRY & SRP fully upheld  
- All UC1–UC9 behavior preserved identically  
- Adding new category (e.g., TemperatureUnit) requires only:  
  - New enum implementing `IMeasurable`  
  - No changes to `Quantity<U>` or app class

## Key Concepts Learned / Tested in UC10
1. **Generic Programming**  
   - `<U extends IMeasurable>` enforces type-safe category handling  
2. **Interface-Based Design**  
   - `IMeasurable` standardizes unit behavior  
3. **Single Responsibility Principle**  
   - Unit enums: conversion only  
   - Quantity<U>: value operations only  
   - App: orchestration only  
4. **DRY Principle**  
   - One implementation for all categories  
5. **Liskov Substitution & Open-Closed**  
   - New enums plug in without modifying existing code  
6. **Polymorphism & Delegation**  
   - Quantity delegates to unit → clean & extensible  
7. **Cross-Category Safety**  
   - `equals()` rejects different categories  
8. **Backward Compatibility**  
   - UC1–UC9 tests pass unchanged

## Key Concepts Tested
- `IMeasurable` implementation by LengthUnit & WeightUnit  
- Generic `Quantity<U>` equality (length & weight)  
- Generic conversion accuracy  
- Generic addition (implicit & explicit target)  
- Cross-category prevention (length ≠ weight)  
- Backward compatibility (all prior tests pass)  
- Generic demo methods handle all categories  
- Null/invalid input validation  
- Round-trip conversion preservation  
- Scalability (new category integration)  
- Type erasure safety & wildcard usage  
- HashCode consistency  
- Precision tolerance across categories
